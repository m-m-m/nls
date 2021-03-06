/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.nls.formatter.impl.plugin;

import io.github.mmm.nls.formatter.NlsFormatterManager;

/**
 * {@link NlsFormatterPluginType} for {@link io.github.mmm.nls.formatter.NlsFormatterManager#STYLE_SHORT short}
 * {@link #getStyle() style}.
 */
public class NlsFormatterPluginTypeShort extends NlsFormatterPluginType {

  /**
   * The constructor.
   */
  public NlsFormatterPluginTypeShort() {

    super(NlsFormatterManager.STYLE_SHORT);
  }

}
