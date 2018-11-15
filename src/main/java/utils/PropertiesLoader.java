package utils;

import java.io.IOException;
import java.util.Properties;

public class PropertiesLoader {
  public Properties getProps(String properties) {
    Properties props = new Properties();
    try {
      props.load(this.getClass().getResourceAsStream(properties));
      return props;
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }
}
