/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.nls.formatter;

import io.github.mmm.nls.formatter.impl.NlsVariableFormatterImpl;
import io.github.mmm.nls.variable.NlsVariable;

/**
 * The {@link NlsVariableFormatter} is an {@link NlsFormatter} for a {@link NlsVariable}. It performs the higher-level
 * formatting with {@link NlsVariable#getJustification() justification} delegating the lower-level formatting to the
 * {@link NlsVariable#getFormatter() according sub-formatter}.
 */
public interface NlsVariableFormatter extends NlsFormatter<NlsVariable> {

  /**
   * @return the instance of {@link NlsVariableFormatter}.
   */
  static NlsVariableFormatter get() {

    return NlsVariableFormatterImpl.INSTANCE;
  }

}
