package components;

import constants.Config;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import utils.Utils;

import java.util.Arrays;

public class AnchorProducer {

  public static final String PRODUCER_PROPERTIES = "/producer.properties";

  public static void main(String []args) {
    KafkaProducer<String, Object> producer =
        new KafkaProducer<>(Utils.loader.getProps(PRODUCER_PROPERTIES));
    for (int i : Arrays.asList(1, 2, 3, 4, 5)) {
      producer.send(new ProducerRecord<>(Config.TOPIC_NAME, String.valueOf(i)));
    }
    producer.close();
  }
}
