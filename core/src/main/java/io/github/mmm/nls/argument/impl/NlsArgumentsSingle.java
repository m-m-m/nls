/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.nls.argument.impl;

import java.util.Objects;

import io.github.mmm.nls.argument.NlsArguments;

/**
 * Empty implementation of {@link NlsArguments}.
 */
public class NlsArgumentsSingle implements NlsArguments {

  private final String key;

  private final Object value;

  /**
   * The constructor.
   *
   * @param key the key of the single {@link #get(String) value}.
   * @param value the single {@link #get(String) value}.
   */
  public NlsArgumentsSingle(String key, Object value) {

    super();
    Objects.requireNonNull(key, "key");
    this.key = key;
    this.value = value;
  }

  @Override
  public Object get(String argKey) {

    if (this.key.equals(argKey)) {
      return this.value;
    }
    return null;
  }

  @Override
  public int size() {

    return 1;
  }

  @Override
  public boolean isEmpty() {

    return false;
  }

  @Override
  public String getKey(int index) {

    if (index == 0) {
      return this.key;
    }
    return null;
  }

}
