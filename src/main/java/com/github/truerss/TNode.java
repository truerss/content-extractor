package com.github.truerss;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by mike on 26.8.15.
 */
public class TNode {

  public final String data;
  public final Type tpe;

  private Long _size = 0L;
  private ArrayList<TNode> children = new ArrayList<>();

  public TNode(String data, Type tpe) {
    this.data = data;
    this.tpe = tpe;
  }

  public void addChild(TNode x) {
    children.add(x);
  }

  public TNode addChild0(ArrayList<TNode> xs) {
    int size = xs.size();
    Optional<TNode> h = get(0);
    if (size == 1) {
      TNode x = xs.get(0);
      if (h.isPresent() && h.get().data.equals(x.data)) {
        h.get().withSize(x.tpe.size);
      } else {
        addChild(x);
      }
    } else {
      TNode x = xs.get(0);
      ArrayList<TNode> xs0 = new ArrayList<>(xs.subList(1, xs.size()));
      if (h.isPresent() && h.get().data.equals(x.data)) {
        h.get().addChild0(xs0);
      } else {
        addChild(x.addChild0(xs0));
      }
    }
    return this;
  }


  private Optional<TNode> get(int index) {
    if (index >= children.size()) {
      return Optional.empty();
    } else {
      return Optional.of(children.get(index));
    }
  }


  public void withSize(Long size) {
    _size += size;
  }

  public String toString() {
    if (children.isEmpty()) {
      return data;
    } else {
      return data + "[" + children.stream().map(x -> x.toString())
          .collect(Collectors.joining(", ")) + "]";
    }
  }

  public Long size() {
    if (children.isEmpty()) {
      return tpe.getSize() + _size;
    } else {
      return childrenSize() + _size;
    }
  }

  public Long childrenSize() {
    return children.stream()
        .map(x -> x.size()).reduce(0L, (a, b) -> a + b);
  }

  public int childrenCount() {
    return children.size();
  }

  public ArrayList<TNode> all() {
    ArrayList<TNode> x = children.stream().map(z -> z.all())
        .reduce(new ArrayList<>(), (a, b) -> {
          a.addAll(b);
          return a;
        });
    x.addAll(children);
    return x;
  }

}
