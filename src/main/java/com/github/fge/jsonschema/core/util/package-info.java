/*
 * Copyright (c) 2014, Francis Galiegue (fgaliegue@gmail.com)
 *
 * This software is dual-licensed under:
 *
 * - the Lesser General Public License (LGPL) version 3.0 or, at your option, any
 *   later version;
 * - the Apache Software License (ASL) version 2.0.
 *
 * The text of both licenses is available under the src/resources/ directory of
 * this project (under the names LGPL-3.0.txt and ASL-2.0.txt respectively).
 *
 * Direct link to the sources:
 *
 * - LGPL 3.0: https://www.gnu.org/licenses/lgpl-3.0.txt
 * - ASL 2.0: http://www.apache.org/licenses/LICENSE-2.0.txt
 */

/**
 * Various utility classes
 *
 * <p>{@link com.github.fge.jsonschema.core.util.RhinoHelper} is in charge of
 * all regex validation: as the standard dictates ECMA 262 regexes, using {@link
 * java.util.regex} is out of the question. See this class' description for more
 * details.</p>
 *
 * <p>There are other, various utility interfaces used elsewhere in the code.
 * </p>
 */
package com.github.fge.jsonschema.core.util;
