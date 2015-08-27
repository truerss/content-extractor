package com.github.truerss;

import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by mike on 26.8.15.
 */
class ElementExt {

  public final Element element;

  public ElementExt(Element element) {
    this.element = element;
  }

  public Element[] getTrueChildren() {
    return element.children().stream()
        .filter(x -> !x.nodeName().trim().isEmpty()).toArray(Element[]::new);
  }

  public Types.Type getType() {
    String tag = element.tagName().trim().toLowerCase();
    if (tag.equals("script") ||
        tag.equals("noscript") ||
        tag.equals("iframe") ||
        tag.equals("img")) {
      return new Types.SkipType();
    } else if (!element.ownText().isEmpty()) {
      return new Types.TextType(Long.valueOf(element.ownText().trim().length()));
    } else if ((tag.equals("div") || tag.equals("span")) &&
        (element.ownText().isEmpty())) {
      return new Types.EmptyType();
    } else {
      return new Types.AnotherType();
    }
  }

  // skip html and body elements
  public String fullPath() {
    String path;
    try {
      path = element.cssSelector();
    } catch (Exception ex) {
      String ownPath = calculatePath(element);

      List<String> parentsPaths = element.parents().stream()
          .map(p -> calculatePath(p))
          .collect(Collectors.toCollection(ArrayList::new));

      Collections.reverse(parentsPaths);
      if (parentsPaths.size() >= 1 && parentsPaths.get(0).startsWith("html")) {
        parentsPaths = parentsPaths.subList(1, parentsPaths.size());
      }
      if (parentsPaths.size() >= 1 && parentsPaths.get(0).startsWith("body")) {
        parentsPaths = parentsPaths.subList(1, parentsPaths.size());
      }

      path = parentsPaths.stream().collect(Collectors.joining(" ")) + " > " + ownPath;

    }
    return path;


  }

  private String calculatePath(Element e) {
    return e.tagName() +
        asId(e.id()) + asClass(e.classNames());
  }

  private String asId(String s) {
    if (s.isEmpty()) {
      return "";
    } else {
      return "#" + s;
    }
  }

  private String asClass(Set<String> xs) {
    Stream<String> stream = xs.stream();
    boolean flag = stream.allMatch(x -> x.isEmpty());
    if (flag) {
      return "";
    } else {
      return "." + xs.stream().collect(Collectors.joining("."));
    }
  }


}
