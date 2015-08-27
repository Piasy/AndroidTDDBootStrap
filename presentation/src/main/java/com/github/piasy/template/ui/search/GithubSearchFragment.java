package com.github.piasy.template.ui.search;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.Bind;
import com.github.piasy.common.android.utils.roms.MiUIUtil;
import com.github.piasy.common.android.utils.ui.CenterTitleSideButtonBar;
import com.github.piasy.common.utils.EmailUtil;
import com.github.piasy.model.entities.GithubUser;
import com.github.piasy.template.R;
import com.github.piasy.template.base.BaseFragment;
import com.github.piasy.template.ui.search.di.GithubSearchComponent;
import com.github.piasy.template.ui.search.mvp.GithubSearchPresenter;
import com.github.piasy.template.ui.search.mvp.GithubSearchView;
import com.promegu.xlog.base.XLog;
import java.util.List;
import javax.inject.Inject;
import timber.log.Timber;

/**
 * A placeholder fragment containing a simple view.
 */
@XLog
public class GithubSearchFragment
        extends BaseFragment<GithubSearchView, GithubSearchPresenter, GithubSearchComponent>
        implements GithubSearchView {

    @Inject
    EmailUtil mEmailUtil;
    @Bind(R.id.mRvSearchResult)
    RecyclerView mRvSearchResult;
    GithubSearchUserResultAdapter mAdapter;
    @Bind(R.id.mTitleBar)
    CenterTitleSideButtonBar mTitleBar;
    @Inject
    Resources mResources;

    public GithubSearchFragment() {
        Timber.i("CTSBB GithubSearchFragment()");
    }

    @Override
    protected void inject() {
        getComponent().inject(this);
    }

    @Override
    public GithubSearchPresenter createPresenter() {
        return null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupView();

        presenter.loadUser();
        showProgress();
    }

    private void setupView() {
        mAdapter = new GithubSearchUserResultAdapter(getActivity().getResources(), mEmailUtil,
                new GithubSearchUserResultAdapter.Action() {
                    @Override
                    public void userDetail(GithubUser user) {
                        startActivity(new Intent(getActivity(), DemoKotlinActivity.class));
                    }
                });
        mRvSearchResult.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRvSearchResult.setAdapter(mAdapter);
    }

    @Inject
    MiUIUtil mMiUIUtil;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_github_search;
    }

    @Override
    public void showSearchUserResult(List<GithubUser> users) {
        mAdapter.addUsers(users);
        stopProgress(true);
    }

    @Override
    public void showHelpBar() {
        Snackbar snackbar =
                Snackbar.make(mTitleBar, R.string.error_auto_start, Snackbar.LENGTH_INDEFINITE)
                        .setAction(R.string.fix_it, v -> {
                            startActivity(mMiUIUtil.jump2AutoStartManeger());
                        });
        snackbar.getView().setBackgroundResource(android.R.color.holo_blue_light);
        if (snackbar.getView() instanceof Snackbar.SnackbarLayout &&
                ((Snackbar.SnackbarLayout) snackbar.getView()).getChildAt(0) instanceof TextView) {
            TextView snackBarText =
                    (TextView) ((Snackbar.SnackbarLayout) snackbar.getView()).getChildAt(0);
            snackBarText.setTextColor(mResources.getColor(android.R.color.white));
        }
        snackbar.show();
    }
}
