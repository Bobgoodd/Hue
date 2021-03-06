package com.example.mijin.hue;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.mijin.hue.Day.GroupSettingScheduleActivity2;
import com.example.mijin.hue.Day.SettingScheduleActivity2;

/**
 * Created by mijin on 2017-12-07.
 */

public class BroadcastD extends BroadcastReceiver {
    String INTENT_ACTION = Intent.ACTION_BOOT_COMPLETED;

    @Override
    public void onReceive(Context context, Intent intent) {//알람 시간이 되었을때 onReceive를 호출함
        //NotificationManager 안드로이드 상태바에 메세지를 던지기위한 서비스 불러오고
        NotificationManager notificationmanager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent pendingIntent;
        if(intent.getBooleanExtra("isGroupSetting",false)) pendingIntent = PendingIntent.getActivity(context, 0, new Intent(context, GroupSettingScheduleActivity2.class), PendingIntent.FLAG_UPDATE_CURRENT);
        else pendingIntent = PendingIntent.getActivity(context, 0, new Intent(context, SettingScheduleActivity2.class), PendingIntent.FLAG_UPDATE_CURRENT);


        Log.d("받은거는", intent.getStringExtra("content"));
        Notification.Builder builder = new Notification.Builder(context);
        builder.setSmallIcon(R.drawable.hue).setTicker("HETT").setWhen(System.currentTimeMillis())
                .setNumber(1).setContentTitle("HUE 일정 알람").setContentText(intent.getStringExtra("content"))
                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE).setContentIntent(pendingIntent).setAutoCancel(true);

        notificationmanager.notify(1, builder.build());
    }

}