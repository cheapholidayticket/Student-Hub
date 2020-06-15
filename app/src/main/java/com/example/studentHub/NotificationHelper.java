package com.example.studentHub;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.studentHub.Admin.MainActivity;
import com.example.studentHub.R;
import com.google.firebase.messaging.FirebaseMessaging;

public class NotificationHelper {

    public static void displayNotification (Context context, String title, String body) {

        Intent intent = new Intent (context, MainActivity.class);
        FirebaseMessaging.getInstance().subscribeToTopic("students");
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 100,
            intent, PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context,
                MainActivity.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_baseline_school_24)
                .setContentTitle(title)
                .setContentText(body)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManagerCompat mNotificationmgr = NotificationManagerCompat.from(context);
        mNotificationmgr.notify(1, mBuilder.build());







    }

}
