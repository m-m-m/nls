/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.nls.formatter;

import net.sf.mmm.nls.NlsMessage;
import net.sf.mmm.nls.argument.NlsArguments;
import net.sf.mmm.nls.formatter.impl.NlsMessageFormatterFactoryImpl;

/**
 * This is the interface to create an {@link NlsFormatter}.
 */
public interface NlsMessageFormatterFactory {

  /**
   * This method creates a new {@link NlsMessageFormatter} for the given {@code message}. <br>
   * The format of the {@code message} is described in {@link NlsMessage}.
   *
   * @param message is the template for the message where potential
   *        {@link net.sf.mmm.nls.formatter.NlsFormatter#format(Object, java.util.Locale, NlsArguments) arguments will
   *        be filled in}.
   * @return the {@link NlsMessageFormatter} for the given {@code message}.
   */
  NlsMessageFormatter create(String message);

  /**
   * @return the instance of {@link NlsMessageFormatterFactory}.
   */
  static NlsMessageFormatterFactory get() {

    return NlsMessageFormatterFactoryImpl.INSTANCE;
  }

}
