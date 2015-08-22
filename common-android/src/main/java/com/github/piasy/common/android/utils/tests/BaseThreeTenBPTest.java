package com.github.piasy.common.android.utils.tests;

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
 */
public abstract class BaseThreeTenBPTest {
    private static final AtomicBoolean initialized = new AtomicBoolean();

    protected void initThreeTenABP() {
        if (initialized.getAndSet(true)) {
            return;
        }
        TzdbZoneRulesProvider provider;
        try {
            InputStream is;
            File dat = new File("./common-android/src/test/assets/org/threeten/bp/TZDB.dat");
            if (dat.exists()) {
                is = new FileInputStream(dat);
            } else {
                dat = new File("../common-android/src/test/assets/org/threeten/bp/TZDB.dat");
                if (dat.exists()) {
                    is = new FileInputStream(dat);
                } else {
                    throw new FileNotFoundException("TZDB.dat");
                }
            }
            provider = new TzdbZoneRulesProvider(is);
        } catch (IOException e) {
            throw new IllegalStateException("TZDB.dat missing from assets.", e);
        }
        ZoneRulesProvider.registerProvider(provider);
    }
}
