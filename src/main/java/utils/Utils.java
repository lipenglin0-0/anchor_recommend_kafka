package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.io.IOException;

public class Utils {
  public static PropertiesLoader loader = new PropertiesLoader();

  private static ObjectMapper mapper = new ObjectMapper();

  public static String recordToString(ConsumerRecord record) {
    return "topic: " + record.topic() + "/ partition: " + record.partition()
        + "/ offset: " + record.offset() + "/ key: " + record.key()
        + "/ value -> " + record.value();
  }

  public static <T> T jsonLoad(String json, Class<T> jClass) {
    try {
      return mapper.readValue(json, jClass);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static byte[] jsonDump(Object JObj) {
    try {
      return mapper.writeValueAsBytes(JObj);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return null;
  }
}
