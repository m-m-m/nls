/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.nls.template;

import java.util.Locale;

import io.github.mmm.nls.NlsMessage;
import io.github.mmm.nls.argument.NlsArguments;

/**
 * This interface represents the template for an internationalized text that can be {@link #translate(Locale)
 * translated} to a given {@link Locale}.
 *
 * @see NlsMessage
 * @see io.github.mmm.nls.template.impl.NlsTemplateImpl
 */
public interface NlsTemplate {

  /**
   * This method translates the represented string for the given {@code locale}. <br>
   * This typically happens via a lookup in a {@link java.util.ResourceBundle}).
   *
   * @param locale is the locale to translate to.
   * @return the resolved string (closest translation for the given {@code locale}).
   */
  String translate(Locale locale);

  /**
   * This method behaves like {@link #translate(Locale)} but additionally fills the given {@code arguments} into the
   * translated message writing into the given {@code buffer}.
   *
   * @param locale is the locale to translate to.
   * @param arguments are the dynamic {@link NlsArguments} to fill into the message.
   * @param buffer is the buffer where the translation will be appended to.
   * @return {@code true} if the (translated) message has been appended to the given {@code messageBuffer} or
   *         {@code false} if the translation failed.
   */
  boolean translate(Locale locale, NlsArguments arguments, Appendable buffer);

}
