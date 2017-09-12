package com.qhfax.androidviewsexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.qhfax.androidviewsexample.canvas.CanvasActivity;
import com.qhfax.androidviewsexample.indicator.PagerActivity;
import com.qhfax.androidviewsexample.progress.ProgressActivity;

public class MainActivity extends AppCompatActivity {
    public static final String[] TITLES = {"canvas", "progress", "indicator"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = (ListView) findViewById(R.id.function_list);
        listView.setDivider(null);
        listView.setAdapter(new FunctionListAdapter());
    }


    private class FunctionListAdapter extends BaseAdapter {

        FunctionListAdapter() {

        }

        @Override
        public int getCount() {
            return TITLES.length;
        }

        @Override
        public Object getItem(int position) {
            return TITLES[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.item_function, parent, false);
                holder = new ViewHolder();
                holder.btnFunction = (Button) convertView.findViewById(R.id.btnFunction);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.btnFunction.setText(TITLES[position]);
            initEvent(holder, position);
            return convertView;
        }

        private void initEvent(ViewHolder holder, final int position) {
            holder.btnFunction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    switch (position) {
                        case 0:
                            intent.setClass(MainActivity.this, CanvasActivity.class);
                            break;
                        case 1:
                            intent.setClass(MainActivity.this, ProgressActivity.class);
                            break;
                        case 2:
                            intent.setClass(MainActivity.this, PagerActivity.class);
                            break;
                        default:
                            throw new NullPointerException("No in listView register this activity");
                    }
                    startActivity(intent);
                }
            });
        }

    }

    private static class ViewHolder {
        Button btnFunction;
    }
}
