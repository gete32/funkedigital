package service;

import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import utils.Utils;

import java.util.*;

@DisplayName("Parser Service Test")
class ServiceTest {

    private static final int DEFAULT_LIMIT = 50;
    private static final String ABENDBLATT = "https://www.abendblatt.de/sitemaps/news.xml";
    private static final String MORGENPOST = "https://www.morgenpost.de/sitemaps/news.xml";
    private static final List<String> DISALLOWED = Arrays.asList("img", "div", "table");

    private static Map<String, Set<String>> urlMap;
    private static Service service;

    private Map<String, Set<String>> initMap(final String name) throws Exception {
        if (urlMap == null) urlMap = new HashMap<>();
        if (urlMap.get(name) == null) {
            Map<String, Date> dateUrls1 = service.getUrls(name);
            urlMap.put(name, Utils.sortAndCut(dateUrls1, DEFAULT_LIMIT).keySet());
        }
        return urlMap;
    }

    @BeforeAll
    static void init(){
        service = new ServiceImpl();
    }

    @DisplayName("Testing <p>")
    @ParameterizedTest
    @ValueSource(strings = {ABENDBLATT})
    void testP(String name) throws Exception {
        initMap(name);
        Set<String> urls = urlMap.get(name);
        urls.stream().forEach(e -> {
            Document document = new Document(e);
        });
    }

    @DisplayName("Testing <h1>")
    @ParameterizedTest
    @ValueSource(strings = {ABENDBLATT})
    void testH(String name) throws Exception {
        initMap(name);
        Set<String> urls = urlMap.get(name);
        urls.stream().forEach(e -> {
            Document document = new Document(e);
        });
    }

    @DisplayName("Testing <p>")
    @ParameterizedTest
    @ValueSource(strings = {ABENDBLATT})
    void testPdisallowed(String name) throws Exception {
        initMap(name);
        Set<String> urls = urlMap.get(name);
        urls.stream().forEach(e -> {
            Document document = new Document(e);
        });
    }

}
