package components;

import org.apache.kafka.common.serialization.Serializer;
import utils.Utils;

import java.util.Map;

public class AnchorSerializer implements Serializer {
  @Override public void configure(Map configs, boolean isKey) {

  }

  @Override public byte[] serialize(String topic, Object data) {
    return Utils.jsonDump(data);
  }

  @Override public void close() {

  }
}
