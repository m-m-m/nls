/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.nls.formatter.impl;

import java.io.IOException;
import java.util.Locale;

import io.github.mmm.base.justification.Justification;
import io.github.mmm.nls.argument.NlsArguments;
import io.github.mmm.nls.formatter.NlsVariableFormatter;
import io.github.mmm.nls.formatter.NlsFormatter;
import io.github.mmm.nls.variable.NlsVariable;

/**
 * The {@link NlsFormatter} for an actual {@link NlsVariable}. It performs the higher-level formatting with
 * {@link NlsVariable#getJustification() justification} delegating the lower-level formatting to the
 * {@link NlsVariable#getFormatter() according sub-formatter} (typically a
 * {@link io.github.mmm.nls.formatter.NlsFormatterPlugin}).
 */
public class NlsVariableFormatterImpl extends AbstractNlsFormatter<NlsVariable> implements NlsVariableFormatter {

  /** The singleton instance. */
  public static final NlsVariableFormatterImpl INSTANCE = new NlsVariableFormatterImpl();

  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  public void doFormat(NlsVariable argument, Locale locale, NlsArguments arguments, Appendable buffer)
      throws IOException {

    Object value = arguments.get(argument.getKey());
    NlsFormatter formatter = argument.getFormatter();
    Justification justification = argument.getJustification();
    if (justification == null) {
      formatter.format(value, locale, arguments, buffer);
    } else {
      StringBuilder sb = new StringBuilder();
      formatter.format(value, locale, arguments, sb);
      justification.justify(sb, buffer);
    }
  }
}
