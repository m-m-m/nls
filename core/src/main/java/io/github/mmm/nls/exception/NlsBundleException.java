/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.nls.exception;

import io.github.mmm.nls.NlsBundle;
import io.github.mmm.nls.NlsMessage;
import io.github.mmm.nls.argument.NlsArguments;

/**
 * {@link NlsBundle} for standard exceptions with NLS support.
 *
 * @since 1.0.0
 */
@SuppressWarnings("javadoc")
public final class NlsBundleException extends NlsBundle {

  public static final NlsBundleException INSTANCE = new NlsBundleException();

  private NlsBundleException() {

    super();
  }

  public NlsMessage errObjectNotFound(Object object) {

    return create("errObjectNotFound", "Could not find {object}.", NlsArguments.ofObject(object));
  }

  public NlsMessage errObjectNotFound(Object object, Object key) {

    if (key == null) {
      return errObjectNotFound(object);
    }
    return create("errObjectNotFound", "Could not find {object} for {key}.", NlsArguments.ofObjectKey(object, key));
  }

}
