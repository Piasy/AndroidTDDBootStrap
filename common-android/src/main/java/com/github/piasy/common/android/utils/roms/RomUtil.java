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

package com.github.piasy.common.android.utils.roms;

import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import org.apache.commons.io.IOUtils;
import timber.log.Timber;

/**
 * Created by Piasy{github.com/Piasy} on 15/8/26.
 *
 * Android ROM utility class.
 */
public final class RomUtil {

    private static final String TAG = "RomUtil";

    private RomUtil() {
        // singleton
    }

    /**
     * Provide the singleton {@link RomUtil} instance.
     *
     * @return the singleton {@link RomUtil} instance.
     */
    public static RomUtil provideRomUtil() {
        return RomUtilHolder.sRomUtil;
    }

    /**
     * Returns a SystemProperty
     *
     * @param propName The Property to retrieve
     * @return The Property, or NULL if not found
     */
    @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
    public String getSystemProperty(final String propName) {
        String line = null;
        BufferedReader input = null;
        try {
            final Process p = Runtime.getRuntime().exec("getprop " + propName);
            input = new BufferedReader(new InputStreamReader(p.getInputStream(), "UTF-8"), 1024);
            line = input.readLine();
            input.close();
        } catch (IOException ex) {
            Timber.e(TAG, "Unable to read sysprop " + propName, ex);
        } finally {
            IOUtils.closeQuietly(input);
        }
        return line;
    }

    /**
     * Checks if there is enough Space on SDCard
     *
     * @param updateSize Size to Check
     * @return {@code true} if the Update will fit on SDCard, {@code false} if not enough space on
     * SDCard. Will also return false, if the SDCard is not mounted as read/write
     */
    @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
    public boolean enoughSpaceOnSdCard(final long updateSize) {
        boolean ret = false;
        final String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            final File path = Environment.getExternalStorageDirectory();
            final StatFs stat = new StatFs(path.getPath());
            final long blockSize = Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2 ?
                    stat.getBlockSizeLong() : stat.getBlockSize();
            final long availableBlocks =
                    Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2 ?
                            stat.getAvailableBlocksLong() : stat.getAvailableBlocks();

            ret = updateSize < availableBlocks * blockSize;
        }

        return ret;
    }

    private static final class RomUtilHolder {
        @SuppressWarnings("PMD.AccessorClassGeneration")
        private static volatile RomUtil sRomUtil = new RomUtil();
    }
}