import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HtmlParser {
    public StationIndex parse(String path) {
        String htmlcode = parseFile(path);
        Document doc = Jsoup.parse(htmlcode);
        Elements elements = doc.select("span");
        ArrayList<String> lines = new ArrayList<>();
        ArrayList<String> stations = new ArrayList<>();
        ArrayList<String> parentsTemp = new ArrayList<>();
        elements.forEach(element -> {
            String line = element.getElementsByAttribute("data-line").text();
            String station = element.getElementsByAttributeValue("class", "name").text();
            Element parent = element.parent();
            if (!line.equals(""))
                lines.add(line);
            if (!station.equals("")) {
                stations.add(station);
                if (!parentsTemp.contains(parent.parent().parent().parent().parent().getElementsByAttribute("data-line").text()))
                    parentsTemp.add(parent.parent().parent().parent().parent().getElementsByAttribute("data-line").text());
            }
        });
        String[] parents = parentsTemp.get(0).split("(\\d+\\.)");
        HashMap<Integer, ArrayList<String>> stationsMap = new HashMap<>();
        Integer curLine = 0;
        for (int i = 0; i < parents.length; i++) {
            if (parents[i].contains("линия") | parents[i].contains("Кольцо") | parents[i].contains("МЦД") | parents[i].contains("Линия")) {
                if (i > 1) {
                    String tmp = (parents[i].split("(\\s)"))[1].trim();
                    stationsMap.get(curLine).add(tmp);
                }
                curLine++;
                stationsMap.put(curLine, new ArrayList<>());
            } else {
                stationsMap.get(curLine).add(parents[i].trim());
            }
        }
        int counter = 0;
        ArrayList<Line> linesList = new ArrayList<>();
        for (String line : lines) {
            counter++;
            linesList.add(new Line(counter, line));
        }
        return new StationIndex(stationsMap, linesList);
    }

    private String parseFile(String path) {
        StringBuilder builder = new StringBuilder();
        try {
            List<String> lines = Files.readAllLines(Paths.get(path));
            lines.forEach(builder::append);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return builder.toString();
    }
}
