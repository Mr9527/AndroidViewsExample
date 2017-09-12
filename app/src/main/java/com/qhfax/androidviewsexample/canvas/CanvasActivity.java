package com.qhfax.androidviewsexample.canvas;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AppCompatActivity;

import com.qhfax.androidviewsexample.PageData;
import com.qhfax.androidviewsexample.PageFragment;
import com.qhfax.androidviewsexample.R;
import com.qhfax.androidviewsexample.databinding.ActivityCanvasBinding;

import java.util.ArrayList;
import java.util.List;

public class CanvasActivity extends AppCompatActivity {

    private List<PageData> mListMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCanvasBinding bind = DataBindingUtil.setContentView(this, R.layout.activity_canvas);
        mListMode = new ArrayList<>();
        mListMode.add(new PageData(getString(R.string.pie), R.layout.layout_practice_pie));
        mListMode.add(new PageData(getString(R.string.coordinate), R.layout.layout_coordinate_view));
        bind.viewPager.setAdapter(new TabPageAdapter(getSupportFragmentManager()));
        bind.tab.setupWithViewPager(bind.viewPager);
    }

    private class TabPageAdapter extends FragmentStatePagerAdapter {

        TabPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PageFragment.newInstance(mListMode.get(position));
        }

        @Override
        public int getCount() {
            return mListMode.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mListMode.get(position).getName();
        }
    }

}
