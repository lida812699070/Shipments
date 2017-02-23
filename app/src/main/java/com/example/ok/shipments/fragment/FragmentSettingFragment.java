package com.example.ok.shipments.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ok.shipments.Activity.ChangePswActivity;
import com.example.ok.shipments.Activity.UpDataInfoActivity;
import com.example.ok.shipments.BaseSettings;
import com.example.ok.shipments.MyAppLocation;
import com.example.ok.shipments.R;
import com.example.ok.shipments.model.GetProfile;
import com.example.ok.shipments.urls.Urls;
import com.example.ok.shipments.utils.BitmapUtils;
import com.example.ok.shipments.utils.CallBackString;
import com.example.ok.shipments.utils.DialoginOkCallBack;
import com.example.ok.shipments.utils.GetProfileXmlparser;
import com.example.ok.shipments.utils.LogU;
import com.example.ok.shipments.utils.PermissionUtil;
import com.example.ok.shipments.utils.ReadImgToBinary2;
import com.example.ok.shipments.utils.RetruenUtils;
import com.example.ok.shipments.view.CircleImageView;
import com.lidroid.xutils.http.RequestParams;

import java.io.File;
import java.io.FileNotFoundException;

import cn.jpush.android.api.JPushInterface;


public class FragmentSettingFragment extends BaseFragment implements View.OnClickListener {


