/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.nls.cli;

import io.github.mmm.nls.cli.property.CliFlagProperty;

/**
 * {@link CliCommand} to print the version of the program.
 */
public class CliCommandVersion extends AbstractCliCommand {

  /**
   * The constructor.
   */
  public CliCommandVersion() {

    super(null);
    add(new CliFlagProperty(NlsBundleCli.INSTANCE.optVersion(), true, "--version", "-v"));
  }

  @Override
  public int run() {

    this.main.printVersion();
    return 0;
  }

}
