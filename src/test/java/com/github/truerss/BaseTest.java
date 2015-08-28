package com.github.truerss;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
/**
 * Created by mike on 27.8.15.
 */
public class BaseTest {
  @Test
  public void getSelector() throws IOException {
    String expected = "#post-711 > div.entry";
    testIt("Simple", expected, "1.html");
  }

  @Test
  public void getSelectorFromBBC() throws IOException {
    String expected = "#page > div > div.container > div.container--primary-and-secondary-columns.column-clearfix > div.column--primary > div.story-body > div.story-body__inner";
    testIt("BBC", expected, "2.html");
  }

  @Test
  public void getSelectorWithCommentsPage() throws IOException {
    String expected = "#content > div.g9.nest.main-column > div.main-content.rel.clear > div.g12.post-full > div.content.clear.bb.pb30.mb30";
    testIt("when comments on page", expected, "3.html");
  }

  @Test
  public void getSelectorWithoutHTML() throws IOException {
    String expected = "body.html.not-front.not-logged-in.page-node.page-node-.page-node-625631.node-type-press-release.og-context.og-context-node.og-context-node-185821.section-usao-ndca.one-sidebar.sidebar-second.og-context-northern-district-of-california > div.l-page > div.l-main > div.l-content > article.node.node--press-release.node--full.node--press-release--full";
    testIt("Wihtou html", expected, "4.html");
  }

  @Test
  public void getSelectorFromMedium() throws IOException {
    String expected = "#prerendered > article.u-sizeViewHeightMin100.postArticle.postArticle--full.is-languageTier1";
    testIt("test medium", expected, "5.html");
  }

  private void testIt(String testName, String expected, String fileName) throws IOException {
    ExtractResult er = ContentExtractor.extract(getElement(fileName));
    System.out.println("test: " + testName);
    System.out.println("  actual  =====> " + er.selector);
    assertEquals(expected, er.selector);
  }

  private Element getElement(String fileName) throws IOException {
    ClassLoader cl = getClass().getClassLoader();
    File file = new File(cl.getResource(fileName).getFile());
    Document doc = Jsoup.parse(file, "UTF-8");
    Elements body = doc.select("body");
    return body.get(0);
  }
}
