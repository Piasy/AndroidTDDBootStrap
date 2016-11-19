package com.github.piasy.bootstrap.model;

import org.threeten.bp.ZoneId;
import org.threeten.bp.chrono.IsoChronology;
import org.threeten.bp.format.DateTimeFormatter;
import org.threeten.bp.format.DateTimeFormatterBuilder;
import org.threeten.bp.format.ResolverStyle;
import org.threeten.bp.temporal.ChronoField;

/**
 * Created by Piasy{github.com/Piasy} on 5/5/16.
 */
@SuppressWarnings("PMD.NonThreadSafeSingleton")
public final class DateTimeFormatterProvider {
    private static volatile DateTimeFormatter sDateTimeFormatter;

    private DateTimeFormatterProvider() {
        // singleton
    }

    public static DateTimeFormatter provideDateTimeFormatter() {
        if (sDateTimeFormatter == null) {
            synchronized (DateTimeFormatterProvider.class) {
                if (sDateTimeFormatter == null) {
                    sDateTimeFormatter = new DateTimeFormatterBuilder().parseCaseInsensitive()
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
                }
            }
        }
        return sDateTimeFormatter;
    }
}
