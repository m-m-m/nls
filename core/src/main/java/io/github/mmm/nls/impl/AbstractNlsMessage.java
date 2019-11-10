/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.nls.impl;

import io.github.mmm.nls.NlsMessage;

/**
 * Abstract base implementation of {@link NlsMessage}.
 */
public abstract class AbstractNlsMessage implements NlsMessage {

  @Override
  public String toString() {

    return getMessage();
  }

}
