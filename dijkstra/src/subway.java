import main.com.core.DijkstraUtil;
import main.com.data.DistanceBuilder;
import main.com.model.Result;
import main.com.model.Station;

import java.io.File;
import java.io.IOException;

public class subway {
    public static void main(String[] args) throws IOException {

        switch (args[0]){
            case "-map":
                if(args.length==2){
                    DistanceBuilder.FILE_PATH = System.getProperty("user.dir") + File.separator + "\\" + args[1];
                    DistanceBuilder.readSubway();
                    System.out.println("成功读取subway.txt文件");
                }else{

                }
                break;

            case "-a":
                //-a 1号线 -map subway.txt -o station.txt
                if(args.length==6){
                    DistanceBuilder.FILE_PATH = System.getProperty("user.dir") + File.separator + "\\" + args[3];
                    DistanceBuilder.WRITE_PATH = System.getProperty("user.dir") + File.separator + "\\" + args[5];
                    DistanceBuilder.readSubway();
                    DistanceBuilder.writeLineData(args[1]);
                    System.out.println("已将结果写入station.txt文件");
                }else{

                }
                break;
            case "-b":
                if(args.length==7){
                    DistanceBuilder.FILE_PATH = System.getProperty("user.dir") + File.separator + "\\" + args[4];
                    DistanceBuilder.WRITE_PATH = System.getProperty("user.dir") + File.separator + "\\" + args[6];
                    DistanceBuilder.readSubway();
                    Result result = DijkstraUtil.calculate(new Station(args[1]), new Station(args[2]));
                    DistanceBuilder.writePassStationLine(result);
                    System.out.println("已将结果写入routine. txt文件");
                }else{
                }
                break;
        }
    }
}
