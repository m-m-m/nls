/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.nls.impl;

import java.io.IOException;
import java.util.Locale;

import io.github.mmm.base.exception.RuntimeIoException;
import io.github.mmm.nls.argument.NlsArguments;
import io.github.mmm.nls.descriptor.NlsMessageDescriptor;
import io.github.mmm.nls.formatter.impl.NlsMessageFormatterImpl;
import io.github.mmm.nls.template.NlsTemplate;
import io.github.mmm.nls.template.impl.NlsTemplateImpl;

/**
 * Implementation of {@link io.github.mmm.nls.NlsMessage}.
 */
public class NlsMessageImpl extends AbstractNlsMessage {

  private NlsTemplateImpl template;

  private String internationalizedMessage;

  private NlsArguments arguments;

  /**
   * The constructor.
   *
   * @param template is the {@link NlsTemplateImpl}.
   * @param internationalizedMessage the {@link #getInternationalizedMessage() internationalized message}
   * @param arguments are the {@link NlsArguments} filled into the message after nationalization.
   */
  public NlsMessageImpl(NlsTemplateImpl template, String internationalizedMessage, NlsArguments arguments) {

    this.template = template;
    this.internationalizedMessage = internationalizedMessage;
    if (arguments == null) {
      this.arguments = NlsArguments.of();
    } else {
      this.arguments = arguments;
    }
  }

  @Override
  public NlsArguments getArguments() {

    return this.arguments;
  }

  @Override
  public String getInternationalizedMessage() {

    if (this.internationalizedMessage == null) {
      this.internationalizedMessage = this.template.translate(Locale.ROOT);
    }
    return this.internationalizedMessage;
  }

  /**
   * @return the {@link NlsTemplate} of this message.
   */
  public NlsTemplate getTemplate() {

    return this.template;
  }

  @Override
  public NlsMessageDescriptor getDescriptor() {

    return this.template;
  }

  @Override
  public void getLocalizedMessage(Locale locale, Appendable buffer) {

    Locale actualLocale = locale;
    if (actualLocale == null) {
      actualLocale = Locale.ROOT;
    }
    try {
      if (this.arguments.isEmpty()) {
        String text = this.template.translate(actualLocale);
        if (text == null) {
          // if (locale != LOCALE_ROOT) {
          // buffer.append(LOCALIZATION_FAILURE_PREFIX);
          // }
          text = this.internationalizedMessage;
        }
        buffer.append(text);
      } else {
        boolean success = false;
        success = this.template.translate(actualLocale, this.arguments, buffer);
        if (!success) {
          NlsMessageFormatterImpl format = new NlsMessageFormatterImpl(this.internationalizedMessage);
          format.format(null, actualLocale, this.arguments, buffer);
        }
      }
    } catch (IOException e) {
      throw new RuntimeIoException(e);
    }
  }

}
