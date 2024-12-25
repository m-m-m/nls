/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.nls.formatter.impl.plugin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;

import io.github.mmm.base.compare.CompareOperator;
import io.github.mmm.base.filter.CharFilter;
import io.github.mmm.base.filter.ListCharFilter;
import io.github.mmm.nls.argument.NlsArguments;
import io.github.mmm.nls.formatter.NlsFormatterManager;
import io.github.mmm.nls.formatter.NlsVariableFormatter;
import io.github.mmm.nls.variable.NlsVariable;
import io.github.mmm.nls.variable.NlsVariableParser;
import io.github.mmm.scanner.CharSequenceScanner;

/**
 * This is the implementation of {@link io.github.mmm.nls.formatter.NlsFormatter} for
 * {@link io.github.mmm.nls.formatter.NlsFormatterManager#TYPE_CHOICE choice-format}. <br>
 * Examples:<br>
 * <table border="1">
 * <tr>
 * <th>{@link io.github.mmm.nls.NlsMessage}</th>
 * <th>Example result</th>
 * </tr>
 * <tr>
 * <td>{deleteCount} {deleteCount,choice,(?==1)'files'(else)'file'} deleted.</td>
 * <td>1 file deleted.</td>
 * </tr>
 * <tr>
 * <td>{flag,choice,(?==true){date}(else){time}}</td>
 * <td>23:59:59</td>
 * </tr>
 * </table>
 * <b>Note:</b><br>
 * Literal text in choice format has to be enclosed by single (') or double (") quotes. If you need to use such quote
 * inside the text either use a different enclosing quote or use a duplicated quote sign to escape the quote. As a
 * single quote is often used in languages such as French or Spanish it is better to escape with double quotes.
 */
public class NlsFormatterPluginChoice extends AbstractNlsFormatterPlugin {

  /** The character used to indicate the start of a Choice condition. */
  public static final char CONDITION_START = '(';

  /** The character used to indicate the end of a Choice condition. */
  public static final char CONDITION_END = ')';

  /**
   * The character used to indicate the variable object of a Choice condition.
   */
  public static final char CONDITION_VAR = '?';

  /** The value of a Choice condition that matches always. */
  public static final String CONDITION_ELSE = "else";

  /** The {@link Filter} for {@link #CONDITION_ELSE}. */
  private static final Predicate<Object> FILTER_ELSE = new Condition(null, null);

  /**
   * The {@link CharFilter} for the {@link CompareOperator#getSyntax() comparator symbol} .
   */
  private static final CharFilter FILTER_COMPARATOR = new ListCharFilter("<=>!");

  /** The {@link CharFilter} for the comparator argument. */
  private static final CharFilter FILTER_COMPARATOR_ARGUMENT = CharFilter.LATIN_LETTER_OR_DIGIT
      .compose(new ListCharFilter("-+.:"));

  /** The {@link Choice}s. */
  private final List<Choice> choices;

  /**
   * The constructor.
   *
   * @param scanner is the {@link CharSequenceScanner} pointing to the choice- {@code formatStyle}.
   */
  public NlsFormatterPluginChoice(CharSequenceScanner scanner) {

    super();
    this.choices = new ArrayList<>();
    boolean hasElse = false;
    int cp = scanner.peek();
    while ((cp == CONDITION_START) && (!hasElse)) {
      scanner.next();
      Choice choice = parseChoice(scanner);
      if (choice.condition == FILTER_ELSE) {
        hasElse = true;
      }
      this.choices.add(choice);
      cp = scanner.peek();
    }
    if (!hasElse) {
      throw new IllegalStateException("no (else) condition!");
    }
    if (this.choices.size() < 2) {
      throw new IllegalStateException("only (else) condition!");
    }
  }

