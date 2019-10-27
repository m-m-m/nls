/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.nls.formatter.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import net.sf.mmm.nls.argument.NlsArguments;
import net.sf.mmm.nls.formatter.NlsArgumentFormatter;
import net.sf.mmm.nls.formatter.NlsMessageFormatter;
import net.sf.mmm.nls.variable.NlsVariable;
import net.sf.mmm.nls.variable.NlsVariableParser;
import net.sf.mmm.scanner.CharScannerSyntaxBean;
import net.sf.mmm.scanner.CharSequenceScanner;

/**
 * Implementation of {@link net.sf.mmm.nls.formatter.NlsMessageFormatter}. <br>
 * <b>NOTE:</b><br>
 * This is more or less a rewrite of {@link java.text.MessageFormat} and is syntax-compatible with the
 * {@link java.text.MessageFormat#applyPattern(String) MessageFormat-pattern}-format. Some special (and somewhat sick)
 * features as modifying internal {@link java.text.Format}s or {@link java.text.FieldPosition} are NOT supported.
 * Currently also parsing is NOT supported. <br>
 * Instead this implementation is immutable and thread-safe. Further it works on any {@link Appendable} rather than only
 * on {@link StringBuffer}. It also uses the same {@link Appendable} for recursive invocations.
 */
public class NlsMessageFormatterImpl extends AbstractNlsFormatter<Void> implements NlsMessageFormatter {

  /** The syntax of the message-format patterns. */
  private static final CharScannerSyntaxBean SYNTAX = new CharScannerSyntaxBean();

  static {
    SYNTAX.setQuote('\'');
    SYNTAX.setQuoteEscape('\'');
    SYNTAX.setQuoteEscapeLazy(true);
  }

  /** The parsed segments of the message pattern. */
  private final PatternSegment[] segments;

  /** The suffix (last segment) of the pattern. */
  private final String suffix;

  /**
   * The constructor.
   *
   * @param pattern is the pattern of the message to format. It is syntax-compatible with
   *        {@link java.text.MessageFormat}.
   */
  public NlsMessageFormatterImpl(String pattern) {

    super();
    List<PatternSegment> segmentList = new ArrayList<>();
    CharSequenceScanner scanner = new CharSequenceScanner(pattern);
    String prefix = scanner.readUntil(NlsVariableParser.START_EXPRESSION, true, '\\');
    while (scanner.hasNext()) {
      NlsVariable variable;
      int index = scanner.getCurrentIndex() - 1;
      try {
        variable = NlsVariableParser.get().parse(scanner);
      } catch (Exception e) {
        throw new IllegalArgumentException(scanner.substring(index, scanner.getCurrentIndex()), e);
      }
      PatternSegment segment = new PatternSegment(prefix, variable);
      segmentList.add(segment);
      prefix = scanner.readUntil(NlsVariableParser.START_EXPRESSION, true, '\\');
    }
    this.suffix = prefix;
    this.segments = segmentList.toArray(new PatternSegment[segmentList.size()]);
  }

  @Override
  public final void doFormat(Void nothing, Locale locale, NlsArguments arguments, Appendable buffer)
      throws IOException {

    for (PatternSegment segment : this.segments) {
      buffer.append(segment.prefix);
      NlsArgumentFormatter.get().format(segment.variable, locale, arguments, buffer);
    }
    buffer.append(this.suffix);
  }

  /**
   * @return the suffix to append after all {@link PatternSegment}s.
   */
  public String getSuffix() {

    return this.suffix;
  }

  /**
   * This inner class represents a segment out of the parsed message-pattern. <br>
   * E.g. if the message-pattern is "Hi {name} you have {count} items!" then it is parsed into two
   * {@link PatternSegment}s. The first has a {@link #getPrefix() prefix} of {@code "Hi "} and {@link #getVariable()
   * variable} of <code>{name}</code> and the second has {@code " you have "} as {@link #getPrefix() prefix} and
   * {@link #getVariable() variable} of <code>{count}</code>. The rest of the pattern which is {@code " items!"} will be
   * stored in {@link NlsMessageFormatterImpl#getSuffix() suffix}.
   */
  protected static class PatternSegment {

    private final String prefix;

    private final NlsVariable variable;

    /**
     * The constructor.
     *
     * @param prefix is the {@link #getPrefix() prefix}.
     * @param variable is the {@link #getVariable() variable}.
     */
    public PatternSegment(String prefix, NlsVariable variable) {

      super();
      this.prefix = prefix;
      this.variable = variable;
    }

    /**
     * @return the static prefix from the message-pattern (until the next '{') that will be appended as is.
     */
    public String getPrefix() {

      return this.prefix;
    }

    /**
     * @return the {@link NlsVariable} to format and append after the {@link #getPrefix() prefix}.
     */
    public NlsVariable getVariable() {

      return this.variable;
    }

    @Override
    public String toString() {

      StringBuilder sb = new StringBuilder();
      sb.append(this.prefix);
      sb.append(this.variable);
      return sb.toString();
    }

  }

}
