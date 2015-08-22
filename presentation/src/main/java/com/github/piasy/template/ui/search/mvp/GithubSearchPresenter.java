package com.github.piasy.template.ui.search.mvp;

import com.hannesdorfmann.mosby.mvp.MvpPresenter;

/**
 * Created by Piasy{github.com/Piasy} on 15/7/24.
 */
public interface GithubSearchPresenter extends MvpPresenter<GithubSearchView> {

    void loadUser();
}
