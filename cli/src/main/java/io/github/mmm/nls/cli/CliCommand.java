/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.nls.cli;

import java.util.Collection;

import io.github.mmm.base.i18n.Localizable;
import io.github.mmm.nls.cli.property.CliProperty;

/**
 * Interface for a single command of a command-line-interface (CLI). E.g. a help command triggered via "--help" or "-h"
 * prints out the usage, while a version command triggered via "--version" or "-v" prints the program version.
 */
public interface CliCommand {

  /**
   * @return the {@link Localizable} describing this command.
   */
  Localizable getHelp();

  /**
   * @return the available {@link CliProperty properties} of this {@link CliCommand}.
   */
  Collection<CliProperty<?>> getProperties();

  /**
   * Executes this {@link CliCommand}.
   *
   * @return the {@link System#exit(int) exit code}.
   */
  int run();

}
