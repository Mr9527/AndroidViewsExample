package com.qhfax.androidviewsexample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;

/**
 * @author chenzhaojun
 * @date 2017/7/10
 * @description
 */

public class PageFragment extends Fragment {
    private static final String TAG = "PageFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page, container, false);
        ViewStub viewStub = (ViewStub) view.findViewById(R.id.stub);
        PageData data = getArguments().getParcelable(TAG);
        if (data != null) {
            viewStub.setLayoutResource(data.getId());
            viewStub.inflate();
        }
        return view;
    }

    public static PageFragment newInstance(PageData data) {
        Bundle args = new Bundle();
        args.putParcelable(TAG, data);
        PageFragment fragment = new PageFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
