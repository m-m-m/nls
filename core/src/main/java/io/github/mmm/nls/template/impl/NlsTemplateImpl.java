/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.nls.template.impl;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.mmm.base.exception.RuntimeIoException;
import io.github.mmm.nls.argument.NlsArguments;
import io.github.mmm.nls.descriptor.NlsMessageDescriptor;
import io.github.mmm.nls.formatter.NlsMessageFormatter;
import io.github.mmm.nls.formatter.NlsMessageFormatterFactory;
import io.github.mmm.nls.template.NlsTemplate;

/**
 * Implementation of the {@link io.github.mmm.nls.template.NlsTemplate} using {@link ResourceBundle}s for localization.
 */
public class NlsTemplateImpl implements NlsTemplate, NlsMessageDescriptor {

  private static final Logger LOG = LoggerFactory.getLogger(NlsTemplateImpl.class);

  private final String bundleName;

  private final String messageKey;

  /**
   * The constructor.
   *
   * @param name is the {@link #getBundleName() name} of the bundle.
   * @param key is the {@link #getMessageKey() key} of the string to lookup in the bundle.
   */
  public NlsTemplateImpl(String name, String key) {

    super();
    this.bundleName = name;
    this.messageKey = key;
  }

  @Override
  public String getBundleName() {

    return this.bundleName;
  }

  @Override
  public String getMessageKey() {

    return this.messageKey;
  }

  @Override
  public String translate(Locale locale) {

    try {
      ResourceBundle bundle = ResourceBundle.getBundle(this.bundleName, locale);
      return bundle.getString(this.messageKey);
    } catch (Exception e) {
      return translateFallback(e);
    }
  }

  @Override
  public boolean translate(Locale locale, NlsArguments arguments, Appendable buffer) {

    String translation = translate(locale);
    if (translation == null) {
      return false;
    } else {
      try {
        NlsMessageFormatter formatter = NlsMessageFormatterFactory.get().create(translation);
        formatter.format(null, locale, arguments, buffer);
        return true;
      } catch (Exception e) {
        try {
          LOG.info("Invalid NLS message {}", translation, e);
          buffer.append(translation);
          buffer.append("@");
          buffer.append(arguments.toString());
          // true lies...
          return true;
        } catch (IOException e1) {
          throw new RuntimeIoException(e1);
        }
      }
    }
  }

  /**
   * Called from {@link #translate(Locale)} if localization failed.
   *
   * @param e is the {@link Exception}.
   * @return the fallback message.
   */
  protected String translateFallback(Exception e) {

    String messageId = this.bundleName + ":" + this.messageKey;
    if (e instanceof java.util.MissingResourceException) {
      e = null; // avoid spam
    }
    LOG.info("Failed to resolve message {}:{}", this.bundleName, this.messageKey, e);
    return translateFallback(messageId);
  }

  /**
   * @see #translateFallback(Exception)
   *
   * @param messageId is the ID of the message composed out of bundle base name and key.
   * @return the fallback message.
   */
  protected String translateFallback(String messageId) {

    return "unresolved (" + messageId + ")";
  }

  @Override
  public String toString() {

    return this.bundleName + ":" + this.messageKey;
  }

}
