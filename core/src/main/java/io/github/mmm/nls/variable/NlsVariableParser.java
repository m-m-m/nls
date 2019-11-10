/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.nls.variable;

import io.github.mmm.nls.NlsMessage;
import io.github.mmm.nls.formatter.NlsFormatter;
import io.github.mmm.nls.formatter.NlsFormatterManager;
import io.github.mmm.nls.variable.impl.NlsVariableParserImpl;
import io.github.mmm.scanner.CharSequenceScanner;

/**
 * The {@link NlsVariableParser} is used to {@link #parse(CharSequenceScanner) parse} an {@link NlsVariable}.
 */
public interface NlsVariableParser {

  /** The character used to start a variable expression: {@value} */
  char START_EXPRESSION = '{';

  /** The character used to end a variable expression: {@value} */
  char END_EXPRESSION = '}';

  /** The character used to separate format type and style: {@value} */
  char FORMAT_SEPARATOR = ',';

  /**
   * This method parses the {@link NlsMessage#getInternationalizedMessage() internationalized message} given as
   * {@link CharSequenceScanner} pointing the beginning of an argument (immediately after
   * {@link NlsVariableParser#START_EXPRESSION}).
   *
   * @see NlsFormatterManager#getFormatter(String, String)
   *
   * @param scanner is the {@link CharSequenceScanner}.
   * @return the according {@link NlsFormatter} instance.
   */
  NlsVariable parse(CharSequenceScanner scanner);

  /**
   * @return the instance of {@link NlsVariableParser}.
   */
  static NlsVariableParser get() {

    return NlsVariableParserImpl.INSTANCE;
  }

}
