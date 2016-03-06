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

package com.github.piasy.base.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicBoolean;
import org.threeten.bp.zone.TzdbZoneRulesProvider;
import org.threeten.bp.zone.ZoneRulesProvider;

/**
 * Created by Piasy{github.com/Piasy} on 15/8/16.
 *
 * Base JUnit test case related to JSR-310 library.
 */
public class BaseThreeTenBPTest {
    private static final AtomicBoolean initialized = new AtomicBoolean();

    protected BaseThreeTenBPTest() {
        // design to be extended
    }

    protected void initThreeTenBP() {
        if (initialized.getAndSet(true)) {
            return;
        }
        try {
            InputStream is;
            File dat = new File("../testbase/src/main/assets/org/threeten/bp/TZDB.dat");
            System.out.println(dat.getAbsolutePath());
            if (dat.exists()) {
                is = new FileInputStream(dat);
            } else {
                throw new FileNotFoundException("TZDB.dat");
            }
            ZoneRulesProvider.registerProvider(new TzdbZoneRulesProvider(is));
        } catch (IOException e) {
            throw new IllegalStateException("TZDB.dat missing from assets.", e);
        }
    }
}
