/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.nls.formatter;

import java.util.Locale;
import java.util.Map;

import net.sf.mmm.base.exception.RuntimeIoException;
import net.sf.mmm.nls.NlsMessage;
import net.sf.mmm.nls.argument.NlsArguments;

/**
 * This is the interface for a formatter of an arbitrary object in a localized way. <br>
 *
 * @see java.text.Format
 *
 * @param <V> type of value to {@link #format(Object, Locale, NlsArguments) format}.
 */
public interface NlsFormatter<V> {

  /**
   * This method formats the given {@code object} according to the given {@code locale}.
   *
   * @param object is the object to format.
   * @param locale is the locale used for localized formatting.
   * @param arguments is the {@link Map} of arguments.
   * @return the formatted and localized string for the given {@code object}.
   */
  default String format(V object, Locale locale, NlsArguments arguments) {

    StringBuilder buffer = new StringBuilder();
    format(object, locale, arguments, buffer);
    return buffer.toString();
  }

  /**
   * This method formats the given {@code object} according to the given {@code locale}.
   *
   * @param object is the object to format.
   * @param locale is the locale used for localized formatting.
   * @param arguments is the {@link Map} of {@link NlsMessage#getArgument(String) arguments}.
   * @param buffer is where to append the formatted {@code object}.
   * @throws RuntimeIoException if the given {@link Appendable} caused an {@link java.io.IOException}.
   */
  void format(V object, Locale locale, NlsArguments arguments, Appendable buffer);

}
