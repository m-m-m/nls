/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.nls.formatter.impl.plugin;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Locale;

import io.github.mmm.nls.formatter.NlsFormatterManager;

/**
 * {@link NlsFormatterPluginDate} for {@link NlsFormatterManager#STYLE_ISO_8601}.
 */
public class NlsFormatterPluginDateIso8601 extends NlsFormatterPluginDate {

  @Override
  protected Format createFormatter(Locale locale) {

    return new SimpleDateFormat("yyyy-MM-dd", locale);
  }

  @Override
  public String getStyle() {

    return NlsFormatterManager.STYLE_ISO_8601;
  }

}
