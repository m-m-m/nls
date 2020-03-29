/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.nls.exception;

import io.github.mmm.base.exception.ObjectNotFoundException;

/**
 * {@link ObjectNotFoundException} {@link #isForUser() for user} with NLS support.
 *
 * @since 1.0.0
 */
public class ObjectNotFoundUserException extends ObjectNotFoundException {

  private static final long serialVersionUID = 1L;

  /**
   * The constructor.
   *
   * @param object is a description (e.g. the classname) of the object that was required but could NOT be found.
   * @param key is the key to the required object.
   */
  public ObjectNotFoundUserException(Object object, Object key) {

    this(object, key, null);
  }

  /**
   * The constructor.
   *
   * @param object is a description (e.g. the classname) of the object that was required but could NOT be found.
   */
  public ObjectNotFoundUserException(Object object) {

    this(object, null, null);
  }

  /**
   * The constructor.
   *
   * @param object is a description (e.g. the classname) of the object that was required but could NOT be found.
   * @param key is the key to the required object.
   * @param cause is the {@link #getCause() cause} of this exception.
   */
  public ObjectNotFoundUserException(Object object, Object key, Throwable cause) {

    super(NlsBundleException.INSTANCE.errObjectNotFound(object, key), cause);
  }

  @Override
  public boolean isForUser() {

    return true;
  }

}
