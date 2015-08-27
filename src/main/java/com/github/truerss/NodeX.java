package com.github.truerss;

import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by mike on 26.8.15.
 */
public class NodeX {
  public final String path;
  public final Type tpe;
  public NodeX(String path, Type tpe) {
    this.path = path;
    this.tpe = tpe;
  }

  public String toString() {
    return "NodeX[" + path + "]";
  }

  public static NodeX create(String path, Type tpe) {
    return new NodeX(path, tpe);
  }

  public static ArrayList<NodeX> extract(ElementExt element) {
    Element[] ch = element.getTrueChildren();
    ArrayList<NodeX> tmp = new ArrayList<>();
    if (ch.length == 0) {
      tmp.add(new NodeX(element.fullPath(), element.getType()));
    } else {
      Arrays.stream(ch)
          .map(x -> new ElementExt(x))
          .map(x -> extract(x)).reduce(tmp, (a, b) -> {
        a.addAll(b);
        return a;
      });
    }
    return tmp;
  }
}
