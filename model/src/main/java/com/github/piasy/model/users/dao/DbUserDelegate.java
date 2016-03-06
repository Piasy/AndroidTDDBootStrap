/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Piasy
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

package com.github.piasy.model.users.dao;

import com.github.piasy.model.users.GithubUser;
import com.pushtorefresh.storio.sqlite.operations.delete.DeleteResult;
import com.pushtorefresh.storio.sqlite.operations.put.PutResults;
import java.util.List;
import rx.Observable;

/**
 * Created by Piasy{github.com/Piasy} on 15/8/14.
 *
 * Delegate to {@link com.pushtorefresh.storio.sqlite.StorIOSQLite}, decouple logic with
 * stor io library.
 */
public interface DbUserDelegate {

    /**
     * Delete all github user.
     *
     * @return delete result.
     */
    DeleteResult deleteAllGithubUser();

    /**
     * put all github user into db.
     *
     * @param users users to put.
     * @return put result.
     */
    PutResults<GithubUser> putAllGithubUser(List<GithubUser> users);

    /**
     * Get all github user in sync.
     *
     * @return all github user in db.
     */
    List<GithubUser> getAllGithubUser();

    /**
     * get all github user in async, with {@link Observable}.
     *
     * @return the {@link Observable} that emit {@link List<GithubUser>} when db changes.
     */
    Observable<List<GithubUser>> getAllGithubUserReactively();
}
