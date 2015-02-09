package com.example.AnnotationApp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import annoations.ActivityArg;

public class MyActivity extends Activity
{
    public static final String EXTRA_DATA = "myactivity:data";
    public static final String EXTRA_DATA_TWO = "myactivity:dataTwo";
    @ActivityArg(key = EXTRA_DATA)
    Integer dataOne;
    @ActivityArg(key = EXTRA_DATA_TWO)
    String dataTwo;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }


    public static Intent getIntent(String data)
    {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_DATA, data);
        return intent;
    }
}
