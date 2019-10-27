/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.nls.argument.impl;

import java.util.Iterator;
import java.util.Map;

import net.sf.mmm.nls.NlsMessage;
import net.sf.mmm.nls.argument.NlsArguments;

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

}
