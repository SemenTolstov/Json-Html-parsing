public class Main {
    public static void main(String[] args) {
        HtmlParser htmlParser = new HtmlParser();
        JsonParser.createJsonFile(htmlParser.parse("data/code.html"));
        JsonParser.readJsonFile("data/mapStation.json");

    }
}
