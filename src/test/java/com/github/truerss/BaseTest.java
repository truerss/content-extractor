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
    ExtractResult er = ContentExtractor.extract(getElement("1.html"));
    System.out.println("=====> " + er.selector);
    assertEquals(er.selector, "#post-711 > div.entry");
  }

  @Test
  public void getSelectorFromBBC() throws IOException {
    ExtractResult er = ContentExtractor.extract(getElement("2.html"));
    System.out.println("=====> " + er.selector);
    assertEquals(er.selector, "#page > div > div.container > div.container--primary-and-secondary-columns.column-clearfix > div.column--primary > div.story-body > div.story-body__inner");
  }

  @Test
  public void getSelectorWithCommentsPage() throws IOException {
    ExtractResult er = ContentExtractor.extract(getElement("3.html"));
    System.out.println("=====> " + er.selector);
    assertEquals(er.selector, "#content > div.g9.nest.main-column > div.main-content.rel.clear > div.g12.post-full > div.content.clear.bb.pb30.mb30");
  }

  @Test
  public void getSelectorWithoutHTML() throws IOException {
    ExtractResult er = ContentExtractor.extract(getElement("4.html"));
    System.out.println("=====> " + er.selector);
    String expected = "body.html.not-front.not-logged-in.page-node.page-node-.page-node-625631.node-type-press-release.og-context.og-context-node.og-context-node-185821.section-usao-ndca.one-sidebar.sidebar-second.og-context-northern-district-of-california > div.l-page > div.l-main > div.l-content";
    assertEquals(er.selector, expected);
  }

  private Element getElement(String fileName) throws IOException {
    ClassLoader cl = getClass().getClassLoader();
    File file = new File(cl.getResource(fileName).getFile());
    Document doc = Jsoup.parse(file, "UTF-8");
    Elements body = doc.select("body");
    return body.get(0);
  }
}
