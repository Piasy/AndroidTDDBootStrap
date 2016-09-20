package com.github.piasy.gh.features.profile;

import com.github.piasy.yamvp.dagger2.ActivityScope;
import com.github.piasy.yamvp.rx.YaRxPresenter;
import javax.inject.Inject;

/**
 * Created by Piasy{github.com/Piasy} on 20/09/2016.
 */

@ActivityScope
public class ProfilePresenter extends YaRxPresenter<ProfileView> {
    @Inject
    ProfilePresenter() {
        super();
    }
}
