/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.nls.formatter.impl.plugin;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Locale;

import io.github.mmm.nls.formatter.NlsFormatterManager;

/**
 * Implementation of {@link io.github.mmm.nls.formatter.NlsFormatter} using a custom date pattern for
 * {@link SimpleDateFormat}.
 */
public class NlsFormatterPluginDatePattern extends AbstractNlsFormatterPluginSimple {

  private final String pattern;

  /**
   * The constructor.
   *
   * @param pattern is the pattern for the {@link SimpleDateFormat}.
   */
  public NlsFormatterPluginDatePattern(String pattern) {

    super();
    this.pattern = pattern;
  }

  @Override
  protected Format createFormatter(Locale locale) {

    return new SimpleDateFormat(this.pattern, locale);
  }

  @Override
  public String getType() {

    return NlsFormatterManager.TYPE_DATE;
  }

  @Override
  public String getStyle() {

    return this.pattern;
  }

}
