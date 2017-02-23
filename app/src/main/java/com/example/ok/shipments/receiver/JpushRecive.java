package com.example.ok.shipments.receiver;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;

import com.example.ok.shipments.Activity.LoginActivity;
import com.example.ok.shipments.R;
import com.example.ok.shipments.utils.LogU;

import java.util.List;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by ${lida} on 2016/7/11.
 */
public class JpushRecive extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle bundle = intent.getExtras();
        if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(500);
            String msg = "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE);
            LogU.e(msg);
            resultActivityBackApp(context, msg);

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            String msg = bundle.getString(JPushInterface.EXTRA_EXTRA);
            LogU.e(msg);
            Log.e("TAG", "[MyReceiver] 接收到推送下来的通知");

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            //TODO
            if (!isAppOnForeground(context)) {
                openNof(context);
            }
        }
    }

    private void openNof(Context context) {
        Intent intent = new Intent();
        intent.setClass(context.getApplicationContext(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.getApplicationContext().startActivity(intent);
    }

    public void resultActivityBackApp(Context context, String text) {
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification.Builder mBuilder = new Notification.Builder(context);
        mBuilder.setTicker("自定义通知");
        mBuilder.setSmallIcon(R.drawable.logo1);
        mBuilder.setContentTitle("自定义通知");
        mBuilder.setContentText(text);

        //设置点击一次后消失（如果没有点击事件，则该方法无效。）
        mBuilder.setAutoCancel(true);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        //点击通知之后需要跳转的页面
        if (!isAppOnForeground(context)) {
            Intent resultIntent = new Intent(context, LoginActivity.class);
            stackBuilder.addParentStack(LoginActivity.class);
            stackBuilder.addNextIntent(resultIntent);
            PendingIntent pIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            mBuilder.setContentIntent(pIntent);
        }

        //使用TaskStackBuilder为“通知页面”设置返回关系
        //为点击通知后打开的页面设定 返回 页面。（在manifest中指定）

        // mId allows you to update the notification later on.
        nm.notify(2, mBuilder.build());
    }

    private boolean isAppOnForeground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getApplicationContext().getSystemService(
                Context.ACTIVITY_SERVICE);
        String packageName = context.getApplicationContext().getPackageName();
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null)
            return false;
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }
        return false;
    }
}
