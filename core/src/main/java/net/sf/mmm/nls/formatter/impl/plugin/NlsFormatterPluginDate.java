/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.nls.formatter.impl.plugin;

import java.text.DateFormat;
import java.util.Locale;

import net.sf.mmm.nls.formatter.NlsFormatterManager;

/**
 * Implementation of {@link net.sf.mmm.nls.formatter.NlsFormatter} for {@link NlsFormatterManager#TYPE_DATE}.
 */
public abstract class NlsFormatterPluginDate extends AbstractNlsFormatterPluginDate {

  /**
   * The constructor.
   */
  public NlsFormatterPluginDate() {

    super();
  }

  @Override
  protected DateFormat createDateFormat(Locale locale, int dateStyle) {

    return DateFormat.getDateInstance(dateStyle, locale);
  }

  @Override
  public String getType() {

    return NlsFormatterManager.TYPE_DATE;
  }

}
