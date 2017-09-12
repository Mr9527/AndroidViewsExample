package com.qhfax.androidviewsexample.canvas;

import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioGroup;

import com.qhfax.androidviewsexample.R;
import com.qhfax.androidviewsexample.canvas.path.BezierView;

public class BezierActivity extends AppCompatActivity {
    private BezierView mBezierView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_second_order_bezier);
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radio);
        mBezierView = (BezierView) findViewById(R.id.bezier);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.second_order:
                        mBezierView.setType(BezierView.SECOND_ORDER_BEZIER);
                        break;
                    case R.id.third_order:
                        mBezierView.setType(BezierView.THIRD_ORDER_BEZIER);
                        break;
                }
            }
        });
    }
}