    public static final String mFilePath = "sdcard/xsjHeadICon.jpg";
    private static final int SELECT_CAMER = 0;
    private static final int SELECT_PICTURE = 1;
    private TextView tvLoginName;
    private TextView tvVersion;
    private PackageInfo packageInfo;
    private CircleImageView ivheadIcon;
    private PopupWindow popupWindow;
    private Bitmap bmp;
    private String imgToBase64;
    private File myCaptureFile;
    private CircleImageView ivHeadIcon;
    private LinearLayout llUserName;
    private TextView tvUserName;
    private LinearLayout llMobile;
    private LinearLayout llVersion;
    private LinearLayout llAddress;
    private TextView tvAddress;
    private RelativeLayout llEMail;
    private TextView tvEMail;
    private String userName = "";
    private String address = "";
    private String email = "";
    private String loginName;
    public static final String ACTION = "com.example.ok.fragment.RefreshMsg";
    private RefreshBroadcastReceiver broadcastReceiver;
    private LinearLayout llChangePsw;
    private LinearLayout llLogout;
    private TextView tvUserName_up;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_setting, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViewById(view);
        //initToolbar(view,"设置");
        setListener();
        setData();
        getInfo();
        registRefreshBarcast();
    }

    private void registRefreshBarcast() {
        broadcastReceiver = new RefreshBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION);
        getActivity().registerReceiver(broadcastReceiver, filter);
    }

    private void getInfo() {
        final String url = Urls.GetProfile;
        RequestParams params = new RequestParams();
        params.addBodyParameter("sessionId", MyAppLocation.login.getSessionId());
        params.addBodyParameter("userId", MyAppLocation.login.getUserId());
        params.addBodyParameter("clientName", MyAppLocation.login.getClientName());
        getData(url, params, new CallBackString() {
            @Override
            public void httFinsh(String data) {
                LogU.e(data);
                GetProfileXmlparser xmlparser = RetruenUtils.getReturnInfo(data, new GetProfileXmlparser());
                if (xmlparser != null) {
                    GetProfile user = xmlparser.getUser();
                    LogU.e(user.toString());
                    tvEMail.setText(user.getEMail());
                    email = user.getEMail();
                    tvAddress.setText(user.getAddress());
                    address = user.getAddress();
                    tvUserName.setText(user.getUserName());
                    tvUserName_up.setText(user.getUserName());
                    userName = user.getUserName();
                }
            }
        });
    }

    private void setData() {
        SharedPreferences sp = getActivity().getSharedPreferences("login", getActivity().MODE_PRIVATE);
        loginName = sp.getString("loginName", "");
        tvLoginName.setText("手机号：" + loginName);
        String isDevelope = null;
        String strVersion = BaseSettings.WEBSERVICE_URL;
        if (strVersion.equals("http://develope.xsjky.cn/LogisticsServer.asmx")) {
            isDevelope = "测试版";
        } else {
            isDevelope = "正式版";
        }
        try {
            packageInfo = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        tvVersion.setText("当前版本：" + isDevelope + packageInfo.versionName);

    }

    private void setListener() {
        ivheadIcon.setOnClickListener(this);
        llAddress.setOnClickListener(this);
        llEMail.setOnClickListener(this);
        llUserName.setOnClickListener(this);
        llChangePsw.setOnClickListener(this);
        llLogout.setOnClickListener(this);
    }

    private void findViewById(View view) {
        tvLoginName = (TextView) view.findViewById(R.id.tvLoginName);
        tvVersion = (TextView) view.findViewById(R.id.tvVersion);
        ivheadIcon = (CircleImageView) view.findViewById(R.id.iv_headIcon);
        llUserName = (LinearLayout) view.findViewById(R.id.ll_userName);
        tvUserName = (TextView) view.findViewById(R.id.tvUserName);
        tvUserName_up = (TextView) view.findViewById(R.id.tvUserName_up);
        llMobile = (LinearLayout) view.findViewById(R.id.ll_mobile);
        llVersion = (LinearLayout) view.findViewById(R.id.ll_version);
        llAddress = (LinearLayout) view.findViewById(R.id.ll_Address);
        llChangePsw = (LinearLayout) view.findViewById(R.id.ll_fragmentSetting_changePsw);
        llLogout = (LinearLayout) view.findViewById(R.id.ll_fragmentSetting_logout);
        tvAddress = (TextView) view.findViewById(R.id.tvAddress);
        llEMail = (RelativeLayout) view.findViewById(R.id.ll_EMail);
        tvEMail = (TextView) view.findViewById(R.id.tvEMail);

    }

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_fragmentSetting_logout:
                showDialog(new DialoginOkCallBack() {
                    @Override
                    public void onClickOk(DialogInterface dialog, int which) {
                        logout();
                    }
                }, "是否确认退出？");
                break;
            case R.id.ll_fragmentSetting_changePsw:
                startActivity(new Intent(getActivity(), ChangePswActivity.class));
                break;
            case R.id.iv_headIcon:
                showPopWindow();
                break;
            case R.id.btn_select_sdcard:
                selectIconBySd();
                break;
            case R.id.btn_tack_pic:
                if (Build.VERSION.SDK_INT >= 23) {
                    showContacts();
                } else {
                    tackPic();
                }
                break;
            case R.id.ll_Address:
                Intent intent2 = new Intent(getActivity(), UpDataInfoActivity.class);
                intent2.putExtra("title", "更改地址");
                intent2.putExtra("username", tvUserName.getText().toString());
                intent2.putExtra("username", tvUserName_up.getText().toString());
                intent2.putExtra("address", tvAddress.getText().toString());
                intent2.putExtra("email", tvEMail.getText().toString());
                startActivity(intent2);
                break;
            case R.id.ll_EMail:
                Intent intent3 = new Intent(getActivity(), UpDataInfoActivity.class);
                intent3.putExtra("title", "更改email");
                intent3.putExtra("username", tvUserName.getText().toString());
                intent3.putExtra("username", tvUserName_up.getText().toString());
                intent3.putExtra("address", tvAddress.getText().toString());
                intent3.putExtra("email", tvEMail.getText().toString());
                startActivity(intent3);
                break;
            case R.id.ll_userName:
                Intent intent4 = new Intent(getActivity(), UpDataInfoActivity.class);
                intent4.putExtra("title", "更改用户名");
                intent4.putExtra("username", tvUserName.getText().toString());
                intent4.putExtra("username", tvUserName_up.getText().toString());
                intent4.putExtra("address", tvAddress.getText().toString());
                intent4.putExtra("email", tvEMail.getText().toString());
                startActivity(intent4);
                break;
        }
    }

    public void showContacts() {
        // Verify that all required contact permissions have been granted.
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            requestContactsPermissions();
        } else {
            tackPic();
        }
    }

    private static final String[] PERMISSIONS_CONTACT = {
            Manifest.permission.CAMERA,
    };

    private static final int REQUEST_CONTACTS = 1;

    private void requestContactsPermissions() {
        // BEGIN_INCLUDE(contacts_permission_request)
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                Manifest.permission.CAMERA)) {
            showDialog(new DialoginOkCallBack() {
                @Override
                public void onClickOk(DialogInterface dialog, int which) {
                    requestPermissions(PERMISSIONS_CONTACT, REQUEST_CONTACTS);
                }
            }, "是否再次申请摄像头权限？");
        } else {
            // Contact permissions have not been granted yet. Request them directly.
            requestPermissions(PERMISSIONS_CONTACT, REQUEST_CONTACTS);
        }
        // END_INCLUDE(contacts_permission_request)
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == REQUEST_CONTACTS) {
            if (PermissionUtil.verifyPermissions(grantResults)) {
                tackPic();
            } else {
                showDialog(new DialoginOkCallBack() {
                    @Override
                    public void onClickOk(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(getActivity(), PERMISSIONS_CONTACT, REQUEST_CONTACTS);
                    }
                }, "拒绝权限可能导致应用无法正常使用，是否再次申请权限？");
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void tackPic() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            myCaptureFile = new File(mFilePath);
            Uri uri = Uri.fromFile(myCaptureFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            startActivityForResult(intent, SELECT_CAMER);
        } else {
            Toast.makeText(getActivity(), "请确认已经插入SD卡", Toast.LENGTH_LONG).show();
        }
    }

    private void selectIconBySd() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            startActivityForResult(Intent.createChooser(intent, "选择图片"), SELECT_PICTURE);
        } else {
            Toast.makeText(getActivity(), "请确认已经插入SD卡", Toast.LENGTH_LONG).show();
        }
    }

    private void showPopWindow() {
        if (popupWindow == null) {
            initPopWindow();
        }
//popupWindow.showAsDropDown(btShow);

        popupWindow.showAtLocation(llLogout, Gravity.BOTTOM, 0, 0);
    }

    private void initPopWindow() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_popwindow_select_pic, null);
