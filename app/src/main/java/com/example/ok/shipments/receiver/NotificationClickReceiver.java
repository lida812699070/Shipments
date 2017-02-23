package com.example.ok.shipments.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.ok.shipments.BaseSettings;
import com.example.ok.shipments.utils.FileUtil;
import com.example.ok.shipments.utils.LogU;

import java.io.File;

/**
 * Created by ${lida} on 2016/6/2.
 */
public class NotificationClickReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        LogU.e("-----------------------下载完成——------------------------");
        String file=intent.getStringExtra("apkPath");
        FileUtil.installApk(context,new File(file));

    }
}
