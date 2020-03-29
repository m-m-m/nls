/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.nls.argument.impl;

import java.util.HashMap;
import java.util.Map;

import io.github.mmm.nls.argument.NlsArguments;

/**
 * Empty implementation of {@link NlsArguments}.
 */
public class NlsArgumentsObjectKey implements NlsArguments {

  private final Object key;

  private final Object object;

  /**
   * The constructor.
   *
   * @param object the {@link #KEY_OBJECT object} {@link #get(String) value}.
   * @param key the {@link #KEY_KEY key} {@link #get(String) value}.
   */
  public NlsArgumentsObjectKey(Object object, Object key) {

    super();
    this.object = object;
    this.key = key;
  }

  @Override
  public Object get(String argKey) {

    if (KEY_OBJECT.equals(argKey)) {
      return this.object;
    } else if (KEY_KEY.equals(argKey)) {
      return this.key;
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
      return KEY_OBJECT;
    } else if (index == 1) {
      return KEY_KEY;
    }
    return null;
  }

  @Override
  public NlsArguments with(String newKey, Object newValue) {

    assert (!KEY_KEY.equals(newKey) && !KEY_OBJECT.equals(newKey));
    Map<String, Object> map = new HashMap<>();
    map.put(KEY_KEY, this.key);
    map.put(KEY_OBJECT, this.object);
    map.put(newKey, newValue);
    return new NlsArgumentsMap(map);
  }

}
