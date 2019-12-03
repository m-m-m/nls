/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.nls;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import io.github.mmm.base.compare.CompareOperator;
import io.github.mmm.base.justification.Justification;
import io.github.mmm.nls.argument.NlsArguments;
import io.github.mmm.nls.argument.NlsArgumentsKeys;
import io.github.mmm.nls.formatter.NlsFormatterManager;
import io.github.mmm.nls.formatter.NlsMessageFormatterFactory;

/**
 * Test of {@link NlsMessage} and {@link NlsMessageFactory}.
 */
@SuppressWarnings("all")
public class NlsMessageTest extends Assertions implements NlsArgumentsKeys {

  private static final char NON_BREAKING_SPACE = 0x000A0;

  /**
   * This method tests the {@link net.sf.mmm.util.nls.api.NlsMessage} using a custom resolver.
   */
  @Test
  public void testMessage() {

    String welcome = "Welcome ";
    String welcomeDe = "Willkommen ";
    String name = "Joelle";
    String value = "'" + name + "'";
    String suffix = "!";
    String key = KEY_NAME;
    String variable = "'{" + key + "}'";
    final String msg = welcome + variable + suffix;
    final String msgDe = welcomeDe + variable + suffix;
    NlsMessage testMessage = NlsMessageFactory.get().create(NlsBundleTest.BUNDLE, "msgWelcome", msg,
        NlsArguments.ofName(name));
    assertThat(testMessage.getInternationalizedMessage()).isEqualTo(msg);
    assertThat(testMessage.getArguments().get(key)).isEqualTo(name);
    assertThat(testMessage.getMessage()).isEqualTo(welcome + value + suffix);
    assertThat(testMessage.getLocalizedMessage(Locale.GERMAN)).isEqualTo(welcomeDe + value + suffix);
    // no l10n for Japanese
    assertThat(testMessage.getLocalizedMessage(Locale.JAPANESE)).isEqualTo(testMessage.getMessage());
  }

  @Test
  public void testCascadedMessage() {

    NlsMessageFactory factory = NlsMessageFactory.get();
    NlsMessage msgInteger = factory.create(NlsBundleTest.BUNDLE, "msgInteger", "integer");
    NlsMessage msgReal = factory.create(NlsBundleTest.BUNDLE, "msgRealRange", "real[{min},{max}]",
        NlsArguments.ofMinMax(Double.valueOf(-5), Double.valueOf(5)));

    NlsMessage cascadedMessage = factory.create(NlsBundleTest.BUNDLE, "errValueInvalidType",
        "The given value must be of the type '{expected}' but has the type '{actual}'!",
        NlsArguments.of("expected", msgInteger, "actual", msgReal));
    assertThat(cascadedMessage.getMessage())
        .isEqualTo("The given value must be of the type 'integer' but has the type 'real[-5,5]'!");
    assertThat(cascadedMessage.getLocalizedMessage(Locale.GERMAN))
        .isEqualTo("Der angegebene Wert muss vom Typ 'Ganze Zahl' sein, hat aber den Typ 'relle Zahl[-5,5]'!");
  }

  @Test
  public void testMessageWithEnumTranslation() {

    // given
    CompareOperator cmp = CompareOperator.GREATER_OR_EQUAL;
    Integer value = Integer.valueOf(42);
    NlsMessage message = NlsMessageFactory.get().create(NlsBundleTest.BUNDLE, "errIllegalValue",
        "The value has to be {comparator} '{value}'!", NlsArguments.of("comparator", cmp, "value", value));

    // when + then
    assertThat(message.getLocalizedMessage(Locale.ROOT)).isEqualTo("The value has to be greater or equal to '42'!");
    assertThat(message.getLocalizedMessage(Locale.GERMAN)).isEqualTo("Der Wert muss größer oder gleich '42' sein!");
  }

