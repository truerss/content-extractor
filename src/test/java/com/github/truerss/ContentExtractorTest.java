package com.github.truerss;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.File;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ContentExtractorTest {

  private final String articleUrl = "https://web.archive.org/web/20210928134309/https://dzone.com/articles/fluentd-vs-logstash-the-ultimate-log-agent-battle";

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
    var classLoader = getClass().getClassLoader();
    var file = new File(classLoader.getResource("blogs.windows.com.html").getFile());
    var doc = Jsoup.parse(file, "UTF-8");
    var result = ContentExtractor.extract(doc.body());
    var expected = "div.l-wrapper > main > article";
    assertTrue(result.selector().contains(expected));
  }

  @Test
  public void extractContentTestsWhenToMuchContent() throws IOException {
    var doc = Jsoup.connect(nonContentableUrl).get();
    var result = ContentExtractor.extract(doc.body());
    assertTrue(result.selector().endsWith("body"));
  }

}
