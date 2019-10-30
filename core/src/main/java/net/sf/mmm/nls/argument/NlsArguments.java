/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.nls.argument;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.mmm.nls.argument.impl.NlsArgumentsDouble;
import net.sf.mmm.nls.argument.impl.NlsArgumentsKeyValue;
import net.sf.mmm.nls.argument.impl.NlsArgumentsMap;
import net.sf.mmm.nls.argument.impl.NlsArgumentsMinMax;
import net.sf.mmm.nls.argument.impl.NlsArgumentsNone;
import net.sf.mmm.nls.argument.impl.NlsArgumentsSingle;

/**
 * A simple container for {@link #get(String) argument}s used as {@link net.sf.mmm.nls.NlsMessage#getArguments dynamic
 * arguments} to fill into a {@link net.sf.mmm.nls.NlsMessage}. It is similar to {@link java.util.Map} but with less
 * overhead in case there are only up to two arguments.
 *
 * @see net.sf.mmm.nls.NlsMessage#getArgument(String)
 * @see net.sf.mmm.nls.variable.NlsVariable
 */
public interface NlsArguments extends NlsArgumentsKeys {

  /**
   * @param key the key of the argument to get. Please prefer one of the {@code KEY_*} constants such as
   *        {@link #KEY_VALUE}.
   * @return the value of the argument with the given {@code key} or {@code null} if no such argument exists.
   * @see java.util.Map#get(Object)
   */
  Object get(String key);

  /**
   * @param index the index of the requested key. Should be in the range from {@code 0} to {@link #size() size}-1.
   * @return the requested key or {@code null} if the index is greater or equal to {@link #size() size}.
   */
  String getKey(int index);

  /**
   * @return the number of arguments.
   */
  int size();

  /**
   * @return {@code true} if empty ({@link #size() size} is {@code 0}).
   */
  default boolean isEmpty() {

    return size() == 0;
  }

  /**
   * @return an empty instance of {@link NlsArguments}.
   */
  public static NlsArguments of() {

    return NlsArgumentsNone.INSTANCE;
  }

  /**
   * @param map the {@link Map} with the arguments to wrap.
   * @return an instance of {@link NlsArguments} that delegates to the given {@link Map}.
   */
  public static NlsArguments of(Map<String, Object> map) {

    int size = map.size();
    if (size == 0) {
      return of();
    } else if (size == 1) {
      Entry<String, Object> entry = map.entrySet().iterator().next();
      return of(entry.getKey(), entry.getValue());
    }
    return new NlsArgumentsMap(map);
  }

  /**
   * @param key the key of the argument.
   * @param value the {@link #get(String) value} of the argument.
   * @return an instance of {@link NlsArguments} containing the single specified argument.
   */
  public static NlsArguments of(String key, Object value) {

    return new NlsArgumentsSingle(key, value);
  }

  /**
   * @param key1 the key of the first argument.
   * @param value1 the {@link #get(String) value} of the first argument.
   * @param key2 the key of the second argument.
   * @param value2 the {@link #get(String) value} of the second argument.
   * @return an instance of {@link NlsArguments} containing the single specified arguments.
   */
  public static NlsArguments of(String key1, Object value1, String key2, Object value2) {

    return new NlsArgumentsDouble(key1, value1, key2, value2);
  }

  /**
   * @param key1 the key of the first argument.
   * @param value1 the {@link #get(String) value} of the first argument.
   * @param key2 the key of the second argument.
   * @param value2 the {@link #get(String) value} of the second argument.
   * @param key3 the key of the third argument.
   * @param value3 the {@link #get(String) value} of the third argument.
   * @return an instance of {@link NlsArguments} containing the single specified arguments.
   */
  public static NlsArguments of(String key1, Object value1, String key2, Object value2, String key3, Object value3) {

    Map<String, Object> map = new HashMap<>(3);
    map.put(key1, value1);
    map.put(key2, value2);
    map.put(key3, value3);
    return new NlsArgumentsMap(map);
  }

  /**
   * @param value the {@link #get(String) value} for {@link #KEY_VALUE}.
   * @return an instance of {@link NlsArguments} containing the single specified argument.
   */
  public static NlsArguments ofValue(Object value) {

    return of(KEY_VALUE, value);
  }

  /**
   * @param value the {@link #get(String) value} for {@link #KEY_OBJECT}.
   * @return an instance of {@link NlsArguments} containing the single specified argument.
   */
  public static NlsArguments ofObject(Object value) {

    return of(KEY_OBJECT, value);
  }

  /**
   * @param value the {@link #get(String) value} for {@link #KEY_ARGUMENT}.
   * @return an instance of {@link NlsArguments} containing the single specified argument.
   */
  public static NlsArguments ofArgument(Object value) {

    return of(KEY_ARGUMENT, value);
  }

  /**
   * @param value the {@link #get(String) value} for {@link #KEY_DEFAULT}.
   * @return an instance of {@link NlsArguments} containing the single specified argument.
   */
  public static NlsArguments ofDefault(Object value) {

    return of(KEY_DEFAULT, value);
  }

  /**
   * @param value the {@link #get(String) value} for {@link #KEY_DATE}.
   * @return an instance of {@link NlsArguments} containing the single specified argument.
   */
  public static NlsArguments ofDate(Object value) {

    return of(KEY_DATE, value);
  }

  /**
   * @param value the {@link #get(String) value} for {@link #KEY_FILE}.
   * @return an instance of {@link NlsArguments} containing the single specified argument.
   */
  public static NlsArguments ofFile(Object value) {

    return of(KEY_FILE, value);
  }

  /**
   * @param value the {@link #get(String) value} for {@link #KEY_NAME}.
   * @return an instance of {@link NlsArguments} containing the single specified argument.
   */
  public static NlsArguments ofName(Object value) {

    return of(KEY_NAME, value);
  }

  /**
   * @param value the {@link #get(String) value} for {@link #KEY_TYPE}.
   * @return an instance of {@link NlsArguments} containing the single specified argument.
   */
  public static NlsArguments ofType(Object value) {

    return of(KEY_TYPE, value);
  }

  /**
   * @param value the {@link #get(String) value} for {@link #KEY_KEY}.
   * @return an instance of {@link NlsArguments} containing the single specified argument.
   */
  public static NlsArguments ofKey(Object value) {

    return of(KEY_DEFAULT, value);
  }

  /**
   * @param key the {@link #get(String) value} for {@link #KEY_KEY}.
   * @param value the {@link #get(String) value} for {@link #KEY_VALUE}.
   * @return an instance of {@link NlsArguments} containing the single specified arguments.
   */
  public static NlsArguments ofKeyValue(Object key, Object value) {

    return new NlsArgumentsKeyValue(key, value);
  }

  /**
   * @param min the {@link #get(String) value} for {@link #KEY_MIN}.
   * @param max the {@link #get(String) value} for {@link #KEY_MAX}.
   * @return an instance of {@link NlsArguments} containing the single specified arguments.
   */
  public static NlsArguments ofMinMax(Object min, Object max) {

    return new NlsArgumentsMinMax(min, max);
  }

}
