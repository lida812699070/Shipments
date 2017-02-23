package com.example.ok.shipments.utils;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import com.example.ok.shipments.MyAppLocation;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

/**
 * Created by ${lida} on 2016/6/13.
 */
public class DialogUtils {

    private static ProgressDialog progressDialog;

    public static void showDialog(final DialoginOkCallBack callBack, String message, Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);  //先得到构造器
        builder.setTitle("提示"); //设置标题
        builder.setMessage(message + ""); //设置内容
        //builder.setIcon(R.mipmap.ic_launcher);//设置图标，图片id即可
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() { //设置确定按钮
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //dialog.dismiss(); //关闭dialog
                //Toast.makeText(BaseMapActivity.this, "确认" + which, Toast.LENGTH_SHORT).show();
                callBack.onClickOk(dialog, which);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() { //设置取消按钮
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //Toast.makeText(BaseMapActivity.this, "取消" + which, Toast.LENGTH_SHORT).show();
            }
        });
        //参数都设置完成了，创建并显示出来
        builder.create().show();
    }

    public static void getData(String url, RequestParams params, final CallBackString callBack, final Context context) {
        showProgressDialog(context);
        MyAppLocation.mHttpUtils.send(HttpRequest.HttpMethod.POST,
                url,
                params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        RetrueCodeHandler parser = RetruenUtils.getReturnInfo(responseInfo.result, new RetrueCodeHandler());
                        if (parser != null && parser.getString().equals("0")) {
                            callBack.httFinsh(responseInfo.result);
                        } else if (parser != null && parser.getString().equals("-1")) {
                            Toast.makeText(context, parser.getError(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "数据获取错误", Toast.LENGTH_SHORT).show();
                        }
                        closeProgressDialog();
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {

                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                        closeProgressDialog();
                    }
                });
    }

    public static void showProgressDialog(Context context) {

        if ((progressDialog == null)) {
            progressDialog = new ProgressDialog(context);
        }
        progressDialog.setTitle("正在请求网络");
        progressDialog.setMessage("请稍后");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

    }

    public static void closeProgressDialog() {
        if (progressDialog != null)
            progressDialog.dismiss();
    }


}
