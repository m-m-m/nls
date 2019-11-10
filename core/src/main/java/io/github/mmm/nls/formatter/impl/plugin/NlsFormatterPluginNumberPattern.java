/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.nls.formatter.impl.plugin;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.Format;
import java.util.Locale;

import io.github.mmm.nls.formatter.NlsFormatterManager;

/**
 * {@link NlsFormatterPluginNumber} for {@link NlsFormatterManager#TYPE_NUMBER} using a custom pattern.
 */
public class NlsFormatterPluginNumberPattern extends NlsFormatterPluginNumber {

  private final String pattern;

  /**
   * The constructor.
   *
   * @param pattern is the custom number pattern.
   */
  public NlsFormatterPluginNumberPattern(String pattern) {

    super();
    this.pattern = pattern;
  }

  @Override
  protected Format createFormatter(Locale locale) {

    DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance(locale);
    return new DecimalFormat(this.pattern, symbols);
  }

  @Override
  public String getStyle() {

    return this.pattern;
  }

}
