package com.github.piasy.octostars.trending;

import com.github.piasy.octostars.repos.GitHubRepo;
import com.github.piasy.yamvp.YaView;
import java.util.List;

/**
 * Created by Piasy{github.com/Piasy} on 20/09/2016.
 */

interface TrendingView extends YaView {
    void showTrending(List<GitHubRepo> trending);
}
