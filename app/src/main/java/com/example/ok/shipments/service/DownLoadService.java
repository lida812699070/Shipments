package com.example.ok.shipments.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.ok.shipments.Activity.LoginActivity;
import com.example.ok.shipments.BaseSettings;
import com.example.ok.shipments.MyAppLocation;
import com.example.ok.shipments.R;
import com.example.ok.shipments.receiver.NotificationClickReceiver;
import com.example.ok.shipments.utils.FileUtil;
import com.example.ok.shipments.utils.LogU;
import com.example.ok.shipments.utils.VibratorUtil;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import java.io.File;

/**
 * Created by ${lida} on 2016/6/2.
 */
public class DownLoadService extends Service {


    private String apkUrl;
    private Notification mNotification;
    private NotificationManager mNotificationManager;
    private Notification.Builder mBuilder;
    private HttpUtils mHttpUtils;

    @Override
    public void onCreate() {
        super.onCreate();
        mHttpUtils = new HttpUtils();//最好整个应用一个实例
//设置线程池数量
        mHttpUtils.configRequestThreadPoolSize(4);

//设置请求重试次数
        mHttpUtils.configRequestRetryCount(3);

//设置响应编码
        mHttpUtils.configResponseTextCharset("utf-8");

//设置请求超时时间
        mHttpUtils.configTimeout(15000);
        mNotificationManager = (NotificationManager) getSystemService(android.content.Context.NOTIFICATION_SERVICE);
        try {
            downLoad();
        }catch (Exception e){
            mNotificationManager.cancelAll();
            Toast.makeText(this,"下载已停止",Toast.LENGTH_SHORT).show();
            stopSelf();
        }

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void downLoad() {
        apkUrl = "http://" + BaseSettings.VERSIONS + ".xsjky.cn/AppUpdateFiles/" + BaseSettings.APP_NAME + ".apk";


        //设置点击一次后消失（如果没有点击事件，则该方法无效。）
        //mBuilder.setAutoCancel(true);

        //TaskStackBuilder stackBuilder = TaskStackBuilder.create(getApplicationContext());
        //点击通知之后需要跳转的页面
               /* if (!isAppOnForeground(context)){
                    Intent resultIntent = new Intent(context, LoginActivity.class);
                    stackBuilder.addParentStack(LoginActivity.class);
                    stackBuilder.addNextIntent(resultIntent);
                    PendingIntent pIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
                    mBuilder.setContentIntent(pIntent);
                }*/
        mBuilder = new Notification.Builder(getApplicationContext());
        mBuilder.setSmallIcon(R.drawable.logo1);
        mBuilder.setContentText("新世纪");
        mBuilder.setTicker("开始下载");
        mNotificationManager.notify(2, mBuilder.build());
        //使用TaskStackBuilder为“通知页面”设置返回关系
        //为点击通知后打开的页面设定 返回 页面。（在manifest中指定）

        // mId allows you to update the notification later on.
        //关闭dialog

        mHttpUtils.download(apkUrl, MyAppLocation.apkPath, true, new RequestCallBack<File>() {
            @Override
            public void onSuccess(ResponseInfo<File> responseInfo) {
                File apk = (File) responseInfo.result;
                downFinish();
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                if ("maybe the file has downloaded completely".equals(msg)) {
                    Toast.makeText(DownLoadService.this,"已经下载完了",Toast.LENGTH_SHORT).show();
                    downFinish();
                }
            }

            @Override
            public void onLoading(final long total, final long current, boolean isUploading) {
                super.onLoading(total, current, isUploading);
                //progressBar.setProgress((int) ((current * 100) / total));

                LogU.e((int) ((current * 100) / total)+"");
                Message message = handler.obtainMessage();
                message.what=0;
                message.arg1=(int) ((current * 100) / total);
                handler.sendMessage(message);

            }
        });
        //Toast.makeText(LoginActivity.this, "确认" + which, Toast.LENGTH_SHORT).show();

    }

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int proess = msg.arg1;
            mBuilder.setProgress(100, proess, false);
            mBuilder.setContentTitle(proess+"%");
            mNotificationManager.notify(2, mBuilder.build());
        }
    };
    private long time;
    private void downFinish() {
        mNotificationManager.cancel(2);
        Notification.Builder mBuilder = new Notification.Builder(getApplicationContext());
        mBuilder.setTicker("下载已完成");
        mBuilder.setSmallIcon(R.drawable.logo1);
        mBuilder.setContentTitle("下载已完成");
        mBuilder.setContentText("新世纪");
        VibratorUtil.Vibrate(getApplication(), 300);

        Intent clickIntent = new Intent(DownLoadService.this, NotificationClickReceiver.class);
        clickIntent.putExtra("apkPath",MyAppLocation.apkPath);

        PendingIntent contentIntent = PendingIntent.getBroadcast(getApplicationContext(), 1, clickIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(contentIntent);
        mBuilder.build().defaults=Notification.DEFAULT_SOUND;
        mNotificationManager.notify(1, mBuilder.build());
        DownLoadService.this.stopSelf();
    }
}
