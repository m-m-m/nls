/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.nls.formatter;

/**
 * This interface extends {@link NlsFormatter} with the methods required to register this automatically as plugin. <br>
 * If no {@link NlsFormatterPlugin} with default {@link #getStyle() style} ({@code null}) is found for some
 * {@link #getType() type} the one with {@link NlsFormatterManager#STYLE_MEDIUM medium} style is used as default.
 */
public interface NlsFormatterPlugin extends NlsFormatter<Object> {

  /**
   * This method gets the {@link io.github.mmm.nls.formatter.NlsFormatterManager#getFormatter(String) type} of this
   * formatter. See {@code TYPE_*} constants of {@link io.github.mmm.nls.formatter.NlsFormatterManager} e.g.
   * {@link io.github.mmm.nls.formatter.NlsFormatterManager#TYPE_NUMBER}.
   *
   * @return the type or {@code null} for the {@link io.github.mmm.nls.formatter.NlsFormatterManager#getFormatter() default
   *         formatter}. If type is {@code null} then also {@link #getStyle() style} needs to be {@code null}.
   */
  String getType();

  /**
   * This method gets the {@link io.github.mmm.nls.formatter.NlsFormatterManager#getFormatter(String, String) style} of
   * this formatter. See {@code STYLE_*} constants of {@link io.github.mmm.nls.formatter.NlsFormatterManager} e.g.
   * {@link io.github.mmm.nls.formatter.NlsFormatterManager#STYLE_LONG}.
   *
   * @return the style or {@code null} for no style.
   */
  String getStyle();

}
