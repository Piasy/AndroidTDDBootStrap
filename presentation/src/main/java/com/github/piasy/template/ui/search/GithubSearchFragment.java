package com.github.piasy.template.ui.search;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.Bind;
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

/**
 * A placeholder fragment containing a simple view.
 */
@XLog
public class GithubSearchFragment
        extends BaseFragment<GithubSearchView, GithubSearchPresenter, GithubSearchComponent>
        implements GithubSearchView {

    @Inject
    EmailUtil mEmailUtil;
    @Bind(R.id.rv_search_result)
    RecyclerView mRvSearchResult;
    GithubSearchUserResultAdapter mAdapter;

    public GithubSearchFragment() {
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
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupView();

        presenter.loadUser();
        showProgress();
    }

    private void setupView() {
        mAdapter = new GithubSearchUserResultAdapter(getActivity().getResources(), mEmailUtil);
        mRvSearchResult.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRvSearchResult.setAdapter(mAdapter);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_github_search;
    }

    @Override
    public void showSearchUserResult(List<GithubUser> users) {
        mAdapter.addUsers(users);
        stopProgress(true);
    }
}
