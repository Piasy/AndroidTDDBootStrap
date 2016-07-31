package com.github.piasy.gh.features.profile;

import android.os.Bundle;
import android.widget.Toast;
import com.github.piasy.base.android.BaseActivity;
import com.github.piasy.gh.model.users.GithubUser;
import com.yatatsu.autobundle.AutoBundleField;

public class ProfileActivity extends BaseActivity {

    @AutoBundleField
    GithubUser mUser;

    @Override
    protected boolean hasArgs() {
        return true;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(this, mUser.login(), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void initializeInjector() {
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        super.onBackPressed();
    }
}
