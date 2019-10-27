/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.nls.descriptor;

import net.sf.mmm.nls.NlsBundle;

/**
 * Descriptor for a {@link java.util.ResourceBundle} or {@link NlsBundle}.
 */
public interface NlsBundleDescriptor {

  /**
   * @return the {@link java.util.ResourceBundle#getBundle(String, java.util.Locale) base-name} used to lookup the
   *         bundle (typically a {@link java.util.ResourceBundle}).
   */
  String getBundleName();

}