  /**
   * This method parses the {@link Choice}.
   *
   * @param scanner is the {@link CharSequenceScanner}.
   * @return the parsed {@link Choice}.
   */
  private Choice parseChoice(CharSequenceScanner scanner) {

    Predicate<Object> condition = parseCondition(scanner);
    List<Segment> segments = new ArrayList<>();
    while (scanner.hasNext()) {
      int index = scanner.getCurrentIndex();
      int cp = scanner.peek();
      String literal = null;
      if ((cp == '"') || (cp == '\'')) {
        scanner.next();
        literal = scanner.readUntil(cp, false, cp);
        if (literal == null) {
          throw new IllegalArgumentException(scanner.substring(index, scanner.getCurrentIndex()));
        }
        cp = scanner.peek();
      }
      NlsVariable variable = null;
      if (cp == NlsVariableParser.START_EXPRESSION) {
        scanner.next();
        variable = NlsVariableParser.get().parse(scanner);
      }
      if ((variable != null) || (literal != null)) {
        segments.add(new Segment(literal, variable));
      } else {
        break;
      }
    }
    return new Choice(condition, segments);
  }

  /**
   * This method parses the {@link Condition}.
   *
   * @param scanner is the {@link CharSequenceScanner}.
   * @return the parsed {@link Condition} or {@link #FILTER_ELSE} in case of {@link #CONDITION_ELSE}.
   */
  private Predicate<Object> parseCondition(CharSequenceScanner scanner) {

    int index = scanner.getCurrentIndex();
    Predicate<Object> condition;
    if (scanner.expectOne(CONDITION_VAR)) {
      // variable choice
      String symbol = scanner.readWhile(FILTER_COMPARATOR);
      CompareOperator comparator = CompareOperator.ofSymbol(symbol);
      if (comparator == null) {
        throw new IllegalArgumentException(symbol);
      }
      Comparable<?> comparatorArgument = parseComparatorArgument(scanner);
      condition = new Condition(comparator, comparatorArgument);
    } else if (scanner.expectUnsafe(CONDITION_ELSE, false)) {
      condition = FILTER_ELSE;
    } else {
      throw new IllegalArgumentException(scanner.substring(index, scanner.getCurrentIndex()));
    }
    if (!scanner.expectOne(CONDITION_END)) {
      throw new IllegalArgumentException(scanner.substring(index, scanner.getCurrentIndex()));
    }
    return condition;
  }

  /**
   * This method parses the {@link Condition#comparatorArgument comparator argument}.
   *
   * @param scanner is the {@link CharSequenceScanner}.
   * @return the parsed comparator argument.
   */
  private Comparable<?> parseComparatorArgument(CharSequenceScanner scanner) {

    int index = scanner.getCurrentIndex();
    Comparable<?> comparatorArgument;
    int cp = scanner.peek();
    if ((cp == '"') || (cp == '\'')) {
      scanner.next();
      comparatorArgument = scanner.readUntil(cp, false, cp);
    } else {
      String argument = scanner.readWhile(FILTER_COMPARATOR_ARGUMENT);
      if (argument.length() == 0) {
        throw new IllegalArgumentException(scanner.substring(index, scanner.getCurrentIndex()));
      }
      if ("null".equals(argument)) {
        comparatorArgument = null;
      } else if ("true".equals(argument)) {
        comparatorArgument = Boolean.TRUE;
      } else if ("false".equals(argument)) {
        comparatorArgument = Boolean.FALSE;
      } else {
        // Temporal temporal = TemporalParser.parse(argument);
        // if (temporal != null) {
        // comparatorArgument = (Comparable<?>) temporal;
        // } else {
        comparatorArgument = Double.valueOf(argument);
        // }
      }
    }
    return comparatorArgument;
  }

  @Override
  public void doFormat(Object object, Locale locale, NlsArguments arguments, Appendable buffer) throws IOException {

    for (Choice choice : this.choices) {
      if (choice.condition.test(object)) {
        for (Segment segment : choice.segments) {
          buffer.append(segment.literal);
          if (segment.variable != null) {
            NlsVariableFormatter.get().format(segment.variable, locale, arguments, buffer);
          }
        }
        return;
      }
    }
    buffer.append(toString());
  }

  @Override
  public String getType() {

    return NlsFormatterManager.TYPE_CHOICE;
  }

  @Override
  public String getStyle() {

    return null;
  }

  /**
   * @return the {@link List} of {@link Choice}s. Do not modify.
   */
  public List<Choice> getChoices() {

    return this.choices;
  }

