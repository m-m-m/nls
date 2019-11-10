/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.nls.exception;

import java.util.Locale;
import java.util.UUID;

import io.github.mmm.base.exception.ApplicationException;
import io.github.mmm.nls.NlsMessage;

/**
 * Extends {@link ApplicationException} with native language support (NLS).
 *
 * @see NlsMessage
 */
public abstract class NlsException extends ApplicationException {

  private static final long serialVersionUID = 1L;

  /** the internationalized message */
  private final NlsMessage nlsMessage;

  /**
   * The constructor.
   *
   * @param message the {@link NlsMessage}.
   */
  public NlsException(NlsMessage message) {

    this(message, null);
  }

  /**
   * The constructor.
   *
   * @param message the {@link NlsMessage}.
   * @param cause is the {@link #getCause() cause} of this exception. May be <code>null</code>.
   */
  public NlsException(NlsMessage message, Throwable cause) {

    this(message, cause, null);
  }

  /**
   * The constructor.
   *
   * @param message the {@link NlsMessage}.
   * @param cause is the {@link #getCause() cause} of this exception. May be <code>null</code>.
   * @param uuid the explicit {@link #getUuid() UUID} or <code>null</code> to initialize by default (from given
   *        {@link Throwable} or as new {@link UUID}).
   */
  public NlsException(NlsMessage message, Throwable cause, UUID uuid) {

    super(null, cause, uuid);
    this.nlsMessage = message;
  }

  /**
   * @return the {@link NlsMessage} describing the problem.
   * @see #getMessage()
   */
  public final NlsMessage getNlsMessage() {

    return this.nlsMessage;
  }

  @Override
  public String getLocalizedMessage() {

    return getNlsMessage().getLocalizedMessage();
  }

  @Override
  public String getLocalizedMessage(Locale locale) {

    return getNlsMessage().getLocalizedMessage(locale);
  }

  @Override
  public void getLocalizedMessage(Locale locale, Appendable appendable) {

    getNlsMessage().getLocalizedMessage(locale, appendable);
  }

}
