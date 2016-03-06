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
import com.pushtorefresh.storio.sqlite.SQLiteTypeMapping;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import java.util.HashMap;
import java.util.Map;
import junit.framework.Assert;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Created by Piasy{github.com/Piasy} on 15/8/12.
 */

public class StorIOSQLiteProviderTest {
    Map<Class, SQLiteTypeMapping> typesMapping = new HashMap<>();
    private StorIOSQLiteProvider.Config mConfig = StorIOSQLiteProvider.Config.builder()
            .sqliteOpenHelper(Mockito.mock(SQLiteOpenHelper.class))
            .typesMapping(typesMapping)
            .build();
    private StorIOSQLite one, two;

    @Test
    public void testProvideStorIOSQLite() {
        one = StorIOSQLiteProvider.provideStorIOSQLite(mConfig);
        two = StorIOSQLiteProvider.provideStorIOSQLite(mConfig);

        Assert.assertTrue(one.equals(two));
    }

    @Test
    public void testProvideStorIOSQLiteConcurrently() {
        final Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                one = StorIOSQLiteProvider.provideStorIOSQLite(mConfig);
            }
        });

        final Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                two = StorIOSQLiteProvider.provideStorIOSQLite(mConfig);
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
