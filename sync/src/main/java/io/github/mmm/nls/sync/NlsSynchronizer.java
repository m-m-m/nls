/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.nls.sync;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import io.github.mmm.base.i18n.LocaleHelper;
import io.github.mmm.base.properties.SortedProperties;
import io.github.mmm.cli.CliMain;
import io.github.mmm.nls.NlsBundle;
import io.github.mmm.nls.NlsMessage;
import io.github.mmm.nls.cli.AbstractCliCommand;
import io.github.mmm.nls.cli.NlsMain;
import io.github.mmm.nls.cli.property.CliClassListProperty;
import io.github.mmm.nls.cli.property.CliLocaleListProperty;
import io.github.mmm.nls.cli.property.CliStringProperty;
import io.github.mmm.nls.descriptor.NlsMessageDescriptor;

/**
 * {@link CliMain Main program} (CLI) to synchronize resource bundles for localization from {@link NlsBundle} classes.
 */
public class NlsSynchronizer extends NlsMain {

  /**
   * The constructor.
   */
  public NlsSynchronizer() {

    super();
    add(new Synchronizer());
  }

  /**
   * @param args the CLI arguments.
   */
  public static void main(String[] args) {

    new NlsSynchronizer().runAndExit(args);
  }

  /**
   * Command to do the actual sync.
   */
  public static class Synchronizer extends AbstractCliCommand {

    private final CliStringProperty path;

    private CliLocaleListProperty locales;

    private CliClassListProperty<NlsBundle> bundleClasses;

    /**
     * The constructor.
     */
    public Synchronizer() {

      super(NlsBundleSync.INSTANCE.msgHelp());
      this.path = add(new CliStringProperty(NlsBundleSync.INSTANCE.optPath(), "src/main/resources"));
      this.locales = add(new CliLocaleListProperty(NlsBundleSync.INSTANCE.optLocales(), true, "--locale", "-l"));
      this.bundleClasses = add(
          new CliClassListProperty<>(NlsBundleSync.INSTANCE.optBundles(), NlsBundle.class, true, "--bundle", "-b"));
    }

    @Override
    public int run() {

      for (Class<? extends NlsBundle> bundleClass : this.bundleClasses.get()) {
        NlsBundle bundle;
        try {
          bundle = bundleClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
          throw new IllegalArgumentException(bundleClass.getName(), e);
        }
        for (Method method : bundleClass.getMethods()) {
          Class<?> returnType = method.getReturnType();
          List<NlsMessage> messages = new ArrayList<>();
          if (returnType == NlsMessage.class) {
            int parameterCount = method.getParameterCount();
            try {
              NlsMessage message = (NlsMessage) method.invoke(bundle, new Object[parameterCount]);
              messages.add(message);
            } catch (Exception e) {
              String error;
              if (e instanceof InvocationTargetException) {
                error = e.getCause().getMessage();
              } else {
                error = e.getMessage();
              }
              console().warning().log("Failed to invoke method ", method, ": ", error);
            }
          }
          syncBundles(bundle, messages);
        }
      }
      return 0;
    }

    private void syncBundles(NlsBundle bundle, List<NlsMessage> messages) {

      String bundleName = bundle.getBundleName();
      String bundlePath = bundleName.replace('.', '/');
      for (Locale locale : this.locales.get()) {
        String localeInfix = LocaleHelper.toInfix(locale);
        Path propertiesPath = Paths.get(this.path.get(), bundlePath + localeInfix + ".properties");
        Properties properties = new SortedProperties();
        boolean write = false;
        if (Files.exists(propertiesPath)) {
          try (BufferedReader reader = Files.newBufferedReader(propertiesPath)) {
            properties.load(reader);
          } catch (Exception e) {
            console().warning().log("Failed to read resource bundle from ", propertiesPath, ": ", e.getMessage());
          }
        } else {
          write = true;
        }
        boolean update = updateProperties(properties, locale.toString(), messages);
        if (update) {
          write = true;
        }
        if (write) {
          try (BufferedWriter writer = Files.newBufferedWriter(propertiesPath)) {
            properties.store(writer, null);
          } catch (Exception e) {

          }
        }
      }
    }

    private boolean updateProperties(Properties properties, String locale, List<NlsMessage> messages) {

      boolean modified = false;
      StringBuilder sb = new StringBuilder();
      for (NlsMessage message : messages) {
        NlsMessageDescriptor descriptor = message.getDescriptor();
        String key = descriptor.getMessageKey();
        if (!properties.containsKey(key)) {
          sb.setLength(0);
          if (locale.length() > 0) {
            sb.append("TODO(");
            sb.append(locale);
            sb.append("):");
          }
          // escape newlines for properties-syntax
          sb.append(message.getInternationalizedMessage().replace("\r", "").replace("\n", "\\n"));
          properties.put(key, sb.toString());
        }
      }
      return modified;
    }

  }

}
