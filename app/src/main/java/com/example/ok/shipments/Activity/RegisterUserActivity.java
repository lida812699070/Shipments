package com.example.ok.shipments.Activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ok.shipments.BaseSettings;
import com.example.ok.shipments.MyAppLocation;
import com.example.ok.shipments.R;
import com.example.ok.shipments.urls.Urls;
import com.example.ok.shipments.utils.CallBackString;
import com.example.ok.shipments.utils.CommonUtil;
import com.example.ok.shipments.utils.RetrueCodeHandler;
import com.example.ok.shipments.utils.RetruenUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.Timer;
import java.util.TimerTask;

public class RegisterUserActivity extends BaseActivity implements View.OnClickListener {

    private EditText et_mobile;
    private EditText etValifyCode;
    private EditText et_new_password;
    private Button btnValifyCode;
    private Button btn_RegisterUser;
    private Timer timer;
    private EditText et_new_password2;
    private TextView tvBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        initview();
    }

    private void initview() {
        et_mobile = (EditText) findViewById(R.id.et_mobile);
        etValifyCode = (EditText) findViewById(R.id.etValifyCode);
        et_new_password = (EditText) findViewById(R.id.et_new_password);
        et_new_password2 = (EditText) findViewById(R.id.et_new_password2);
        btnValifyCode = (Button) findViewById(R.id.btnValifyCode);
        tvBack = (TextView) findViewById(R.id.tv_login_versions);
        btn_RegisterUser = (Button) findViewById(R.id.btn_RegisterUser);
        btn_RegisterUser.setOnClickListener(this);
        tvBack.setOnClickListener(this);
        btnValifyCode.setOnClickListener(this);
    }

    private boolean IsLogin = true;
    private int currTime = 60;
    private void getValifyCode() {
        showProgressDialog();
        String url = Urls.ApplyAuthenticode;
        RequestParams params = new RequestParams();
        params.addBodyParameter("securityKey", BaseSettings.securityKey);
        params.addBodyParameter("mobileNumber", et_mobile.getText().toString().trim());
        MyAppLocation.mHttpUtils.send(HttpRequest.HttpMethod.POST,
                url,
                params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        closeProgressDialog();
                        RetrueCodeHandler parser = RetruenUtils.getReturnInfo(responseInfo.result, new RetrueCodeHandler());
                        if (parser != null && parser.getString().equals("0")) {
                            IsLogin = false;
                            if (timer!=null){
                                timer.cancel();
                                timer=null;
                            }
                            timer = new Timer();
                            timer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            btnValifyCode.setText("重新获取验证码(" + currTime + ")s");
                                            currTime--;
                                            if (currTime <= 0) {
                                                btnValifyCode.setClickable(true);
                                                btnValifyCode.setText("重新获取验证码");
                                                currTime = 60;
                                                timer.cancel();
                                            }
                                        }
                                    });
                                }
                            }, 0, 1000);
                        }

                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        btnValifyCode.setClickable(true);
                        btnValifyCode.setText("重新获取验证码");
                        currTime = 60;
                        Tos(msg);
                        closeProgressDialog();
                    }
                });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btnValifyCode:
                if (!CommonUtil.isValidMobiNumber(et_mobile.getText().toString())){
                    Tos("请输入正确的手机号");
                    return;
                }
                getValifyCode();
                break;
            case R.id.btn_RegisterUser:
                registerUser();
                break;
            case R.id.tv_login_versions:
                finish();
                break;

        }
    }

    @Override
    protected void onDestroy() {
        if (timer!=null){
            timer.cancel();
            timer=null;
        }
        super.onDestroy();
    }

    private void registerUser() {
        String psw1 = et_new_password.getText().toString();
        String psw2 = et_new_password2.getText().toString();
        if (TextUtils.isEmpty(psw1) || TextUtils.isEmpty(psw2) ){
            Tos("密码不能为空");
            return;
        }
        if (!psw1.equals(psw2)){
            Tos("两次输入的密码不一样！");
        }
        RequestParams params = new RequestParams();
        params.addBodyParameter("clientName",BaseSettings.CLIENT_NAME);
        params.addBodyParameter("securityKey",BaseSettings.RegisterUsersecurityKey);
        params.addBodyParameter("mobileNumber",et_mobile.getText().toString().trim());
        params.addBodyParameter("verifyCode",etValifyCode.getText().toString());
        params.addBodyParameter("password",et_new_password.getText().toString());
        getData(Urls.RegisterUser, params, new CallBackString() {
            @Override
            public void httFinsh(String data) {
                Tos("注册成功");
                finish();
            }
        });
    }
}
