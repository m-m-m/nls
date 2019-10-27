/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.nls.impl;

import net.sf.mmm.nls.NlsMessage;

/**
 * Abstract base implementation of {@link NlsMessage}.
 */
public abstract class AbstractNlsMessage implements NlsMessage {

  @Override
  public String toString() {

    return getMessage();
  }

}
