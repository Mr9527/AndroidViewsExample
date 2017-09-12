package com.qhfax.androidviewsexample.progress;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.qhfax.androidviewsexample.R;

public class ProgressActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

    }

    @Override
    protected void onResume() {
        super.onResume();
        VIPLevelProgressView progressView = (VIPLevelProgressView) findViewById(R.id.progress);
        progressView.setProgressByAnim(75);
    }
}
