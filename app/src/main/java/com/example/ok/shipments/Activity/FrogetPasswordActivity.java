package com.example.ok.shipments.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.ok.shipments.BaseSettings;
import com.example.ok.shipments.MyAppLocation;
import com.example.ok.shipments.R;
import com.example.ok.shipments.urls.Urls;
import com.example.ok.shipments.utils.CallBackString;
import com.example.ok.shipments.utils.RetrueCodeHandler;
import com.example.ok.shipments.utils.RetruenUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.Timer;
import java.util.TimerTask;

public class FrogetPasswordActivity extends BaseActivity {

    private EditText etMobile;
    private EditText etValifyCode;
    private EditText et_new_password;
    private EditText et_new_password2;
    private Button btnValifyCode;
    private Button btn_ForgetPassword;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_froget_password);
        initBackActionbar("忘记密码");
        findViewById();
    }

    public static boolean isValidMobiNumber(String paramString) {
        String regex = "^1\\d{10}$";
        if (paramString.matches(regex)) {
            return true;
        }
        return false;
    }

    private void findViewById() {
        etMobile = (EditText) findViewById(R.id.et_mobile);
        etValifyCode = (EditText) findViewById(R.id.etValifyCode);
        et_new_password = (EditText) findViewById(R.id.et_new_password);
        et_new_password2 = (EditText) findViewById(R.id.et_new_password2);
        btnValifyCode = (Button) findViewById(R.id.btnValifyCode);
        btn_ForgetPassword = (Button) findViewById(R.id.btn_ForgetPassword);
        btnValifyCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String trim = etMobile.getText().toString().trim();
                if (!isValidMobiNumber(trim)) {
                    Tos("请输入正确的手机号");
                    return;
                }
                btnValifyCode.setClickable(false);
                getValifyCode();
            }
        });
        btn_ForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String pass1 = et_new_password.getText().toString();
                String pass2 = et_new_password2.getText().toString();
                if (!pass1.equals(pass2)) {
                    Tos("两次的密码不一样");
                    return;
                }
                RequestParams params = new RequestParams();
                params.addBodyParameter("securityKey", BaseSettings.ResetPasswordsecurityKey);
                params.addBodyParameter("mobileNumber", etMobile.getText().toString().trim());
                params.addBodyParameter("valifyCode", etValifyCode.getText().toString().trim());
                params.addBodyParameter("newPassword", pass1);
                getData(Urls.ResetPassword, params, new CallBackString() {
                    @Override
                    public void httFinsh(String data) {

                        RetrueCodeHandler codeHandler = RetruenUtils.getReturnInfo(data, new RetrueCodeHandler());
                        if (codeHandler != null && codeHandler.getString().equals("0")) {
                            Tos("密码重置成功");
                            SharedPreferences sp = getSharedPreferences("login", Context.MODE_PRIVATE);
                            SharedPreferences.Editor edit = sp.edit();
                            edit.putString("loginName", etMobile.getText().toString().trim());
                            edit.putString("data", pass1);
                            edit.commit();
                            finish();
                        }
                    }
                });
            }
        });
    }

    private int currTime = 60;

    private void getValifyCode() {
        showProgressDialog();
        String url = Urls.ApplyAuthenticode;
        RequestParams params = new RequestParams();
        params.addBodyParameter("securityKey", BaseSettings.securityKey);
        params.addBodyParameter("mobileNumber", etMobile.getText().toString().trim());
        MyAppLocation.mHttpUtils.send(HttpRequest.HttpMethod.POST,
                url,
                params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        closeProgressDialog();
                        RetrueCodeHandler parser = RetruenUtils.getReturnInfo(responseInfo.result, new RetrueCodeHandler());
                        if (parser != null && parser.getString().equals("0")) {
                            if (timer != null) {
                                timer.cancel();
                                timer = null;
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

    public void back(View view) {
        finish();
    }

    @Override
    protected void onDestroy() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        super.onDestroy();
    }
}
