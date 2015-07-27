package com.piasy.template.ui.search;

import com.google.gson.Gson;

import com.piasy.common.Constants;
import com.piasy.model.entities.GithubUser;
import com.piasy.template.R;
import com.piasy.template.base.BaseFragment;
import com.piasy.template.ui.search.di.GithubSearchComponent;
import com.piasy.template.ui.search.mvp.GithubSearchPresenter;
import com.piasy.template.ui.search.mvp.GithubSearchView;
import com.promegu.xlog.base.XLog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * A placeholder fragment containing a simple view.
 */
@XLog
public class GithubSearchFragment
        extends BaseFragment<GithubSearchView, GithubSearchPresenter, GithubSearchComponent>
        implements GithubSearchView {

    @Bind(R.id.tv_content)
    TextView mTvContent;
    @Inject
    Gson mGson;

    public GithubSearchFragment() {
    }

    @Override
    public GithubSearchPresenter createPresenter() {
        return null;
    }

    @Override
    protected void inject() {
        getComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_github_search, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        String test = "{\"login\":\"PiaSys\"}";
        Toast.makeText(getActivity(), mGson.fromJson(test, GithubUser.class).getLogin(), Toast.LENGTH_LONG)
                .show();

        presenter.searchUser("piasy",
                Constants.GithubAPIParams.SEARCH_SORT_JOINED,
                Constants.GithubAPIParams.SEARCH_ORDER_DESC);
        showProgress();
    }

    @Override
    public void showSearchUserResult(List<GithubUser> users) {
        mTvContent.setText(mGson.toJson(users));
        stopProgress(true);
    }
}
