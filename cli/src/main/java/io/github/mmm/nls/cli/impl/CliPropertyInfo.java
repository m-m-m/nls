/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.nls.cli.impl;

import io.github.mmm.nls.cli.property.CliCollectionProperty;
import io.github.mmm.nls.cli.property.CliFlagProperty;
import io.github.mmm.nls.cli.property.CliProperty;

/**
 * Container for a {@link CliProperty} together with {@link #getSyntax() syntax} and {@link #getUsage() usage}.
 *
 * @since 1.0.0
 */
public class CliPropertyInfo {

  private final CliProperty<?> property;

  private final String syntax;

  private final String usage;

  /**
   * The constructor.
   *
   * @param property the {@link CliProperty}.
   */
  public CliPropertyInfo(CliProperty<?> property) {

    super();
    this.property = property;
    StringBuilder sb = new StringBuilder();
    char separator = 0;
    for (String name : property.getNames()) {
      if (separator == 0) {
        separator = '|';
      } else {
        sb.append(separator);
      }
      sb.append(name);
    }
    this.syntax = sb.toString();
    sb.setLength(0);
    boolean mandatory = property.isMandatory();
    if (!mandatory) {
      sb.append('[');
    }
    boolean option = property.isOption();
    if (!option) {
      sb.append('<');
    }
    sb.append(this.syntax);
    if (!option) {
      sb.append('>');
    } else if (!(property instanceof CliFlagProperty)) {
      sb.append(" <");
      String name = property.getName();
      if (name.startsWith("--")) {
        name = name.substring(2);
      } else {
        name = "value";
      }
      sb.append(name);
      sb.append('>');
      if (property instanceof CliCollectionProperty) {
        sb.append("...");
      }
    }
    if (!mandatory) {
      sb.append(']');
    }
    this.usage = sb.toString();
  }

  /**
   * @return the {@link CliProperty}.
   */
  public CliProperty<?> getProperty() {

    return this.property;
  }

  /**
   * @return the plain syntax for help (e.g. "--locale|-l").
   */
  public String getSyntax() {

    return this.syntax;
  }

  /**
   * @return the usage (e.g. "[--locale|-l <locale>]").
   */
  public String getUsage() {

    return this.usage;
  }

  @Override
  public String toString() {

    return this.property.toString();
  }

}
