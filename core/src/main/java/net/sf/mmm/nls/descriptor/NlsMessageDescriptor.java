/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.nls.descriptor;

/**
 * Descriptor for a {@link net.sf.mmm.nls.NlsMessage} or {@link net.sf.mmm.nls.template.NlsTemplate}.
 */
public interface NlsMessageDescriptor extends NlsBundleDescriptor {

  /**
   * @return the {@link java.util.ResourceBundle#getString(String) key} of the string to lookup from the
   *         {@link #getBundleName() bundle}. The key is a technical ID that has to be unique within the
   *         {@link #getBundleName() bundle}.
   */
  String getMessageKey();

}
