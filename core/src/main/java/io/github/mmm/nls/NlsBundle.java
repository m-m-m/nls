/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.nls;

import java.util.Locale;

import io.github.mmm.base.i18n.Localizable;
import io.github.mmm.nls.argument.NlsArguments;
import io.github.mmm.nls.argument.NlsArgumentsKeys;
import io.github.mmm.nls.descriptor.NlsBundleDescriptor;

/**
 * Abstract base class for all "resource-bundles" using this native language support (NLS) module. Instead of a
 * {@link java.util.ResourceBundle} you create a custom class extending this one in order to define the
 * {@link NlsMessage#getInternationalizedMessage() messages}. This is done by defining methods with {@link NlsMessage}
 * as return type and the {@link NlsMessage#getArguments() dynamic argument values} as parameters. We recommend to
 * follow these conventions: <br>
 * <ul>
 * <li>For your bundle you define a {@link Package} and a base name (e.g. {@code my.package.NlsBundleExample}). This
 * namespace must not be occupied by an existing type.</li>
 * <li>You create a non-abstract final class for that namespace (e.g. {@code my.package.NlsBundleExample}) extending
 * {@link NlsBundle}.</li>
 * <li>This class defines a public method for each provided {@link NlsMessage} using the
 * {@link NlsMessage#getInternationalizedMessage() internationalized message} for the {@link java.util.Locale#ROOT root
 * locale}.</li>
 * <li>By default the {@link io.github.mmm.nls.descriptor.NlsMessageDescriptor#getMessageKey() message key} used in the
 * method implementation (see example below) shall be the {@link java.lang.reflect.Method#getName() method name}.</li>
 * <li>You have to ensure that the {@link io.github.mmm.nls.descriptor.NlsMessageDescriptor#getMessageKey() message
 * key}s are unique within an {@link NlsBundle} class as otherwise the localization is NOT possible.</li>
 * <li>Your localized {@code *.properties} files have to be named using the base name followed by the according locale
 * suffix as for a regular {@link java.util.ResourceBundle} (e.g. {@code my/package/NlsBundleExample_de.properties} or
 * {@code my/package/NlsBundleExample_zh_HK.properties}). For the root locale you ommit that locale suffix (e.g.
 * {@code my/package/NlsBundleExample.properties}).</li>
 * <li>For creating and maintaining these localized {@code *.properties} files, we provide the CLI tool
 * {@code NlsBundleSynchronizer}.</li>
 * </ul>
 * Here is an example:
 *
 * <pre>
 * package com.example;
 * public final class NlsBundleMynameRoot extends {@link NlsBundle} {
 *
 *   private static NlsBundleMynameRoot INSTANCE = new NlsBundleMynameRoot();
 *
 *   public {@link NlsMessage} errorValueOutOfRange({int value, int min, int max) {
 *     return create("errorValueOutOfRange", "The value {value} has to be in the range from {min} to {max}!", NlsArguments.of(KEY_VALUE, value, KEY_MIN, min, KEY_MAX, max));
 *   }
 *
 *   public static NlsBundleMynameRoot get() {
 *     return INSTANCE;
 *   }
 * }
 * </pre>
 *
 * For localization you create or generate {@code *.properties} files in the same package for each supported
 * {@link java.util.Locale}. In the example above e.g. {@code com/example/NlsBundleMyname_de.properties} with this
 * content:
 *
 * <pre>
 * errorValueOutOfRange = Der Wert {value} muss innerhalb des Wertebereichs von {min} bis {max} liegen!
 * </pre>
 *
 * Now you can use this as following:
 *
 * <pre>
 * {@link NlsMessage} message = NlsBundleMynameRoot.get().errorValueOutOfRange(5, 0, 4);
 * System.out.println(message.{@link NlsMessage#getMessage() getMessage}();
 * System.out.println(message.{@link NlsMessage#getLocalizedMessage(java.util.Locale) getLocalizedMessage}({@link Locale#GERMAN});
 * </pre>
 *
 * This will print the following result:
 *
 * <pre>
 * The value 5 has to be in the range from 0 to 4!
 * Der Wert 5 muss innerhalb des Wertebereichs von 0 bis 4 liegen!
 * </pre>
 *
 * See {@link io.github.mmm.nls.variable.NlsVariable} for advanced features.
 */
public class NlsBundle implements NlsBundleDescriptor, NlsArgumentsKeys {

  private final String bundleName;

  /**
   * The constructor.
   */
  public NlsBundle() {

    this(null);
  }

  /**
   * The constructor.
   *
   * @param bundleName the {@link #getBundleName() bundle name}.
   */
  protected NlsBundle(String bundleName) {

    super();
    if (bundleName == null) {
      this.bundleName = createBundleName();
    } else {
      this.bundleName = bundleName;
    }
  }

  /**
   * Determines the static {@link #getBundleName() bundle name}. May be overridden to change default behavior.
   *
   * @return the {@link #getBundleName()}.
   * @see Localizable#createBundleName(Class)
   */
  protected String createBundleName() {

    return Localizable.createBundleName(getClass());
  }

  @Override
  public final String getBundleName() {

    return this.bundleName;
  }

  /**
   * @param key the {@link io.github.mmm.nls.descriptor.NlsMessageDescriptor#getMessageKey() message key}.
   * @param message the {@link NlsMessage#getInternationalizedMessage() internationalized message}.
   * @return the {@link NlsMessage}.
   */
  protected NlsMessage create(String key, String message) {

    return create(key, message, NlsArguments.of());
  }

  /**
   * @param key the {@link io.github.mmm.nls.descriptor.NlsMessageDescriptor#getMessageKey() message key}.
   * @param message the {@link NlsMessage#getInternationalizedMessage() internationalized message}.
   * @param arguments the {@link NlsMessage#getArguments() dynamic arguments} to fill into the message.
   * @return the {@link NlsMessage}.
   */
  protected NlsMessage create(String key, String message, NlsArguments arguments) {

    return NlsMessageFactory.get().create(this.bundleName, key, message, arguments);
  }

}
