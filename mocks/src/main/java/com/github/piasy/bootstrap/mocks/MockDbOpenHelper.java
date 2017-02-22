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

package com.github.piasy.bootstrap.mocks;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import java.util.List;

/**
 * Created by piasy on 15/8/10.
 */
public class MockDbOpenHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "AndroidTDDBootStrap_db_mock";

    private static final int VERSION = 1;

    private final List<String> mTableCreations;

    public MockDbOpenHelper(@NonNull final Context context, List<String> tableCreations) {
        super(context, DB_NAME, null, VERSION);
        mTableCreations = tableCreations;
    }

    // Better than static final field -> allows VM to unload useless String
    // Because you need this string only once per application life on the device

    @Override
    public void onCreate(final SQLiteDatabase db) {
        final int size = mTableCreations.size();
        for (int i = 0; i < size; i++) {
            db.execSQL(mTableCreations.get(i));
        }
    }

    @Override
    public void onUpgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {
        // no impl
    }
}
