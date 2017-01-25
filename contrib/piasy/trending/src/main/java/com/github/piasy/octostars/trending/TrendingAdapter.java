package com.github.piasy.octostars.trending;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import com.github.piasy.bootstrap.base.ux.BaseViewHolder;
import com.github.piasy.octostars.repos.GitHubRepo;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Piasy{github.com/Piasy} on 23/01/2017.
 */

class TrendingAdapter extends RecyclerView.Adapter<TrendingAdapter.TrendingVH> {

    private final List<GitHubRepo> mTrendingRepos = new ArrayList<>();

    void showTrending(final List<GitHubRepo> trendingRepos) {
        mTrendingRepos.clear();
        mTrendingRepos.addAll(trendingRepos);
        notifyDataSetChanged();
    }

    @Override
    public TrendingVH onCreateViewHolder(final ViewGroup parent, final int viewType) {
        return new TrendingVH(parent, R.layout.ui_trending_repo_item);
    }

    @Override
    public void onBindViewHolder(final TrendingVH holder, final int position) {
        holder.mTvFullName.setText(mTrendingRepos.get(position).full_name());
    }

    @Override
    public int getItemCount() {
        return mTrendingRepos.size();
    }

    static class TrendingVH extends BaseViewHolder {

        @BindView(R2.id.mTvFullName)
        TextView mTvFullName;

        public TrendingVH(final ViewGroup parent, final @LayoutRes int layout) {
            super(parent, layout);
        }
    }
}
