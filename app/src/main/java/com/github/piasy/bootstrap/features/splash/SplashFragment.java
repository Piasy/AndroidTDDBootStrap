package com.github.piasy.bootstrap.features.splash;

import com.chenenyu.router.Router;
import com.github.piasy.bootstrap.R;
import com.github.piasy.bootstrap.base.android.BaseFragment;
import com.github.piasy.octostars.RouteTable;

/**
 * Created by Piasy{github.com/Piasy} on 20/09/2016.
 */

public class SplashFragment extends BaseFragment<SplashView, SplashPresenter, SplashComponent>
        implements SplashView {
    @Override
    protected int getLayoutRes() {
        return R.layout.splash_activity;
    }

    @Override
    protected void injectDependencies(final SplashComponent component) {
        component.inject(this);
    }

    @Override
    public void finishSplash(final boolean initSuccess) {
        if (initSuccess) {
            Router.build(RouteTable.TRENDING)
                    .go(getContext());
            getActivity().finish();
        }
    }
}
