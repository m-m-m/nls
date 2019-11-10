/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.nls.formatter.impl.plugin;

import java.text.Format;
import java.text.NumberFormat;
import java.util.Locale;

import io.github.mmm.nls.formatter.NlsFormatterManager;

/**
 * This is an implementation of {@link io.github.mmm.nls.formatter.NlsFormatter} for
 * {@link NlsFormatterManager#STYLE_CURRENCY}.
 */
public class NlsFormatterPluginCurrency extends AbstractNlsFormatterPluginSimple {

  @Override
  protected Format createFormatter(Locale locale) {

    return NumberFormat.getCurrencyInstance(locale);
  }

  @Override
  public String getType() {

    return NlsFormatterManager.TYPE_NUMBER;
  }

  @Override
  public String getStyle() {

    return NlsFormatterManager.STYLE_CURRENCY;
  }

}
