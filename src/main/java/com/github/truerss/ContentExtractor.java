package com.github.truerss;

import org.jsoup.nodes.Element;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class ContentExtractor {

  private final static String articleSelector = "article";
  private final static String textQuery = "p:matchesOwn(^.+$), div:matchesOwn(^.+$)";

  public static ExtractResult extract(Element element) {
    var articles = element.select(articleSelector);

    if (articles.size() == 1) {
      return new ExtractResult(articles.first().cssSelector());
    }

    var elements = element.select(textQuery);
    var map = new HashMap<String, Long>(elements.size());

    for (var elem: elements) {
      var key = elem.parent().cssSelector();
      var value = map.getOrDefault(key, 0L);
      var len = (long) elem.text().length();
      map.put(key, len + value);
    }

    var result = map.entrySet()
      .stream()
      .max(Comparator.comparingLong(Map.Entry::getValue))
      .map(Map.Entry::getKey).orElse(element.cssSelector());

    return new ExtractResult(result);

  }

}
