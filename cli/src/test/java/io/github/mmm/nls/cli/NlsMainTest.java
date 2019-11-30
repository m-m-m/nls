/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.nls.cli;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import io.github.mmm.base.i18n.Localizable;
import io.github.mmm.cli.io.impl.CliConsoleImpl;
import io.github.mmm.nls.cli.property.CliLongProperty;
import io.github.mmm.nls.cli.property.CliStringProperty;

/**
 * Test of {@link NlsMain}.
 */
public class NlsMainTest extends Assertions {

  /** Test of {@link NlsMain} with help option. */
  @Test
  public void testHelp() {

    String expectedOut = "Usage:\n" //
        + "io.github.mmm.nls.cli.NlsMainTest$Program --help|-h\n\n" //
        + "io.github.mmm.nls.cli.NlsMainTest$Program --version|-v\n\n" //
        + "io.github.mmm.nls.cli.NlsMainTest$Program --mode|-m <mode> <value>\n" //
        + "Test the command-line-interface.\n\n" //
        + "Required options:\n" //
        + "  --help|-h     Print this help.\n" //
        + "  --version|-v  Print the version of this program.\n" //
        + "  --mode|-m     Mode of test.\n\n" //
        + "Additional options:\n" //
        + "  -Duser.language  The locale for translation (e.g. use '-Duser.language=de' for German).\n\n" //
        + "Parameters:\n" //
        + "  value  Value of test.\n";
    String expectedErr = "";

    assertProgram(0, expectedOut, expectedErr, "-h");
    assertProgram(0, expectedOut, expectedErr, "--help");
  }

  /** Test of {@link NlsMain} with version option. */
  @Test
  public void testVersion() {

    String expectedOut = "1.2.3.4\n";
    String expectedErr = "";

    assertProgram(0, expectedOut, expectedErr, "-v");
    assertProgram(0, expectedOut, expectedErr, "--version");
  }

  /** Test of {@link NlsMain} ({@link Program}) with custom option. */
  @Test
  public void testCustom() {

    assertProgram(0, "42\n", "", "--mode", "JUnit", "42");
    assertProgram(1, "",
        "ERROR: No arguments were specified. Please read help usage and provide required arguments.\n");
  }

  private void assertProgram(int expectedCode, String expectedOut, String expectedErr, String... args) {

    // given
    Program prg = new Program();
    CliConsoleImpl console = (CliConsoleImpl) prg.console();
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    console.setStdOut(new PrintStream(out));
    ByteArrayOutputStream err = new ByteArrayOutputStream();
    console.setStdErr(new PrintStream(err));
    // when
    int code = prg.run(args);
    // then
    assertThat(err.toString()).isEqualTo(expectedErr);
    assertThat(out.toString()).isEqualTo(expectedOut);
    assertThat(code).isEqualTo(expectedCode);
  }

  /**
   * Stupid main program for testing.
   */
  public class Program extends NlsMain {

    /**
     * The constructor.
     */
    public Program() {

      super();
      add(new CliCommandTest());
    }

  }

  /**
   * The test command.
   */
  public class CliCommandTest extends AbstractCliCommand {

    private final CliStringProperty mode;

    private final CliLongProperty value;

    /**
     * The constructor.
     */
    public CliCommandTest() {

      super(Localizable.ofStatic("Test the command-line-interface."));
      this.mode = add(new CliStringProperty(Localizable.ofStatic("Mode of test."), true, "--mode", "-m"));
      this.value = add(new CliLongProperty(Localizable.ofStatic("Value of test."), true, "value"));
    }

    @Override
    public int run() {

      console().out().log(this.value.get());
      if ("JUnit".equals(this.mode.get())) {
        return 0;
      }
      return -1;
    }

  }

}
