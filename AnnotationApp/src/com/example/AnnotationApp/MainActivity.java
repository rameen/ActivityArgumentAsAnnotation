package com.example.AnnotationApp;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        String data = "data to be passed";
        //MyActivityLauncher.getIntent(data);
        MyActivity.getIntent(data);
    }
}
