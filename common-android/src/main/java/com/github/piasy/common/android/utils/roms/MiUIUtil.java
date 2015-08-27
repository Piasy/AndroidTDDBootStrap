package com.github.piasy.common.android.utils.roms;

import android.content.ComponentName;
import android.content.Intent;
import android.text.TextUtils;

/**
 * Created by Piasy{github.com/Piasy} on 15/8/26.
 */
public class MiUIUtil {
    private static final String KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code";
    private static final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
    private static final String KEY_MIUI_INTERNAL_STORAGE = "ro.miui.internal.storage";

    private static final String AUTO_START_MANAGER_PACKAGE = "com.miui.securitycenter";
    private static final String AUTO_START_MANAGER_CLASS =
            "com.miui.permcenter.autostart.AutoStartManagementActivity";

    private final RomUtil mRomUtil;

    public MiUIUtil(RomUtil mRomUtil) {
        this.mRomUtil = mRomUtil;
    }

    public boolean isMiUI() {
        return !TextUtils.isEmpty(mRomUtil.getSystemProperty(KEY_MIUI_VERSION_CODE))
                || !TextUtils.isEmpty(mRomUtil.getSystemProperty(KEY_MIUI_VERSION_NAME))
                || !TextUtils.isEmpty(mRomUtil.getSystemProperty(KEY_MIUI_INTERNAL_STORAGE));
    }

    public Intent jump2AutoStartManeger() {
        Intent i = new Intent();
        ComponentName comp =
                new ComponentName(AUTO_START_MANAGER_PACKAGE, AUTO_START_MANAGER_CLASS);
        i.setComponent(comp);
        return i;
    }
}