  /**
   * Tests {@link NlsFormatterManager#TYPE_DATETIME datetime format}.
   */
  @Test
  public void testMessageWithDateTime() {

    OffsetDateTime offsetDateTime = OffsetDateTime.parse("1999-12-31T23:59:59+01:00");
    Date date = Date.from(offsetDateTime.toInstant());
    NlsMessage msg = NlsMessageFactory.get().create(NlsBundleTest.BUNDLE, "msgTestDate",
        "Date formatted by locale: {date}, by ISO-8601: {date,datetime,iso8601} and by custom pattern: {date,date,yyyy.MM.dd-HH:mm:ssZ}!",
        NlsArguments.ofDate(date));
    // Make os/locale independent...
    TimeZone.setDefault(TimeZone.getTimeZone("GMT+01:00"));
    String dateString = formatDate(date, Locale.ROOT);
    assertThat(msg.getMessage()).isEqualTo(
        "Date formatted by locale: 1999-12-31 23:59:59 GMT+01:00, by ISO-8601: 1999-12-31T23:59:59+0100 and by custom pattern: 1999.12.31-23:59:59+0100!");
    Locale german = Locale.GERMAN;
    String dateStringDe = formatDate(date, german);
    assertThat(msg.getLocalizedMessage(german)).isEqualTo("Datum formatiert nach Locale: " + dateStringDe
        + ", nach ISO-8601: 1999-12-31T23:59:59+0100 und nach individueller Vorlage: 1999.12.31-23:59:59+0100!");
  }

  /**
   * Tests {@link NlsFormatterManager#TYPE_DATETIME datetime format} with custom pattern.
   */
  @Test
  public void testMessageWithDateTimeCustomPattern() {

    OffsetDateTime offsetDateTime = OffsetDateTime.parse("1999-12-31T23:59:59+01:00");
    Date date = Date.from(offsetDateTime.toInstant());
    NlsMessage msg = NlsMessageFactory.get().create(NlsBundleTest.BUNDLE, "non-existent-key",
        "{date,date,yyyyMMdd_HHmmss}", NlsArguments.ofDate(date));
    assertThat(msg.getMessage()).isEqualTo("19991231_235959");
  }

  public void testMessageWithAllDateFormats() {

    OffsetDateTime offsetDateTime = OffsetDateTime.parse("1999-12-31T23:59:59+01:00");
    Date date = Date.from(offsetDateTime.toInstant());
    NlsMessageFactory factory = NlsMessageFactory.get();
    NlsArguments arguments = NlsArguments.ofDate(date);
    String[] types = new String[] { NlsFormatterManager.TYPE_DATE, NlsFormatterManager.TYPE_TIME,
    NlsFormatterManager.TYPE_DATETIME };
    String[] styles = new String[] { NlsFormatterManager.STYLE_SHORT, NlsFormatterManager.STYLE_MEDIUM,
    NlsFormatterManager.STYLE_LONG, NlsFormatterManager.STYLE_FULL, null };
    int[] dateStyles = new int[] { DateFormat.SHORT, DateFormat.MEDIUM, DateFormat.LONG, DateFormat.FULL,
    DateFormat.MEDIUM };
    Locale locale = Locale.GERMANY;
    for (String type : types) {
      for (int styleIndex = 0; styleIndex < styles.length; styleIndex++) {
        String style = styles[styleIndex];
        int dateStyle = dateStyles[styleIndex];
        String message = "{date," + type;
        if (style != null) {
          message = message + "," + style;
        }
        message = message + "}";
        NlsMessage msg = factory.create(NlsBundleTest.BUNDLE, "non-existent-key", message, arguments);
        DateFormat dateFormat;
        if (type == NlsFormatterManager.TYPE_DATE) {
          dateFormat = DateFormat.getDateInstance(dateStyle, locale);
        } else if (type == NlsFormatterManager.TYPE_TIME) {
          dateFormat = DateFormat.getTimeInstance(dateStyle, locale);
        } else if (type == NlsFormatterManager.TYPE_DATETIME) {
          dateFormat = DateFormat.getDateTimeInstance(dateStyle, dateStyle, locale);
        } else {
          throw new IllegalStateException(type);
        }
        String localizedMessage = msg.getLocalizedMessage(locale);
        String expected = dateFormat.format(date);
        assertThat(localizedMessage).as("wrong result for message: " + message + "!").isEqualTo(expected);
        // double check!
        expected = new MessageFormat(message, locale).format(new Object[] { date });
        assertThat(localizedMessage).as("wrong result for message: " + message + "!").isEqualTo(expected);
      }
    }
  }

