/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.nls.argument.impl;

import java.util.HashMap;
import java.util.Map;

import io.github.mmm.nls.argument.NlsArguments;

/**
 * Empty implementation of {@link NlsArguments}.
 */
public class NlsArgumentsKeyValue implements NlsArguments {

  private final Object key;

  private final Object value;

  /**
   * The constructor.
   *
   * @param key the key of the single {@link #get(String) value}.
   * @param value the single {@link #get(String) value}.
   */
  public NlsArgumentsKeyValue(Object key, Object value) {

    super();
    this.key = key;
    this.value = value;
  }

  @Override
  public Object get(String argKey) {

    if (KEY_KEY.equals(argKey)) {
      return this.key;
    } else if (KEY_VALUE.equals(argKey)) {
      return this.value;
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
      return KEY_KEY;
    } else if (index == 1) {
      return KEY_VALUE;
    }
    return null;
  }

  @Override
  public NlsArguments with(String newKey, Object newValue) {

    assert (!KEY_KEY.equals(newKey) && !KEY_VALUE.equals(newKey));
    Map<String, Object> map = new HashMap<>();
    map.put(KEY_KEY, this.key);
    map.put(KEY_VALUE, this.value);
    map.put(newKey, newValue);
    return new NlsArgumentsMap(map);
  }

}
