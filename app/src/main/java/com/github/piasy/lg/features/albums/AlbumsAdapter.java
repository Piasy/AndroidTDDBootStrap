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

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.ButterKnife;
import com.facebook.drawee.view.SimpleDraweeView;
import com.github.piasy.lg.R;
import com.github.piasy.model.ligui.LGAlbum;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Piasy{github.com/Piasy} on 15/8/3.
 *
 * Recycler view adapter.
 */
public final class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.AlbumVH> {

    private final List<LGAlbum> mAlbums = new ArrayList<>();
    private final Action mAction;

    /**
     * create adapter with needed dependencies.
     *
     * @param action used to perform action.
     */
    public AlbumsAdapter(final Action action) {
        super();
        mAction = action;
    }

    /**
     * add albums
     *
     * @param albums to be added.
     */
    public void showAlbums(@NonNull final List<LGAlbum> albums) {
        mAlbums.clear();
        mAlbums.addAll(albums);
        notifyDataSetChanged();
    }

    @Override
    public AlbumVH onCreateViewHolder(final ViewGroup parent, final int type) {
        return new AlbumVH(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.albums_item, parent, false), mAction);
    }

    @Override
    public void onBindViewHolder(final AlbumVH vh, final int position) {
        vh.bind(mAlbums.get(position));
    }

    @Override
    public int getItemCount() {
        return mAlbums.size();
    }

    /**
     * actions to perform on a GithubUser card.
     */
    public interface Action {

        /**
         * view the detail info.
         *
         * @param album album to view detail info.
         */
        void openAlbum(LGAlbum album);
    }

    /**
     * View holder to hold the item view of RecyclerView.
     */
    static class AlbumVH extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final SimpleDraweeView mIvCover;
        private final TextView mTvName;
        private final CardView mCardView;
        private final Action mAction;
        private LGAlbum mAlbum;

        /**
         * create view holder.
         *
         * @param itemView view to hold.
         * @param action to perform action.
         */
        AlbumVH(final View itemView, final Action action) {
            super(itemView);
            mAction = action;
            ButterKnife.bind(this, itemView);
            mIvCover = ButterKnife.findById(itemView, R.id.mIvCover);
            mTvName = ButterKnife.findById(itemView, R.id.mTvName);
            mCardView = ButterKnife.findById(itemView, R.id.mCardView);
            mCardView.setOnClickListener(this);
        }

        /**
         * bind the GithubUser to the view.
         *
         * @param album GithubUser to bind.
         */
        void bind(final LGAlbum album) {
            mAlbum = album;
            mIvCover.setImageURI(Uri.parse(mAlbum.cover() == null ? "" : mAlbum.cover()));
            mTvName.setText(mAlbum.name());
        }

        @Override
        public void onClick(@NonNull final View v) {
            mAction.openAlbum(mAlbum);
        }
    }
}
