/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.example;

import net.sf.mmm.nls.NlsBundle;
import net.sf.mmm.nls.NlsMessage;
import net.sf.mmm.nls.argument.NlsArguments;

@SuppressWarnings("all")
public class NlsBundleExample extends NlsBundle {

  public static final NlsBundleExample INSTANCE = new NlsBundleExample();

  public NlsMessage msgWelcome(String name) {

    return create("msgWelcome", "Welcome '{name}'!", NlsArguments.ofName(name));
  }

  public NlsMessage msgBye(String name) {

    return create("msgBye", "Bye,bye \"{name}\"!", NlsArguments.ofName(name));
  }

  public NlsMessage msgTestDate(Object date) {

    return create("msgTestDate",
        "Date formatted by locale: {date}, by ISO-8601: {date,datetime,iso8601} and by custom pattern: {date,date,yyyy.MM.dd-HH:mm:ssZ}!",
        NlsArguments.ofDate(date));
  }

  public NlsMessage msgTestNumber(Number value) {

    return create("msgTestNumber",
        "Number formatted by default: {value}, as percent: {value,number,percent}, as currency: {value,number,currency} and by custom pattern: {value,number,'#'##.##}!",
        NlsArguments.ofValue(value));
  }

  public NlsMessage errNull(Object source) {

    return create("errNull", "NullPointerException caused by {source}!", NlsArguments.ofValue(source));
  }

}
