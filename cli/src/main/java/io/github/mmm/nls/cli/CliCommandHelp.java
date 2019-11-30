/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.nls.cli;

import io.github.mmm.nls.cli.property.CliFlagProperty;

/**
 * {@link CliCommand} to print the help of the program.
 */
public class CliCommandHelp extends AbstractCliCommand {

  /**
   * The constructor.
   */
  public CliCommandHelp() {

    super(null);
    add(new CliFlagProperty(NlsBundleCli.INSTANCE.optHelp(), true, "--help", "-h"));
  }

  @Override
  public int run() {

    this.main.printHelp();
    return 0;
  }

}
