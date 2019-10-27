/*
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */
module net.sf.mmm.nls {

  requires transitive net.sf.mmm.scanner;

  requires org.slf4j;

  exports net.sf.mmm.nls;

  exports net.sf.mmm.nls.argument;

  exports net.sf.mmm.nls.descriptor;

  exports net.sf.mmm.nls.exception;

  exports net.sf.mmm.nls.formatter;

  exports net.sf.mmm.nls.template;

  exports net.sf.mmm.nls.variable;

}
