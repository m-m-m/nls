/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.nls.formatter.impl.plugin;

import net.sf.mmm.nls.formatter.NlsFormatterManager;

/**
 * {@link NlsFormatterPluginDateTime} for {@link net.sf.mmm.nls.formatter.NlsFormatterManager#STYLE_SHORT short}
 * {@link #getStyle() style}.
 */
public class NlsFormatterPluginDateTimeShort extends NlsFormatterPluginDateTime {

  @Override
  public String getStyle() {

    return NlsFormatterManager.STYLE_SHORT;
  }

}
