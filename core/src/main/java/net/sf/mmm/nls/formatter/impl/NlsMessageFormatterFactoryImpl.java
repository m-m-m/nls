/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.nls.formatter.impl;

import net.sf.mmm.nls.formatter.NlsMessageFormatter;
import net.sf.mmm.nls.formatter.NlsMessageFormatterFactory;

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
