/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.nls.argument.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import io.github.mmm.nls.NlsMessage;
import io.github.mmm.nls.argument.NlsArguments;

/**
 * Implementation of {@link NlsArguments} backed by a {@link Map}. Only use for complex {@link NlsMessage}s with many
 * arguments.
 */
public class NlsArgumentsMap implements NlsArguments {

  private final Map<String, Object> map;

  /**
   * The constructor.
   *
   * @param map the {@link Map}.
   */
  public NlsArgumentsMap(Map<String, Object> map) {

    super();
    this.map = map;
  }

  @Override
  public Object get(String key) {

    return this.map.get(key);
  }

  @Override
  public String getKey(int index) {

    Iterator<String> iterator = this.map.keySet().iterator();
    int i = 0;
    while (iterator.hasNext()) {
      String key = iterator.next();
      if (i == index) {
        return key;
      }
      i++;
    }
    return null;
  }

  @Override
  public int size() {

    return this.map.size();
  }

  @Override
  public boolean isEmpty() {

    return this.map.isEmpty();
  }

  @Override
  public NlsArguments with(String key, Object value) {

    assert (!this.map.containsKey(key));
    Map<String, Object> newMap = new HashMap<>(this.map);
    newMap.put(key, value);
    return new NlsArgumentsMap(newMap);
  }

}
