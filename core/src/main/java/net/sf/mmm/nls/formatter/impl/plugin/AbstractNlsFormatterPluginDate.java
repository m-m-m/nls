/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.nls.formatter.impl.plugin;

import java.text.DateFormat;
import java.text.Format;
import java.util.Locale;

import net.sf.mmm.nls.formatter.NlsFormatterManager;

/**
 * This is the abstract base implementation for a {@link AbstractNlsFormatterPluginSimple} for {@link java.util.Date}s.
 */
public abstract class AbstractNlsFormatterPluginDate extends AbstractNlsFormatterPluginSimple {

  @Override
  protected Format createFormatter(Locale locale) {

    int dateStyle = convertStyle(getStyle());
    return createDateFormat(locale, dateStyle);
  }

  /**
   * @param locale the {@link Locale}.
   * @param dateStyle the {@link #getStyle() style} converted to the corresponding {@code int} constant for
   *        {@link DateFormat}.
   * @return the {@link DateFormat} to wrap.
   */
  protected abstract DateFormat createDateFormat(Locale locale, int dateStyle);

  /**
   * This method converts the given {@link net.sf.mmm.nls.formatter.NlsFormatterPlugin#getStyle() style} to the
   * according {@link DateFormat}-style constant.
   *
   * @param style is the {@link net.sf.mmm.nls.formatter.NlsFormatterPlugin#getStyle() style} to convert.
   * @return the according {@link DateFormat}-style constant (e.g. {@link DateFormat#MEDIUM}).
   */
  private static int convertStyle(String style) {

    if (style.equals(NlsFormatterManager.STYLE_SHORT)) {
      return DateFormat.SHORT;
    } else if (style.equals(NlsFormatterManager.STYLE_LONG)) {
      return DateFormat.LONG;
    } else if (style.equals(NlsFormatterManager.STYLE_MEDIUM)) {
      return DateFormat.MEDIUM;
    } else if (style.equals(NlsFormatterManager.STYLE_FULL)) {
      return DateFormat.FULL;
    } else {
      throw new IllegalArgumentException(style);
    }
  }

}
