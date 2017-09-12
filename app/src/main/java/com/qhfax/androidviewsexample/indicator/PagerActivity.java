package com.qhfax.androidviewsexample.indicator;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import com.qhfax.androidviewsexample.R;

import java.util.ArrayList;
import java.util.List;

public class PagerActivity extends AppCompatActivity {
    public static final String[] TITLE = {"青铜会员", "白银会员", "黄金会员", "钻石会员"};
    List<View> views;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager);
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        ViewPageIndicator indicator = (ViewPageIndicator) findViewById(R.id.indicator);
        views=new ArrayList<>();
        views.add(new View(this));
        views.add(new View(this));
        views.add(new View(this));
        views.add(new View(this));
        viewPager.setAdapter(new PagerAdapter() {

            @Override
            public int getCount() {
                return views.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return TITLE[position];
            }


            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                View v = views.get(position);
                container.addView(v);
                return v;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(views.get(position));
            }

        });
        indicator.setWithViewPager(viewPager);
    }
}
