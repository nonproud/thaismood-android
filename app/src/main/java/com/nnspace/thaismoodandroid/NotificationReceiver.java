package com.nnspace.thaismoodandroid;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import com.nnspace.thaismoodandroid.Activities.AddMoodActivity;

import com.nnspace.thaismoodandroid.Activities.SettingActivity;

import static com.nnspace.thaismoodandroid.R.drawable.*;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent repeat_intent = new Intent(context, AddMoodActivity.class);
        repeat_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context,
                SettingActivity.MOOD_NOTIFICATION_REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT );

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setContentIntent(pendingIntent)
                .setSmallIcon(thaismood_logo)
                .setContentTitle("ได้เวลาบันทึกอารมณ์ประจำวันแล้ว")
                .setContentText("สวัสดีคุณ nonproud ได้วเลาบันทึกอารมณ์ประจำวันแล้ว :)")
                .setAutoCancel(true);
        notificationManager.notify(SettingActivity.MOOD_NOTIFICATION_REQUEST_CODE, builder.build());

    }
}
