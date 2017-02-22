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
