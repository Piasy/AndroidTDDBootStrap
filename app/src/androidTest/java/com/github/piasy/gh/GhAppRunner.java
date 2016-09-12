package com.github.piasy.gh;

import android.app.Application;
import android.content.Context;
import com.github.piasy.test.runner.MultiDexRestMockRunner;

/**
 * Created by Piasy{github.com/Piasy} on 11/09/2016.
 */

public class GhAppRunner extends MultiDexRestMockRunner {
    @Override
    public Application newApplication(final ClassLoader cl, final String className,
            final Context context)
            throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        return super.newApplication(cl, MockBootstrapApp.class.getName(), context);
    }
}
