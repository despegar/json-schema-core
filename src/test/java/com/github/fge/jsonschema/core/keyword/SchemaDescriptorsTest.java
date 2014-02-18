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

package com.github.fge.jsonschema.core.keyword;

import com.github.fge.jsonschema.SchemaVersion;
import com.github.fge.jsonschema.core.schema.SchemaDescriptor;
import com.github.fge.jsonschema.core.schema.SchemaDescriptors;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.net.URI;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static org.testng.Assert.*;

public final class SchemaDescriptorsTest
{
    private static final Set<String> COMMON_KEYWORDS = ImmutableSet.of(
        // Array
        "additionalItems", "items", "minItems", "maxItems", "uniqueItems",
        // Integers/numbers
        "minimum", "exclusiveMinimum", "maximum", "exclusiveMaximum",
        // Objects
        "additionalProperties", "dependencies", "patternProperties",
        "properties", "required",
        // Strings
        "minLength", "maxLength", "pattern",
        // All/metadata
        "$schema", "$ref", "id", "description", "title", "enum", "format",
        "default", "type"
    );

    private static final Set<String> DRAFTV4_KEYWORDS = ImmutableSet.of(
        // Integers/numbers
        "multipleOf",
        // Objects
        "minProperties", "maxProperties",
        // All/metadata
        "allOf", "anyOf", "oneOf", "not", "definitions"
    );

    private static final Set<String> DRAFTV4_HYPERSCHEMA_KEYWORDS
        =  ImmutableSet.of("pathStart", "fragmentResolution", "media", "links");

    private static final Set<String> DRAFTV3_KEYWORDS = ImmutableSet.of(
        // Integers/numbers
        "divisibleBy",
        // Other
        "disallow", "extends"
    );

    @DataProvider
    public Iterator<Object[]> expectedOutcomes()
    {
        final List<Object[]> list = Lists.newArrayList();

        SchemaDescriptor descriptor;
        URI locator;
        Iterable<String> iterable;
        Set<String> supported;

        descriptor = SchemaDescriptors.draftv4();
        locator = SchemaVersion.DRAFTV4.getLocation();
        iterable = Iterables.concat(COMMON_KEYWORDS, DRAFTV4_KEYWORDS);
        supported = ImmutableSet.copyOf(iterable);
        list.add(new Object[] { descriptor, locator, supported });

        descriptor = SchemaDescriptors.draftv4HyperSchema();
        locator = SchemaVersion.DRAFTV4_HYPERSCHEMA.getLocation();
        iterable = Iterables.concat(COMMON_KEYWORDS, DRAFTV4_KEYWORDS,
            DRAFTV4_HYPERSCHEMA_KEYWORDS);
        supported = ImmutableSet.copyOf(iterable);
        list.add(new Object[] { descriptor, locator, supported });

        descriptor = SchemaDescriptors.draftv3();
        locator = SchemaVersion.DRAFTV3.getLocation();
        iterable = Iterables.concat(COMMON_KEYWORDS, DRAFTV3_KEYWORDS);
        supported = ImmutableSet.copyOf(iterable);
        list.add(new Object[] { descriptor, locator, supported });
        return list.iterator();
    }

    @Test(dataProvider = "expectedOutcomes")
    public void defaultSchemaDescriptorsHaveExcpectedLocationAndKeywords(
        final SchemaDescriptor descriptor, final URI locator,
        final Set<String> supported
    )
    {
        assertEquals(descriptor.getLocator(), locator);
        assertEquals(descriptor.getSupportedKeywords(), supported);
    }
}
