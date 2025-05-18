/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.nls.formatter;

import java.util.Locale;

import io.github.mmm.nls.argument.NlsArguments;

/**
 * This is the interface for a formatter of a message-text. It is a simplified view on something like
 * {@link java.text.MessageFormat}.
 */
public interface NlsMessageFormatter extends NlsFormatter<Void> {

  /**
   * This method formats the underlying pattern by filling in the given {@code arguments} and writing the result into
   * the given {@code buffer}.
   *
   * {@inheritDoc}
   *
   * @param nothing has to be {@code null}. Only for generic compatibility.
   */
  @Override
  void format(Void nothing, Locale locale, NlsArguments arguments, Appendable buffer);

}
