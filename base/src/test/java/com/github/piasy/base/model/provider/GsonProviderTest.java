/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Piasy
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.github.piasy.base.model.provider;

import com.github.piasy.base.test.BaseThreeTenBPTest;
import com.google.gson.Gson;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.threeten.bp.ZoneId;
import org.threeten.bp.chrono.IsoChronology;
import org.threeten.bp.format.DateTimeFormatter;
import org.threeten.bp.format.DateTimeFormatterBuilder;
import org.threeten.bp.format.ResolverStyle;
import org.threeten.bp.temporal.ChronoField;

/**
 * Created by Piasy{github.com/Piasy} on 15/8/12.
 */
public class GsonProviderTest extends BaseThreeTenBPTest {

    private static final String TIME_FORMAT_ISO_8601 = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    private final DateTimeFormatter mDateTimeFormatter =
            new DateTimeFormatterBuilder().parseCaseInsensitive()
                    .append(DateTimeFormatter.ISO_LOCAL_DATE)
                    .appendLiteral('T')
                    .appendValue(ChronoField.HOUR_OF_DAY, 2)
                    .appendLiteral(':')
                    .appendValue(ChronoField.MINUTE_OF_HOUR, 2)
                    .optionalStart()
                    .appendLiteral(':')
                    .appendValue(ChronoField.SECOND_OF_MINUTE, 2)
                    .appendLiteral('Z')
                    .toFormatter()
                    .withResolverStyle(ResolverStyle.STRICT)
                    .withChronology(IsoChronology.INSTANCE)
                    .withZone(ZoneId.systemDefault());
    private final GsonProvider.Config mConfig = GsonProvider.Config.builder()
            .dateTimeFormatter(mDateTimeFormatter)
            .dateFormatString(TIME_FORMAT_ISO_8601)
            .build();

    private Gson one, two;

    @Before
    public void setUp() {
        initThreeTenBP();
    }

    @Test
    public void testProvideGson() {
        one = GsonProvider.provideGson(mConfig);
        two = GsonProvider.provideGson(mConfig);

        Assert.assertTrue(one.equals(two));
    }

    @Test
    public void testProvideGsonConcurrently() {
        final Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                one = GsonProvider.provideGson(mConfig);
            }
        });

        final Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                two = GsonProvider.provideGson(mConfig);
            }
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();

            Assert.assertTrue(one.equals(two));
        } catch (InterruptedException e) {
            Assert.assertTrue(false);
        }
    }
}
