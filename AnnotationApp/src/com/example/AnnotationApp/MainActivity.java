package com.example.AnnotationApp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ((TextView) findViewById(R.id.text_view)).setText("MainActivityu");
        MyActivityBuilder builder = new MyActivityBuilder();
        builder.setdataTwo("data two");
        builder.setdataOne(1);
        Intent intent= builder.getIntent(this);
        startActivity(intent);

    }
}
