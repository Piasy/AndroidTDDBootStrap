package com.piasy.template.base;

import com.piasy.common.di.HasComponent;

import android.support.v4.app.Fragment;

/**
 * Created by Piasy{github.com/Piasy} on 15/7/23.
 */
public class BaseFragment extends Fragment {

    @SuppressWarnings("unchecked")
    protected <C> C getComponent(Class<C> componentType) {
        return componentType.cast(((HasComponent<C>) getActivity()).getComponent());
    }
}
