/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.nls.formatter.impl;

import java.io.IOException;
import java.util.Locale;
import java.util.Map;

import io.github.mmm.nls.NlsMessage;
import io.github.mmm.nls.argument.NlsArguments;
import io.github.mmm.nls.formatter.NlsFormatter;

/**
 * This is the abstract base implementation of the {@link NlsFormatter} interface. <br>
 * You should extend this class rather than directly implementing the {@link NlsFormatter} interface to gain
 * compatibility with further releases.
 *
 * @param <V> is the generic type of the object to {@link #format(Object, Locale, NlsArguments)}.
 */
public abstract class AbstractNlsFormatter<V> implements NlsFormatter<V> {

  @Override
  public void format(V object, Locale locale, NlsArguments arguments, Appendable buffer) {

    try {
      doFormat(object, locale, arguments, buffer);
    } catch (IOException e) {
      throw new IllegalStateException("Error writing to a", e);
    }
  }

  /**
   * @param object is the object to format.
   * @param locale is the locale used for localized formatting.
   * @param arguments is the {@link Map} of {@link NlsMessage#getArgument(String) arguments}.
   * @param buffer is where to append the formatted {@code object}.
   * @throws IOException if the given {@link Appendable} caused an {@link java.io.IOException}.
   * @see #format(Object, Locale, NlsArguments, Appendable)
   */
  protected abstract void doFormat(V object, Locale locale, NlsArguments arguments, Appendable buffer)
      throws IOException;

}
