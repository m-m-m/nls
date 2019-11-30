/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.nls.cli;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;

import io.github.mmm.base.i18n.Localizable;
import io.github.mmm.cli.CliArgs;
import io.github.mmm.cli.CliMain;
import io.github.mmm.cli.CliOption;
import io.github.mmm.cli.CliValue;
import io.github.mmm.cli.io.CliConsole;
import io.github.mmm.cli.io.CliOut;
import io.github.mmm.cli.io.impl.CliConsoleImpl;
import io.github.mmm.nls.NlsMessage;
import io.github.mmm.nls.cli.exception.CliDuplicateOptionsException;
import io.github.mmm.nls.cli.exception.CliInvalidUsageException;
import io.github.mmm.nls.cli.exception.CliNoArgumentsException;
import io.github.mmm.nls.cli.impl.CliProperties;
import io.github.mmm.nls.cli.impl.CliPropertyGroup;
import io.github.mmm.nls.cli.impl.CliPropertyInfo;
import io.github.mmm.nls.cli.property.CliCollectionProperty;
import io.github.mmm.nls.cli.property.CliFlagProperty;
import io.github.mmm.nls.cli.property.CliLocaleProperty;
import io.github.mmm.nls.cli.property.CliProperty;

/**
 * {@link CliMain Main program} (CLI) .
 */
public abstract class NlsMain extends CliMain {

  private final List<CliCommand> commands;

  /**
   * The constructor.
   */
  public NlsMain() {

    this(null);
  }

  /**
   * The constructor.
   *
   * @param console the {@link #console() console}.
   */
  public NlsMain(CliConsole console) {

    super(console);
    this.commands = new ArrayList<>();
    add(new CliCommandHelp());
    add(new CliCommandVersion());
  }

  /**
   * @param command the {@link CliCommand} to register.
   */
  protected void add(CliCommand command) {

    Objects.requireNonNull(command, "command");
    this.commands.add(command);
    if (command instanceof AbstractCliCommand) {
      ((AbstractCliCommand) command).main = this;
    }
  }

  /**
   * Typically a program shall provide multiple values for a {@link CliOption} by providing them as multiple
   * {@link CliValue}s after the {@link CliOption} (e.g. "--keys foo bar some"). However, some programs like to repeat
   * the same {@link CliOption} for that purpose (e.g. "--key foo --key bar --key some"). By default this will cause an
   * error as it is not recommended. However, if you want to support this in your CLI program, you may override this
   * method.
   *
   * @return {@code true} to tolerate duplicate occurrences of the same {@link CliOption}, {@code false} otherwise
   *         (default).
   */
  protected boolean isTolerateDuplicateOptions() {

    return false;
  }

  @Override
  public final int run(CliArgs args) {

    boolean unique = !isTolerateDuplicateOptions();
    if (unique) {
      Set<String> duplicatedOptions = args.getDuplicatedOptions();
      int size = duplicatedOptions.size();
      if (size > 0) {
        throw new CliDuplicateOptionsException(duplicatedOptions);
      }
    }
    boolean localeUnset = true;
    CliOption lastOption = args.getLastOption();
    CliValue parameter = null;
    if (lastOption != null) {
      parameter = lastOption.getNextValue();
    }
    for (int i = this.commands.size() - 1; i >= 0; i--) {
      CliCommand command = this.commands.get(i);
      boolean commandMatches = true;
      for (CliProperty<?> property : command.getProperties()) {
        List<String> names = property.getNames();
        if (property.isOption()) {
          CliOption option = args.getOption(unique, names);
          if (option != null) {
            if (property instanceof CliCollectionProperty) {
              CliCollectionProperty<?, ?> collectionProperty = (CliCollectionProperty<?, ?>) property;
              List<String> values = option.getValues();
              for (String value : values) {
                collectionProperty.addFromString(value);
              }
              if (option == lastOption) {
                parameter = null;
              }
            } else {
              String value = option.getValue();
              if (value != null) {
                property.setFromString(value);
                if ((option == lastOption) && (parameter != null)) {
                  parameter = parameter.getNextValue();
                }
                if (localeUnset && (property instanceof CliLocaleProperty) && (property.getName().equals("--locale"))) {
                  Locale locale = ((CliLocaleProperty) property).get();
                  if (this.c instanceof CliConsoleImpl) {
                    ((CliConsoleImpl) this.c).setLocale(locale);
                  }
                  localeUnset = false;
                }
              } else if (property instanceof CliFlagProperty) {
                ((CliFlagProperty) property).set(Boolean.TRUE);
              } else {
                throw new IllegalArgumentException("Option '" + option.get()
                    + "' has to be followed by a value of type " + property.getValueClass().getSimpleName());
              }
            }
          }
        } else {
          if (parameter != null) {
            property.setFromString(parameter.get());
            parameter = parameter.getNextValue();
          }
        }
        if (property.isMandatory() && property.isEmpty()) {
          commandMatches = false;
          break;
        }
      }
      if (commandMatches) {
        for (CliProperty<?> property : command.getProperties()) {
          property.validate();
        }
        return command.run();
      }
    }
    if (args.isEmpty()) {
      throw new CliNoArgumentsException();
    }
    throw new CliInvalidUsageException(args);
  }

