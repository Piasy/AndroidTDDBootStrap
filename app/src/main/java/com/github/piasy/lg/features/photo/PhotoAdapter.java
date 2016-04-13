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

package com.github.piasy.lg.features.photo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.github.piasy.lg.R;
import java.util.List;

import static butterknife.ButterKnife.findById;

/**
 * Created by Piasy{github.com/Piasy} on 16/4/13.
 */
public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoVH> {

    private final List<String> mPhotos;

    public PhotoAdapter(final List<String> photos) {
        mPhotos = photos;
    }

    @Override
    public PhotoVH onCreateViewHolder(final ViewGroup parent, final int viewType) {
        return new PhotoVH(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.photo_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final PhotoVH holder, final int position) {
        holder.mPhotoView.loadPhoto(mPhotos.get(position));
    }

    @Override
    public int getItemCount() {
        return mPhotos.size();
    }

    static class PhotoVH extends RecyclerView.ViewHolder {
        private final LGPhotoView mPhotoView;

        PhotoVH(final View view) {
            super(view);
            mPhotoView = findById(view, R.id.mPhoto);
        }
    }
}
