/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.nls.formatter.impl;

import io.github.mmm.nls.formatter.impl.plugin.NlsFormatterPluginCurrency;
import io.github.mmm.nls.formatter.impl.plugin.NlsFormatterPluginDateFull;
import io.github.mmm.nls.formatter.impl.plugin.NlsFormatterPluginDateIso8601;
import io.github.mmm.nls.formatter.impl.plugin.NlsFormatterPluginDateLong;
import io.github.mmm.nls.formatter.impl.plugin.NlsFormatterPluginDateMedium;
import io.github.mmm.nls.formatter.impl.plugin.NlsFormatterPluginDateShort;
import io.github.mmm.nls.formatter.impl.plugin.NlsFormatterPluginDateTimeFull;
import io.github.mmm.nls.formatter.impl.plugin.NlsFormatterPluginDateTimeIso8601;
import io.github.mmm.nls.formatter.impl.plugin.NlsFormatterPluginDateTimeLong;
import io.github.mmm.nls.formatter.impl.plugin.NlsFormatterPluginDateTimeMedium;
import io.github.mmm.nls.formatter.impl.plugin.NlsFormatterPluginDateTimeShort;
import io.github.mmm.nls.formatter.impl.plugin.NlsFormatterPluginDefault;
import io.github.mmm.nls.formatter.impl.plugin.NlsFormatterPluginInteger;
import io.github.mmm.nls.formatter.impl.plugin.NlsFormatterPluginNumber;
import io.github.mmm.nls.formatter.impl.plugin.NlsFormatterPluginPercent;
import io.github.mmm.nls.formatter.impl.plugin.NlsFormatterPluginTimeFull;
import io.github.mmm.nls.formatter.impl.plugin.NlsFormatterPluginTimeIso8601;
import io.github.mmm.nls.formatter.impl.plugin.NlsFormatterPluginTimeLong;
import io.github.mmm.nls.formatter.impl.plugin.NlsFormatterPluginTimeMedium;
import io.github.mmm.nls.formatter.impl.plugin.NlsFormatterPluginTimeShort;
import io.github.mmm.nls.formatter.impl.plugin.NlsFormatterPluginTypeFull;
import io.github.mmm.nls.formatter.impl.plugin.NlsFormatterPluginTypeLong;
import io.github.mmm.nls.formatter.impl.plugin.NlsFormatterPluginTypeMedium;
import io.github.mmm.nls.formatter.impl.plugin.NlsFormatterPluginTypeShort;

/**
 * This is a sub-class of {@link NlsFormatterMap} as a ready to use configurable component and contains all the
 * defaults.
 */
public class ConfiguredNlsFormatterMap extends NlsFormatterMap {

  /**
   * The constructor.
   */
  public ConfiguredNlsFormatterMap() {

    super();
    // default format
    registerFormatter(new NlsFormatterPluginDefault());
    // number format
    registerFormatter(new NlsFormatterPluginNumber());
    registerFormatter(new NlsFormatterPluginCurrency());
    registerFormatter(new NlsFormatterPluginInteger());
    registerFormatter(new NlsFormatterPluginPercent());
    // date format
    registerFormatter(new NlsFormatterPluginDateShort());
    registerFormatter(new NlsFormatterPluginDateMedium());
    registerFormatter(new NlsFormatterPluginDateLong());
    registerFormatter(new NlsFormatterPluginDateFull());
    registerFormatter(new NlsFormatterPluginDateIso8601());
    // time format
    registerFormatter(new NlsFormatterPluginTimeShort());
    registerFormatter(new NlsFormatterPluginTimeMedium());
    registerFormatter(new NlsFormatterPluginTimeLong());
    registerFormatter(new NlsFormatterPluginTimeFull());
    registerFormatter(new NlsFormatterPluginTimeIso8601());
    // date-time format
    registerFormatter(new NlsFormatterPluginDateTimeShort());
    registerFormatter(new NlsFormatterPluginDateTimeMedium());
    registerFormatter(new NlsFormatterPluginDateTimeLong());
    registerFormatter(new NlsFormatterPluginDateTimeFull());
    registerFormatter(new NlsFormatterPluginDateTimeIso8601());
    // type format
    registerFormatter(new NlsFormatterPluginTypeShort());
    registerFormatter(new NlsFormatterPluginTypeMedium());
    registerFormatter(new NlsFormatterPluginTypeLong());
    registerFormatter(new NlsFormatterPluginTypeFull());
  }

}
