package collector;

import beans.AnchorList;
import constants.Config;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import utils.Utils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

public class Crawler {
  private URIBuilder uriBuilder;
  private CloseableHttpClient client;
  CloseableHttpResponse response;

  public Crawler() {

    try {
      uriBuilder = new URIBuilder(Config.URL_SEED);
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
    uriBuilder.addParameter(Config.PAGE_NO, Config.DEFAULT_PAGE_NO);
    uriBuilder.addParameter(Config.PAGE_NUM, Config.DEFAULT_PAGE_NUM);
    client = HttpClients.createDefault();
  }

  private String get(URI uri) {
    HttpGet request = new HttpGet(uri);
    try {
      response = client.execute(request);
      int statusCode = response.getStatusLine().getStatusCode();
      if (statusCode == HttpStatus.SC_OK) {
        HttpEntity entity = response.getEntity();
        return EntityUtils.toString(entity, StandardCharsets.UTF_8);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  public AnchorList getAnchorListByPageNo(Integer pageNo) {
    uriBuilder.setParameter(Config.PAGE_NO, String.valueOf(pageNo));
    try {
      String json = get(uriBuilder.build());
      return Utils.jsonLoad(json, AnchorList.class);
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
    return null;
  }

  public void shutdown() {
    try {
      response.close();
      client.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
