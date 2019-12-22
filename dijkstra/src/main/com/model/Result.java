package main.com.model;


import java.util.ArrayList;
import java.util.List;

public class Result {
    private Station start;
    private Station end;
    private Double distance = 0.0D;
    private List<Station> passStations = new ArrayList<>();
    public Station getStar() {

        return start;
    }
    public void setStar(Station start) {

        this.start = start;
    }
    public Station getEnd() {

        return end;
    }
    public void setEnd(Station end) {

        this.end = end;
    }
    public Double getDistance() {

        return distance;
    }
    public void setDistance(Double distance) {

        this.distance = distance;
    }
    public List<Station> getPassStations() {

        return passStations;
    }
    public void setPassStations(List<Station> passStations) {

        this.passStations = passStations;
    }
    public Result(Station star, Station end, Double distance) {
        this.start = star;
        this.end = end;
        this.distance = distance;
    }
    public Result() {

    }

    @Override
    public String toString() {
        return "Result{" +
                "star=" + start +
                ", end=" + end +
                ", distance=" + distance +
                ", passStations=" + passStations +
                '}';
    }
}
