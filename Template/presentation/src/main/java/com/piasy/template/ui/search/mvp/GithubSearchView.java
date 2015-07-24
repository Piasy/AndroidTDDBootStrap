package com.piasy.template.ui.search.mvp;

import com.hannesdorfmann.mosby.mvp.MvpView;
import com.piasy.model.entities.GithubUser;

import java.util.List;

/**
 * Created by Piasy{github.com/Piasy} on 15/7/24.
 */
public interface GithubSearchView extends MvpView {

    void showSearchUserResult(List<GithubUser> users);

}
