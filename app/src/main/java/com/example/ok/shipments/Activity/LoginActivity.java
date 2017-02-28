package com.example.ok.shipments.Activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ok.shipments.BaseSettings;
import com.example.ok.shipments.MyAppLocation;
import com.example.ok.shipments.R;
import com.example.ok.shipments.model.FindInProgressRequest;
import com.example.ok.shipments.model.Login;
import com.example.ok.shipments.model.UpDataInfo;
import com.example.ok.shipments.urls.Urls;
import com.example.ok.shipments.utils.CallBackString;
import com.example.ok.shipments.utils.DialoginOkCallBack;
import com.example.ok.shipments.utils.LogU;
import com.example.ok.shipments.utils.LoginXmlparser;
import com.example.ok.shipments.utils.PermissionUtil;
import com.example.ok.shipments.utils.ProgressRequestXmlParser;
import com.example.ok.shipments.utils.RetrueCodeHandler;
import com.example.ok.shipments.utils.RetruenUtils;
import com.example.ok.shipments.utils.VersionUpDataXmlPaser;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.mining.app.zxing.EncodingHandler;
import com.umeng.analytics.MobclickAgent;

import java.io.ByteArrayInputStream;
import java.util.HashSet;
import java.util.Timer;
import java.util.TimerTask;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import cn.jpush.android.api.JPushInterface;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private PackageInfo packageInfo;
    private Button btnLogin;
    private EditText etPassword;
    private EditText etUsername;
    private TextView tvVersion;
    private String username;
    private String password;
    private ImageView ivBg;
    private FindInProgressRequest progressRequest;
    private Button btnValifyCode;
    private Button btnForgetPassword;
    private String versionName;
    private UpDataInfo upDataInfo;
    private ImageView ivLogo;
    private static final String[] PERMISSIONS_CONTACT = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };

    private static final int REQUEST_CONTACTS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (Build.VERSION.SDK_INT >= 23) {
            showContacts();
        } else {
            init();
        }
    }

    public void showContacts() {
        Log.e(TAG, "Show contacts button pressed. Checking permissions.");

        // Verify that all required contact permissions have been granted.
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Contacts permissions have not been granted.
            Log.e(TAG, "Contact permissions has NOT been granted. Requesting permissions.");
            requestContactsPermissions();

        } else {

            // Contact permissions have been granted. Show the contacts fragment.
            Log.e(TAG,
                    "Contact permissions have already been granted. Displaying contact details.");
            init();
        }
    }

    private void requestContactsPermissions() {
        // BEGIN_INCLUDE(contacts_permission_request)
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            showDialog(new DialoginOkCallBack() {
                @Override
                public void onClickOk(DialogInterface dialog, int which) {
                    ActivityCompat.requestPermissions(LoginActivity.this, PERMISSIONS_CONTACT, REQUEST_CONTACTS);
                }
            },"是否再次申请该权限？",null);
        } else {
            // Contact permissions have not been granted yet. Request them directly.

            ActivityCompat.requestPermissions(this, PERMISSIONS_CONTACT, REQUEST_CONTACTS);
        }
        // END_INCLUDE(contacts_permission_request)
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == REQUEST_CONTACTS) {
            if (PermissionUtil.verifyPermissions(grantResults)) {

                init();

            } else {
                showDialog(new DialoginOkCallBack() {
                    @Override
                    public void onClickOk(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(LoginActivity.this, PERMISSIONS_CONTACT, REQUEST_CONTACTS);
                    }
                }, "拒绝权限可能导致应用无法正常使用，是否再次申请权限？", null);

            }


        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void init() {
        try {
            packageInfo = getPackageManager().getPackageInfo(
                    getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        findViewById();
        setData();
        IsFirstLogin();
    }

    private void IsFirstLogin() {
        SharedPreferences sp = getSharedPreferences("login", Context.MODE_PRIVATE);
        String loginName = sp.getString("loginName", "");
        String password = sp.getString("data", "");
        if (!TextUtils.isEmpty(loginName) && !TextUtils.isEmpty(password)) {
            etUsername.setText(loginName);
            etPassword.setText(password);
            login2();
        } else {
            ivBg.setVisibility(View.GONE);
        }
    }

    public static boolean isValidMobiNumber(String paramString) {
        String regex = "^1\\d{10}$";
        if (paramString.matches(regex)) {
            return true;
        }
        return false;
    }

    private void findViewById() {
        btnLogin = (Button) findViewById(R.id.login_btn);
        btnForgetPassword = (Button) findViewById(R.id.btnForgetPassword);
        btnValifyCode = (Button) findViewById(R.id.btnValifyCode);
        etPassword = (EditText) findViewById(R.id.login_password);
        etUsername = (EditText) findViewById(R.id.login_username);
        tvVersion = (TextView) findViewById(R.id.tv_login_versions);
        ivBg = (ImageView) findViewById(R.id.iv_welcome);
        ivLogo = (ImageView) findViewById(R.id.iv_logo);
        ivBg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnLogin.setOnClickListener(this);
        btnValifyCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getValifyCode();
                //注册
                registerUser();
            }
        });
        btnForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, FrogetPasswordActivity.class));
            }
        });
    }

    private void registerUser() {
        startActivity(new Intent(this, RegisterUserActivity.class));
    }

    private void setData() {
        String isDevelope = null;
        String strVersion = BaseSettings.WEBSERVICE_URL;
        if (strVersion.equals("http://develope.xsjky.cn/LogisticsServer.asmx")) {
            isDevelope = "测试版";
        } else {
            isDevelope = "正式版";
        }
        tvVersion.setText(isDevelope + packageInfo.versionName);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn:
                login2();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void login2() {
        username = etUsername.getText().toString();
        password = etPassword.getText().toString();
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String device_id = tm.getDeviceId();
        //TODO 登录接口
        String url = Urls.AppLogin;
        RequestParams params = new RequestParams();
        params.addBodyParameter("loginName", username);
        params.addBodyParameter("password", password);
        params.addBodyParameter("clientName", BaseSettings.CLIENT_NAME);
        params.addBodyParameter("clientVersion", packageInfo.versionName + ".0");
        params.addBodyParameter("deviceId", device_id);
        showProgressDialog();
        MyAppLocation.mHttpUtils.send(HttpRequest.HttpMethod.POST,
                url,
                params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        RetrueCodeHandler parser = RetruenUtils.getReturnInfo(responseInfo.result, new RetrueCodeHandler());
                        if (parser != null && parser.getString().equals("0")) {
                            LoginXmlparser loginXmlparser = RetruenUtils.getReturnInfo(responseInfo.result, new LoginXmlparser());
                            if (loginXmlparser != null) {
                                Login login = loginXmlparser.getLogin();
                                MyAppLocation.login = login;
                                SharedPreferences sp = getSharedPreferences("login", Context.MODE_PRIVATE);
                                SharedPreferences.Editor edit = sp.edit();
                                edit.putString("loginName", username);
                                edit.putString("data", password);
                                edit.commit();
                                //loginSuccess();
                                getCurrVersion();
                            }
                        } else if (parser != null && parser.getString().equals("-1")) {
                            Tos(parser.getError());
                            ivBg.setVisibility(View.GONE);
                            etPassword.setText("");
                            closeProgressDialog();
                        }
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        Toast.makeText(LoginActivity.this, "数据请求错误", Toast.LENGTH_SHORT).show();
                        ivBg.setVisibility(View.GONE);
                        etPassword.setText("");
                        closeProgressDialog();
                    }
                });
    }

    private void getCurrVersion() {
        showProgressDialog();
        final Login loginInfo = MyAppLocation.login;
        RequestParams params = new RequestParams();
        params.addBodyParameter("sessionId", loginInfo.getSessionId());
        params.addBodyParameter("userId", loginInfo.getUserId() + "");
        params.addBodyParameter("clientName", BaseSettings.CLIENT_NAME);
        params.addBodyParameter("appName", BaseSettings.APP_NAME);
        String url = Urls.GetLatestVersion;
        MyAppLocation.mHttpUtils.send(HttpRequest.HttpMethod.POST,
                url,
                params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        //LogU.e(responseInfo.result);
                        SAXParserFactory factory = SAXParserFactory.newInstance();
                        SAXParser parser = null;
                        PackageInfo packageInfo = null;
                        try {
                            parser = factory.newSAXParser();
                            RetrueCodeHandler handler = new RetrueCodeHandler();
                            ByteArrayInputStream tInputStringStream = new ByteArrayInputStream(responseInfo.result.getBytes());
                            parser.parse(tInputStringStream, handler);
                            if (handler.getString().equals("0")) {
                                SAXParserFactory factory1 = SAXParserFactory.newInstance();
                                SAXParser parser1 = factory1.newSAXParser();
                                VersionUpDataXmlPaser handler1 = new VersionUpDataXmlPaser();
                                ByteArrayInputStream tInputStringStream1 = new ByteArrayInputStream(responseInfo.result.getBytes());
                                parser1.parse(tInputStringStream1, handler1);
                                upDataInfo = handler1.getUpDataInfo();
                            }
                            packageInfo = getPackageManager().getPackageInfo(
                                    getPackageName(), 0);
                            if (packageInfo != null && upDataInfo != null) {
                                versionName = packageInfo.versionName;
                                //Toast.makeText(LoginActivity.this,versionName,Toast.LENGTH_SHORT).show();
                                String appVersion = upDataInfo.getAppVersion();
                                Double myAppVersion = Double.valueOf(versionName.replace(".", "") + 00);
                                Double currAppVersion = Double.valueOf(appVersion.replace(".", ""));
                                LogU.e(versionName + "" + currAppVersion);
                                if (myAppVersion < currAppVersion) {
                                    showUpdataVersion();
                                } else {
                                    loginSuccess();
                                }

                            } else {
                                loginSuccess();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            closeProgressDialog();
                        }

                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        Toast.makeText(LoginActivity.this, "数据获取失败", Toast.LENGTH_SHORT).show();
                        closeProgressDialog();
                    }
                });
    }


    @Override
    protected void onResume() {
        JPushInterface.onResume(this);
        super.onResume();
    }

    @Override
    protected void onPause() {
        JPushInterface.onPause(this);
        super.onPause();
    }

    private void showUpdataVersion() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);  //先得到构造器
        builder.setTitle("检测到版本更新"); //设置标题
        builder.setMessage("是否更新?"); //设置内容
        builder.setIcon(R.drawable.ic_launcher);//设置图标，图片id即可

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() { //设置确定按钮
            @Override
            public void onClick(final DialogInterface dialog, int which) {
                Uri uri = Uri.parse("http://android.myapp.com/myapp/detail.htm?apkName=com.example.ok.shipments");
                Intent it = new Intent(Intent.ACTION_VIEW, uri);
                it.setData(uri);
                it.setAction(Intent.ACTION_VIEW);
                it.setClassName("com.android.browser", "com.android.browser.BrowserActivity");
                LoginActivity.this.startActivity(it);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() { //设置取消按钮
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                loginSuccess();

            }
        });
        //参数都设置完成了，创建并显示出来
        builder.create().show();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitBy2Click(); //调用双击退出函数
        }
        return false;
    }

    private static Boolean isExit = false;

    private void exitBy2Click() {
        Timer tExit = null;
        if (isExit == false) {
            isExit = true; // 准备退出
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

        } else {
            finish();
            System.exit(0);
        }
    }

    private void loginSuccess() {
        MobclickAgent.onProfileSignIn(MyAppLocation.login.getUserId());
        showProgressDialog();
        String url = Urls.FindInProgressRequest;
        RequestParams params = new RequestParams();
        params.addBodyParameter("sessionId", MyAppLocation.login.getSessionId());
        params.addBodyParameter("userId", MyAppLocation.login.getUserId());
        params.addBodyParameter("clientName", MyAppLocation.login.getClientName());

        MyAppLocation.mHttpUtils.send(HttpRequest.HttpMethod.POST,
                url,
                params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        LogU.e(responseInfo.result);
                        RetrueCodeHandler retrueCode = RetruenUtils.getReturnInfo(responseInfo.result, new RetrueCodeHandler());
                        if (retrueCode != null && retrueCode.getString().equals("0")) {
                            ProgressRequestXmlParser parser = RetruenUtils.getReturnInfo(responseInfo.result, new ProgressRequestXmlParser());
                            if (parser != null) {
                                progressRequest = parser.getProgressRequest();
                                MyAppLocation.progressRequest = progressRequest;
                            } else {
                                Tos("解析错误");
                            }
                        } else if (retrueCode != null && retrueCode.getString().equals("-1")) {
                            Tos(retrueCode.getError());
                        } else {
                            Tos("请求数据错误");
                        }
                        intoMainActivity();
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        intoMainActivity();
                        Tos(msg);
                    }
                });
    }

    private void intoMainActivity() {
        closeProgressDialog();
        HashSet<String> strings = new HashSet<>();
        strings.add(username);
        JPushInterface.setAliasAndTags(getApplicationContext(), username, strings);
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }
}
