/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Piasy
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

package com.github.piasy.lg.features.albums;

import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import butterknife.ButterKnife;
import com.github.piasy.base.android.BaseMvpFragment;
import com.github.piasy.lg.R;
import com.github.piasy.lg.features.albums.di.AlbumsComponent;
import com.github.piasy.lg.features.albums.mvp.AlbumsPresenter;
import com.github.piasy.lg.features.albums.mvp.AlbumsView;
import com.github.piasy.model.ligui.LGAlbum;
import com.github.piasy.model.users.GithubUser;
import java.util.List;
import javax.inject.Inject;
import timber.log.Timber;

public class AlbumsFragment extends BaseMvpFragment<AlbumsView, AlbumsPresenter, AlbumsComponent>
        implements AlbumsView {

    private static final int SPAN_COUNT = 3;

    @Inject
    Resources mResources;

    private AlbumsAdapter mAdapter;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_search;
    }

    @Override
    protected void inject() {
        getComponent().inject(this);
    }

    @Override
    protected void bindView(final View rootView) {
        final RecyclerView rvSearchResult = ButterKnife.findById(rootView, R.id.mRvSearchResult);
        mAdapter = new AlbumsAdapter(mResources, new AlbumsAdapter.Action() {
            @Override
            public void userDetail(final GithubUser user) {
            }
        });
        rvSearchResult.setLayoutManager(
                new StaggeredGridLayoutManager(SPAN_COUNT, StaggeredGridLayoutManager.VERTICAL));
        rvSearchResult.setAdapter(mAdapter);
    }

    @Override
    protected void startBusiness() {
        super.startBusiness();
        mPresenter.loadAlbums();
    }

    @Override
    public void showAlbums(final List<LGAlbum> albums) {
        for (LGAlbum album : albums) {
            Timber.d(album.name());
        }
        //mAdapter.showUsers(users);
    }
}
