
package com.example.ok.shipments.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.example.ok.shipments.Activity.GetLocationActivity;
import com.example.ok.shipments.Activity.MapActivity;
import com.example.ok.shipments.Activity.SelectAddressActivity;
import com.example.ok.shipments.Activity.UpdateCargoInfosActivity;
import com.example.ok.shipments.BaseSettings;
import com.example.ok.shipments.MyAppLocation;
import com.example.ok.shipments.R;
import com.example.ok.shipments.adapter.ItemLbslocationLvAdapter;
import com.example.ok.shipments.model.AddressBook;
import com.example.ok.shipments.model.BastLocationJson;
import com.example.ok.shipments.model.CargoInfo;
import com.example.ok.shipments.model.FindInProgressRequest;
import com.example.ok.shipments.model.HandlerCoordinate;
import com.example.ok.shipments.model.Login;
import com.example.ok.shipments.model.OneLocationData;
import com.example.ok.shipments.urls.Urls;
import com.example.ok.shipments.utils.AddressBookXml;
import com.example.ok.shipments.utils.CallBackString;
import com.example.ok.shipments.utils.DialoginOkCallBack;
import com.example.ok.shipments.utils.GetMarkNamesXmlparser;
import com.example.ok.shipments.utils.HandlerCoordinateXmlParser;
import com.example.ok.shipments.utils.LocationXmlParser;
import com.example.ok.shipments.utils.LogU;
import com.example.ok.shipments.utils.MySoap;
import com.example.ok.shipments.utils.OnClickutils;
import com.example.ok.shipments.utils.ProgressRequestXmlParser;
import com.example.ok.shipments.utils.RetrueCodeHandler;
import com.example.ok.shipments.utils.RetruenUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class FragmentShipFragment extends BaseFragment implements View.OnClickListener {

    public static final int SHIP = 5;
    private EditText etRemark;
    private Spinner spTime;
    private Button btnShipOK;
    private Spinner spVol;
    private ArrayList<String> data_list;
    private ArrayAdapter<String> arr_adapter;
    private ArrayList<String> arrCars;
    private ArrayList<String> listNames;
    private LinearLayout llMarkNames;
    private ArrayList<CheckBox> boxes;
    private ArrayList<String> dates;
    private LinearLayout llTime1;
    private LinearLayout llTime2;
    private ArrayList<String> timeTist;
    private FindInProgressRequest progressRequest;
    private EditText etVol;
    private LinearLayout ll_send;
    private LinearLayout ll_cancelDocument;
    private Button btn_cancelDocument;
    private EditText etCancelReason;
    private Button btn_cancelDocumentOk;
    private TextView tv_status;
    private TextView tv_drive_address;
    private TextView tv_drive_num;
    private TextView tv_callNum;
    private Timer timer;
    private OneLocationData oneLocationData;
    private TextView tvSelectSendPeople;
    private LinearLayout ll_sendPeople;
    private TextView tvName;
    private TextView tvComName;
    private TextView tvCallNum;
    private TextView tvAddress;
    private Button btnDelete;
    private TextView tv_distance;
    private AlertDialog builder;
    private EditText etCancelRes;
    private ArrayList<Date> times;
    private CardView canclecardView;
    private LinearLayout ll_shipment_UpdateCargoInfos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ship, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findviewbyId(view);
        getMarkNames();
        initToolbar(view, "我要发货");
        initTime();
        getLastUser();
        if (MyAppLocation.progressRequest != null) {
            ll_cancelDocument.setVisibility(View.VISIBLE);
            canclecardView.setVisibility(View.VISIBLE);
            ll_send.setVisibility(View.GONE);
            if (!MyAppLocation.progressRequest.getStatus().equals("IsRequest")) {
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        getDocumentLocation(MyAppLocation.progressRequest.getRequestId());
                    }
                }, 0, 2 * 60 * 1000);
            } else {
                tv_status.setText("等待司机接收订单");
            }
        }
    }


    private void getLastUser() {
        RequestParams params = new RequestParams();
        params.addBodyParameter("sessionId", MyAppLocation.login.getSessionId());
        params.addBodyParameter("userId", MyAppLocation.login.getUserId());
        params.addBodyParameter("clientName", MyAppLocation.login.getClientName());
        getData(Urls.GetDefault, params, new CallBackString() {
            @Override
            public void httFinsh(String data) {
                AddressBookXml parser = RetruenUtils.getReturnInfo(data, new AddressBookXml());
                if (parser != null) {
                    AddressBook user = parser.getUser();
                    MyAppLocation.shipBook = user;
                    ll_sendPeople.removeAllViews();
                    addView(ll_sendPeople, user);
                }
            }
        });
    }

    private void getAddress(final LatLng latLng) {
        String url = "http://api.map.baidu.com/geocoder/v2/?ak=sfuQIH09bvPcDELFp0vIkyQ0nGRd07Tw&callback=renderReverse&" +
                "location=" + latLng.latitude + "," + latLng.longitude + "&output=xml&pois=1";
        MyAppLocation.mHttpUtils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                LocationXmlParser parser = RetruenUtils.getReturnInfo(responseInfo.result, new LocationXmlParser());
                assert parser != null;
                {
                    tv_drive_address.setText("地址：" + parser.getAddress());
                }

            }

            @Override
            public void onFailure(HttpException error, String msg) {

            }
        });
    }

    private void getNetWork() {
        String driveUser = MyAppLocation.progressRequest.getHandler().split(",")[0];
        String url = Urls.getLocationHandler + driveUser + "_" + MyAppLocation.progressRequest.getHandlerId();
        LogU.e(url);
        MyAppLocation.mHttpUtils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                LogU.e(responseInfo.result);
                try {
                    if (new JSONObject(responseInfo.result).getString("status").equals("0")) {
                        oneLocationData = OneLocationData.objectFromData(responseInfo.result);
                        //Tos(oneLocationData.getEntities().get(0).getModify_time());
                        List<Double> location = oneLocationData.getEntities().get(0).getRealtime_point().getLocation();
                        getAddress(new LatLng(location.get(1), location.get(0)));
                    }
                } catch (Exception e) {
                    LogU.e(e.toString());
                    Tos("解析出错");
                }
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                LogU.e(msg);
                Tos("获取不到位置");
            }
        });
    }

    private void getDocumentLocation(String requestId) {
        String url = Urls.GetRequestHandlerCoordinate;
        RequestParams params = new RequestParams();
        params.addBodyParameter("sessionId", MyAppLocation.login.getSessionId());
        params.addBodyParameter("userId", MyAppLocation.login.getUserId());
        params.addBodyParameter("clientName", MyAppLocation.login.getClientName());
        params.addBodyParameter("requestId", requestId);
        MyAppLocation.mHttpUtils.send(HttpRequest.HttpMethod.POST,
                url, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        LogU.e(responseInfo.result);
                        RetrueCodeHandler retrueCode = RetruenUtils.getReturnInfo(responseInfo.result, new RetrueCodeHandler());
                        if (retrueCode != null && retrueCode.getString().equals("0")) {
                            HandlerCoordinateXmlParser parser = RetruenUtils.getReturnInfo(responseInfo.result, new HandlerCoordinateXmlParser());
                            if (parser != null) {
                                final HandlerCoordinate coordinate = parser.getCoordinate();
                                MyAppLocation.coordinate = coordinate;
                                if (TextUtils.isEmpty(coordinate.getDriverName())) {
                                    tv_status.setText("等待司机接收");
                                    tv_callNum.setText("");
                                    tv_drive_num.setText("");
                                    tv_drive_address.setText("");
                                    return;
                                }
                                tv_status.setText("司机：" + coordinate.getDriverName());
                                tv_drive_num.setText("车牌号：" + coordinate.getTruckNumber());
                                tv_callNum.setText("电话：" + coordinate.getDriverMobile());
                                tv_callNum.setTextColor(Color.BLUE);
                                tv_callNum.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        if (OnClickutils.isFastDoubleClick()) {
                                            return;
                                        }
                                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + coordinate.getDriverMobile()));
                                        if (TextUtils.isEmpty(coordinate.getDriverMobile())) {
                                            Toast.makeText(getActivity(), "电话号码有误", Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                        getActivity().startActivity(intent);
                                    }
                                });
                                LatLng latLng = new LatLng(Double.valueOf(coordinate.getCoordinate()[0]), Double.valueOf(coordinate.getCoordinate()[1]));
                                getAddress(latLng);
                                setDistance(latLng);
                            }
                        } else if (retrueCode != null && retrueCode.getString().equals("-1")) {
                            Tos(retrueCode.getError());
                        } else {
                            Tos("请求数据错误");
                        }
                        closeProgressDialog();
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        closeProgressDialog();
                        Tos(msg);
                    }
                });
    }

    private void getBestLocaltion(String shipperStr, final LatLng latLng) {
        String url = BaseSettings.plaseUrl + shipperStr + BaseSettings.plaseUrlParameter;
        MyAppLocation.mHttpUtils.send(HttpRequest.HttpMethod.POST,
                url,
                //params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        try {
                            JSONObject jsonObject = new JSONObject(responseInfo.result);
                            String status = jsonObject.getString("status");
                            if (status.equals("0")) {
                                List<BastLocationJson> list = BastLocationJson.arrayBastLocationJsonFromData(jsonObject.getString("results"));
                                if (list.size() == 0) {
                                    tv_distance.setText("录入的地址无法解析请进入地图查看");
                                    return;
                                }
                                double distance = DistanceUtil.getDistance(latLng, new LatLng(list.get(0).getLocation().getLat(), list.get(0).getLocation().getLng()));
                                tv_distance.setText("距离接货地点还有：" + new Double(distance).intValue() + "米");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), "地址不明确无法定位", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        Toast.makeText(getActivity(), "无法获取数据", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    //司机位置
    private void setDistance(LatLng latLng) {
        //目的地
        String latitude = MyAppLocation.progressRequest.getLatitude();
        String longitude = MyAppLocation.progressRequest.getLongitude();
        if (latitude.equals("0.0000") && longitude.equals("0.0000")) {
            String address = MyAppLocation.progressRequest.getAddress().replace(" ", "");
            getBestLocaltion(address, latLng);
            return;
        }
        double distance = DistanceUtil.getDistance(latLng, new LatLng(Double.valueOf(latitude), Double.valueOf(longitude)));
        tv_distance.setText("距离接货地点还有：" + new Double(distance).intValue() + "米");
    }

    private void inToMap() {
        Intent intent = new Intent(getActivity(), MapActivity.class);
        intent.putExtra("requestId", MyAppLocation.progressRequest.getRequestId());
        startActivity(intent);
    }

    private void getMarkNames() {
        showProgressDialog();
        String url = Urls.GetMarkNames;
        RequestParams params = new RequestParams();
        params.addBodyParameter("sessionId", MyAppLocation.login.getSessionId());
        params.addBodyParameter("userId", MyAppLocation.login.getUserId());
        params.addBodyParameter("clientName", MyAppLocation.login.getClientName());
        getData(url, params, new CallBackString() {
            @Override
            public void httFinsh(String data) {
                RetrueCodeHandler retrueCode = RetruenUtils.getReturnInfo(data, new RetrueCodeHandler());
                if (retrueCode != null && retrueCode.getString().equals("0")) {
                    GetMarkNamesXmlparser namesXmlparser = RetruenUtils.getReturnInfo(data, new GetMarkNamesXmlparser());
                    if (namesXmlparser != null) {
                        listNames = namesXmlparser.getGetMarkNames().getListNames();
                        initMarkNames();
                    } else {
                        Tos("解析出错");
                    }
                } else if (retrueCode != null && retrueCode.getString().equals("-1")) {
                    Tos(retrueCode.getError());
                } else {
                    Tos("请求数据错误");
                }
            }
        });
    }

    private void initMarkNames() {
        boxes = new ArrayList<>();
        for (int i = 0; i < listNames.size(); i++) {
            CheckBox checkBox = new CheckBox(getActivity());
            checkBox.setText(listNames.get(i));
            llMarkNames.addView(checkBox);
            boxes.add(checkBox);
        }
    }

    private void saveInfo() {
        SharedPreferences sp = getActivity().getSharedPreferences("info", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.commit();
    }

    private void initTime() {
        final Calendar c = Calendar.getInstance();
        long lTime = System.currentTimeMillis();
        dates = new ArrayList<>();
        times = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        timeTist = new ArrayList<>();
        for (int i = 1; i < 24; i++) {
            Date date = new Date(lTime + 1000 * 60 * 60 * i + 1000 * 60 * 5);
            this.dates.add(sdf.format(date));
            if (date.getMinutes() < 30) {
                date = new Date(lTime + 1000 * 60 * 60 * (i + 1));
                timeTist.add(date.getDate() + "号 " + date.getHours() + "点");
                times.add(date);
            } else {
                date = new Date(lTime + 1000 * 60 * 60 * (i + 2));
                timeTist.add(date.getDate() + "号 " + date.getHours() + "点");
                times.add(date);
            }
        }
        spTime.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, timeTist));
    }

    private void findviewbyId(View view) {
        ll_send = (LinearLayout) view.findViewById(R.id.ll_send);
        ll_shipment_UpdateCargoInfos = (LinearLayout) view.findViewById(R.id.ll_shipment_UpdateCargoInfos);
        ll_shipment_UpdateCargoInfos.setOnClickListener(this);
        ll_sendPeople = (LinearLayout) view.findViewById(R.id.ll_sendPeople);
        tvSelectSendPeople = (TextView) view.findViewById(R.id.tv_selectSendPeople);
        tvSelectSendPeople.setClickable(true);
        tvSelectSendPeople.setOnClickListener(this);
        ll_cancelDocument = (LinearLayout) view.findViewById(R.id.ll_cancelDocument);
        canclecardView = (CardView) view.findViewById(R.id.cv);
        etRemark = (EditText) view.findViewById(R.id.et_remark);
        etVol = (EditText) view.findViewById(R.id.etVol);
        spTime = (Spinner) view.findViewById(R.id.spTime);
        btnShipOK = (Button) view.findViewById(R.id.btn_shipOk);
        btnShipOK.setOnClickListener(this);
        spVol = (Spinner) view.findViewById(R.id.spVol);
        llMarkNames = (LinearLayout) view.findViewById(R.id.ll_cks);
        llTime1 = (LinearLayout) view.findViewById(R.id.ll_time1);
        btn_cancelDocument = (Button) view.findViewById(R.id.btn_cancelDocument);
        etCancelReason = (EditText) view.findViewById(R.id.etCancelReason);
        btn_cancelDocumentOk = (Button) view.findViewById(R.id.btn_cancelDocumentOk);

        tv_status = (TextView) view.findViewById(R.id.tv_status);
        tv_drive_address = (TextView) view.findViewById(R.id.tv_drive_address);
        tv_drive_num = (TextView) view.findViewById(R.id.tv_drive_num);
        tv_callNum = (TextView) view.findViewById(R.id.tv_callNum);
        tv_distance = (TextView) view.findViewById(R.id.tv_distance);

        btn_cancelDocument.setOnClickListener(this);
        btn_cancelDocumentOk.setOnClickListener(this);
        initSpData();
        spVol.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                etVol.setText(arrCars.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ll_sendPeople.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvSelectSendPeople.performClick();
            }
        });
    }

    private void initSpData() {
        //数据
        data_list = new ArrayList<String>();
        data_list.add("选择");
        data_list.add("小型面包车(5)");
        data_list.add("中型面包车(10)");
        data_list.add("小型货车(15)");
        data_list.add("中型货车(20)");
        data_list.add("大型货车(50)");

        arrCars = new ArrayList<>();
        arrCars.add(0 + "");
        arrCars.add(5 + "");
        arrCars.add(10 + "");
        arrCars.add(15 + "");
        arrCars.add(20 + "");
        arrCars.add(50 + "");
        //适配器
        arr_adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, data_list);
        //设置样式
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        spVol.setAdapter(arr_adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_shipOk:
                if (OnClickutils.isFastDoubleClick()) {
                    return;
                }
                sendGoods();
                break;/*
            case R.id.etTimeSecond:
                showTimePickerDialog();
                break;*/
            case R.id.ll_shipment_UpdateCargoInfos:
                Intent intent1 = new Intent(getActivity(), UpdateCargoInfosActivity.class);
                if (UpdateCargoInfosActivity.getCargoInfos()!=null){
                    intent1.putExtra("data",UpdateCargoInfosActivity.getCargoInfos());
                }
                startActivity(intent1);
                break;
            case R.id.btn_cancelDocument:
                if (MyAppLocation.progressRequest == null) {
                    Tos("当前无订单");
                    return;
                }

                if (builder == null) {
                    builder = new AlertDialog.Builder(getActivity()).create();
                    builder.setView(LayoutInflater.from(getActivity()).inflate(R.layout.dialog_et, null));
                    builder.setTitle("是否确定取消订单？");
                    builder.getWindow().setGravity(Gravity.CENTER);

                }
                builder.show();
                builder.findViewById(R.id.btn_dialog_ok).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cancelDocument();
                        builder.dismiss();
                    }
                });
                builder.findViewById(R.id.btn_dialog_cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        builder.dismiss();
                    }
                });
                etCancelRes = (EditText) builder.findViewById(R.id.et_dialog_cancelRes);
                /*etCancelReason.setVisibility(View.VISIBLE);
                btn_cancelDocumentOk.setVisibility(View.VISIBLE);*/
                break;
            case R.id.btn_cancelDocumentOk:
                cancelDocument();
                break;
            case R.id.tv_selectSendPeople:
                Intent intent = new Intent(getActivity(), SelectAddressActivity.class);
                intent.putExtra("IsSend", SHIP);
                startActivity(intent);
                break;
        }
    }


    @Override
    public void onPause() {
        if (timer != null) {
            timer.cancel();
        }
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void cancelDocument() {
        showProgressDialog();
        String url = Urls.CancelDeliveryRequest;
        RequestParams params = new RequestParams();
        params.addBodyParameter("sessionId", MyAppLocation.login.getSessionId());
        params.addBodyParameter("userId", MyAppLocation.login.getUserId());
        params.addBodyParameter("clientName", MyAppLocation.login.getClientName());
        params.addBodyParameter("requestId", MyAppLocation.progressRequest.getRequestId());
        if (builder == null) {
            return;
        }
        if (TextUtils.isEmpty(etCancelRes.getText().toString())) {
            closeProgressDialog();
            Tos("请写明取消原因");
            return;
        }
        params.addBodyParameter("cancelReason", etCancelRes.getText().toString());
        MyAppLocation.mHttpUtils.send(HttpRequest.HttpMethod.POST,
                url, params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        RetrueCodeHandler retrueCode = RetruenUtils.getReturnInfo(responseInfo.result, new RetrueCodeHandler());
                        if (retrueCode != null && retrueCode.getString().equals("0")) {
                            Tos("取消成功");
                            UpdateCargoInfosActivity.clearCargoInfos();
                            MyAppLocation.progressRequest = null;
                            flash();
                            //TODO

                        } else if (retrueCode != null && retrueCode.getString().equals("-1")) {
                            Tos(retrueCode.getError());
                        } else {
                            Tos("请求数据错误");
                        }
                        closeProgressDialog();
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        closeProgressDialog();
                        Tos(msg);
                    }
                });
    }

    private void getLocation() {
        Intent intent = new Intent(getActivity(), GetLocationActivity.class);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        flash();

        if (MyAppLocation.shipBook != null) {
            ll_sendPeople.removeAllViews();
            addView(ll_sendPeople, MyAppLocation.shipBook);
        }
        super.onResume();
    }

    public void flash() {
        if (MyAppLocation.progressRequest != null && !MyAppLocation.progressRequest.getStatus().equals("IsRequest")) {
            getDocumentLocation(MyAppLocation.progressRequest.getRequestId());
        } else if (MyAppLocation.progressRequest != null && (!MyAppLocation.progressRequest.getStatus().equals("IsRespond") || !MyAppLocation.progressRequest.getStatus().equals("IsRequest"))) {
            tv_status.setText("等待收货员接收");
            tv_callNum.setText("");
            tv_distance.setText("");
            tv_drive_address.setText("");
            tv_drive_num.setText("");
        }
        try {
            if (MyAppLocation.progressRequest != null) {
                btn_cancelDocument.setText("取消订单");
                btnShipOK.setText("订单正在进行中(点击进入地图)");
                ll_send.setVisibility(View.GONE);
                ll_cancelDocument.setVisibility(View.VISIBLE);
                canclecardView.setVisibility(View.VISIBLE);

                btnShipOK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loginSuccess();
                    }
                });
            } else {
                btn_cancelDocument.setText("当前无订单");
                etCancelReason.setVisibility(View.GONE);
                btn_cancelDocumentOk.setVisibility(View.GONE);
                ll_send.setVisibility(View.VISIBLE);
                ll_cancelDocument.setVisibility(View.GONE);
                canclecardView.setVisibility(View.GONE);
                btnShipOK.setText("确认发货");
                btnShipOK.setOnClickListener(FragmentShipFragment.this);
            }
        } catch (Exception e) {

        }

    }

    private void loginSuccess() {
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
                        closeProgressDialog();
                        if (MyAppLocation.progressRequest.getStatus().equals("IsRequest")) {
                            Tos("等待收货员接收请求");
                            return;
                        }
                        inToMap();

                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        closeProgressDialog();
                        Tos(msg);
                    }
                });
    }

    private void sendGoods() {
        showProgressDialog();
        String endPoint = "http://" + BaseSettings.VERSIONS + ".xsjky.cn/DeliveryRequest/DeliveryRequestService.asmx";
        // SOAP Action
        final Login loginInfo = MyAppLocation.login;
        final String soapAction = "http://www.xsjky.cn/NewRequest";
        final MySoap transport = new MySoap(endPoint);
        String info = BaseSettings.NewRequest;
        info = info.replace("userIdValue", loginInfo.getUserId() + "");
        info = info.replace("clientNameValue", loginInfo.getClientName());
        info = info.replace("sessionIdValue", loginInfo.getSessionId());
        if (MyAppLocation.shipBook == null) {
            Tos("请选择发件人");
            closeProgressDialog();
            return;
        }
        info = info.replace("longitudeValue", MyAppLocation.shipBook.getCoordinate().getLongitude() + "");
        info = info.replace("latitudeValue", MyAppLocation.shipBook.getCoordinate().getLatitude() + "");
        // String contactPersonValue = etSendPeople.getText().toString();
        info = info.replace("contactPersonValue", MyAppLocation.shipBook.getContactName());
        //String contactPhoneValue = etPhone.getText().toString();
        info = info.replace("contactPhoneValue", MyAppLocation.shipBook.getMobileNumber());
        info = info.replace("ShipperNameValue", MyAppLocation.shipBook.getCompanyName());
        String appointmentTimeValue = "";
        appointmentTimeValue = dates.get(spTime.getSelectedItemPosition());
        Date date = times.get(spTime.getSelectedItemPosition());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH");
        String format = sdf.format(date);
        info = info.replace("appointmentTimeValue", format + ":00:00");
        info = info.replace("cargoWeightValue", "0");

        int selectedItemPosition = spVol.getSelectedItemPosition();
        if (selectedItemPosition == 0 && (TextUtils.isEmpty(etVol.getText().toString()) || etVol.getText().toString().equals("0"))) {
            Tos("请选择车型");
            closeProgressDialog();
            return;
        }
        info = info.replace("cargoVolumnValue", etVol.getText().toString());
        info = info.replace("toCityValue", "");
        String reMark = etRemark.getText().toString();
        if (TextUtils.isEmpty(reMark)) {
            reMark = "";
        }
        info = info.replace("remarksValue", reMark);
        String requestMarksValue = "";
        //TODO
        for (int i = 0; i < boxes.size(); i++) {
            if (boxes.get(i).isChecked()) {
                requestMarksValue += "<string>" + listNames.get(i) + "</string>";
            }
        }
        info = info.replace("requestMarksValue", requestMarksValue);
        //String addressValue = etGetLocation.getText().toString() + " " + etcity.getText().toString() + " " + etdes.getText().toString() + " " + etAddressDetail.getText().toString();
        info = info.replace("addressValue", MyAppLocation.shipBook.getAddress());
        transport.setinfo(info);
        transport.debug = true;
        new Thread() {
            @Override
            public void run() {
                try {
                    String call = transport.call(soapAction, null, null, "");
                    final RetrueCodeHandler returnInfo = RetruenUtils.getReturnInfo(call, new RetrueCodeHandler());
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (returnInfo != null && returnInfo.getString().equals("0")) {
                                Tos("请求成功");
                                //saveLastUser();
                                getProgressRequest();
                                saveInfo();
                            } else if (returnInfo != null && returnInfo.getString().equals("-1")) {
                                Tos(returnInfo.getError());
                                saveInfo();
                            } else {
                                Tos("请求失败");
                            }
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    closeProgressDialog();
                }
                //mListView.onRefreshComplete();
            }
        }.start();
    }

    private void UpdateCargoInfos(String requestId) {

        ArrayList<CargoInfo> cargoInfos = UpdateCargoInfosActivity.getCargoInfos();
        if (cargoInfos == null || cargoInfos.size() == 0) {
            return;
        }
        String endPoint = "http://" + BaseSettings.VERSIONS + ".xsjky.cn/DeliveryRequest/DeliveryRequestService.asmx";
        // SOAP Action
        final Login loginInfo = MyAppLocation.login;
        final String soapAction = "http://www.xsjky.cn/UpdateCargoInfos";
        final MySoap transport = new MySoap(endPoint);
        String info = BaseSettings.UpdateCargoInfos;
        info = info.replace("userIdValue", loginInfo.getUserId() + "");
        info = info.replace("clientNameValue", loginInfo.getClientName());
        info = info.replace("sessionIdValue", loginInfo.getSessionId());
        info = info.replace("requestIdValue", requestId);
        String cargoString = " <CargoInfo>\n" +
                "          <RecordId>0</RecordId>\n" +
                "          <ProductName>ProductNameValue</ProductName>\n" +
                "          <Length>LengthValue</Length>\n" +
                "          <Width>WidthValue</Width>\n" +
                "          <Height>HeightValue</Height>\n" +
                "          <Quantity>QuantityValue</Quantity>\n" +
                "          <Volumn>VolumnValue</Volumn>\n" +
                "          <Remarks>RemarksValue</Remarks>\n" +
                "        </CargoInfo>";
        String cargosValues = "";
        for (int i = 0; i < cargoInfos.size(); i++) {
            cargosValues += cargoString;
            cargosValues = cargosValues.replace("ProductNameValue", cargoInfos.get(i).getProductName());
            cargosValues = cargosValues.replace("LengthValue", cargoInfos.get(i).getLength());
            cargosValues = cargosValues.replace("WidthValue", cargoInfos.get(i).getWidth());
            cargosValues = cargosValues.replace("HeightValue", cargoInfos.get(i).getHeight());
            cargosValues = cargosValues.replace("QuantityValue", cargoInfos.get(i).getQuantity());
            cargosValues = cargosValues.replace("VolumnValue", cargoInfos.get(i).getVolumn());
            cargosValues = cargosValues.replace("RemarksValue", cargoInfos.get(i).getRemarks());

        }
        info = info.replace("cargosValues", cargosValues);
        transport.setinfo(info);
        transport.debug = true;
        new Thread() {
            @Override
            public void run() {
                try {
                    String call = transport.call(soapAction, null, null, "");
                    final RetrueCodeHandler returnInfo = RetruenUtils.getReturnInfo(call, new RetrueCodeHandler());
                    LogU.e(call);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (returnInfo != null && returnInfo.getString().equals("0")) {
                                LogU.e("成功");
                            } else if (returnInfo != null && returnInfo.getString().equals("-1")) {
                                Tos(returnInfo.getError());
                                saveInfo();
                            } else {
                                Tos("请求失败");
                            }
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    closeProgressDialog();
                }
                //mListView.onRefreshComplete();
            }
        }.start();
    }


    private void getProgressRequest() {
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
                                UpdateCargoInfos(progressRequest.getRequestId());
                                flash();
                            } else {
                                Tos("解析错误");
                            }
                        } else if (retrueCode != null && retrueCode.getString().equals("-1")) {
                            Tos(retrueCode.getError());
                        } else {
                            Tos("请求数据错误");
                        }
                        closeProgressDialog();
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        closeProgressDialog();
                        Tos(msg);
                    }
                });


    }

    private void addView(LinearLayout linearLayout, AddressBook book) {
        if (book == null) {
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
   /* public void showDatePickerDialog() {
        DatePickerFragment datePicker = new DatePickerFragment();
        datePicker.setDateCallBack(new DatePickerFragment.DateCallBack() {
            @Override
            public void getDate(String date) {
                etTime.setText(date);
            }
        });
        datePicker.show(getFragmentManager(), "datePicker");
    }*/

   /* public void showTimePickerDialog() {
        TimePickerFragment datePicker = new TimePickerFragment();
        datePicker.setDateCallBack(new TimePickerFragment.DateCallBack() {
            @Override
            public void getDate(String date) {
                etTimeSecond.setText(date);
            }
        });
        datePicker.show(getFragmentManager(), "timePicker");
    }*/

    @Override
    public void onDestroyView() {
        MyAppLocation.shipBook = null;
        super.onDestroyView();
    }
}
