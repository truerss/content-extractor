package com.github.truerss;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;

public class ContentExtractor {

  public static ExtractResult extract(Element element) {
    Elements articles = element.select("article");
    if (articles.size() == 1) {
      return new ExtractResult(articles.first().cssSelector());
    }

    Elements elements = element.select("p:matchesOwn(^.+$), div:matchesOwn(^.+$)");
    HashMap<String, Long> map = new HashMap<>(elements.size());

    elements
        .forEach(x -> {
          String key = x.parent().cssSelector();
          Long v = map.get(key);
          if (v == null) {
            map.put(key, (long)x.text().length());
          } else {
            map.put(key, x.text().length() + v);
          }
        });

    String result = null;
    if (map.isEmpty()) {
      result = element.cssSelector();
    } else {
      result = map.entrySet()
          .stream().max((entry1, entry2) ->
              entry1.getValue() > entry2.getValue() ? 1 : -1).get().getKey();
    }

    map.clear();

    return new ExtractResult(result);

  }

}
