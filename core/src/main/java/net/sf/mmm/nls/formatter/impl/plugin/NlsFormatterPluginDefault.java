/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.nls.formatter.impl.plugin;

import java.io.IOException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

import net.sf.mmm.nls.NlsMessage;
import net.sf.mmm.nls.NlsObject;
import net.sf.mmm.nls.argument.NlsArguments;

/**
 * Default implementation of {@link AbstractNlsFormatterPlugin}.
 */
public class NlsFormatterPluginDefault extends AbstractNlsFormatterPlugin {

  @Override
  public void doFormat(Object object, Locale locale, NlsArguments arguments, Appendable buffer) throws IOException {

    String result = null;
    if (object != null) {
      if (object instanceof Number) {
        result = NumberFormat.getInstance(locale).format(object);
      } else if (object instanceof Date) {
        DateFormat format = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG, locale);
        result = format.format(object);
      } else if (object instanceof NlsObject) {
        NlsMessage message = ((NlsObject) object).toNlsMessage();
        if (message != null) {
          message.getLocalizedMessage(locale, buffer);
          return;
        }
      } else if (object instanceof Class) {
        result = ((Class<?>) object).getName();
        // } else if (object instanceof Enum) {
        // NlsMessage message = NlsMessageFactory.get().create(object.toString());
        // message.getLocalizedMessage(locale, buffer);
        // return;
      } else {
        result = object.toString();
      }
    }
    if (result == null) {
      result = "null";
    }
    buffer.append(result);
  }

  @Override
  public String getStyle() {

    return null;
  }

  @Override
  public String getType() {

    return null;
  }

}
