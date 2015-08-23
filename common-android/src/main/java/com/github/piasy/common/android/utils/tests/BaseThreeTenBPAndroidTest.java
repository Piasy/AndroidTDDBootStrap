package com.github.piasy.common.android.utils.tests;

import android.content.Context;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicBoolean;
import org.threeten.bp.zone.TzdbZoneRulesProvider;
import org.threeten.bp.zone.ZoneRulesProvider;

/**
 * Created by Piasy{github.com/Piasy} on 15/8/16.
 */
public abstract class BaseThreeTenBPAndroidTest {
    private static final AtomicBoolean initialized = new AtomicBoolean();

    protected void initThreeTenABP(Context context) {
        if (initialized.getAndSet(true)) {
            return;
        }
        TzdbZoneRulesProvider provider;
        try {
            InputStream is = context.getAssets().open("org/threeten/bp/TZDB.dat");
            provider = new TzdbZoneRulesProvider(is);
        } catch (IOException e) {
            throw new IllegalStateException("TZDB.dat missing from assets.", e);
        }

        ZoneRulesProvider.registerProvider(provider);
    }
}
