package components;

import constants.Config;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import utils.Utils;

import java.time.Duration;
import java.util.Collections;
import java.util.Deque;

public class AnchorConsumer {

  /*
    1. 多个消费者，属于相同消费组，订阅相同topic，消费不同partition
    2. 消费者数量要小于partition 数量
    3. 不同消费组，订阅相同topic，会同时消费消息
   */
  /*
    分区再平衡
    1. 新消费者去消费老消费者消费的 partition
    2. 消费者宕机，老消费者消费他的 partition
   */
  /*
    properties:
    bootstrap.servers
    group.id
    key.deserializer
    value.deserializer
   */
  /*
    新消费者第一次调用poll 时，会找GroupCoordinator，加入到消费组，与分配的 partition连接
  */
  /*
    ExecutorService
   */
  /*
    1. 提交的offset 小于最后提交的offset，区间的消息会被重复消费
    2. 消费的offset 大于最后提交的offset，区间的消息会被忽略
   */
  public static void main(String[] args) {
    KafkaConsumer<String, String> consumer = new KafkaConsumer<>(Utils.loader.getProps(Config.CONSUMER_PROPERTIES));
    consumer.subscribe(Collections.singletonList(Config.TOPIC_NAME));
    while (true) {
      ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(Config.TIMEOUT_SECONDS));
      for (ConsumerRecord<String, String> record : records) {
        System.out.println(Utils.recordToString(record));
      }
    }
  }
}
