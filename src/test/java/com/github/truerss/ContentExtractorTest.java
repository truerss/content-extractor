package com.github.truerss;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ContentExtractorTest {

  private final String articleUrl = "https://dzone.com/articles/fluentd-vs-logstash-the-ultimate-log-agent-battle";

  private final String commonUrl = "https://blogs.windows.com/windowsexperience/2021/05/19/the-future-of-internet-explorer-on-windows-10-is-in-microsoft-edge/";

  private final String nonContentableUrl = "https://news.ycombinator.com/";

  @Test
  public void extractContentTestsWhenArticleIsPresent() throws IOException {
    var doc = Jsoup.connect(articleUrl).get();
    var result = ContentExtractor.extract(doc.body());
    assertTrue(result.selector().contains("article"));
    assertTrue(result.selector().endsWith("article"));
  }

  @Test
  public void extractContentTestsWhenArticleIsDivOrAnotherElement() throws IOException {
    var doc = Jsoup.connect(commonUrl).get();
    var result = ContentExtractor.extract(doc.body());
    var expected = "div.l-container.l-container--small.l-container--article > div.item-single__content.t-content > div";
    assertTrue(result.selector().contains(expected));
  }

  @Test
  public void extractContentTestsWhenToMuchContent() throws IOException {
    var doc = Jsoup.connect(nonContentableUrl).get();
    var result = ContentExtractor.extract(doc.body());
    assertTrue(result.selector().endsWith("body"));
  }

}