  @Override
  public String toString() {

    StringBuilder sb = new StringBuilder();
    sb.append(NlsFormatterManager.TYPE_CHOICE);
    sb.append(",");
    for (Choice choice : this.choices) {
      sb.append(choice);
    }
    return sb.toString();
  }

  /**
   * This inner class represents a single choice.
   */
  public static final class Choice {

    /** The condition that determines when the choice applies. */
    private final Predicate<Object> condition;

    /** The segments */
    private final List<Segment> segments;

    /**
     * The constructor.
     *
     * @param condition is the {@link #condition}.
     * @param segments is the {@link List} of {@link Segment}s.
     */
    private Choice(Predicate<Object> condition, List<Segment> segments) {

      super();
      this.condition = condition;
      this.segments = segments;
    }

    /**
     * @return the condition that {@link Predicate#test(Object) determines} if this {@link Choice} will match.
     */
    public Predicate<Object> getCondition() {

      return this.condition;
    }

    /**
     * @return {@code true} if {@link #getCondition() condition} is '(else)', {@code false} otherwise.
     */
    public boolean isElse() {

      return (this.condition == FILTER_ELSE);
    }

    /**
     * @return the {@link List} of {@link Segment}s. Do not modify this {@link List}.
     */
    public List<Segment> getSegments() {

      return this.segments;
    }

    @Override
    public String toString() {

      StringBuilder buffer = new StringBuilder();
      buffer.append(this.condition);
      for (Segment segment : this.segments) {
        buffer.append('\'');
        buffer.append(segment.literal.replace("'", "''"));
        buffer.append('\'');
        if (segment.variable != null) {
          buffer.append(segment.variable);
        }
      }
      return buffer.toString();
    }
  }

  /**
   * This inner class represents a single segment of a {@link Choice}.
   */
  public static class Segment {

    private final String literal;

    private final NlsVariable variable;

    /**
     * The constructor.
     *
     * @param literal is the literal (prefix).
     * @param variable is the {@link NlsVariable} or {@code null} if this is the last {@link Segment}.
     */
    public Segment(String literal, NlsVariable variable) {

      super();
      if (literal == null) {
        this.literal = "";
      } else {
        this.literal = literal;
      }
      this.variable = variable;
    }

    /**
     * @return the literal (static text).
     */
    public String getLiteral() {

      return this.literal;
    }

    /**
     * @return the dynamic {@link NlsVariable}.
     */
    public NlsVariable getVariable() {

      return this.variable;
    }

  }

  /**
   * This inner class represents a single choice.
   */
  @SuppressWarnings("rawtypes")
  private static class Condition implements Predicate<Object> {

    /** The {@link CompareOperator}. */
    private final CompareOperator comparator;

    /** The argument for the {@link #comparator}. */
    private final Comparable comparatorArgument;

    private final double numericArgument;

    /**
     * The constructor.
     *
     * @param comparator is the {@link #comparator}.
     * @param comparatorArgument is the {@link #comparatorArgument}.
     */
    public Condition(CompareOperator comparator, Comparable comparatorArgument) {

      super();
      this.comparator = comparator;
      if (comparatorArgument instanceof Double) {
        this.comparatorArgument = null;
        this.numericArgument = ((Double) comparatorArgument).doubleValue();
      } else {
        this.comparatorArgument = comparatorArgument;
        this.numericArgument = 0;
      }
    }

    @Override
    public boolean test(Object value) {

      if (this.comparator == null) {
        return true;
      }
      if (this.comparatorArgument == null) {
        if (value instanceof Number) {
          return this.comparator.evalDouble(((Number) value).doubleValue(), this.numericArgument);
        } else {
          return false; // invalid
        }
      } else {
        return this.comparator.evalObject(value, this.comparatorArgument);
      }
    }

    @Override
    public String toString() {

      if (this.comparator == null) {
        return "(else)";
      }
      StringBuilder buffer = new StringBuilder();
      buffer.append("(?");
      buffer.append(this.comparator.getSymbol());
      if (this.comparatorArgument == null) {
        buffer.append(this.numericArgument);
      } else {
        buffer.append(this.comparatorArgument);
      }
      buffer.append(")");
      return buffer.toString();
    }
  }

}
