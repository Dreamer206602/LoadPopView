package com.mx;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mx.loadingpoppoint.LoadingPopPointView;

public class MainActivity extends AppCompatActivity {

    private LoadingPopPointView mLoadingPopPointView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
