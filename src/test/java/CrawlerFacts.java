import static beans.AnchorList.*;

import beans.AnchorList;
import collector.Crawler;
import org.junit.Test;

public class CrawlerFacts {

  @Test
  public void should_get_anchor_list_by_page_no() {
    Crawler crawler = new Crawler();
    int count = 0;
    for (int i = 1; i < Integer.MAX_VALUE; i++) {
      AnchorList anchorList = crawler.getAnchorListByPageNo(i);
      if (anchorList.getData().isEmpty()) {
        crawler.shutdown();
        break;
      }
      for (Anchor anchor : anchorList.getData()) {
        System.out.println(anchor);
        count++;
      }
    }
    System.out.println(count);
  }
}
