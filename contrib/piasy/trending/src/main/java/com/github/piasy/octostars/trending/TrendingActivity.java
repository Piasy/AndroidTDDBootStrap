package com.github.piasy.octostars.trending;

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

        mTrending.setLayoutManager(new LinearLayoutManager(this));
        mTrendingAdapter = new TrendingAdapter();
        mTrending.setAdapter(mTrendingAdapter);

        mPresenter.attachView(this);

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
