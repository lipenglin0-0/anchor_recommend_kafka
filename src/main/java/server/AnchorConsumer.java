package server;

import static beans.AnchorList.*;

import constants.Config;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.Utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Duration;
import java.util.Collections;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
    ExecutorService:
      1. execute -> runnable or callable
      2. shutdown -> no new task will be accepted
   */
  /*
    1. 提交的offset 小于最后提交的offset，区间的消息会被重复消费
    2. 消费的offset 大于最后提交的offset，区间的消息会被忽略
   */
  private static Logger logger = LoggerFactory.getLogger(AnchorConsumer.class);

  public static void main(String[] args) {
    ExecutorService consumerPool = Executors.newFixedThreadPool(Config.PARTITION_SUM);
    for (int i = 0; i < Config.PARTITION_SUM; i++) {
      consumerPool.execute(AnchorConsumer::run);
    }
    consumerPool.shutdown();
  }

  private static void saveRecord(String record, String consumer) {
    Path path = Paths.get(Config.PANDA_CONSUMER);
    try {
      if (!Files.exists(path)) {
        Files.createDirectories(path);
      }
      Path file = Paths.get(Config.PANDA_CONSUMER + consumer + ".dat");
      Files.write(file, (record + "\n").getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void run() {
    KafkaConsumer<String, Anchor> consumer = new KafkaConsumer<>(Utils.loader.getProps(Config.CONSUMER_PROPERTIES));
    consumer.subscribe(Collections.singletonList(Config.TOPIC_NAME));
    while (true) {
      ConsumerRecords<String, Anchor> records = consumer.poll(Duration.ofSeconds(Config.TIMEOUT_SECONDS));
      for (ConsumerRecord<String, Anchor> record : records) {
        logger.info(Utils.recordToString(record));
        saveRecord(Utils.recordToString(record), Thread.currentThread().getName());
      }
    }
  }
}
