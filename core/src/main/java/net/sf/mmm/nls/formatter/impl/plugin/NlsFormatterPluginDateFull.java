/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.nls.formatter.impl.plugin;

import net.sf.mmm.nls.formatter.NlsFormatterManager;

/**
 * {@link NlsFormatterPluginDate} for {@link net.sf.mmm.nls.formatter.NlsFormatterManager#STYLE_FULL full}
 * {@link #getStyle() style}.
 */
public class NlsFormatterPluginDateFull extends NlsFormatterPluginDate {

  /**
   * The constructor.
   */
  public NlsFormatterPluginDateFull() {

    super();
  }

  @Override
  public String getStyle() {

    return NlsFormatterManager.STYLE_FULL;
  }

}
