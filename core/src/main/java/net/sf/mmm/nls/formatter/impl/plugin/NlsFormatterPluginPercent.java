/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.nls.formatter.impl.plugin;

import java.text.Format;
import java.text.NumberFormat;
import java.util.Locale;

import net.sf.mmm.nls.formatter.NlsFormatterManager;

/**
 * {@link NlsFormatterPluginNumber} for {@link NlsFormatterManager#STYLE_PERCENT}.
 */
public class NlsFormatterPluginPercent extends NlsFormatterPluginNumber {

  @Override
  protected Format createFormatter(Locale locale) {

    return NumberFormat.getPercentInstance(locale);
  }

  @Override
  public String getStyle() {

    return NlsFormatterManager.STYLE_PERCENT;
  }

}