  private String formatDate(Date date, Locale locale) {

    return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG, locale).format(date);
  }

  /**
   * Tests {@link NlsFormatterManager#TYPE_NUMBER number format}.
   */
  @Test
  public void testMessageWithNumber() {

    Number number = Double.valueOf(0.42);
    NlsMessage msg = NlsMessageFactory.get().create(NlsBundleTest.BUNDLE, "msgTestNumber",
        "Number formatted by default: {value}, as percent: {value,number,percent}, as currency: {value,number,currency} and by custom pattern: {value,number,'#'##.##}!",
        NlsArguments.ofValue(number));
    String nonBreakingSpace = "" + NON_BREAKING_SPACE;
    assertThat(msg.getMessage().replace(nonBreakingSpace, "")).isEqualTo(
        "Number formatted by default: 0.42, as percent: 42%, as currency: \u00a40.42 and by custom pattern: #0.42!");
    assertThat(msg.getLocalizedMessage(Locale.GERMANY).replace(nonBreakingSpace, "")).isEqualTo(
        "Zahl formatiert nach Standard: 0,42, in Prozent: 42%, als Währung: 0,42\u20ac und nach individueller Vorlage: #0,42!");
  }

  /**
   * Tests {@link NlsFormatterManager#TYPE_NUMBER number format}.
   */
  @Test
  public void testMessageWithChoice() {

    NlsMessage msg;
    String message;

    // boolean choice
    message = "{value,choice,(?==true)'foo'(else)'bar'}";
    NlsMessageFactory factory = NlsMessageFactory.get();
    msg = factory.create(NlsBundleTest.BUNDLE, "dummy-key", message, NlsArguments.ofValue(Boolean.TRUE));
    assertThat(msg.getMessage()).isEqualTo("foo");
    msg = factory.create(NlsBundleTest.BUNDLE, "dummy-key", message, NlsArguments.ofValue(Boolean.FALSE));
    assertThat(msg.getMessage()).isEqualTo("bar");

    // numeric choice
    message = "{value,choice,(?==1)'one'(?>1)'many'(?<0)'negative'(else)'zero'}";
    msg = factory.create(NlsBundleTest.BUNDLE, "dummy-key", message, NlsArguments.ofValue(1));
    assertThat(msg.getMessage()).isEqualTo("one");
    msg = factory.create(NlsBundleTest.BUNDLE, "dummy-key", message, NlsArguments.ofValue(2));
    assertThat(msg.getMessage()).isEqualTo("many");
    msg = factory.create(NlsBundleTest.BUNDLE, "dummy-key", message, NlsArguments.ofValue(-1));
    assertThat(msg.getMessage()).isEqualTo("negative");
    msg = factory.create(NlsBundleTest.BUNDLE, "dummy-key", message, NlsArguments.ofValue(0));
    assertThat(msg.getMessage()).isEqualTo("zero");

    // date choice
    message = "{value,choice,(?==2010-01-31T23:59:59Z)'special day'(?>2010-01-31T23:59:59Z)'after'(else)'before'}";
    LocalDateTime datetime = LocalDateTime.of(2010, 01, 31, 23, 59, 59);
    msg = factory.create(NlsBundleTest.BUNDLE, "dummy-key", message, NlsArguments.ofValue(datetime));
    assertThat(msg.getMessage()).isEqualTo("special day");
    msg = factory.create(NlsBundleTest.BUNDLE, "dummy-key", message,
        NlsArguments.ofValue(datetime.plus(1, ChronoUnit.SECONDS)));
    assertThat(msg.getMessage()).isEqualTo("after");
    msg = factory.create(NlsBundleTest.BUNDLE, "dummy-key", message,
        NlsArguments.ofValue(datetime = datetime.minus(1, ChronoUnit.SECONDS)));
    assertThat(msg.getMessage()).isEqualTo("before");

    // string choice
    message = "{value,choice,(?=='hello')'magic'(?>'hello')'after'(else)'before'}";
    msg = factory.create(NlsBundleTest.BUNDLE, "dummy-key", message, NlsArguments.ofValue("hello"));
    assertThat(msg.getMessage()).isEqualTo("magic");
    msg = factory.create(NlsBundleTest.BUNDLE, "dummy-key", message, NlsArguments.ofValue("hella"));
    assertThat(msg.getMessage()).isEqualTo("before");
    msg = factory.create(NlsBundleTest.BUNDLE, "dummy-key", message, NlsArguments.ofValue("hellp"));
    assertThat(msg.getMessage()).isEqualTo("after");

    // test quotation-symbol
    message = "{value,choice,(?=='a\"''b')\"a'\"\"b\"(else)''''}";
    msg = factory.create(NlsBundleTest.BUNDLE, "dummy-key", message, NlsArguments.ofValue("a\"'b"));
    assertThat(msg.getMessage()).isEqualTo("a'\"b");

    // test nested choice
    String key2 = "key2";
    String key3 = "key3";
    message = "{value,choice,(?==true)'foo'(else){" + key2 + ",choice,(?==true)'bar'(else){" + key3 + "}}}";
    msg = factory.create(NlsBundleTest.BUNDLE, "dummy-key", message, NlsArguments.ofValue(Boolean.TRUE));
    assertThat(msg.getMessage()).isEqualTo("foo");
    msg = factory.create(NlsBundleTest.BUNDLE, "dummy-key", message,
        NlsArguments.of(KEY_VALUE, Boolean.FALSE, key2, Boolean.TRUE));
    assertThat(msg.getMessage()).isEqualTo("bar");
    msg = factory.create(NlsBundleTest.BUNDLE, "dummy-key", message,
        NlsArguments.of(KEY_VALUE, Boolean.FALSE, key2, Boolean.FALSE, key3, key3));
    assertThat(msg.getMessage()).isEqualTo(key3);

    // test missing else
    try {
      NlsMessageFormatterFactory.get().create("{key,choice,(?==true)'foo'}");
      failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
    } catch (IllegalArgumentException e) {
      Throwable t = e;
      while (t != null) {
        t = t.getCause();
        if (t instanceof IllegalStateException) {
          break;
        }
      }
      assertThat(t).hasMessage("no (else) condition!");
    }

    // test only else
    try {
      NlsMessageFormatterFactory.get().create("{key,choice,(else)'foo'}");
      failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
    } catch (IllegalArgumentException e) {
      Throwable t = e;
      while (t != null) {
        t = t.getCause();
        if (t instanceof IllegalStateException) {
          break;
        }
      }
      assertThat(t).hasMessage("only (else) condition!");
    }
  }

  /**
   * Tests {@link NlsMessage message} with {@link Justification}.
   */
  @Test
  public void testMessageWithJustification() {

    String key = "value";
    Integer value = Integer.valueOf(42);

    // right
    NlsMessageFactory factory = NlsMessageFactory.get();
    NlsArguments arguments = NlsArguments.ofValue(value);
    NlsMessage msg = factory.create(NlsBundleTest.BUNDLE, "dummy-key", "{value{0+4}}", arguments);
    assertThat(msg.getMessage()).isEqualTo("0042");
    // left
    msg = factory.create(NlsBundleTest.BUNDLE, "dummy-key", "{value{.-4}}", arguments);
    assertThat(msg.getMessage()).isEqualTo("42..");
    // center
    msg = factory.create(NlsBundleTest.BUNDLE, "dummy-key", "{value{_~11}}", arguments);
    assertThat(msg.getMessage()).isEqualTo("____42_____");
    // combined
    msg = factory.create(NlsBundleTest.BUNDLE, "dummy-key", "Value {value,number,currency{_+15}}", arguments);
    String message = msg.getLocalizedMessage(Locale.GERMANY);
    message = message.replace(NON_BREAKING_SPACE, ' ');
    assertThat(message).isEqualTo("Value ________42,00 €");
  }

  /**
   * Tests {@link NlsFormatterManager#TYPE_TYPE type format}.
   */
  @Test
  public void testMessageWithType() throws Exception {

    Method method = Generic.class.getMethod("get", new Class[0]);
    Type type = method.getGenericReturnType();
    String key = "key";
    NlsMessage msg;
    NlsArguments arguments = NlsArguments.ofType(type);
    msg = NlsMessageFactory.get().create(NlsBundleTest.BUNDLE, "key", "{type,type}", arguments);
    assertThat(msg.getMessage()).isEqualTo("java.util.Map");
    msg = NlsMessageFactory.get().create(NlsBundleTest.BUNDLE, "key", "{type,type,short}", arguments);
    assertThat(msg.getMessage()).isEqualTo("Map");
    msg = NlsMessageFactory.get().create(NlsBundleTest.BUNDLE, "key", "{type,type,medium}", arguments);
    assertThat(msg.getMessage()).isEqualTo("java.util.Map");
    msg = NlsMessageFactory.get().create(NlsBundleTest.BUNDLE, "key", "{type,type,long}", arguments);
    assertThat(msg.getMessage()).isEqualTo(
        "java.util.Map<java.util.List<? extends String>, java.util.List<java.util.Map<? extends Object, ? super VARIABLE[]>>>");
    msg = NlsMessageFactory.get().create(NlsBundleTest.BUNDLE, "key", "{type,type,full}", arguments);
    assertThat(msg.getMessage()).isEqualTo(
        "java.util.Map<java.util.List<? extends java.lang.String>, java.util.List<java.util.Map<? extends java.lang.Object, ? super VARIABLE[]>>>");
  }

  public static class Generic {

    public <VARIABLE> java.util.Map<java.util.List<? extends String>, java.util.List<java.util.Map<? extends Object, ? super VARIABLE[]>>> get() {

      return null;
    }
  }
}
