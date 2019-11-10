/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.nls.formatter;

import io.github.mmm.nls.formatter.impl.NlsFormatterManagerImpl;
import io.github.mmm.nls.formatter.impl.plugin.AbstractNlsFormatterPlugin;
import io.github.mmm.scanner.CharSequenceScanner;

/**
 * This is the interface for a manager of {@link NlsFormatter}s. <br>
 * A legal implementation of this interface has to be thread-safe.
 */
public interface NlsFormatterManager {

  /** @see java.text.NumberFormat */
  String TYPE_NUMBER = "number";

  /** @see java.text.DateFormat#getDateInstance(int, java.util.Locale) */
  String TYPE_DATE = "date";

  /** @see java.text.DateFormat#getTimeInstance(int, java.util.Locale) */
  String TYPE_TIME = "time";

  /** @see java.text.DateFormat#getDateTimeInstance(int, int, java.util.Locale) */
  String TYPE_DATETIME = "datetime";

  /** @see io.github.mmm.nls.formatter.impl.plugin.NlsFormatterPluginChoice */
  String TYPE_CHOICE = "choice";

  /** Format for {@link java.lang.reflect.Type} */
  String TYPE_TYPE = "type";

  /** @see java.text.DateFormat#SHORT */
  String STYLE_SHORT = "short";

  /** @see java.text.DateFormat#MEDIUM */
  String STYLE_MEDIUM = "medium";

  /** @see java.text.DateFormat#LONG */
  String STYLE_LONG = "long";

  /** @see java.text.DateFormat#FULL */
  String STYLE_FULL = "full";

  /** @see java.text.NumberFormat#getIntegerInstance() */
  String STYLE_INTEGER = "integer";

  /** @see java.text.NumberFormat#getCurrencyInstance() */
  String STYLE_CURRENCY = "currency";

  /** @see java.text.NumberFormat#getPercentInstance() */
  String STYLE_PERCENT = "percent";

  /** ISO-8601 date/time format */
  String STYLE_ISO_8601 = "iso8601";

  /**
   * @return the default {@link NlsFormatter} instance.
   * @see #getFormatter(String, String)
   */
  default NlsFormatterPlugin getFormatter() {

    return getFormatter(null);
  }

  /**
   * @param formatType is the {@link NlsFormatterPlugin#getType() type} to be formatted.
   * @return the according {@link NlsFormatterPlugin} instance.
   * @see #getFormatter(String, String)
   */
  default NlsFormatterPlugin getFormatter(String formatType) {

    return getFormatter(formatType, (String) null);
  }

  /**
   * This method gets the {@link NlsFormatter} for the given {@code formatType} and {@code formatStyle}. <br>
   * To be compliant with {@link java.text.MessageFormat} the following types and styles need to be supported by the
   * implementation: <br>
   * <br>
   * {@code formatType}:
   * <ul>
   * <li>{@link #TYPE_NUMBER number}</li>
   * <li>{@link #TYPE_DATE date}</li>
   * <li>{@link #TYPE_TIME time}</li>
   * <li>{@link #TYPE_CHOICE choice}</li>
   * <li>{@link #TYPE_DATETIME datetime}</li>
   * </ul>
   * <br>
   * {@code formatStyle}:
   * <ul>
   * <li>{@link #STYLE_SHORT short}</li>
   * <li>{@link #STYLE_MEDIUM medium}</li>
   * <li>{@link #STYLE_LONG long}</li>
   * <li>{@link #STYLE_FULL full}</li>
   * <li>{@link #STYLE_INTEGER integer}</li>
   * <li>{@link #STYLE_CURRENCY currency}</li>
   * <li>{@link #STYLE_PERCENT percent}</li>
   * <li>{@link #STYLE_ISO_8601 iso8601}</li>
   * <li><em>additional custom styles (named [a-z]*)</em></li>
   * <li><em>anything else will be treated as SubformatPattern</em></li>
   * </ul>
   *
   * <b>ATTENTION:</b><br>
   * The support for {@link java.text.ChoiceFormat}s is NOT provided in a compatible way as by hacking internal arrays
   * of {@link java.text.MessageFormat}. Instead this implementation provides a clean configuration via
   * {@code formatStyle} when {@code formatType} is {@code choice} (see
   * {@link io.github.mmm.nls.formatter.impl.plugin.NlsFormatterPluginChoice}).
   *
   * @see java.text.MessageFormat
   *
   * @param formatType is the type to be formatted.
   * @param formatStyle is the style defining details of formatting.
   * @return the according {@link NlsFormatter} instance.
   */
  NlsFormatterPlugin getFormatter(String formatType, String formatStyle);

  /**
   * This method is like {@link #getFormatter(String, String)} but reads the
   * {@link AbstractNlsFormatterPlugin#getStyle() style} from the given scanner.
   *
   * @param formatType is the type to be formatted.
   * @param scanner is the current {@link CharSequenceScanner} for parsing the style defining details of formatting.
   * @return the according {@link NlsFormatter}.
   */
  NlsFormatterPlugin getFormatter(String formatType, CharSequenceScanner scanner);

  /**
   * @return the instance of {@link NlsFormatterManager}.
   */
  static NlsFormatterManager get() {

    return NlsFormatterManagerImpl.INSTANCE;
  }

}
