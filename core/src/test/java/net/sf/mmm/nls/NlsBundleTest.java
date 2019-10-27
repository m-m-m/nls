/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.nls;

import java.util.Locale;

import net.sf.mmm.example.NlsBundleExample;
import net.sf.mmm.nls.argument.NlsArgumentsKeys;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test of {@link NlsB} and {@link NlsMessageFactory}.
 */
@SuppressWarnings("all")
public class NlsBundleTest extends Assertions {

  static final String BUNDLE = "net.sf.mmm.example.NlsBundleExample";

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