  /**
   * @return {@code true} if the help shall be printed per {@link CliCommand} with all its arguments (options and
   *         parameters), {@code false} otherwise (to first print all {@link CliCommand}s and then print all arguments
   *         together).
   */
  protected boolean isPrintHelpPerCommand() {

    return false;
  }

  /**
   * @return the name of this program.
   */
  protected String getProgramName() {

    return getClass().getName();
  }

  void printVersion() {

    this.c.getStdOut().println(getVersion());
  }

  /**
   * Prints the help usage of this program.
   */
  protected void printHelp() {

    CliOut out = this.c.out(null);
    out.log(NlsBundleCli.INSTANCE.msgUsage());
    CliProperties properties = new CliProperties();
    boolean printHelpPerCommand = isPrintHelpPerCommand();
    StringBuilder usageBuffer = new StringBuilder(getProgramName());
    int usageMinLength = usageBuffer.length();
    boolean first = true;
    for (CliCommand command : this.commands) {
      if (first) {
        first = false;
      } else {
        out.log();
      }
      for (CliProperty<?> property : command.getProperties()) {
        CliPropertyInfo propertyInfo = new CliPropertyInfo(property);
        properties.add(propertyInfo);
        usageBuffer.append(' ');
        usageBuffer.append(propertyInfo.getUsage());
      }
      out.log(usageBuffer.toString());
      Localizable help = command.getHelp();
      if (help != null) {
        out.log(help);
      }
      usageBuffer.setLength(usageMinLength);
      if (printHelpPerCommand) {
        printHelp(out, properties);
        properties.clear();
      }
    }
    if (!printHelpPerCommand) {
      printHelp(out, properties);
    }
  }

  private void printHelp(CliOut out, CliProperties properties) {

    printHelp(out, NlsBundleCli.INSTANCE.msgRequiredOptions(), properties.getMandatoryOptions());
    printHelp(out, NlsBundleCli.INSTANCE.msgAdditionalOptions(), properties.getAdditionalOptions());
    printHelp(out, NlsBundleCli.INSTANCE.msgParameters(), properties.getParameters());
  }

  private void printHelp(CliOut out, NlsMessage groupMessage, CliPropertyGroup group) {

    List<CliPropertyInfo> properties = group.getPropeties();
    if (properties.isEmpty()) {
      return;
    }
    out.log();
    out.log(groupMessage);
    int maxLength = group.getMaxLength();
    StringBuilder spaces = new StringBuilder(maxLength);
    for (CliPropertyInfo propertyInfo : properties) {
      String syntax = propertyInfo.getSyntax();
      int len = maxLength - syntax.length() + 2;
      spaces.setLength(0);
      for (int i = 0; i < len; i++) {
        spaces.append(' ');
      }
      out.log("  ", syntax, spaces, propertyInfo.getProperty().getHelp());
    }
  }

}
