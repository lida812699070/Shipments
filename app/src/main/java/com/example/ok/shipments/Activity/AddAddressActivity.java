package com.example.ok.shipments.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.baidu.mapapi.model.LatLng;
import com.example.ok.shipments.MyAppLocation;
import com.example.ok.shipments.R;
import com.example.ok.shipments.model.AddressBook;
import com.example.ok.shipments.urls.Urls;
import com.example.ok.shipments.utils.CallBackString;
import com.example.ok.shipments.utils.DialoginOkCallBack;
import com.example.ok.shipments.utils.RetruenUtils;
import com.example.ok.shipments.utils.StringXmlPaser;
import com.lidroid.xutils.http.RequestParams;

public class AddAddressActivity extends BaseActivity implements View.OnClickListener {

    private EditText etName;
    private EditText etPhone;
    private EditText etAddress;
    private Button btnlocation;
    //private EditText etAddressDetail;
    private Button btnSaveAddress;
    private LatLng latLng;
    private EditText etCom;
    private Button btnDelete;
    private AddressBook addressBook;
    private EditText et_Hausnummer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        initBackActionbar("选择地址");
        addressBook = (AddressBook) getIntent().getSerializableExtra("addressBook");
        findViewById();
        if (addressBook != null) {
            setdata();
        }
    }

    private void setdata() {
        String[] split = addressBook.getAddress().split("\\s+", 4);
        //etAddressDetail.setText(split[3]);
        etAddress.setText(split[0] + " " + split[1] + " " + split[2] + " " + split[3]);
        etCom.setText(addressBook.getCompanyName());
        etName.setText(addressBook.getContactName());
        etPhone.setText(addressBook.getMobileNumber());
    }

    private void findViewById() {
        etName = (EditText) findViewById(R.id.et_Name);
        etCom = (EditText) findViewById(R.id.et_Com);
        etPhone = (EditText) findViewById(R.id.et_Phone);
        etAddress = (EditText) findViewById(R.id.et_Address);
        et_Hausnummer = (EditText) findViewById(R.id.et_Hausnummer);
        //etAddressDetail = (EditText) findViewById(R.id.et_AddressDetail);
        btnlocation = (Button) findViewById(R.id.btn_location);
        btnSaveAddress = (Button) findViewById(R.id.btnSaveAddress);
        btnlocation.setOnClickListener(this);
        btnSaveAddress.setOnClickListener(this);
        etAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tos("通过定位选点");
            }
        });
        etAddress.setFocusable(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_location:
                startActivityForResult(new Intent(this, GetLocationActivity.class), 0);
                break;
            case R.id.btnSaveAddress:
                saveAddress();
                break;
            case R.id.head_flip:
                finish();
                break;
        }
    }

    private void saveAddress() {
        if (TextUtils.isEmpty(etAddress.getText().toString())
                || TextUtils.isEmpty(etName.getText().toString())
                || TextUtils.isEmpty(etPhone.getText().toString())
                || latLng == null && addressBook == null) {
            Tos("请完善您的个人信息");
            return;
        }
        RequestParams params = new RequestParams();
        params.addBodyParameter("sessionId", MyAppLocation.login.getSessionId());
        params.addBodyParameter("userId", MyAppLocation.login.getUserId() + "");
        params.addBodyParameter("clientName", MyAppLocation.login.getClientName());
        params.addBodyParameter("companyName", etCom.getText().toString());
        params.addBodyParameter("contactName", etName.getText().toString());
        params.addBodyParameter("mobileNumber", etPhone.getText().toString());
        String hausnummer = et_Hausnummer.getText().toString();
        hausnummer = hausnummer.replace(" ", "");
        params.addBodyParameter("address", etAddress.getText().toString() + " " + " " + hausnummer);
        params.addBodyParameter("tel", "");
        params.addBodyParameter("fax", "");
        params.addBodyParameter("email", "");
        params.addBodyParameter("remarks", "");
        params.addBodyParameter("returnValue", true + "");
        String url = Urls.New;
        if (addressBook != null) {
            params.addBodyParameter("updateRecordId", addressBook.getRecordId());
            params.addBodyParameter("longitude", addressBook.getCoordinate().getLongitude() + "");
            params.addBodyParameter("latitude", addressBook.getCoordinate().getLatitude() + "");
            url = Urls.Update;
        } else {
            params.addBodyParameter("longitude", latLng.longitude + "");
            params.addBodyParameter("latitude", latLng.latitude + "");
        }
        getData(url, params, new CallBackString() {
            @Override
            public void httFinsh(String data) {
                Tos("操作成功");
                StringXmlPaser stringXmlPaser = new StringXmlPaser();
                stringXmlPaser.setName("RecordId");
                StringXmlPaser xmlPaser = RetruenUtils.getReturnInfo(data, stringXmlPaser);
                assert xmlPaser != null;
                recordId = xmlPaser.getString();
                sendBroadcast(new Intent(SelectAddressActivity.ACTION));
                saveLastUser();
            }
        });
    }

    private String recordId = "";

    private void saveLastUser() {
        showDialog(new DialoginOkCallBack() {
            @Override
            public void onClickOk(DialogInterface dialog, int which) {
                RequestParams params = new RequestParams();
                params.addBodyParameter("sessionId", MyAppLocation.login.getSessionId());
                params.addBodyParameter("userId", MyAppLocation.login.getUserId());
                params.addBodyParameter("clientName", MyAppLocation.login.getClientName());
                if (TextUtils.isEmpty(recordId)) {
                    Tos("还没有保存的地址");
                    return;
                }
                params.addBodyParameter("defaultRecordId", recordId);
                getData(Urls.SetDefault, params, new CallBackString() {
                    @Override
                    public void httFinsh(String data) {
                        Tos("保存成功");
                        finish();
                    }
                });

            }
        }, "是否这设置当前发货地址为默认的发货地址？", new DialoginOkCallBack() {
            @Override
            public void onClickOk(DialogInterface dialog, int which) {
                finish();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (resultCode == RESULT_OK) {
            Bundle data = intent.getBundleExtra("data");
            etAddress.setText(data.getString("Province") + " " +
                    data.getString("City") + " " +
                    data.getString("District") + " " +
                    data.getString("Street"));
            latLng = new LatLng(Double.valueOf(data.getString("latLng")), Double.valueOf(data.getString("longitude")));
        }
        super.onActivityResult(requestCode, resultCode, intent);
    }

}
