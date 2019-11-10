/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.nls.formatter.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import io.github.mmm.nls.formatter.NlsFormatter;
import io.github.mmm.nls.formatter.NlsFormatterManager;
import io.github.mmm.nls.formatter.NlsFormatterPlugin;

/**
 * This class is like a {@link Map} to {@link #registerFormatter(NlsFormatterPlugin, String, String) register} and
 * {@link #getFormatter(String, String) retrieve} {@link NlsFormatterPlugin}s. <br>
 * <b>ATTENTION:</b><br>
 * The {@link io.github.mmm.nls.formatter.NlsFormatterManager#getFormatter() default formatter} is NOT stored in this map.
 *
 * @see io.github.mmm.nls.formatter.impl.ConfiguredNlsFormatterMap
 */
public class NlsFormatterMap {

  /** @see #getFormatter(String, String) */
  private final Map<String, Map<String, NlsFormatterPlugin>> builders;

  /**
   * The constructor.
   */
  public NlsFormatterMap() {

    super();
    this.builders = new HashMap<>();
  }

  /**
   * This method registers the given {@code formatBuilder}.
   *
   * @param formatter is the {@link NlsFormatterPlugin} to register.
   * @return the {@link NlsFormatter} that was registered for the given {@link NlsFormatterPlugin#getType() type} and
   *         {@link NlsFormatterPlugin#getStyle() style} and is now replaced by the given {@code formatter} or
   *         {@code null} if no {@link NlsFormatter} was replaced.
   */
  public NlsFormatter<?> registerFormatter(NlsFormatterPlugin formatter) {

    return registerFormatter(formatter, formatter.getType(), formatter.getStyle());
  }

  /**
   * This method registers the given {@code formatBuilder}.
   *
   * @param formatter is the formatter to register.
   * @param formatType is the type to be formatted.
   * @param formatStyle is the style defining details of formatting. May be {@code null} for default formatter.
   * @return the {@link NlsFormatter} that was registered for the given {@code formatType} and {@code formatStyle} and
   *         is now replaced by the given {@code formatter} or {@code null} if no {@link NlsFormatter} was replaced.
   */
  public NlsFormatter<?> registerFormatter(NlsFormatterPlugin formatter, String formatType, String formatStyle) {

    Objects.requireNonNull(formatter, "formatter");
    if (formatStyle != null) {
      Objects.requireNonNull(formatType, "formatType");
    }
    Map<String, NlsFormatterPlugin> style2builderMap = this.builders.get(formatType);
    if (style2builderMap == null) {
      style2builderMap = new HashMap<>();
      this.builders.put(formatType, style2builderMap);
    }
    return style2builderMap.put(formatStyle, formatter);
  }

  /**
   * @see io.github.mmm.nls.formatter.NlsFormatterManager#getFormatter(String, String)
   *
   * @param formatType is the type to be formatted.
   * @param formatStyle is the style defining details of formatting. May be {@code null} for default formatter of the
   *        given {@code formatType}.
   * @return the according {@link NlsFormatter} instance or {@code null} if NO such {@link NlsFormatter} is
   *         {@link #registerFormatter(NlsFormatterPlugin, String, String) registered} .
   */
  public NlsFormatterPlugin getFormatter(String formatType, String formatStyle) {

    NlsFormatterPlugin result = null;
    Map<String, NlsFormatterPlugin> style2builderMap = this.builders.get(formatType);
    if (style2builderMap != null) {
      result = style2builderMap.get(formatStyle);
      if ((result == null) && (formatStyle == null)) {
        result = style2builderMap.get(NlsFormatterManager.STYLE_MEDIUM);
      }
    }
    return result;
  }

}
