/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.nls.variable.impl;

import io.github.mmm.base.filter.CharFilter;
import io.github.mmm.base.filter.ListCharFilter;
import io.github.mmm.base.justification.Justification;
import io.github.mmm.nls.formatter.NlsFormatterManager;
import io.github.mmm.nls.formatter.NlsFormatterPlugin;
import io.github.mmm.nls.variable.NlsVariable;
import io.github.mmm.nls.variable.NlsVariableParser;
import io.github.mmm.scanner.CharSequenceScanner;

/**
 * Implementation of {@link NlsVariableParser}.
 */
public class NlsVariableParserImpl implements NlsVariableParser {

  /** The singleton instance. */
  public static final NlsVariableParserImpl INSTANCE = new NlsVariableParserImpl();

  /** A char filter that accepts everything except ',' and '}'. */
  protected static final CharFilter NO_COMMA_OR_END_EXPRESSION = new ListCharFilter(FORMAT_SEPARATOR, END_EXPRESSION)
      .negate();

  /** A char filter that accepts everything except '{' and '}'. */
  protected static final CharFilter NO_EXPRESSION = new ListCharFilter(START_EXPRESSION, END_EXPRESSION).negate();

  @Override
  public NlsVariable parse(CharSequenceScanner scanner) {

    NlsFormatterManager formatterManager = NlsFormatterManager.get();
    String key = scanner.readWhile(CharFilter.IDENTIFIER);
    char c = scanner.next();
    int index = scanner.getCurrentIndex();
    String formatType = null;
    NlsFormatterPlugin formatter = null;
    if (c == NlsVariableParserImpl.FORMAT_SEPARATOR) {
      formatType = scanner.readWhile(NO_COMMA_OR_END_EXPRESSION);
      index = scanner.getCurrentIndex();
      c = scanner.next();
      if (c == NlsVariableParserImpl.FORMAT_SEPARATOR) {
        index = scanner.getCurrentIndex();
        try {
          formatter = formatterManager.getFormatter(formatType, scanner);
        } catch (Exception e) {
          throw new IllegalArgumentException(
              "Failed to parse '" + scanner.substring(index, scanner.getCurrentIndex()) + "' as NlsFormatter.", e);
        }
        c = scanner.next();
      } else {
        formatter = formatterManager.getFormatter(formatType);
      }
    }
    Justification justification = null;
    if (c == NlsVariableParserImpl.START_EXPRESSION) {
      String formatJustification = scanner.readUntil(NlsVariableParserImpl.END_EXPRESSION, false);
      justification = Justification.of(formatJustification);
      c = scanner.next();
    }
    if (c != NlsVariableParserImpl.END_EXPRESSION) {
      throw new IllegalArgumentException(scanner.substring(index, scanner.getCurrentIndex()));
    }
    if (formatter == null) {
      formatter = formatterManager.getFormatter();
    }
    return new NlsVariable(key, formatter, justification);
  }

}
