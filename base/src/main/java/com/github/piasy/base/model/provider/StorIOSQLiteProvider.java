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

import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import auto.parcel.AutoParcel;
import com.pushtorefresh.storio.sqlite.SQLiteTypeMapping;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.impl.DefaultStorIOSQLite;
import java.util.Map;

/**
 * Created by Piasy{github.com/Piasy} on 15/7/24.
 *
 * A singleton provider providing {@link StorIOSQLite}.
 */
@SuppressWarnings(value = {"PMD.NonThreadSafeSingleton", "PMD.DataflowAnomalyAnalysis"})
public final class StorIOSQLiteProvider {

    private static volatile StorIOSQLite sStorIOSQLite;

    private StorIOSQLiteProvider() {
        // singleton
    }

    /**
     * Provide the {@link StorIOSQLite} singleton instance.
     *
     * @return the singleton {@link StorIOSQLite}.
     */
    static StorIOSQLite provideStorIOSQLite(final Config config) {
        if (sStorIOSQLite == null) {
            synchronized (StorIOSQLiteProvider.class) {
                if (sStorIOSQLite == null) {
                    final DefaultStorIOSQLite.CompleteBuilder builder =
                            DefaultStorIOSQLite.builder()
                                    .sqliteOpenHelper(config.sqliteOpenHelper());
                    for (final Class clazz : config.typesMapping().keySet()) {
                        builder.addTypeMapping(clazz, config.typesMapping().get(clazz));
                    }
                    sStorIOSQLite = builder.build();
                }
            }
        }
        return sStorIOSQLite;
    }

    // CHECKSTYLE:OFF
    @AutoParcel
    public abstract static class Config {
        @NonNull
        public static Builder builder() {
            return new AutoParcel_StorIOSQLiteProvider_Config.Builder();
        }

        public abstract Map<Class, SQLiteTypeMapping> typesMapping();

        public abstract SQLiteOpenHelper sqliteOpenHelper();

        @AutoParcel.Builder
        public abstract static class Builder {
            public abstract Builder typesMapping(final Map<Class, SQLiteTypeMapping> typesMapping);

            public abstract Builder sqliteOpenHelper(final SQLiteOpenHelper sqliteOpenHelper);

            public abstract Config build();
        }
    }
    // CHECKSTYLE:ON
}
