/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.nls.cli;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import io.github.mmm.base.i18n.Localizable;
import io.github.mmm.cli.io.CliConsole;
import io.github.mmm.nls.cli.property.CliProperty;

/**
 * Abstract base implementation of {@link CliCommand}.
 *
 * @since 1.0.0
 */
public abstract class AbstractCliCommand implements CliCommand {

  private final Localizable help;

  private final List<CliProperty<?>> properties;

  private int lastOptionIndex;

  NlsMain main;

  /**
   * The constructor.
   *
   * @param help the {@link #getHelp() help message}.
   */
  public AbstractCliCommand(Localizable help) {

    super();
    this.lastOptionIndex = 0;
    this.help = help;
    this.properties = new ArrayList<>();
  }

  /**
   * @param <P> type of the given {@link CliProperty}.
   * @param property the {@link CliProperty} to add.
   * @return the given {@link CliProperty}.
   */
  protected <P extends CliProperty<?>> P add(P property) {

    boolean isOption = !property.getNames().isEmpty();
    if (isOption) {
      if (this.lastOptionIndex < this.properties.size()) {
        this.properties.add(this.lastOptionIndex, property);
      } else {
        this.properties.add(property);
      }
      this.lastOptionIndex++;
    } else {
      this.properties.add(property);
    }
    return property;
  }

  @Override
  public Localizable getHelp() {

    return this.help;
  }

  @Override
  public Collection<CliProperty<?>> getProperties() {

    return this.properties;
  }

  /**
   * @return the {@link CliConsole}.
   */
  public CliConsole console() {

    return this.main.console();
  }

}
