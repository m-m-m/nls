/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.nls.formatter.impl.plugin;

import java.text.DateFormat;
import java.util.Locale;

import io.github.mmm.nls.formatter.NlsFormatterManager;

/**
 * Implementation of {@link io.github.mmm.nls.formatter.NlsFormatter} for {@link NlsFormatterManager#TYPE_TIME}.
 */
public abstract class NlsFormatterPluginTime extends AbstractNlsFormatterPluginDate {

  @Override
  protected DateFormat createDateFormat(Locale locale, int dateStyle) {

    return DateFormat.getTimeInstance(dateStyle, locale);
  }

  @Override
  public String getType() {

    return NlsFormatterManager.TYPE_TIME;
  }

}
