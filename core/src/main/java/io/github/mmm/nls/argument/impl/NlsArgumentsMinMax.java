/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.nls.argument.impl;

import java.util.HashMap;
import java.util.Map;

import io.github.mmm.nls.argument.NlsArguments;

/**
 * Empty implementation of {@link NlsArguments}.
 */
public class NlsArgumentsMinMax implements NlsArguments {

  private final Object min;

  private final Object max;

  /**
   * The constructor.
   *
   * @param min the {@link #KEY_MIN min} {@link #get(String) value}.
   * @param max the {@link #KEY_MAX max} {@link #get(String) value}.
   */
  public NlsArgumentsMinMax(Object min, Object max) {

    super();
    this.min = min;
    this.max = max;
  }

  @Override
  public Object get(String argKey) {

    if (KEY_MIN.equals(argKey)) {
      return this.min;
    } else if (KEY_MAX.equals(argKey)) {
      return this.max;
    }
    return null;
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

    if (index == 0) {
      return KEY_MIN;
    } else if (index == 1) {
      return KEY_MAX;
    }
    return null;
  }

  @Override
  public NlsArguments with(String newKey, Object newValue) {

    assert (!KEY_MIN.equals(newKey) && !KEY_MAX.equals(newKey));
    Map<String, Object> map = new HashMap<>();
    map.put(KEY_MIN, this.min);
    map.put(KEY_MAX, this.max);
    map.put(newKey, newValue);
    return new NlsArgumentsMap(map);
  }

}
