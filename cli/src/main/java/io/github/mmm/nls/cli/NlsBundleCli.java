/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.nls.cli;

import io.github.mmm.nls.NlsBundle;
import io.github.mmm.nls.NlsMessage;
import io.github.mmm.nls.argument.NlsArguments;

/**
 * {@link NlsBundle} for command-line-interface (CLI).
 */
@SuppressWarnings("javadoc")
public class NlsBundleCli extends NlsBundle {

  public static final NlsBundleCli INSTANCE = new NlsBundleCli();

  private NlsBundleCli() {

    super();
  }

  public NlsMessage msgUsage() {

    return create("msgUsage", "Usage:", NlsArguments.of());
  }

  public NlsMessage msgRequiredOptions() {

    return create("msgRequiredOptions", "Required options:", NlsArguments.of());
  }

  public NlsMessage msgAdditionalOptions() {

    return create("msgAdditionalOptions", "Additional options:", NlsArguments.of());
  }

  public NlsMessage msgParameters() {

    return create("msgParameters", "Parameters:", NlsArguments.of());
  }

  public NlsMessage optLocale() {

    return create("optionLocale", "The locale for translation (e.g. use '-Duser.language=de' for German).",
        NlsArguments.of());
  }

  public NlsMessage optHelp() {

    return create("optionHelp", "Print this help.", NlsArguments.of());
  }

  public NlsMessage optVersion() {

    return create("optionVersion", "Print the version of this program.", NlsArguments.of());
  }

  public NlsMessage errArgumentMandatory(String name) {

    return create("errArgumentMandatory", "The value for '{name}' is required.", NlsArguments.ofName(name));
  }

  public NlsMessage errDuplicateOptions(Object options) {

    return create("errDuplicateOptions", "Duplicated option(s): {value}", NlsArguments.ofValue(options));
  }

  public NlsMessage errInvalidArguments(Object arguments) {

    return create("errInvalidArguments", "Invalid argument(s): {argument}", NlsArguments.ofArgument(arguments));
  }

  public NlsMessage errNoArguments() {

    return create("errNoArguments",
        "No arguments were specified. Please read help usage and provide required arguments.", NlsArguments.of());
  }

  /**
   * @param type
   * @param superType
   * @return
   */
  public NlsMessage errTypeNotExtending(Object type, Object superType) {

    return create("errTypeNotExtending", "The type {type} does not extend the expected super-type {expected}.",
        NlsArguments.ofType(type).with(KEY_EXPECTED, superType));
  }

}
