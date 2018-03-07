package utils;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class Utils {

    private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    public static InputStream stringToInputString(String string){
        if (isEmptyString(string)) return null;
        return new ByteArrayInputStream(string.getBytes());
    }

    public static boolean isEmptyString(final String string){
        return string == null || string.length() == 0;
    }

    public static List<Node> getNodesByName(NodeList list, String name){
        if (list == null || list.getLength() == 0) return Collections.emptyList();
        int size = list.getLength();
        final List<Node> nodes = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Node node = list.item(i);
            String nodeName = node.getNodeName();
            if (name.equals(nodeName)) {
                nodes.add(node);
            }
        }
        return nodes;
    }

    public static Node getChildNodeByName(final Node node, String name){
        if (node == null) return null;
        NodeList list = node.getChildNodes();
        if (list == null || list.getLength() == 0) return null;
        int size = list.getLength();
        for (int i = 0; i < size; i++) {
            Node childNode = list.item(i);
            String nodeName = childNode.getNodeName();
            if (name.equals(nodeName)) {
                return childNode;
            }
        }
        return null;
    }

    public static String nodeToString(Node node){
        if (node == null) return "";
        return node.getTextContent();
    }

    public static Date nodeToDate(Node node){
        String str = nodeToString(node);
        try {
            return sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Map<String, Date> sortAndCut(Map<String, Date> map, int limit) {
        if (map == null || map.size() == 0) return Collections.emptyMap();

        return map.entrySet().stream()
                .sorted(Map.Entry.<String, Date> comparingByValue().reversed())
                .limit(limit)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (v1, v2) -> v1,
                        LinkedHashMap::new));
    }
}
