/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.nls.impl;

import io.github.mmm.nls.NlsMessage;
import io.github.mmm.nls.NlsMessageFactory;
import io.github.mmm.nls.argument.NlsArguments;
import io.github.mmm.nls.template.impl.NlsTemplateImpl;
import io.github.mmm.nls.template.impl.NlsTemplateImplWithMessage;

/**
 * Implementation of {@link NlsMessageFactory}.
 */
public class NlsMessageFactoryImpl implements NlsMessageFactory {

  /** The singleton instance. */
  public static final NlsMessageFactoryImpl INSTANCE = new NlsMessageFactoryImpl();

  @Override
  public NlsMessage create(String bundleName, String messageKey, String internationalizedMessage,
      NlsArguments arguments) {

    NlsTemplateImpl template = new NlsTemplateImplWithMessage(bundleName, messageKey, internationalizedMessage);
    return new NlsMessageImpl(template, internationalizedMessage, arguments);
  }

}
