package com.piasy.common.android.utils.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.piasy.common.android.utils.provider.GsonProvider;
import com.piasy.common.android.utils.tests.BaseThreeTenBPTest;
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
        String dateStr = "2015-08-16T13:27:33Z";
        ZonedDateTime date = mDateTimeFormatter.parse(dateStr, ZonedDateTime.FROM);
        Assert.assertEquals(2015, date.getYear());
        Assert.assertEquals(8, date.getMonthValue());
        Assert.assertEquals(16, date.getDayOfMonth());
        Assert.assertEquals(13, date.getHour());
        Assert.assertEquals(27, date.getMinute());
        Assert.assertEquals(33, date.getSecond());
    }

    @Test
    public void testZonedDateTimeSerialise() {
        String dateStr = "2015-08-16T13:27:33Z";
        String dateJsonStr = "\"2015-08-16T13:27:33Z\"";
        ZonedDateTime date = mDateTimeFormatter.parse(dateStr, ZonedDateTime.FROM);
        String toJson = mGson.toJson(date);
        ZonedDateTime fromJson = mGson.fromJson(dateJsonStr, ZonedDateTime.class);
        Assert.assertEquals(dateJsonStr, toJson);
        Assert.assertEquals(date, fromJson);
    }

    @Test
    public void testIntegrateGsonProviderSerialise() {
        String json = "{\"time\":\"2015-08-16T13:27:33Z\"}";
        String dateStr = "2015-08-16T13:27:33Z";
        ZonedDateTimeHolder holder = new ZonedDateTimeHolder();
        holder.time = mDateTimeFormatter.parse(dateStr, ZonedDateTime.FROM);
        ZonedDateTimeHolder fromJson = GsonProvider.provideGson(mock(ThreeTenABPDelegate.class))
                .fromJson(json, ZonedDateTimeHolder.class);
        Assert.assertEquals(holder.time, fromJson.time);
    }

    static class ZonedDateTimeHolder {
        ZonedDateTime time;
    }
}
