package com.piasy.template.ui.search;

import com.piasy.common.Constants;
import com.piasy.model.entities.GithubSearchResult;
import com.piasy.model.entities.GithubUser;
import com.piasy.model.rest.GsonProvider;
import com.piasy.model.rest.github.GithubAPI;
import com.piasy.template.R;
import com.piasy.template.base.BaseFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


/**
 * A placeholder fragment containing a simple view.
 */
public class GithubSearchActivityFragment extends BaseFragment {

    public GithubSearchActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_github_search, container, false);
    }

    @Bind(R.id.tv_content)
    TextView mTvContent;

    @Inject
    GithubAPI mGithubAPI;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        getComponent(GithubSearchComponent.class).inject(this);

        mGithubAPI.searchGithubUsers("piasy",
                Constants.GithubAPIParams.SEARCH_SORT_JOINED,
                Constants.GithubAPIParams.SEARCH_ORDER_DESC)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<GithubSearchResult<GithubUser>>() {
                    @Override
                    public void call(GithubSearchResult<GithubUser> githubUserSearchResult) {
                        mTvContent.setText(GsonProvider.provideGson().toJson(githubUserSearchResult));
                    }
                });
    }
}
