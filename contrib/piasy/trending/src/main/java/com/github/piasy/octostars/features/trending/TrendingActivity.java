/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017 Piasy
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.github.piasy.octostars.features.trending;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.chenenyu.router.annotation.Route;
import com.github.piasy.octostars.BootstrapActivity;
import com.github.piasy.octostars.BootstrapApp;
import com.github.piasy.octostars.RouteTable;
import com.github.piasy.octostars.repos.GitHubRepo;
import java.util.List;
import javax.inject.Inject;

@Route(RouteTable.TRENDING)
public class TrendingActivity extends BootstrapActivity<TrendingComponent> implements TrendingView {

    @Inject
    TrendingPresenter mPresenter;

    @BindView(R2.id.mTrending)
    RecyclerView mTrending;

    private TrendingComponent mProfileComponent;
    private TrendingAdapter mTrendingAdapter;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_trending);
        ButterKnife.bind(this);

        mPresenter.attachView(this);

        mTrending.setLayoutManager(new LinearLayoutManager(this));
        mTrendingAdapter = new TrendingAdapter();
        mTrending.setAdapter(mTrendingAdapter);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();

        mPresenter.loadTrending("java", TrendingRepo.SINCE_DAILY);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mProgressDialog.dismiss();
        mPresenter.detachView();
        mPresenter.onDestroy();
    }

    @Override
    protected void initializeDi() {
        mProfileComponent = DaggerTrendingComponent.builder()
                .appComponent(BootstrapApp.get().appComponent())
                .build();
        mProfileComponent.inject(this);
    }

    @Override
    public TrendingComponent getComponent() {
        return mProfileComponent;
    }

    @Override
    public void showTrending(final List<GitHubRepo> trending) {
        mProgressDialog.dismiss();
        mTrendingAdapter.showTrending(trending);
    }
}
