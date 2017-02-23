package com.example.ok.shipments.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.example.ok.shipments.MyAppLocation;
import com.example.ok.shipments.R;
import com.example.ok.shipments.utils.CallBackString;
import com.example.ok.shipments.utils.DialoginOkCallBack;
import com.example.ok.shipments.utils.RetrueCodeHandler;
import com.example.ok.shipments.utils.RetruenUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

/**
 * Created by OK on 2016/4/22.
 */
public abstract class BaseFragment extends Fragment {
    private ProgressDialog progressDialog;
    private Context context;
    private boolean Isshowing = false;

    public void Tos(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    public void showProgressDialog() {
        if (Isshowing) {
            return;
        }
        progressDialog = new ProgressDialog(getActivity());
        this.progressDialog.setTitle("正在请求网络");
        this.progressDialog.setMessage("请稍后");
        this.progressDialog.setCanceledOnTouchOutside(false);
        this.progressDialog.show();
        Isshowing = true;
    }

    protected void initToolbar(View view, String title) {
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.activity_main_toolbar);
        if (toolbar == null) {
            return;
        }
        toolbar.setTitle(title);
        toolbar.setTitleTextColor(Color.WHITE);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
    }

    protected Toolbar getToolBar(View view) {
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.activity_main_toolbar);
        return toolbar;
    }

    public void getData(String url, RequestParams params, final CallBackString callBack) {
        context = getActivity();
        showProgressDialog();
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

    public void getData(String url, RequestParams params, final CallBackString callBack,boolean isshow) {
        context = getActivity();
        if (isshow){
            showProgressDialog();
        }
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
    public void closeProgressDialog() {
        if (this.progressDialog != null) {
            this.progressDialog.dismiss();
        }
        Isshowing = false;
    }

    @Override
    public void onDestroy() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
        super.onDestroy();
    }

    public void Toast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public abstract void flash();

    protected void showDialog(final DialoginOkCallBack callBack, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());  //先得到构造器
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
}
