/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.nls;

import io.github.mmm.nls.argument.NlsArguments;
import io.github.mmm.nls.impl.NlsMessageFactoryImpl;
import io.github.mmm.nls.template.NlsTemplate;

/**
 * This is the interface for a factory used to create instances of {@link NlsMessage}.
 */
public interface NlsMessageFactory {

  /**
   * This method creates a new instance of {@link NlsMessage} from the given arguments.
   *
   * @param template is the {@link NlsTemplate} for the {@link NlsMessage}.
   * @param arguments are the dynamic {@link NlsArguments} to fill into the message.
   * @return the new {@link NlsMessage} instance.
   */
  NlsMessage create(NlsTemplate template, NlsArguments arguments);

  /**
   * @param bundleName the {@link io.github.mmm.nls.descriptor.NlsBundleDescriptor#getBundleName() bundle name}.
   * @param messageKey the {@link io.github.mmm.nls.descriptor.NlsMessageDescriptor#getMessageKey() message key}.
   * @param internationalizedMessage the {@link NlsMessage#getInternationalizedMessage() internationalized message}.
   * @return the new {@link NlsMessage} instance without {@link NlsArguments}.
   * @see #create(String, String, String, NlsArguments)
   */
  default NlsMessage create(String bundleName, String messageKey, String internationalizedMessage) {

    return create(bundleName, messageKey, internationalizedMessage, NlsArguments.of());
  }

  /**
   * @param bundleName the {@link io.github.mmm.nls.descriptor.NlsBundleDescriptor#getBundleName() bundle name}.
   * @param messageKey the {@link io.github.mmm.nls.descriptor.NlsMessageDescriptor#getMessageKey() message key}.
   * @param internationalizedMessage the {@link NlsMessage#getInternationalizedMessage() internationalized message}.
   * @param arguments are the dynamic {@link NlsArguments} to fill into the message.
   * @return the new {@link NlsMessage} instance.
   */
  NlsMessage create(String bundleName, String messageKey, String internationalizedMessage, NlsArguments arguments);

  /**
   * @return the configured instance of {@link NlsMessageFactory}.
   */
  static NlsMessageFactory get() {

    return NlsMessageFactoryImpl.INSTANCE;
  }

}
