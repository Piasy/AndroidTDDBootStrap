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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.ButterKnife;
import com.github.piasy.base.android.BaseMvpFragment;
import com.github.piasy.base.utils.ScreenUtil;
import com.github.piasy.lg.R;
import com.github.piasy.lg.features.albums.di.AlbumsComponent;
import com.github.piasy.lg.features.albums.mvp.AlbumsPresenter;
import com.github.piasy.lg.features.albums.mvp.AlbumsView;
import com.github.piasy.lg.features.photo.PhotoActivityAutoBundle;
import com.github.piasy.model.ligui.LGAlbum;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by Piasy{github.com/Piasy} on 4/10/16.
 */
public class AlbumsFragment extends BaseMvpFragment<AlbumsView, AlbumsPresenter, AlbumsComponent>
        implements AlbumsView {

    @Inject
    Resources mResources;
    @Inject
    ScreenUtil mScreenUtil;

    private AlbumsAdapter mAdapter;

    @Override
    protected int getLayoutRes() {
        return R.layout.albums_fragment;
    }

    @Override
    protected void inject() {
        getComponent().inject(this);
    }

    @Override
    protected void bindView(final View rootView) {
        final RecyclerView rvAlbums = ButterKnife.findById(rootView, R.id.mRvAlbums);
        mAdapter = new AlbumsAdapter(new AlbumsAdapter.Action() {
            @Override
            public void openAlbum(final LGAlbum album) {
                startActivitySafely(PhotoActivityAutoBundle.createIntentBuilder(album.cover())
                        .build(getContext()));
            }
        }, getContext(), mScreenUtil);
        rvAlbums.setLayoutManager(new LinearLayoutManager(getContext()));
        rvAlbums.setAdapter(mAdapter);
    }

    @Override
    protected void startBusiness() {
        super.startBusiness();
        mPresenter.loadAlbums();
    }

    @Override
    public void showAlbums(final List<LGAlbum> albums) {
        mAdapter.showAlbums(albums);
    }
}
