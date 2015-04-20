package com.garbonzobeans.basictimer;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;

/**
 * Created by Bfriedman on 4/15/15.
 */
//Extends the BroadcastReceiver class, this is important so we may register as a broadcast receiver and get our onReceive callback
public class AlarmNotification extends BroadcastReceiver {

    //override onReceiver so we can get our callback
    @Override
    public void onReceive(Context context, Intent intent) {

        /* Double check our context is valid */
        if(context == null)
            return;

        //// NOTIFICATION CODE

        //Post a notification to the user, note we are using the mipmap & string resource folders for our image/text, you can find them under res/
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context)
                        //set the image for this notification as or app icon
                        .setSmallIcon(R.mipmap.ic_launcher)
                        //set the title for this notification
                        .setContentTitle(context.getString(R.string.temp_title))
                        //set the description for this notification
                        .setContentText(context.getString(R.string.temp_text));

        //Create an intent to show the main activity again when this is pressed
        Intent i = new Intent(context, MainActivity.class);

        //Create a pending intent for the system to use
        PendingIntent pi = PendingIntent.getActivity( context, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);

        //attach our intent to our NotificationBuilder
        builder.setContentIntent(pi);

        //get a handle to the NotificationManager object from the system
        NotificationManager nm = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

        //create our notification
        Notification n = builder.build();

        //make our notification dismissable when pressed
        n.flags = Notification.FLAG_AUTO_CANCEL;

        //post our notification to the device
        nm.notify(0, n);


        //// VIBRATOR CODE

        //retrieve a reference to the system vibrator object
        Vibrator v = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
        //check if we actually have a vibrator (emulators & some devices will fail this)
        if(v.hasVibrator()) {
            //Create a pattern
            //NOTE: It will be interpreted as {SLEEP_TIME, VIBRATE_TIME, SLEEP_TIME, VIBRATE_TIME...}
            long[] pattern = { 100, 500, 100, 500};
            //Start vibrating, and pass -1 to say no looping
            v.vibrate(pattern, -1);
        }
    }
}
