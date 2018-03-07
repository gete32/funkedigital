package service;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import utils.Utils;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiceImpl implements Service {

    private String URL_KEY = "url";
    private String LOC_KEY = "loc";
    private String NEWS_NEWS_KEY = "news:news";
    private String NEWS_PUBLICATION_DATE_KEY = "news:publication_date";

    private Document parseXML(InputStream content) throws Exception {
        DocumentBuilderFactory objDocumentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder objDocumentBuilder = objDocumentBuilderFactory.newDocumentBuilder();
        return objDocumentBuilder.parse(content);
    }

    private HttpURLConnection getConnection(final String url) throws IOException {
        URL myUrl = new URL(url);
        return (HttpsURLConnection) myUrl.openConnection();
    }

    private String getContent(HttpsURLConnection connection){
        final StringBuilder sb = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            while ((inputLine = bufferedReader.readLine()) != null) {
                sb.append(inputLine);
            }
        } catch (IOException e) {
            connection.disconnect();
        }
        return sb.toString();
    }

    @Override
    public Map<String, Date> getUrls(String urlString) throws Exception {
        Map<String, Date> dateUrlMap = new HashMap<>();
        HttpsURLConnection connection = (HttpsURLConnection) getConnection(urlString);
        InputStream is = Utils.stringToInputString(getContent(connection));
        Document doc = parseXML(is);
        NodeList nodeList = doc.getElementsByTagName(URL_KEY);
        List<Node> nodes = Utils.getNodesByName(nodeList, URL_KEY);
        for (Node node : nodes) {
            Node urlNode = Utils.getChildNodeByName(node, LOC_KEY);
            Node newsNode = Utils.getChildNodeByName(node, NEWS_NEWS_KEY);
            Node newsPublicationDateNode = Utils.getChildNodeByName(newsNode, NEWS_PUBLICATION_DATE_KEY);
            dateUrlMap.putIfAbsent(Utils.nodeToString(urlNode), Utils.nodeToDate(newsPublicationDateNode));
        }
        return dateUrlMap;
    }


}
