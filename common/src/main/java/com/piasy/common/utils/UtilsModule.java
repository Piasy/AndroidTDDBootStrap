package com.piasy.common.utils;

import com.piasy.common.Constants;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

/**
 * Created by Piasy{github.com/Piasy} on 15/8/9.
 */
@Module
public class UtilsModule {

    @Singleton
    @Provides
    EmailUtil provideEmailUtil() {
        return new EmailUtil(Constants.REPatterns.EMAIL_PATTERN);
    }
}
