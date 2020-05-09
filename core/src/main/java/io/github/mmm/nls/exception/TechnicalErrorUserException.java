/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.nls.exception;

import io.github.mmm.base.exception.ApplicationException;
import io.github.mmm.nls.NlsMessage;

/**
 * A {@link TechnicalErrorUserException} is wrapping an arbitrary technical error to a generic exception for end-users
 * or clients. It helps to prevent "sensitive data exposure", an OWASP top ten security vulnerability.
 *
 * @since 1.0.0
 */
public class TechnicalErrorUserException extends ApplicationException {

  /** @see #getCode() */
  public static final String CODE = "TechnicalError";

  private static final long serialVersionUID = 1L;

  /**
   * The constructor.
   *
   * @param cause is the {@link #getCause() cause}.
   */
  public TechnicalErrorUserException(Throwable cause) {

    super(NlsBundleException.INSTANCE.errTechnical(), cause);
  }

  /**
   * The constructor for the very special case that you want to define a custom message. You are not encouraged to use
   * this constructor. Please also consider that you can still customize the localized texts for the message when using
   * {@link #TechnicalErrorUserException(Throwable)}.
   *
   * @param nested is the {@link #getCause() cause}.
   * @param message is a custom {@link #getNlsMessage() message}.
   */
  public TechnicalErrorUserException(NlsMessage message, Throwable nested) {

    super(message, nested);
  }

  @Override
  public final String getCode() {

    return CODE;
  }

  @Override
  public final boolean isTechnical() {

    return true;
  }

  @Override
  public final boolean isForUser() {

    return true;
  }

  /**
   * Gets the given {@link Throwable} as {@link #isForUser() user} {@link ApplicationException} or converts it to such.
   *
   * @param exception is the {@link Throwable} to convert.
   * @return the {@link ApplicationException} with {@link #isForUser()} returning {@code true} .
   */
  public static ApplicationException getOrCreateUserException(Throwable exception) {

    if (exception instanceof ApplicationException) {
      ApplicationException applicationException = (ApplicationException) exception;
      if (applicationException.isForUser()) {
        return applicationException;
      }
    }
    return new TechnicalErrorUserException(exception);
  }

}
