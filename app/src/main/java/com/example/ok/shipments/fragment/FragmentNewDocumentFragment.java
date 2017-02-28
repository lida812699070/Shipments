package com.example.ok.shipments.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ok.shipments.Activity.SelectAddressActivity;
import com.example.ok.shipments.MyAppLocation;
import com.example.ok.shipments.R;
import com.example.ok.shipments.model.AddressBook;
import com.example.ok.shipments.model.Login;
import com.example.ok.shipments.model.ShippingMode;
import com.example.ok.shipments.soap.SoapAction;
import com.example.ok.shipments.soap.SoapEndpoint;
import com.example.ok.shipments.soap.XmlStr;
import com.example.ok.shipments.urls.Urls;
import com.example.ok.shipments.utils.AddressBookXml;
import com.example.ok.shipments.utils.CallBackString;
import com.example.ok.shipments.utils.LogU;
import com.example.ok.shipments.utils.MySoapHttpRequest;
import com.example.ok.shipments.utils.RetruenUtils;
import com.example.ok.shipments.utils.ShipModeXmlParser;
import com.lidroid.xutils.http.RequestParams;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//新增运单接口 等待接口
public class FragmentNewDocumentFragment extends BaseFragment implements View.OnClickListener {

    public static final int SEND = 0x110;
    public static final int GET = 0x111;
    private Button btnSave;
    private Spinner spMode;
    private EditText etProDuctName;
    private EditText etProDuctQty;
    private EditText etProDuctWeight;
    private EditText etProDuctVol;
    private CheckBox ckNeedDlivery;
    private CheckBox ckNeedGoFloor;
    private CheckBox ckgoodsControl;
    private ArrayAdapter<String> mShippingModeAdapter;
    private Button btnSelectSend;
    private Button btnSelectGet;
    private LinearLayout llSend;
    private LinearLayout llGet;
    private TextView tvName;
    private TextView tvComName;
    private TextView tvCallNum;
    private TextView tvAddress;
    private Button btnDelete;
    private EditText etPrice;
    private ArrayList<String> arrModes;
    private List<ShippingMode> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_new_document, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViewById(view);
        initData();
    }

    private void initData() {
        getshippingModes();
        if (MyAppLocation.shipBook == null){
            RequestParams params = new RequestParams();
            params.addBodyParameter("sessionId",MyAppLocation.login.getSessionId());
            params.addBodyParameter("userId",MyAppLocation.login.getUserId());
            params.addBodyParameter("clientName",MyAppLocation.login.getClientName());
            getData(Urls.GetDefault, params, new CallBackString() {
                @Override
                public void httFinsh(String data) {
                    AddressBookXml parser = RetruenUtils.getReturnInfo(data, new AddressBookXml());
                    if (parser != null) {
                        AddressBook user = parser.getUser();
                        MyAppLocation.shipBook = user;
                        llSend.removeAllViews();
                        addView(llSend, user);
                    }
                }
            });
        }
    }

    private void getshippingModes() {
        final String endPoint = SoapEndpoint.SET_IS_SIGNUP;
        final String soapAction = SoapAction.GetShippingModes;
        final Login loginInfo = MyAppLocation.login;
        String info = XmlStr.GETSHIPPINGMODES_TEMPLET;
        info = info.replace("UserIdValue", loginInfo.getUserId() + "");
        info = info.replace("ClientNameValue", loginInfo.getClientName());
        info = info.replace("SessionIdValue", loginInfo.getSessionId());
        info = info.replace("RoleDataValue", loginInfo.getRoleData() + "");
        info = info.replace("getDisablesValue", true + "");

        final String finalInfo = info;
        MySoapHttpRequest.getString(new CallBackString() {
            @Override
            public void httFinsh(final String data) {
                ShipModeXmlParser xmlParser = RetruenUtils.getReturnInfo(data, new ShipModeXmlParser());
                if (xmlParser != null) {
                    list = xmlParser.getList();
                    arrModes = new ArrayList<>();
                    for (int i = 0; i < list.size(); i++) {
                        arrModes.add(list.get(i).getModeName());
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mShippingModeAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arrModes);
                            mShippingModeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spMode.setAdapter(mShippingModeAdapter);
                        }
                    });
                }
            }
        }, endPoint, soapAction, finalInfo);
    }

    private void findViewById(View view) {
        btnSave = (Button) view.findViewById(R.id.new_save_btn);
        btnSelectSend = (Button) view.findViewById(R.id.btn_selectSendPeople);
        btnSelectGet = (Button) view.findViewById(R.id.btn_selectGetPeople);
        spMode = (Spinner) view.findViewById(R.id.new_shipping_mode);
        etProDuctName = (EditText) view.findViewById(R.id.new_product_name);
        etProDuctQty = (EditText) view.findViewById(R.id.new_product_quantity);
        etProDuctWeight = (EditText) view.findViewById(R.id.new_product_weight);
        etProDuctVol = (EditText) view.findViewById(R.id.new_product_volumn);
        etPrice = (EditText) view.findViewById(R.id.new_insuranceAmt);
        ckNeedDlivery = (CheckBox) view.findViewById(R.id.new_needDelivery);
        ckNeedGoFloor = (CheckBox) view.findViewById(R.id.new_needGOFloor);
        ckgoodsControl = (CheckBox) view.findViewById(R.id.new_goodsControl);
        llSend = (LinearLayout) view.findViewById(R.id.ll_sendPeople);
        llGet = (LinearLayout) view.findViewById(R.id.ll_getPeople);
        btnSave.setOnClickListener(this);
        btnSelectSend.setOnClickListener(this);
        btnSelectGet.setOnClickListener(this);

        ckNeedGoFloor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ckNeedDlivery.setChecked(true);
                }
            }
        });
        ckNeedDlivery.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    ckNeedGoFloor.setChecked(false);
                }
            }
        });
    }

    public void openKeybord(EditText mEditText, Context mContext) {
        mEditText.requestFocusFromTouch();
        mEditText.requestFocus();
        InputMethodManager imm = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.new_save_btn:
                Tos("目前正在建设中...期待下次更新");
                //saveDocument();
                break;
            case R.id.btn_selectSendPeople:
                Intent intent = new Intent(getActivity(), SelectAddressActivity.class);
                intent.putExtra("IsSend", SEND);
                startActivity(intent);
                break;
            case R.id.btn_selectGetPeople:
                Intent intent1 = new Intent(getActivity(), SelectAddressActivity.class);
                intent1.putExtra("IsSend", GET);
                startActivity(intent1);
                break;
        }
    }

    private void saveDocument() {
        if (MyAppLocation.shipBook == null || MyAppLocation.getBook == null) {
            Tos("请选择或添加收件人和寄件人");
            return;
        }
        String productNamestr = etProDuctName.getText().toString();
        if (TextUtils.isEmpty(productNamestr)) {
            Tos("请输入货品名称");
            openKeybord(etProDuctName, getActivity());
            return;
        }
        String productQty = etProDuctQty.getText().toString();
        if (TextUtils.isEmpty(productQty)) {
            openKeybord(etProDuctQty, getActivity());
            Tos("请输入货品数量");
            return;
        }
        String productWeightstr = etProDuctWeight.getText().toString();
        if (TextUtils.isEmpty(productWeightstr)) {
            openKeybord(etProDuctWeight, getActivity());
            Tos("请输入货品重量");
            return;
        }
        String productVolstrr = etProDuctVol.getText().toString();
        if (TextUtils.isEmpty(productVolstrr)) {
            openKeybord(etProDuctVol, getActivity());
            Tos("请输入货品体积");
            return;
        }

        String price = etPrice.getText().toString();
        if (TextUtils.isEmpty(price)) {
            openKeybord(etPrice, getActivity());
            Tos("请输入声明的保险价格");
            return;
        }

        final String endPoint = SoapEndpoint.SET_IS_SIGNUP;
        final String soapAction = SoapAction.SaveDocument;
        final Login loginInfo = MyAppLocation.login;
        String getAddress = MyAppLocation.getBook.getAddress();
        String sendAddress = MyAppLocation.shipBook.getAddress();
        String[] getsplit = getAddress.split(" ");
        String[] sendsplit = sendAddress.split(" ");
        int selectedItemPosition = spMode.getSelectedItemPosition();
        String info = XmlStr.SaveDocument;
        info = info.replace("UserIdValue", loginInfo.getUserId() + "");
        info = info.replace("ClientNameValue", loginInfo.getClientName());
        info = info.replace("SessionIdValue", loginInfo.getSessionId());
        info = info.replace("RoleDataValue", loginInfo.getRoleData() + "");
        info = info.replace("ConsigneeAddressAddressValue", getsplit[3]);
        info = info.replace("ConsigneeAddressCityValue", getsplit[1]);
        info = info.replace("ConsigneeAddressDistrictValue", getsplit[2]);
        info = info.replace("ConsigneeAddressProvinceValue", getsplit[0]);
        info = info.replace("ConsigneeContactPersonValue", MyAppLocation.getBook.getContactName());
        info = info.replace("ConsigneeNameValue", MyAppLocation.getBook.getCompanyName());
        info = info.replace("ConsigneePhoneNumberValue", MyAppLocation.getBook.getMobileNumber());
        long l = System.currentTimeMillis();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = format.format(new Date(l));
        date = date.replace(" ", "T");
        info = info.replace("CreateTimeValue", date);
        info = info.replace("ProductNameValue", etProDuctName.getText().toString());
        info = info.replace("QuantityValue", etProDuctQty.getText().toString());
        info = info.replace("VolumnValue", etProDuctVol.getText().toString());
        info = info.replace("WeightValue", etProDuctWeight.getText().toString());
        info = info.replace("ModeIdValue", list.get(selectedItemPosition).getModeId());
        info = info.replace("ModeNameValue", list.get(selectedItemPosition).getModeName());
        info = info.replace("ShipperContactNameValue", MyAppLocation.shipBook.getContactName());
        info = info.replace("ShipperNameValue", MyAppLocation.shipBook.getCompanyName());
        info = info.replace("ShipperPhoneNumberValue", MyAppLocation.shipBook.getMobileNumber());
        info = info.replace("ShipperAddressAddressValue", sendsplit[3]);
        info = info.replace("ShipperAddressCityValue", sendsplit[1]);
        info = info.replace("ShipperAddressDistrictValue", sendsplit[2]);
        info = info.replace("ShipperAddressProvinceValue", sendsplit[0]);
        info = info.replace("UpstairValue", ckNeedGoFloor.isChecked() + "");
        info = info.replace("NeedDeliveryValue", ckNeedDlivery.isChecked() + "");
        info = info.replace("DeliveryAfterNotifyValue", ckgoodsControl.isChecked() + "");
        info = info.replace("PremiumValue", etPrice.getText().toString());
        info = info.replace("FromCityValue", sendsplit[1]);
        final String finalInfo = info;
        MySoapHttpRequest.getString(new CallBackString() {
            @Override
            public void httFinsh(final String data) {
                LogU.e(data);
            }
        }, endPoint, soapAction, finalInfo);
    }

    @Override
    public void onResume() {
        if (MyAppLocation.getBook != null) {
            llGet.removeAllViews();
            addView(llGet, MyAppLocation.getBook);
        } else if (MyAppLocation.shipBook != null) {
            llSend.removeAllViews();
            addView(llSend, MyAppLocation.shipBook);
        }
        super.onResume();
    }

    @Override
    public void flash() {

    }

    private void addView(LinearLayout linearLayout, AddressBook book) {
        if (book==null){
            return;
        }
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_addressinfo, null);
        tvName = (TextView) view.findViewById(R.id.tvName);
        tvComName = (TextView) view.findViewById(R.id.tvComName);
        tvCallNum = (TextView) view.findViewById(R.id.tvCallNum);
        tvAddress = (TextView) view.findViewById(R.id.tvAddress);
        btnDelete = (Button) view.findViewById(R.id.btn_delete);
        btnDelete.setVisibility(View.GONE);
        tvName.setText(book.getContactName());
        tvComName.setText(book.getCompanyName());
        tvCallNum.setText(book.getMobileNumber());
        tvAddress.setText(book.getAddress());
        linearLayout.addView(view);
    }
}
