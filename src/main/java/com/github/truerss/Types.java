package com.github.truerss;

/**
 * Created by mike on 27.8.15.
 */

class Types {
  static abstract class Type {
    protected Long size = 0L;
    public Long getSize() {
      return size;
    }
  }
  static class AnotherType extends Type {}
  static class TextType extends Type {
    public TextType(Long size) {
      this.size = size;
    }
  }
  static class SkipType extends Type {}
  static class EmptyType extends Type {}

  public static final SkipType skipType = new SkipType();
  public static final EmptyType emptyType = new EmptyType();
  public static final AnotherType anotherType = new AnotherType();
}
