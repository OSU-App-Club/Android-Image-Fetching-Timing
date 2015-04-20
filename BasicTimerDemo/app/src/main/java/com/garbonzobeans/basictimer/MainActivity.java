package com.garbonzobeans.basictimer;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //call our super
        super.onCreate(savedInstanceState);

        //set our base layout for this activity, in this case activity_main.xml is what we're using
        setContentView(R.layout.activity_main);

        //fetch our 'Set Alarm' button by finding it with the id 'setAlarmButton'
        Button setAlarmButton = (Button)findViewById(R.id.setAlarmButton);

        //fetch our text field by the id we gave it (in this case 'timeView'). We'll use this to extract what time we gave.
        //NOTE the final declaration is so we can access our timeView variable in our anonymous inner class below
        final EditText timeView = (EditText)findViewById(R.id.timeView);

        //set the onClickListener (who gets called when the button is clicked) to an anonymous inner class
        setAlarmButton.setOnClickListener(new View.OnClickListener() {

            //override the onClick method so we can get called when the button is pressed
            @Override
            public void onClick(View v) {

                //Extract the time from our EditText
                String txt = timeView.getText().toString();

                //clear our display
                timeView.setText("");

                //Set zero as default, if blank text is passed we will simply set an instant alarm
                long sleepTime = 0;
                if(!txt.equals("")) {
                    //Parse a long from the string we extracted in our timeView, and make it represent a second (AlarmManager takes milliseconds)
                    sleepTime = Long.parseLong(txt) * 1000;
                }

                //Get a handle to the system alarm
                AlarmManager am = (AlarmManager)getApplication().getSystemService(Context.ALARM_SERVICE);
                //Create a new intent to using our AlarmNotification class
                Intent intent = new Intent(getApplication(), AlarmNotification.class);
                //add some data into our intent, just a basic key/value system.
                intent.putExtra("time", sleepTime);
                //Create a pending intent to allow the system to call on our behalf, quote from the docs to explain:
                /*
                    By giving a PendingIntent to another application (system), you are granting it the right to perform the operation you have specified as if the other application (system) was yourself
                 */
                PendingIntent magnumPI = PendingIntent.getBroadcast( getApplication(), 201923, intent, PendingIntent.FLAG_CANCEL_CURRENT);

                //Set our alarm, passing Type, a sleeptime in milliseconds offset from the CURRENT SystemClock time, and a pending intent
                am.set( AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + sleepTime, magnumPI);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
