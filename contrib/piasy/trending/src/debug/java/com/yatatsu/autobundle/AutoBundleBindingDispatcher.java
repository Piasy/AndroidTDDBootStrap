package com.yatatsu.autobundle;

import android.os.Bundle;
import android.support.annotation.NonNull;

public final class AutoBundleBindingDispatcher implements AutoBundleDispatcher {
    @Override
    public boolean bind(@NonNull Object target, @NonNull Bundle args) {
        return false;
    }

    @Override
    public boolean bind(@NonNull Object target) {
        return false;
    }

    @Override
    public boolean pack(@NonNull Object target, @NonNull Bundle args) {
        return false;
    }
}
