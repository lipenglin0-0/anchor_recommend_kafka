package server;

import beans.AnchorList;
import beans.AnchorList.Anchor;
import collector.Crawler;
import constants.Config;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.Utils;

public class AnchorProducer {
  private static Logger logger = LoggerFactory.getLogger(AnchorProducer.class);

  public static void main(String[] args) {
    try (KafkaProducer<String, Anchor> producer = new KafkaProducer<>(
        Utils.loader.getProps(Config.PRODUCER_PROPERTIES))) {
      sendMsgByCrawler(producer, new Crawler());
    }
  }

  private static void sendMsgByCrawler(KafkaProducer<String, Anchor> producer, Crawler crawler) {
    try {
      for (int i = 1; i < Integer.MAX_VALUE; i++) {
        AnchorList anchorList = crawler.getAnchorListByPageNo(i);
        if (anchorList.getData().isEmpty()) {
          break;
        }
        sendRecordToCluster(producer, anchorList);
        logger.info("page: " + i + "/ sum: " + anchorList.getData().size());
      }
    } finally {
      crawler.shutdown();
    }

  }

  private static void sendRecordToCluster(KafkaProducer<String, Anchor> producer, AnchorList anchorList) {
    for (Anchor anchor : anchorList.getData()) {
      producer.send(new ProducerRecord<>(
          Config.TOPIC_NAME, String.valueOf(anchor.getPersonNum()), anchor));
    }
  }
}
