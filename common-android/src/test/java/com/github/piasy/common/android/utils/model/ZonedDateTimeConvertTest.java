/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Piasy
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

package com.github.piasy.common.android.utils.model;

import com.github.piasy.common.android.jsr310.ThreeTenABPDelegate;
import com.github.piasy.common.android.provider.ProviderModule;
import com.github.piasy.common.android.utils.tests.BaseThreeTenBPTest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZonedDateTime;
import org.threeten.bp.chrono.IsoChronology;
import org.threeten.bp.format.DateTimeFormatter;
import org.threeten.bp.format.DateTimeFormatterBuilder;
import org.threeten.bp.format.ResolverStyle;
import org.threeten.bp.temporal.ChronoField;

import static org.mockito.Mockito.mock;

/**
 * Created by Piasy{github.com/Piasy} on 15/8/16.
 */
public class ZonedDateTimeConvertTest extends BaseThreeTenBPTest {

    private DateTimeFormatter mDateTimeFormatter;
    private Gson mGson;

    @Before
    public void setUp() {
        initThreeTenABP();
        mDateTimeFormatter = new DateTimeFormatterBuilder().parseCaseInsensitive()
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
        mGson = new GsonBuilder().registerTypeAdapter(ZonedDateTime.class,
                new CustomZonedDateTimeConverter(mDateTimeFormatter)).setPrettyPrinting().create();
    }

    @Test
    public void testSimpleParse() {
        final String dateStr = "2015-08-16T13:27:33Z";
        final ZonedDateTime date = mDateTimeFormatter.parse(dateStr, ZonedDateTime.FROM);
        Assert.assertEquals(2015, date.getYear());
        Assert.assertEquals(8, date.getMonthValue());
        Assert.assertEquals(16, date.getDayOfMonth());
        Assert.assertEquals(13, date.getHour());
        Assert.assertEquals(27, date.getMinute());
        Assert.assertEquals(33, date.getSecond());
    }

    @Test
    public void testZonedDateTimeSerialise() {
        final String dateStr = "2015-08-16T13:27:33Z";
        final String dateJsonStr = "\"2015-08-16T13:27:33Z\"";
        final ZonedDateTime date = mDateTimeFormatter.parse(dateStr, ZonedDateTime.FROM);
        final String toJson = mGson.toJson(date);
        final ZonedDateTime fromJson = mGson.fromJson(dateJsonStr, ZonedDateTime.class);
        Assert.assertEquals(dateJsonStr, toJson);
        Assert.assertEquals(date, fromJson);
    }

    @Test
    public void testIntegrateGsonProviderSerialise() {
        final String json = "{\"time\":\"2015-08-16T13:27:33Z\"}";
        final String dateStr = "2015-08-16T13:27:33Z";
        final ZonedDateTimeHolder holder = new ZonedDateTimeHolder();
        holder.time = mDateTimeFormatter.parse(dateStr, ZonedDateTime.FROM);
        final ZonedDateTimeHolder fromJson =
                new ProviderModule().provideGson(mock(ThreeTenABPDelegate.class))
                        .fromJson(json, ZonedDateTimeHolder.class);
        Assert.assertEquals(holder.time, fromJson.time);
    }

    static class ZonedDateTimeHolder {
        ZonedDateTime time;
    }
}
