/*
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */
/**
 * Provides advanced native language support.
 */
module io.github.mmm.nls {

  requires transitive io.github.mmm.scanner;

  requires org.slf4j;

  exports io.github.mmm.nls;

  exports io.github.mmm.nls.argument;

  exports io.github.mmm.nls.descriptor;

  exports io.github.mmm.nls.exception;

  exports io.github.mmm.nls.formatter;

  exports io.github.mmm.nls.template;

  exports io.github.mmm.nls.variable;

}
