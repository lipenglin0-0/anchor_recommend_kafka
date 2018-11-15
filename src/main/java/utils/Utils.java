package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.io.IOException;

public class Utils {
  public static PropertiesLoader loader = new PropertiesLoader();

  private ObjectMapper mapper = new ObjectMapper();

  public static String recordToString(ConsumerRecord record) {
    return "topic: " + record.topic() + "/ partition: " + record.partition()
        + "/ offset: " + record.offset() + "/ key: " + record.key()
        + "/ value: " + record.value();
  }

  public <T> T jsonLoad(String json, Class<T> jClass) {
    try {
      return mapper.readValue(json, jClass);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }
}