//新建popwindow，设置宽高都未WRAP_CONTENT,并获取焦点
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

//设置背景色,如果要点击外部消失，必须设置背景色
        ColorDrawable cd = new ColorDrawable(Color.BLACK);
        popupWindow.setBackgroundDrawable(cd);

        //设置背景图片
        //popupWindow.setBackgroundDrawable(getResources().getDrawable(R.mipmap.ic_launcher));

        //点击测试按钮退出popwindow，点击popwindow外的区域或者点击返回键也可以退出
        Button btTest = (Button) view.findViewById(R.id.btnClose);
        Button btnSelectSdcard = (Button) view.findViewById(R.id.btn_select_sdcard);
        Button btSelectTackPic = (Button) view.findViewById(R.id.btn_tack_pic);
        btnSelectSdcard.setOnClickListener(this);
        btSelectTackPic.setOnClickListener(this);
        btTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                popupWindow = null;
            }
        });
    }

    private void logout() {
        JPushInterface.stopPush(getActivity());
        SharedPreferences sp = getActivity().getSharedPreferences("login", getActivity().MODE_PRIVATE);

        SharedPreferences.Editor editor = sp.edit();

        editor.remove("data");

        editor.remove("loginName");

        editor.commit();
        getActivity().finish();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                //选择图片
                Uri uri = data.getData();
                ContentResolver cr = getActivity().getContentResolver();
                try {
                    if (bmp != null)//如果不释放的话，不断取图片，将会内存不够
                        bmp.recycle();
                    bmp = BitmapFactory.decodeStream(cr.openInputStream(uri));
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                System.out.println("the bmp toString: " + bmp);
                ivheadIcon.setImageBitmap(bmp);

            } else if (requestCode == SELECT_CAMER) {
                //拍照
                Bitmap bitmap = BitmapUtils.getBitmap(mFilePath, 600, 800);
                BitmapUtils.saveFile(bitmap, mFilePath);
                imgToBase64 = ReadImgToBinary2.imgToBase64(mFilePath, bitmap, "");
                ivheadIcon.setImageBitmap(bitmap);
            }

        }

    }

    @Override
    public void flash() {

    }

    class RefreshBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            getInfo();
        }
    }

    @Override
    public void onDestroy() {
        if (broadcastReceiver != null) {
            getActivity().unregisterReceiver(broadcastReceiver);
        }
        super.onDestroy();
    }
}
