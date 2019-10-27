/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.nls;

/**
 * This is the interface for an object with native language support. Such object be can {@link #toNlsMessage()
 * converted} to an {@link NlsMessage i18n-message} describing the object analog to its {@link Object#toString() string
 * representation}.
 */
public interface NlsObject {

  /**
   * This method is the equivalent to {@link Object#toString()} with native language support.
   *
   * @return an {@link NlsMessage} representing this object.
   */
  NlsMessage toNlsMessage();

}
