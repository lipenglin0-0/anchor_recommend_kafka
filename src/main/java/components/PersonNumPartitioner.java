package components;

import constants.Config;
import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

import java.util.Map;

public class PersonNumPartitioner implements Partitioner {

  @Override
  public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
    return Integer.valueOf(String.valueOf(key)) > Config.THRESHOLD_PERSON_NUM ? 0 : 1;
  }

  @Override public void close() {

  }

  @Override public void configure(Map<String, ?> configs) {

  }
}
