/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.nls.argument.impl;

import io.github.mmm.nls.argument.NlsArguments;

/**
 * Empty implementation of {@link NlsArguments}.
 */
public class NlsArgumentsNone implements NlsArguments {

  /** Singleton instance. */
  public static final NlsArgumentsNone INSTANCE = new NlsArgumentsNone();

  @Override
  public Object get(String key) {

    return null;
  }

  @Override
  public int size() {

    return 0;
  }

  @Override
  public boolean isEmpty() {

    return true;
  }

  @Override
  public String getKey(int index) {

    return null;
  }

  @Override
  public NlsArguments with(String key, Object value) {

    return NlsArguments.of(key, value);
  }

}
