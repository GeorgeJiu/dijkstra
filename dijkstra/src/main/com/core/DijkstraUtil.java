package main.com.core;

import main.com.data.DistanceBuilder;
import main.com.model.Result;
import main.com.model.Station;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class DijkstraUtil {

    private static HashMap<Station, Result> Map1 = new HashMap<>();
    private static List<Station> List1 = new ArrayList<>();
    public static Result calculate(Station start, Station end) {
        if (!List1.contains(start)) {
            List1.add(start);
        }
        if (start.equals(end)) {
            Result result = new Result();
            
            result.setDistance(0.0D);
            result.setEnd(start);
            result.setStar(start);
            return Map1.put(start, result);
        }
        if (Map1.isEmpty()) {
            List<Station> linkStations = getLinkStations(start);
            for (Station station : linkStations) {
                Result result = new Result();
                result.setStar(start);
                result.setEnd(station);
                String key = start.getName() + ":" + station.getName();
                Double distance = DistanceBuilder.getDistance(key);
                result.setDistance(distance);
                result.getPassStations().add(station);
                Map1.put(station, result);
            }
        }
        Station parent = getNextStation();
        if (parent == null) {
            Result result = new Result();
            result.setDistance(0.0D);
            result.setStar(start);
            result.setEnd(end);
            return Map1.put(end, result);
        }
        if (parent.equals(end)) {
            return Map1.get(parent);
        }
        List<Station> childLinkStations = getLinkStations(parent);
        for (Station child : childLinkStations) {
            if (List1.contains(child)) {
                continue;
            }
            String key = parent.getName() + ":" + child.getName();
            Double distance;
            distance = DistanceBuilder.getDistance(key);
            DistanceBuilder.getDistance(key);
            if (parent.getName().equals(child.getName())) {
                distance = 0.0D;
            }
            Double parentDistance = Map1.get(parent).getDistance();
            distance = doubleAdd(distance, parentDistance);
            List<Station> parentPassStations = Map1.get(parent).getPassStations();
            Result childResult = Map1.get(child);
            if (childResult != null) {
                if (childResult.getDistance() > distance) {
                    childResult.setDistance(distance);
                    childResult.getPassStations().clear();
                    childResult.getPassStations().addAll(parentPassStations);
                    childResult.getPassStations().add(child);
                }
            } else {
                childResult = new Result();
                childResult.setDistance(distance);
                childResult.setStar(start);
                childResult.setEnd(child);
                childResult.getPassStations().addAll(parentPassStations);
                childResult.getPassStations().add(child);
            }
            Map1.put(child, childResult);
        }
        List1.add(parent);
        return calculate(start, end);
    }

    public static List<Station> getLinkStations(Station station) {
        List<Station> linkedStaions = new ArrayList<Station>();

       for (List<Station> line : DistanceBuilder.lineSet) {
            for (int i = 0; i < line.size(); i++) {
                if (station.equals(line.get(i))) {
                    if (i == 0) {
                        linkedStaions.add(line.get(i + 1));
                    } else if (i == (line.size() - 1)) {
                        linkedStaions.add(line.get(i - 1));
                    } else {
                        linkedStaions.add(line.get(i + 1));
                        linkedStaions.add(line.get(i - 1));
                    }
                }
            }
        }
        return linkedStaions;
    }

    private static Station getNextStation() {
        Double min = Double.MAX_VALUE;
        Station rets = null;
        Set<Station> stations = Map1.keySet();
        for (Station station : stations) {
            if (List1.contains(station)) {
                continue;
            }
            Result result = Map1.get(station);
            if (result.getDistance() < min) {
                min = result.getDistance();
                rets = result.getEnd();
            }
        }
        return rets;
    }

    private static double doubleAdd(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }

    public static void getResultToText(String filePath) throws IOException {
        BufferedWriter writer = null;
        writer = new BufferedWriter(new FileWriter(filePath, true));
        Set<List<Station>> lineSet = DistanceBuilder.lineSet;
        for (List<Station> stations : lineSet) {
            for (Station station : stations) {
                for (List<Station> stations2 : lineSet) {
                    for (Station stationTarget : stations2) {
                       DijkstraUtil dijkstraUtil = new DijkstraUtil();
                        Result result = dijkstraUtil.calculate(station, stationTarget);
                        Map1 = new HashMap<>();
                        List1 = new ArrayList<>();
                        for (Station s : result.getPassStations()) {
                            if (s.getName().equals(stationTarget.getName())) {
                                String text = station.getName() + "\t" + stationTarget.getName() + "\t" + result.getPassStations().size() + "\t" + result.getDistance() + "\t";
                                for (Station test : result.getPassStations()) {
                                //    LOGGER.info(test.getName() + ",");
                                    text = text + test.getName() + ",";
                                }
                            /*    LOGGER.info("{}" , text);
                                LOGGER.info("↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑\n");*/
                                writer.write(text);
                                writer.newLine();
                            }
                        }
                    }

                }
            }
        }
        writer.flush();
        writer.close();
    }
}
