/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.nls;

import java.util.Locale;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import io.github.mmm.example.NlsBundleExample;
import io.github.mmm.nls.argument.NlsArgumentsKeys;

/**
 * Test of {@link NlsB} and {@link NlsMessageFactory}.
 */
@SuppressWarnings("all")
public class NlsBundleTest extends Assertions {

  static final String BUNDLE = "l10n.io.github.mmm.example.NlsBundleExample";

  @Test
  public void testNlsBundleExample() {

    NlsBundleExample bundle = NlsBundleExample.INSTANCE;
    assertThat(bundle.getBundleName()).isEqualTo(BUNDLE);
    NlsMessage message = bundle.msgBye("Joelle");
    assertThat(message.getInternationalizedMessage()).isEqualTo("Bye,bye \"{name}\"!");
    assertThat(message.getArguments().get(NlsArgumentsKeys.KEY_NAME)).isEqualTo("Joelle");
    assertThat(message.getMessage()).isEqualTo("Bye,bye \"Joelle\"!");
    assertThat(message.getLocalizedMessage(Locale.GERMAN)).isEqualTo("Tschüss \"Joelle\"!");
    // no l10n for Japanese
    assertThat(message.getLocalizedMessage(Locale.JAPANESE)).isEqualTo(message.getMessage());
  }

}
