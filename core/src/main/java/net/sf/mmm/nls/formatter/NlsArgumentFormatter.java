/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.nls.formatter;

import net.sf.mmm.nls.formatter.impl.NlsArgumentFormatterImpl;
import net.sf.mmm.nls.variable.NlsVariable;

/**
 * The {@link NlsArgumentFormatter} is an {@link NlsFormatter} for a {@link NlsVariable}. It performs the higher-level
 * formatting with {@link NlsVariable#getJustification() justification} delegating the lower-level formatting to the
 * {@link NlsVariable#getFormatter() according sub-formatter}.
 */
public interface NlsArgumentFormatter extends NlsFormatter<NlsVariable> {

  /**
   * @return the instance of {@link NlsArgumentFormatter}.
   */
  static NlsArgumentFormatter get() {

    return NlsArgumentFormatterImpl.INSTANCE;
  }

}
