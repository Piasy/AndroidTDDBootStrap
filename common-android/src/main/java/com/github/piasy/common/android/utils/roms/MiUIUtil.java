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

import android.content.ComponentName;
import android.content.Intent;
import android.text.TextUtils;

/**
 * Created by Piasy{github.com/Piasy} on 15/8/26.
 *
 * ROM utility class for MiUI.
 */
public class MiUIUtil {
    private static final String KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code";
    private static final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
    private static final String KEY_MIUI_INTERNAL_STORAGE = "ro.miui.internal.storage";

    private static final String AUTO_START_MANAGER_PACKAGE = "com.miui.securitycenter";
    private static final String AUTO_START_MANAGER_CLASS =
            "com.miui.permcenter.autostart.AutoStartManagementActivity";

    private final RomUtil mRomUtil;

    /**
     * Create instance with the given {@link RomUtil}.
     *
     * @param romUtil the given {@link RomUtil}.
     */
    public MiUIUtil(final RomUtil romUtil) {
        this.mRomUtil = romUtil;
    }

    /**
     * Check whether the device runs on MiUI.
     *
     * @return {@code true} if the device runs on MiUI.
     */
    public boolean isMiUI() {
        return !TextUtils.isEmpty(mRomUtil.getSystemProperty(KEY_MIUI_VERSION_CODE)) ||
                !TextUtils.isEmpty(mRomUtil.getSystemProperty(KEY_MIUI_VERSION_NAME)) ||
                !TextUtils.isEmpty(mRomUtil.getSystemProperty(KEY_MIUI_INTERNAL_STORAGE));
    }

    /**
     * Get the {@link Intent} that will start the MiUi AutoStartManagerActivity.
     *
     * @return the {@link Intent} that will start the MiUi AutoStartManagerActivity.
     */
    public Intent jump2AutoStartManager() {
        final Intent i = new Intent();
        final ComponentName comp =
                new ComponentName(AUTO_START_MANAGER_PACKAGE, AUTO_START_MANAGER_CLASS);
        i.setComponent(comp);
        return i;
    }
}
