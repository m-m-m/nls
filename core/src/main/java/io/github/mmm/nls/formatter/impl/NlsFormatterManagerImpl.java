/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.nls.formatter.impl;

import io.github.mmm.base.filter.CharFilter;
import io.github.mmm.base.filter.ListCharFilter;
import io.github.mmm.nls.formatter.NlsFormatter;
import io.github.mmm.nls.formatter.NlsFormatterManager;
import io.github.mmm.nls.formatter.NlsFormatterPlugin;
import io.github.mmm.nls.formatter.impl.plugin.AbstractNlsFormatterPlugin;
import io.github.mmm.nls.formatter.impl.plugin.NlsFormatterPluginChoice;
import io.github.mmm.nls.formatter.impl.plugin.NlsFormatterPluginDatePattern;
import io.github.mmm.nls.formatter.impl.plugin.NlsFormatterPluginNumberPattern;
import io.github.mmm.nls.variable.NlsVariableParser;
import io.github.mmm.scanner.CharSequenceScanner;

/**
 * Implementation of {@link NlsFormatterManager}.
 */
public class NlsFormatterManagerImpl implements NlsFormatterManager {

  /** The singleton instance. */
  public static final NlsFormatterManagerImpl INSTANCE = new NlsFormatterManagerImpl();

  /** A char filter that accepts everything except ',' and '}'. */
  protected static final CharFilter NO_COMMA_OR_END_EXPRESSION = new ListCharFilter(
      "" + NlsVariableParser.FORMAT_SEPARATOR + NlsVariableParser.END_EXPRESSION).negate();

  /** A char filter that accepts everything except ',' and '}'. */
  protected static final CharFilter NO_EXPRESSION = new ListCharFilter(
      "" + NlsVariableParser.START_EXPRESSION + NlsVariableParser.END_EXPRESSION).negate();

  private final NlsFormatterMap formatterMap;

  /**
   * The constructor.
   */
  public NlsFormatterManagerImpl() {

    this(new ConfiguredNlsFormatterMap());
  }

  /**
   * The constructor.
   *
   * @param formatterMap is the map with the registered formatters.
   */
  public NlsFormatterManagerImpl(NlsFormatterMap formatterMap) {

    super();
    this.formatterMap = formatterMap;
  }

  /**
   * This method is like {@link #getFormatter(String, String)} but reads the
   * {@link AbstractNlsFormatterPlugin#getStyle() style} from the given scanner.
   *
   * @param formatType is the type to be formatted.
   * @param scanner is the current {@link CharSequenceScanner} for parsing the style defining details of formatting.
   * @return the according {@link NlsFormatter}.
   */
  @Override
  public NlsFormatterPlugin getFormatter(String formatType, CharSequenceScanner scanner) {

    if (TYPE_CHOICE.equals(formatType)) {
      return new NlsFormatterPluginChoice(scanner);
    }
    String formatStyle = scanner.readWhile(NO_EXPRESSION);
    return getFormatter(formatType, formatStyle);
  }

  /**
   * This method creates the {@link NlsFormatter} for the given {@code formatType} and the custom {@code subformat}. It
   * is called if no formatter is {@link NlsFormatterMap#registerFormatter(NlsFormatterPlugin, String, String)
   * registered} for the given arguments. <br>
   *
   * @param formatType is the type to be formatted.
   * @param subformat is the custom formatStyle for which no static formatter is registered.
   * @return the according custom formatter or {@code null} if no such formatter is could be created.
   */
  protected NlsFormatterPlugin getSubFormatter(String formatType, String subformat) {

    if (TYPE_NUMBER.equals(formatType)) {
      return new NlsFormatterPluginNumberPattern(subformat);
    } else if ((TYPE_DATE.equals(formatType)) || (TYPE_TIME.equals(formatType)) || (TYPE_DATETIME.equals(formatType))) {
      return new NlsFormatterPluginDatePattern(subformat);
    }
    return null;
  }

  @Override
  public NlsFormatterPlugin getFormatter(String formatType, String formatStyle) {

    NlsFormatterPlugin result = null;
    result = this.formatterMap.getFormatter(formatType, formatStyle);
    if (result == null) {
      if (formatStyle != null) {
        result = getSubFormatter(formatType, formatStyle);
        if (result == null) {
          result = this.formatterMap.getFormatter(formatType, null);
        }
      }
    }
    if (result == null) {
      if (formatType == null) {
        throw new IllegalStateException();
      }
    }
    return result;
  }

  /**
   * @return the formatterMap
   */
  protected NlsFormatterMap getFormatterMap() {

    return this.formatterMap;
  }

}
