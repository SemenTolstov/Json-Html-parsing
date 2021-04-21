import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Map;

public class JsonParser {
    public static void createJsonFile(StationIndex index) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter("data/mapStation.json")) {
            gson.toJson(index, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean readJsonFile(String path) {
        Gson gson = new Gson();
        try {
            Reader reader = new FileReader(path);
            StationIndex stationIndex = gson.fromJson(reader, StationIndex.class);
            for (Map.Entry<Integer, ArrayList<String>> entry : stationIndex.stations.entrySet()) {
                System.out.println(entry.getKey() + " линия: " + entry.getValue().size() + " станций.");

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
