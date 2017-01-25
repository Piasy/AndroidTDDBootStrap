package com.github.piasy.octostars;

import android.app.Application;
import android.content.Context;
import android.support.test.runner.AndroidJUnitRunner;

/**
 * Created by Piasy{github.com/Piasy} on 11/09/2016.
 */

public class BootstrapAppRunner extends AndroidJUnitRunner {
    @Override
    public Application newApplication(final ClassLoader cl, final String className,
            final Context context)
            throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        AppShell.setRealAppName(MockBootstrapApp.class.getCanonicalName());
        return super.newApplication(cl, AppShell.class.getName(), context);
    }
}
