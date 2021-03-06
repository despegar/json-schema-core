/*
 * Copyright (c) 2014, Francis Galiegue (fgaliegue@gmail.com)
 *
 * This software is dual-licensed under:
 *
 * - the Lesser General Public License (LGPL) version 3.0 or, at your option, any
 *   later version;
 * - the Apache Software License (ASL) version 2.0.
 *
 * The text of this file and of both licenses is available at the root of this
 * project or, if you have the jar distribution, in directory META-INF/, under
 * the names LGPL-3.0.txt and ASL-2.0.txt respectively.
 *
 * Direct link to the sources:
 *
 * - LGPL 3.0: https://www.gnu.org/licenses/lgpl-3.0.txt
 * - ASL 2.0: http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package com.github.fge.jsonschema.core.report;

import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.google.common.collect.Lists;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;

import static com.github.fge.jsonschema.matchers.ProcessingMessageAssert.*;
import static org.mockito.Mockito.*;
import static org.testng.Assert.*;

public final class AbstractProcessingReportTest
{
    /*
     * All levels except fatal
     */
    private static final EnumSet<LogLevel> LEVELS
        = EnumSet.complementOf(EnumSet.of(LogLevel.NONE));

    @DataProvider
    public Iterator<Object[]> getLogLevels()
    {
        final List<Object[]> list = Lists.newArrayList();

        for (final LogLevel level: LEVELS)
            list.add(new Object[] { level });

        // We don't want the values in the same order repeatedly, so...
        Collections.shuffle(list);

        return list.iterator();
    }

    @Test(dataProvider = "getLogLevels")
    public void logThresholdIsRespected(final LogLevel logLevel)
        throws ProcessingException
    {
        final AbstractProcessingReport report
            = spy(new LogThreshold(logLevel));
        final ProcessingMessage message = new ProcessingMessage();
        // OK, that's ugly, but it works...
        final int count = LogLevel.NONE.ordinal() - logLevel.ordinal();

        report.debug(message);
        report.info(message);
        report.warn(message);
        report.error(message);
        report.fatal(message);

        verify(report, times(count)).log(any(LogLevel.class), same(message));
    }

    @Test
    public void logLevelIsCorrectlySetInMessages()
        throws ProcessingException
    {
        final ProcessingReport report = new LogThreshold(LogLevel.NONE);
        final ProcessingMessage message = new ProcessingMessage();

        report.debug(message);
        assertMessage(message).hasLevel(LogLevel.DEBUG);
        report.info(message);
        assertMessage(message).hasLevel(LogLevel.INFO);
        report.warn(message);
        assertMessage(message).hasLevel(LogLevel.WARNING);
        report.error(message);
        assertMessage(message).hasLevel(LogLevel.ERROR);
        report.fatal(message);
        assertMessage(message).hasLevel(LogLevel.FATAL);
    }

    @Test(dataProvider = "getLogLevels")
    public void exceptionThresholdIsRespected(final LogLevel logLevel)
    {
        final ProcessingReport report
            = new LogThreshold(LogLevel.DEBUG, logLevel);
        final ProcessingMessage message = new ProcessingMessage();
        final int expected = LogLevel.NONE.ordinal() - logLevel.ordinal();
        int actual = 0;

        try {
            report.debug(message);
        } catch (ProcessingException ignored) {
            actual++;
        }
        try {
            report.info(message);
        } catch (ProcessingException ignored) {
            actual++;
        }
        try {
            report.warn(message);
        } catch (ProcessingException ignored) {
            actual++;
        }
        try {
            report.error(message);
        } catch (ProcessingException ignored) {
            actual++;
        }

        try {
            report.fatal(message);
        } catch (ProcessingException ignored) {
            actual++;
        }

        assertEquals(actual, expected);
    }

    private static class LogThreshold
        extends AbstractProcessingReport
    {
        private LogThreshold(final LogLevel logLevel,
            final LogLevel exceptionThreshold)
        {
            super(logLevel, exceptionThreshold);
        }

        private LogThreshold(final LogLevel logThreshold)
        {
            super(logThreshold, LogLevel.NONE);
        }

        @Override
        public void log(final LogLevel level, final ProcessingMessage message)
        {
        }
    }
}
