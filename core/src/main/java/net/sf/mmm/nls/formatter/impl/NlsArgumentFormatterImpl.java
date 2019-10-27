/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.nls.formatter.impl;

import java.io.IOException;
import java.util.Locale;

import net.sf.mmm.base.justification.Justification;
import net.sf.mmm.nls.argument.NlsArguments;
import net.sf.mmm.nls.formatter.NlsArgumentFormatter;
import net.sf.mmm.nls.formatter.NlsFormatter;
import net.sf.mmm.nls.variable.NlsVariable;

/**
 * The {@link NlsFormatter} for an actual {@link NlsVariable}. It performs the higher-level formatting with
 * {@link NlsVariable#getJustification() justification} delegating the lower-level formatting to the
 * {@link NlsVariable#getFormatter() according sub-formatter} (typically a
 * {@link net.sf.mmm.nls.formatter.NlsFormatterPlugin}).
 */
public class NlsArgumentFormatterImpl extends AbstractNlsFormatter<NlsVariable> implements NlsArgumentFormatter {

  /** The singleton instance. */
  public static final NlsArgumentFormatterImpl INSTANCE = new NlsArgumentFormatterImpl();

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
