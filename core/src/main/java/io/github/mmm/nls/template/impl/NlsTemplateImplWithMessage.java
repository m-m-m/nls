/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.nls.template.impl;

import java.util.Locale;

/**
 * Extends {@link NlsTemplateImpl} with the {@link io.github.mmm.nls.NlsMessage#getInternationalizedMessage()
 * internationalized message} as fallback.
 */
public class NlsTemplateImplWithMessage extends NlsTemplateImpl {

  private final String message;

  /**
   * The constructor.
   *
   * @param name is the {@link #getBundleName() name} of the bundle.
   * @param key is the {@link #getMessageKey() key} of the string to lookup in the bundle.
   * @param message is the {@link io.github.mmm.nls.NlsMessage#getInternationalizedMessage() internationalized message}.
   */
  public NlsTemplateImplWithMessage(String name, String key, String message) {

    super(name, key);
    this.message = message;
  }

  @Override
  public String translate(Locale locale) {

    if ((this.message != null) && (locale == Locale.ROOT)) {
      return this.message;
    }
    return super.translate(locale);
  }

  @Override
  protected String translateFallback(String messageId) {

    if (this.message != null) {
      return this.message;
    }
    return super.translateFallback(messageId);
  }

}
