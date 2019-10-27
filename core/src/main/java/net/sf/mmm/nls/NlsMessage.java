/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.nls;

import java.util.Locale;

import net.sf.mmm.nls.argument.NlsArguments;
import net.sf.mmm.nls.variable.NlsVariable;

/**
 * This is the interface for an internationalized message. It stores an {@link #getInternationalizedMessage()
 * internationalized-message} separated from language independent {@link #getArguments arguments}. This approach ensures
 * that the message is always available in the internationalized language (should be English) while it still allows to
 * {@link #getLocalizedMessage(Locale) translate} the message to a native language. For an introduction first read
 * {@link net.sf.mmm.nls here}<br>
 * The format of the {@link #getInternationalizedMessage() internationalized-message} is similar to
 * {@link java.text.MessageFormat}. This allows to migrate existing code from {@link java.text.MessageFormat} to
 * {@link NlsMessage} easily. However, there are some advanced features available. While using numbers to identify the
 * {@link #getArguments arguments} is a maintenance-hell for large messages, this solution is using
 * {@link NlsArguments#get(String) named arguments} ({@link NlsArguments#getKey(int) keys}) that should be
 * self-explanatory. Further, there is also support for additional styles as well as
 * {@link net.sf.mmm.base.justification.Justification}. See {@link net.sf.mmm.nls.variable.NlsVariable} for the
 * specification of the argument syntax.<br>
 * To specify {@link NlsMessage}s you create an {@link NlsBundle} per module.
 *
 * For the term <em>internationalization</em> usually the shortcut <em>i18n</em> is used.
 *
 * @see NlsVariable
 * @see NlsBundle
 * @see NlsMessageFactory
 * @see net.sf.mmm.nls
 */
public interface NlsMessage extends NlsObject {

  /**
   * The prefix appended to the {@link #getInternationalizedMessage() message} if the localization (translation) failed.
   */
  String LOCALIZATION_FAILURE_PREFIX = "#";

  /**
   * This method gets the internationalized message what is the actual message template for the {@link Locale#ROOT root
   * locale} without resolving its {@link #getArguments() arguments}.<br>
   *
   * @see NlsMessage
   * @see #getArguments
   * @see net.sf.mmm.nls.variable.NlsVariable
   *
   * @return the message for internationalization (e.g. <code>"Welcome {name}!"</code>).
   */
  String getInternationalizedMessage();

  /**
   * This method gets the language independent argument value for the given {@code key}.
   *
   * @param key is the name of the requested argument.
   * @return the argument value for the given key or {@code null} if NOT defined.
   * @see net.sf.mmm.nls.argument.NlsArguments#get(String)
   * @see net.sf.mmm.nls.variable.NlsVariable
   */
  default Object getArgument(String key) {

    return getArguments().get(key);
  }

  /**
   * @return the dynamic {@link NlsArguments} to fill into the message after localization.
   * @see NlsArguments
   * @see net.sf.mmm.nls.variable.NlsVariable
   */
  NlsArguments getArguments();

  /**
   * <b>ATTENTION:</b><br>
   * In most cases you wand to use {@link #getLocalizedMessage(Locale)} instead of this method.
   *
   * @return the {@link #getInternationalizedMessage() untranslated message} with arguments filled in. This results in
   *         the message in its original language that should typically be English.
   * @see #getLocalizedMessage()
   */
  default String getMessage() {

    return getLocalizedMessage(Locale.ROOT);
  }

  /**
   * This method tries to get the {@link #getLocalizedMessage(Locale) localized message} as {@link String} using the
   * {@link Locale#getDefault() default locale}. <b>ATTENTION:</b><br>
   * If possible try to avoid using this method and use {@link #getLocalizedMessage(Locale)} instead (e.g. using spring
   * {@code LocaleContextHolder} to get the users locale).
   *
   * @return the localized message.
   */
  default String getLocalizedMessage() {

    return getLocalizedMessage(Locale.getDefault());
  }

  /**
   * This method gets the resolved and localized message.<br>
   * First it will translate the {@link #getInternationalizedMessage() internationalized message} to the given
   * {@link Locale}. If this fails for whatever reason, the {@link #getInternationalizedMessage() internationalized
   * message} is used as fallback.<br>
   * Then this message gets resolved by replacing the {@link net.sf.mmm.nls.variable.NlsVariable variables} with their
   * according {@link #getArguments() arguments}.<br>
   * <b>Example:</b><br>
   * We assume the {@link #getInternationalizedMessage() internationalized message} is <code>"Welcome {name}!"</code>
   * and the {@link #getArguments() argument} with the {@link NlsArguments#getKey(int) key} {@code "name"} has the
   * {@link NlsArguments#get(String) value} {@code "Joelle"}. After translation to {@link Locale#GERMAN} the message may
   * be <code>"Willkommen {name}!"</code>. This results in the resolved message {@code "Willkommen Joelle!"}. If the
   * German localization was not present or has a syntax error, the {@link Locale#ROOT root locale} is used as fallback
   * resulting in {@code "Welcome Joelle!"}.
   *
   * @see net.sf.mmm.nls
   *
   * @param locale is the locale to translate to.
   * @return the localized message.
   */
  default String getLocalizedMessage(Locale locale) {

    StringBuilder result = new StringBuilder();
    getLocalizedMessage(locale, result);
    return result.toString();
  }

  /**
   * This method writes the {@link #getLocalizedMessage(Locale) localized message} to the given {@code buffer}. <br>
   *
   * @see #getLocalizedMessage(Locale)
   *
   * @param locale is the locale to translate to.
   * @param buffer is the buffer where to write the message to.
   */
  void getLocalizedMessage(Locale locale, Appendable buffer);

  @Override
  default NlsMessage toNlsMessage() {

    return this;
  }

}
