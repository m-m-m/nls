/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.nls.formatter.impl.plugin;

import java.io.IOException;
import java.text.Format;
import java.util.Locale;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.mmm.nls.argument.NlsArguments;

/**
 * Abstract base implementation of {@link io.github.mmm.nls.formatter.NlsFormatterPlugin} based on {@link Format}.
 */
public abstract class AbstractNlsFormatterPluginSimple extends AbstractNlsFormatterPlugin {

  private static final Logger LOG = LoggerFactory.getLogger(AbstractNlsFormatterPluginSimple.class);

  /**
   * The constructor.
   */
  public AbstractNlsFormatterPluginSimple() {

    super();
  }

  /**
   * @param locale is the locale for the {@link Format} to create.
   * @return the according {@link Format}.
   */
  protected abstract Format createFormatter(Locale locale);

  @Override
  public String format(Object object, Locale locale, NlsArguments arguments) {

    Format format = createFormatter(locale);
    return format.format(object);
  }

  @Override
  protected void doFormat(Object object, Locale locale, NlsArguments arguments, Appendable buffer) throws IOException {

    String text;
    try {
      text = format(object, locale, arguments);
    } catch (Exception e) {
      LOG.info("Invalid message format", e);
      text = Objects.toString(object);
    }
    buffer.append(text);
  }

}
