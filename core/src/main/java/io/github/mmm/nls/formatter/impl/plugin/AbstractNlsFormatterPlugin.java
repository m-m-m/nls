/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.nls.formatter.impl.plugin;

import io.github.mmm.nls.formatter.NlsFormatterPlugin;
import io.github.mmm.nls.formatter.impl.AbstractNlsFormatter;

/**
 * Abstract base-implementation of {@link NlsFormatterPlugin} with a specific {@link #getType() type} and
 * {@link #getStyle() style}.
 */
public abstract class AbstractNlsFormatterPlugin extends AbstractNlsFormatter<Object> implements NlsFormatterPlugin {

  @Override
  public String toString() {

    StringBuilder sb = new StringBuilder();
    sb.append(getType());
    String style = getStyle();
    if (style != null) {
      sb.append(",");
      sb.append(style);
    }
    return sb.toString();
  }

}
