package com.example.ok.shipments.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ok.shipments.MyAppLocation;
import com.example.ok.shipments.R;
import com.example.ok.shipments.fragment.FragmentSettingFragment;
import com.example.ok.shipments.urls.Urls;
import com.example.ok.shipments.utils.CallBackString;
import com.example.ok.shipments.utils.CommonUtil;
import com.example.ok.shipments.utils.LogU;
import com.lidroid.xutils.http.RequestParams;
//修改用户信息
public class UpDataInfoActivity extends BaseActivity implements View.OnClickListener {

    private EditText etChange;
    private ImageView ivBack;
    private TextView tvTitle;
    private Button btnSave;
    private String title;
    private String username;
    private String address;
    private String email;
    private String loginName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_data_info);
        title = getIntent().getStringExtra("title");
        username = getIntent().getStringExtra("username");
        address = getIntent().getStringExtra("address");
        email = getIntent().getStringExtra("email");
        SharedPreferences sp = getSharedPreferences("login", MODE_PRIVATE);
        loginName = sp.getString("loginName", "");
        findViews();
        if (title.equals("更改地址")){
            etChange.setText(address);
        }else if (title.equals("更改email")){
            etChange.setText(email);
        }else if (title.equals("更改用户名")){
            etChange.setText(username);
        }
        setListener();
    }

    private void setListener() {
        ivBack.setOnClickListener(this);
        btnSave.setOnClickListener(this);
    }

    private void findViews() {
        etChange = (EditText) findViewById(R.id.et_updata);
        ivBack = (ImageView) findViewById(R.id.title_back_btn);
        tvTitle = (TextView) findViewById(R.id.toolbar_title);
        tvTitle.setText(title);
        btnSave = (Button) findViewById(R.id.btn_right);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_right:
                if (title.equals("更改email") && !CommonUtil.isValidEmail(etChange.getText().toString().trim())) {
                    Toast.makeText(this, "请输入正确的email", Toast.LENGTH_SHORT).show();
                    return;
                }
                saveInfo();
                break;
            case R.id.title_back_btn:
                finish();
                break;
        }
    }

    private void upDataInfo() {
        String url = Urls.UpdateProfile;
        RequestParams params = new RequestParams();
        params.addBodyParameter("sessionId", MyAppLocation.login.getSessionId());
        params.addBodyParameter("userId", MyAppLocation.login.getUserId());
        params.addBodyParameter("clientName", MyAppLocation.login.getClientName());
        params.addBodyParameter("updateUserId", MyAppLocation.login.getUserId());
        params.addBodyParameter("loginName", loginName);
        params.addBodyParameter("postCode", "");
        params.addBodyParameter("webChat", "");
        params.addBodyParameter("remarks", "");
        if (title.equals("更改地址")){
            params.addBodyParameter("userName", username);
            params.addBodyParameter("address", etChange.getText().toString());
            params.addBodyParameter("email", email);
        }else if (title.equals("更改email")){
            params.addBodyParameter("userName", username);
            params.addBodyParameter("address", address);
            params.addBodyParameter("email", etChange.getText().toString());
        }else if (title.equals("更改用户名")){
            params.addBodyParameter("userName", etChange.getText().toString());
            params.addBodyParameter("address", address);
            params.addBodyParameter("email", email);
        }
        getData(url, params, new CallBackString() {
            @Override
            public void httFinsh(String data) {
                LogU.e(data);
                Tos("成功提交");
                sendBroadcast(new Intent(FragmentSettingFragment.ACTION));
                finish();
            }
        });
    }

    private void saveInfo() {
        upDataInfo();
    }
}
