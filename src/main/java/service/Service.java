package service;

import java.util.Date;
import java.util.Map;

public interface Service {

    Map<String, Date> getUrls(String urlString) throws Exception;
}
