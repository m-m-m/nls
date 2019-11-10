/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.nls.formatter.impl.plugin;

import java.io.IOException;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Locale;

import io.github.mmm.nls.argument.NlsArguments;
import io.github.mmm.nls.formatter.NlsFormatterManager;

/**
 * Implementation of {@link io.github.mmm.nls.formatter.NlsFormatter} for {@link NlsFormatterManager#TYPE_TYPE}.
 */
public class NlsFormatterPluginType extends AbstractNlsFormatterPlugin {

  /** The package {@code java.lang}. */
  private static final Package PACKAGE_JAVA_LANG = Package.class.getPackage();

  private final String style;

  /**
   * The constructor.
   *
   * @param style is the {@link #getStyle() style}.
   */
  public NlsFormatterPluginType(String style) {

    super();
    this.style = style;
  }

  @Override
  public String getStyle() {

    return this.style;
  }

  @Override
  public String getType() {

    return NlsFormatterManager.TYPE_TYPE;
  }

  @Override
  public void doFormat(Object object, Locale locale, NlsArguments arguments, Appendable buffer) throws IOException {

    if (object == null) {
      buffer.append("null");
    } else if (object instanceof Type) {
      doFormat((Type) object, buffer);
    } else {
      buffer.append(object.toString());
    }
  }

  private void doFormat(Type type, Appendable appendable) throws IOException {

    if (type instanceof Class<?>) {
      Class<?> clazz = (Class<?>) type;
      if (this.style.equals(NlsFormatterManager.STYLE_SHORT)) {
        appendable.append(clazz.getSimpleName());
      } else if (this.style.equals(NlsFormatterManager.STYLE_LONG)
          || this.style.equals(NlsFormatterManager.STYLE_MEDIUM)) {
        if (PACKAGE_JAVA_LANG.equals(clazz.getPackage())) {
          appendable.append(clazz.getSimpleName());
        } else {
          appendable.append(clazz.getCanonicalName());
        }
      } else {
        appendable.append(clazz.getCanonicalName());
      }
    } else if (type instanceof ParameterizedType) {
      ParameterizedType parameterizedType = (ParameterizedType) type;
      boolean longOrFullStyle = this.style.equals(NlsFormatterManager.STYLE_LONG)
          || this.style.equals(NlsFormatterManager.STYLE_FULL);
      if (longOrFullStyle) {
        Type ownerType = parameterizedType.getOwnerType();
        if (ownerType != null) {
          doFormat(ownerType, appendable);
          appendable.append('.');
        }
      }
      doFormat(parameterizedType.getRawType(), appendable);
      if (longOrFullStyle) {
        appendable.append('<');
        boolean separator = false;
        for (Type arg : parameterizedType.getActualTypeArguments()) {
          if (separator) {
            appendable.append(", ");
          }
          doFormat(arg, appendable);
          separator = true;
        }
        appendable.append('>');
      }
    } else if (type instanceof TypeVariable<?>) {
      TypeVariable<?> typeVariable = (TypeVariable<?>) type;
      appendable.append(typeVariable.getName());
      Type[] bounds = typeVariable.getBounds();
      if (bounds.length > 0) {
        // is this supported after all?
        Type firstBound = bounds[0];
        if (!Object.class.equals(firstBound)) {
          appendable.append(" extends ");
          doFormat(firstBound, appendable);
        }
      }
    } else if (type instanceof WildcardType) {
      WildcardType wildcardType = (WildcardType) type;
      Type[] lowerBounds = wildcardType.getLowerBounds();
      if (lowerBounds.length > 0) {
        appendable.append("? super ");
        doFormat(lowerBounds[0], appendable);
      } else {
        Type[] upperBounds = wildcardType.getUpperBounds();
        if (upperBounds.length > 0) {
          appendable.append("? extends ");
          doFormat(upperBounds[0], appendable);
        } else {
          appendable.append("?");
        }
      }
    } else if (type instanceof GenericArrayType) {
      doFormat(((GenericArrayType) type).getGenericComponentType(), appendable);
      appendable.append("[]");
    } else {
      appendable.append(type.toString());
    }
  }

}
