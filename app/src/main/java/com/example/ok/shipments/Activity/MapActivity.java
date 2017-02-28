package com.example.ok.shipments.Activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.TextOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.example.ok.shipments.BaseSettings;
import com.example.ok.shipments.MyAppLocation;
import com.example.ok.shipments.R;
import com.example.ok.shipments.map.DrivingRouteOverlay;
import com.example.ok.shipments.model.BastLocationJson;
import com.example.ok.shipments.model.HandlerCoordinate;
import com.example.ok.shipments.urls.Urls;
import com.example.ok.shipments.utils.DialoginOkCallBack;
import com.example.ok.shipments.utils.HandlerCoordinateXmlParser;
import com.example.ok.shipments.utils.LogU;
import com.example.ok.shipments.utils.OnClickutils;
import com.example.ok.shipments.utils.PermissionUtil;
import com.example.ok.shipments.utils.RetrueCodeHandler;
import com.example.ok.shipments.utils.RetruenUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MapActivity extends BaseActivity implements View.OnClickListener, OnGetRoutePlanResultListener {

    private static final String TAG = "TAG";
    private static final int REQUEST_CONTACTS = 1;
    private static final String[] PERMISSIONS_CONTACT = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
    };
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private LocationClient mLocationClient;
    private LatLng ll;
    public static BDLocation bdLocation;
    private Button btnBackLocation;
    private Overlay overlay;
    private BitmapDescriptor bdicon;
    private LatLng sendLocation;
    private RoutePlanSearch routePlanSearch;
    private Button btnDrivingRouteResult;
    private Button btnGetSendLoacation;
    private String requestId;
    private HandlerCoordinate coordinate;
    private String stat;
    private TextView tvDocumentStatus;
    private TextOptions ooText;
    private InfoWindow mInfoWindow;
    private List<BastLocationJson> list;
    private DrivingRoutePlanOption option;
    private Timer timer;
    private TextView tv_info;
    private Button btncall;
    private ImageView ivBackLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        mMapView = (MapView) findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();

        if (Build.VERSION.SDK_INT >= 23) {
            showContacts(mMapView);
        } else {
            init();
        }
    }

    public void showContacts(View v) {
        Log.e(TAG, "Show contacts button pressed. Checking permissions.");

        // Verify that all required contact permissions have been granted.
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            // Contacts permissions have not been granted.
            Log.e(TAG, "Contact permissions has NOT been granted. Requesting permissions.");
            requestContactsPermissions(v);

        } else {

            // Contact permissions have been granted. Show the contacts fragment.
            Log.e(TAG,
                    "Contact permissions have already been granted. Displaying contact details.");
            init();
        }
    }

    private void requestContactsPermissions(View v) {
        // BEGIN_INCLUDE(contacts_permission_request)
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                || ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                || ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                || ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_PHONE_STATE)
                ) {
            showDialog(new DialoginOkCallBack() {
                @Override
                public void onClickOk(DialogInterface dialog, int which) {
                    ActivityCompat.requestPermissions(MapActivity.this, PERMISSIONS_CONTACT, REQUEST_CONTACTS);
                }
            }, "是否再次申请该权限？", null);
        } else {
            // Contact permissions have not been granted yet. Request them directly.
            ActivityCompat.requestPermissions(this, PERMISSIONS_CONTACT, REQUEST_CONTACTS);
        }
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
                        ActivityCompat.requestPermissions(MapActivity.this, PERMISSIONS_CONTACT, REQUEST_CONTACTS);
                    }
                }, "拒绝权限可能导致应用无法正常使用，是否再次申请权限？", null);
            }


        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void init() {
        setLocationMap();
        findViewById();
        setListener();
        requestId = getIntent().getStringExtra("requestId");
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                getDocumentLocation();
            }
        }, 0, 2 * 60 * 1000);
    }
    //获取位置  进行线路规划
    private void getDocumentLocation() {
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
                                coordinate = parser.getCoordinate();
                                LogU.e(coordinate.toString());
                                //TODO
                                if (coordinate.getCoordinate() == null) {
                                    return;
                                }
                                tv_info.setText("司机:" + coordinate.getDriverName() + " 车牌号:" + coordinate.getTruckNumber());
                                btncall.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (OnClickutils.isFastDoubleClick()) {
                                            return;
                                        }
                                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + coordinate.getDriverMobile()));
                                        if (TextUtils.isEmpty(coordinate.getDriverMobile())) {
                                            Toast.makeText(MapActivity.this, "电话号码有误", Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                        startActivity(intent);
                                    }
                                });
                                addoverlay(new LatLng(Double.valueOf(coordinate.getCoordinate()[0]), Double.valueOf(coordinate.getCoordinate()[1])));
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

    private void setListener() {
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(final Marker marker) {
                Button button = new Button(getApplicationContext());
                button.setBackgroundResource(R.drawable.popup);
                button.setText("拨打电话");
                button.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        //TODO
                        if (coordinate == null || TextUtils.isEmpty(coordinate.getDriverMobile())) {
                            Tos("没有电话");
                            mBaiduMap.hideInfoWindow();
                            return;
                        }
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + coordinate.getDriverMobile()));
                        startActivity(intent);
                        mBaiduMap.hideInfoWindow();
                    }
                });
                LatLng ll = marker.getPosition();
                mInfoWindow = new InfoWindow(button, ll, -47);
                mBaiduMap.showInfoWindow(mInfoWindow);
                return true;
            }
        });
    }

    private void getAddress(LatLng latLng) {
        String url = "http://api.map.baidu.com/geocoder/v2/?ak=sfuQIH09bvPcDELFp0vIkyQ0nGRd07Tw&callback=renderReverse&" +
                "location=" + latLng.latitude + "," + latLng.longitude + "&output=json&pois=1";
        MyAppLocation.mHttpUtils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                LogU.e(responseInfo.result);
            }

            @Override
            public void onFailure(HttpException error, String msg) {

            }
        });
    }

    private void findViewById() {
        btnBackLocation = (Button) findViewById(R.id.btn_backLocation);

        tv_info = (TextView) findViewById(R.id.tv_info);

        ivBackLocation = (ImageView) findViewById(R.id.iv_backlocation);
        ivBackLocation.setOnClickListener(this);
        btncall = (Button) findViewById(R.id.btncall);
        btnDrivingRouteResult = (Button) findViewById(R.id.btn_DrivingRouteResult);
        tvDocumentStatus = (TextView) findViewById(R.id.tvDocumentStatus);
        btnBackLocation.setOnClickListener(this);
        btnDrivingRouteResult.setOnClickListener(this);
        setStatus();
    }

    private void setStatus() {
        String[] stats2 = {"已提交", "已响应", "已拒绝", "已同意", "在路上", "已完成", "已取消"};
        String strStatus = "";
        switch (MyAppLocation.progressRequest.getStatus()) {
            case "IsRequest":
                strStatus = stats2[0];
                break;
            case "IsRespond":
                strStatus = stats2[1];
                break;
            case "IsReject":
                strStatus = stats2[2];
                break;
            case "IsAccept":
                strStatus = stats2[3];
                break;
            case "IsOnWay":
                strStatus = stats2[4];
                break;
            case "IsFinished":
                strStatus = stats2[5];
                break;
            case "IsCancel":
                strStatus = stats2[6];
                break;
        }
        tvDocumentStatus.setText("订单状态：" + strStatus);
    }

    public void line(LatLng latLng) {
        try {
            routePlanSearch = RoutePlanSearch.newInstance();
            routePlanSearch.setOnGetRoutePlanResultListener(this);
            option = new DrivingRoutePlanOption();
            if (MyAppLocation.progressRequest == null) {
                Toast.makeText(MapActivity.this, "无法获取司机位置", Toast.LENGTH_SHORT).show();
                return;
            }
            PlanNode from = null;
            try {
                if (MyAppLocation.progressRequest.getLatitude().equals("0.0000")) {
                    String address = MyAppLocation.progressRequest.getAddress();
                    getBestLocaltion(latLng, address);
                    return;
                } else {
                    from = PlanNode.withLocation(new LatLng(Double.valueOf(MyAppLocation.progressRequest.getLatitude()), Double.valueOf(MyAppLocation.progressRequest.getLongitude())));
                }
            } catch (Exception e) {
                Toast.makeText(MapActivity.this, "无法获取司机位置", Toast.LENGTH_SHORT).show();
            }
            PlanNode to = PlanNode.withLocation(latLng);

            option.from(to);
            option.to(from);
            option.policy(DrivingRoutePlanOption.DrivingPolicy.ECAR_DIS_FIRST);
            routePlanSearch.drivingSearch(option);
        } catch (Exception e) {
            Toast.makeText(MapActivity.this, "地址错误无法定位", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
    //解析地址  获取坐标位置
    private void getBestLocaltion(final LatLng latLng1, String shipperStr) {
        //"http://api.map.baidu.com/place/v2/search?query=%E7%99%BE%E5%BA%A6%E5%A4%A7%E5%8E%A6&region=%E6%B7%B1%E5%9C%B3&city_limit=true&output=json&ak=sfuQIH09bvPcDELFp0vIkyQ0nGRd07Tw"
        //String url="http://api.map.baidu.com/place/v2/search";
        /*String url1="http://api.map.baidu.com/place/v2/search?query="+shipperStr+"&page_size=6&page_num=0&scope=1&region=深圳&output=json&ak=sfuQIH09bvPcDELFp0vIkyQ0nGRd07Tw";
        String WebPlaseAPI_KEY="sfuQIH09bvPcDELFp0vIkyQ0nGRd07Tw";*/
        shipperStr = shipperStr.replace(" ", "");
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
                                list = BastLocationJson.arrayBastLocationJsonFromData(jsonObject.getString("results"));
                                if (list.size() == 0) {
                                    //poiSearch();
                                    Toast.makeText(MapActivity.this, "无法找到目的地点", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                LatLng latLng = new LatLng(list.get(0).getLocation().getLat(), list.get(0).getLocation().getLng());
                                PlanNode from = PlanNode.withLocation(latLng);
                                PlanNode to = PlanNode.withLocation(latLng1);
                                option.from(to);
                                option.to(from);
                                option.policy(DrivingRoutePlanOption.DrivingPolicy.ECAR_DIS_FIRST);
                                routePlanSearch.drivingSearch(option);
                                //LogU.e(list.toString());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(MapActivity.this, "地址不明确无法定位", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        Toast.makeText(MapActivity.this, "无法获取数据", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private boolean isFirstLoc = true;

    private void setLocationMap() {
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        //这样设置就会有小箭头指定方向
        mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL, true, null));
        mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
        mLocationClient.registerLocationListener(new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                if (bdLocation == null || mMapView == null) {
                    return;
                }
                MyAppLocation.Location = bdLocation;
                LogU.e(bdLocation.getLatitude() + "---" + bdLocation.getLongitude());
                MyLocationData myLocationData = new MyLocationData.Builder()
                        .accuracy(bdLocation.getRadius())//获取定位半径
                        .direction(100)
                        .latitude(bdLocation.getLatitude())
                        .longitude(bdLocation.getLongitude())
                        .build();
                mBaiduMap.setMyLocationData(myLocationData);
                MapActivity.bdLocation = bdLocation;
                if (isFirstLoc) {
                    isFirstLoc = false;
                    //返回当前初始位置
                    sendLocation = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
                    backLocation(bdLocation);
                }
            }
        });
        initLocation();
        mLocationClient.start();
    }

    //清除覆盖物
    public void clearOverlay() {
        if (mBaiduMap != null)
            mBaiduMap.clear();
        overlay = null;
    }

    //添加覆盖物
    private void addoverlay(final LatLng latLng) {
        clearOverlay();
        if (coordinate != null) {
            line(latLng);
            ooText = new TextOptions().bgColor(0xAAFFFF00)
                    .align(TextOptions.ALIGN_LEFT, TextOptions.ALIGN_BOTTOM)
                    .fontSize(30).fontColor(0xFFFF00FF).text("     " + coordinate.getDriverName() + "/\n" + coordinate.getDriverMobile())
                    .position(latLng);


            mBaiduMap.addOverlay(ooText);

            bdicon = BitmapDescriptorFactory
                    .fromResource(R.mipmap.icon_meitu_2);
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(latLng)//坐标点
                    .icon(bdicon)
                    .zIndex(9)//缩放比例
                    .draggable(false);//是否可以拖拽
            markerOptions.animateType(MarkerOptions.MarkerAnimateType.none);//设置进入的动画类型
            overlay = mBaiduMap.addOverlay(markerOptions);


        }
    }

    //返回定位
    private void backLocation(BDLocation bdLocation) {
        ll = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(ll).zoom(15);

        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
    }

    //设置定位服务所需要的服务
    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        option.setScanSpan(0);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        // option.setLocationMode(LocationClientOption.LocationMode.Battery_Saving);//低功耗模式开启定位
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(false);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(false);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(false);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        closeProgressDialog();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_backLocation:
                backLocation(bdLocation);
                break;
            case R.id.iv_backlocation:
                backLocation(bdLocation);
                break;
            case R.id.btn_DrivingRouteResult:
                if (coordinate != null && coordinate.getCoordinate() != null)
                    line(new LatLng(Double.valueOf(coordinate.getCoordinate()[0]), Double.valueOf(coordinate.getCoordinate()[1])));
                else
                    Tos("无法获取当前司机的位置");
                break;
        }
    }

    @Override
    public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {

    }

    @Override
    public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {

    }

    @Override
    public void onGetDrivingRouteResult(DrivingRouteResult result) {
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(MapActivity.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
        }
        if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
            // 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
            // result.getSuggestAddrInfo()
            return;
        }
        //重写getstartmarker方法改变线路规划条图标
        DrivingRouteOverlay overlay = new DrivingRouteOverlay(mBaiduMap) {
            @Override
            public BitmapDescriptor getStartMarker() {
                return bdicon = BitmapDescriptorFactory.fromResource(R.mipmap.icon_meitu_2);
            }
        };
        //把时间传递类OverLay
        mBaiduMap.setOnMarkerClickListener(overlay);
        try {
            overlay.setData(result.getRouteLines().get(0));
            overlay.addToMap();
            overlay.zoomToSpan();
        } catch (Exception e) {

        }

    }

    @Override
    public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {

    }
}
