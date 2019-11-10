/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.nls.formatter.impl;

import io.github.mmm.nls.formatter.NlsMessageFormatter;
import io.github.mmm.nls.formatter.NlsMessageFormatterFactory;

/**
 * Implementation of {@link NlsMessageFormatterFactory}.
 */
public class NlsMessageFormatterFactoryImpl implements NlsMessageFormatterFactory {

  /** The singleton instance. */
  public static final NlsMessageFormatterFactoryImpl INSTANCE = new NlsMessageFormatterFactoryImpl();

  private NlsMessageFormatterFactoryImpl() {

    super();
  }

  @Override
  public NlsMessageFormatter create(String message) {

    return new NlsMessageFormatterImpl(message);
  }

}
