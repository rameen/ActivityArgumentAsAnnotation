package com.example.AnnotationApp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import annoations.ActivityArg;

public class MyActivity extends Activity
{
    public static final String EXTRA_DATA = "myactivity:data";
    public static final String EXTRA_DATA_TWO = "myactivity:dataTwo";
    @ActivityArg(key = EXTRA_DATA)
    Integer dataOne;
    @ActivityArg(key = EXTRA_DATA_TWO)
    String dataTwo;
    private final String simpleName = MyActivity.this.getClass().getSimpleName();


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        TextView viewById = (TextView) findViewById(R.id.text_view);
        viewById.setText("done done");
        viewById.setClickable(true);
        viewById.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(MyActivity.this, getIntent().getStringExtra(EXTRA_DATA_TWO), Toast.LENGTH_LONG).show();
                Log.d(simpleName, getIntent().getStringExtra(EXTRA_DATA_TWO));
            }
        });



    }

   /* public Intent getLauncherIntent(Context context)
    {
            Intent intent = new Intent(context,MyActivity.class);

    }
*/

}
