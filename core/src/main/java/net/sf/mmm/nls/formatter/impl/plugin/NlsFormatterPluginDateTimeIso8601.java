/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.nls.formatter.impl.plugin;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Locale;

import net.sf.mmm.nls.formatter.NlsFormatterManager;

/**
 * {@link NlsFormatterPluginDateTime} for {@link net.sf.mmm.nls.formatter.NlsFormatterManager#STYLE_ISO_8601}.
 */
public class NlsFormatterPluginDateTimeIso8601 extends NlsFormatterPluginDateTime {

  @Override
  protected Format createFormatter(Locale locale) {

    return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", locale);
  }

  @Override
  public String getStyle() {

    return NlsFormatterManager.STYLE_ISO_8601;
  }

}
