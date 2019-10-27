/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.nls.argument.impl;

import java.util.Objects;

import net.sf.mmm.nls.argument.NlsArguments;

/**
 * Empty implementation of {@link NlsArguments}.
 */
public class NlsArgumentsDouble extends NlsArgumentsSingle {

  private final String key2;

  private final Object value2;

  /**
   * The constructor.
   *
   * @param key1 the first key.
   * @param value1 the first {@link #get(String) value}.
   * @param key2 the second key.
   * @param value2 the second {@link #get(String) value}.
   */
  public NlsArgumentsDouble(String key1, Object value1, String key2, Object value2) {

    super(key1, value1);
    Objects.requireNonNull(key2, "key2");
    assert (!key1.equals(key2));
    this.key2 = key2;
    this.value2 = value2;
  }

  @Override
  public Object get(String argKey) {

    if (this.key2.equals(argKey)) {
      return this.value2;
    }
    return super.get(argKey);
  }

  @Override
  public int size() {

    return 2;
  }

  @Override
  public boolean isEmpty() {

    return false;
  }

  @Override
  public String getKey(int index) {

    if (index == 1) {
      return this.key2;
    }
    return super.getKey(index);
  }

}
