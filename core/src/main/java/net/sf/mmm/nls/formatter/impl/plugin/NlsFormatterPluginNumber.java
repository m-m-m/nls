/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.nls.formatter.impl.plugin;

import java.text.Format;
import java.text.NumberFormat;
import java.util.Locale;

import net.sf.mmm.nls.formatter.NlsFormatterManager;

/**
 * Implementation of {@link net.sf.mmm.nls.formatter.NlsFormatter} using {@link NlsFormatterManager#TYPE_NUMBER}.
 */
public class NlsFormatterPluginNumber extends AbstractNlsFormatterPluginSimple {

  @Override
  protected Format createFormatter(Locale locale) {

    return NumberFormat.getInstance(locale);
  }

  @Override
  public String getType() {

    return NlsFormatterManager.TYPE_NUMBER;
  }

  @Override
  public String getStyle() {

    return null;
  }

}
