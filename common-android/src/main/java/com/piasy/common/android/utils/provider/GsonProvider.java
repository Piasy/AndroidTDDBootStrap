package com.piasy.common.android.utils.provider;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.piasy.common.Constants;
import com.piasy.common.android.utils.model.CustomZonedDateTimeConverter;
import com.piasy.common.android.utils.model.ThreeTenABPDelegate;
import com.piasy.common.utils.model.AutoParcelTypeAdapterFactory;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZonedDateTime;
import org.threeten.bp.chrono.IsoChronology;
import org.threeten.bp.format.DateTimeFormatter;
import org.threeten.bp.format.DateTimeFormatterBuilder;
import org.threeten.bp.format.ResolverStyle;
import org.threeten.bp.temporal.ChronoField;

/**
 * Created by Piasy{github.com/Piasy} on 15/7/23.
 */
public class GsonProvider {

    public static Gson provideGson(ThreeTenABPDelegate delegate) {
        delegate.init();
        return GsonHolder.sGson;
    }

    private static class GsonHolder {
        // lazy instantiate
        private static volatile DateTimeFormatter sDateTimeFormatter =
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

        private static volatile Gson sGson = new GsonBuilder()
                //.excludeFieldsWithoutExposeAnnotation()   //not exclude for auto parcel
                .registerTypeAdapterFactory(new AutoParcelTypeAdapterFactory())
                .registerTypeAdapter(ZonedDateTime.class,
                        new CustomZonedDateTimeConverter(sDateTimeFormatter))
                .setDateFormat(Constants.TimeFormat.ISO_8601)
                .setPrettyPrinting()
                .create();
    }
}
