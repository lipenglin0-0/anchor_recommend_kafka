package components;

import static beans.AnchorList.*;

import beans.AnchorList;
import org.apache.kafka.common.serialization.Deserializer;
import utils.Utils;

import java.util.Map;

public class AnchorDeserializer implements Deserializer {
  @Override public void configure(Map configs, boolean isKey) {

  }

  @Override public Object deserialize(String topic, byte[] data) {
    return Utils.jsonLoad(new String(data), Anchor.class);
  }

  @Override public void close() {

  }
}
