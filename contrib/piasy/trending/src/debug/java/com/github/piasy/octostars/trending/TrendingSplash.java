package com.github.piasy.octostars.trending;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.github.piasy.bootstrap.mocks.DebugSplashActivity;

/**
 * Created by Piasy{github.com/Piasy} on 23/01/2017.
 */

public class TrendingSplash extends DebugSplashActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startActivity(new Intent(this, TrendingActivity.class));
        finish();
    }
}
