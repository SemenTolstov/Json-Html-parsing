import java.util.ArrayList;
import java.util.HashMap;

public class StationIndex {
    HashMap<Integer, ArrayList<String>> stations;
    ArrayList<Line> lines;

    public StationIndex(HashMap<Integer, ArrayList<String>> stations, ArrayList<Line> lines) {
        this.stations = stations;
        this.lines = lines;
    }


    @Override
    public String toString() {
        return "StationIndex{" +
                "stations=" + stations +
                ", lines=" + lines +
                '}';
    }
}
