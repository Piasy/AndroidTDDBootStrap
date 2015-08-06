package com.piasy.template.ui.search;

import com.google.gson.Gson;

import com.piasy.model.entities.GithubUser;
import com.piasy.template.R;
import com.piasy.template.base.BaseFragment;
import com.piasy.template.ui.search.di.GithubSearchComponent;
import com.piasy.template.ui.search.mvp.GithubSearchPresenter;
import com.piasy.template.ui.search.mvp.GithubSearchView;
import com.promegu.xlog.base.XLog;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;


/**
 * A placeholder fragment containing a simple view.
 */
@XLog
public class GithubSearchFragment
        extends BaseFragment<GithubSearchView, GithubSearchPresenter, GithubSearchComponent>
        implements GithubSearchView {

    @Inject
    Gson mGson;
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

        String test = "{\"login\":\"PiaSys\"}";
        Toast.makeText(getActivity(), mGson.fromJson(test, GithubUser.class).getLogin(), Toast.LENGTH_LONG)
                .show();

        presenter.loadUser();
        showProgress();
    }

    private void setupView() {
        mAdapter = new GithubSearchUserResultAdapter();
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
