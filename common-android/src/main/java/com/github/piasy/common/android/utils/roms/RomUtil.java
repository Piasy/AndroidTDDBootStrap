package com.github.piasy.common.android.utils.roms;

import android.os.Environment;
import android.os.StatFs;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import org.apache.commons.io.IOUtils;
import timber.log.Timber;

public class RomUtil {

    private static final String TAG = "RomUtil";

    private static class RomUtilHolder {
        private static final RomUtil sInstance = new RomUtil();
    }

    public static RomUtil provideRomUtil() {
        return RomUtilHolder.sInstance;
    }

    private RomUtil() {
    }

    /**
     * Returns a SystemProperty
     *
     * @param propName The Property to retrieve
     * @return The Property, or NULL if not found
     */
    public String getSystemProperty(String propName) {
        String line;
        BufferedReader input = null;
        try {
            Process p = Runtime.getRuntime().exec("getprop " + propName);
            input = new BufferedReader(new InputStreamReader(p.getInputStream()), 1024);
            line = input.readLine();
            input.close();
        } catch (IOException ex) {
            Timber.e(TAG, "Unable to read sysprop " + propName, ex);
            return null;
        } finally {
            IOUtils.closeQuietly(input);
        }
        return line;
    }

    /**
     * Checks if there is enough Space on SDCard
     *
     * @param UpdateSize Size to Check
     * @return True if the Update will fit on SDCard, false if not enough space on SDCard
     * Will also return false, if the SDCard is not mounted as read/write
     */
    public boolean EnoughSpaceOnSdCard(long UpdateSize) {
        String status = Environment.getExternalStorageState();
        if (!status.equals(Environment.MEDIA_MOUNTED)) {
            return false;
        }
        File path = Environment.getExternalStorageDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return (UpdateSize < availableBlocks * blockSize);
    }
}