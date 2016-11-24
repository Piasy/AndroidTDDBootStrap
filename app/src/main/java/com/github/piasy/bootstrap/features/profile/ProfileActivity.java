package com.github.piasy.bootstrap.features.profile;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import com.github.piasy.bootstrap.BootstrapActivity;
import com.github.piasy.bootstrap.BootstrapApp;
import com.github.piasy.bootstrap.features.search.SearchFragment;
import com.github.piasy.bootstrap.model.users.GithubUser;
import com.yatatsu.autobundle.AutoBundleField;

public class ProfileActivity extends
        BootstrapActivity<ProfileView, ProfilePresenter, ProfileComponent> {

    @AutoBundleField
    GithubUser mUser;
    private ProfileComponent mProfileComponent;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(this, mUser.login(), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void initializeDi() {
        mProfileComponent = BootstrapApp.get().appComponent().profileComponent(getActivityModule());
    }

    @Override
    public void onBackPressed() {
        final Intent intent = new Intent();
        intent.putExtra(SearchFragment.RESULT_KEY_DUMMY, "bingo!");
        setResult(RESULT_OK, intent);
        super.onBackPressed();
    }

    @Override
    public ProfileComponent getComponent() {
        return mProfileComponent;
    }
}
