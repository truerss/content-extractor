package com.github.truerss;

import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * Created by mike on 27.8.15.
 */
class ContentExtractor {

  private static String[] forRemove = {"a", "ul", "code", "pre", "form", "input"};

  public static ExtractResult extract(Element element) {
    Arrays.stream(forRemove)
        .forEach(tag -> element.select(tag)
            .forEach(x -> x.remove()));

    ElementExt body = new ElementExt(element);

    TNode root = new TNode("root", new Types.SkipType());

    NodeX.extract(body).stream().map(z ->
            Arrays.stream(z.path.split(" > "))
                .map(x -> new TNode(x, z.tpe))
                .collect(Collectors.toCollection(ArrayList::new))
    ).forEach(x -> root.addChild0(x));

    ArrayList<TNode> z = root.all();

    TNode[] e = z.stream()
        .filter(x -> x.all().stream().allMatch(c -> c.size() > 0))
        .filter(x -> x.childrenCount() >= 1)
        .filter(x -> x.size() > 0)
        .filter(x -> x.size().equals(x.childrenSize()))
        .toArray(TNode[]::new);

    long max = 0L;
    int index = 0;
    for(int i = 0; i < e.length; i++) {
      if (e[i].size() > max) {
        index = i;
        max = e[i].size();
      }
    }

    String need1 = e[index].data;
    Optional<Element> e1 = element.select(need1).stream()
        .max(Comparator.comparingInt(x -> x.text().length()));

    String need2 = e[0].data;
    Optional<Element> e2 = element.select(need2).stream()
        .max(Comparator.comparingInt(x -> x.text().length()));

    String result;

    if (e1.isPresent() && e2.isPresent()) {
      if (e1.isPresent() && !e2.isPresent()) {
        result = e1.get().cssSelector();
      } else if (e2.isPresent() && !e1.isPresent()) {
        result = e2.get().cssSelector();
      } else {
        if (e1.get().text().length() > e2.get().text().length()) {
          result = e1.get().cssSelector();
        } else {
          result = e2.get().cssSelector();
        }
      }
    } else {
      throw new RuntimeException("Fail extract content from " + element.cssSelector());
    }

    // and need remove html if it first element
    if (result.startsWith("html")) {
      StringBuilder bf = new StringBuilder(result.length() - 4);
      String[] tmp = result.split(" > ");
      int length = tmp.length;
      for (int i = 1; i < length; i ++) {
        bf.append(tmp[i]);
        if (i != length-1) {
          bf.append(" > ");
        }
      }
      result = bf.toString();
    }

    return new ExtractResult(result);


  }

}
