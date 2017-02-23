package com.example.ok.shipments.Activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.TextOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
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
import com.example.ok.shipments.adapter.ItemLbslocationLvAdapter;
import com.example.ok.shipments.map.DrivingRouteOverlay;
import com.example.ok.shipments.model.BastLocationJson;
import com.example.ok.shipments.utils.DialoginOkCallBack;
import com.example.ok.shipments.utils.LocationXmlParser;
import com.example.ok.shipments.utils.LogU;
import com.example.ok.shipments.utils.PermissionUtil;
import com.example.ok.shipments.utils.RetruenUtils;
import com.example.ok.shipments.utils.SetMarginUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GetLocationActivity extends BaseActivity implements View.OnClickListener, OnGetRoutePlanResultListener, AdapterView.OnItemClickListener {

    private static final String TAG = "TAG";
    private static final int REQUEST_CONTACTS = 1;
    private static final String[] PERMISSIONS_CONTACT = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_PHONE_STATE,
    };
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private LocationClient mLocationClient;
    private LatLng sendLocation;
    private Marker overlay;
    private BitmapDescriptor bdicon;
    private LatLng ll;
    public static BDLocation bdLocation;
    private Button btnBackLocation;
    private Button btnGetSendLoacation;
    private RoutePlanSearch routePlanSearch;
    private MarkerOptions markerOptions;
    private OverlayOptions ooText;
    private InfoWindow mInfoWindow;
    private EditText etAddressStr;
    private Button btnAddressStr;
    private boolean showLbsLocationFlag;
    private ListView lv;
    private Button btnBack;
    private LocationXmlParser parser;
    private Point mCenterPoint;
    private ImageView mImageViewPointer;
    private ImageView iv_mapCenter;
    private ImageView ivBackLocation;
    private ArrayList<String> strings;
    private RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_location);
        initBackActionbar("地址录入");
        Tos("默认当前定位");
        mMapView = (MapView) findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
        if (Build.VERSION.SDK_INT >= 23) {
            showContacts(mMapView);
        } else {
            init();
        }
        //checkPermission(REQUEST_CONTACTS,Manifest.permission.ACCESS_FINE_LOCATION);
    }

    protected void checkPermission(int id, String permission) {
        //版本判断
        if (Build.VERSION.SDK_INT >= 23) {
            //减少是否拥有权限
            int checkPermissionResult = this.checkSelfPermission(permission);
            if (checkPermissionResult != PackageManager.PERMISSION_GRANTED) {
                //弹出对话框接收权限
                requestPermissions(new String[]{permission}, id);
                return;
            } else {
                //获取到权限
                init();
            }
        } else {
            init();
            //获取到权限
        }
    }

    /*@Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //获取到权限
                init();

            } else {
                //没有获取到权限
            }
        }
    }*/
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
                    ActivityCompat.requestPermissions(GetLocationActivity.this, PERMISSIONS_CONTACT, REQUEST_CONTACTS);
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


            }


        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void init() {

        findViewById();
        setLocationMap();
        setListener();
        initListener();
    }

    private void initListener() {
        mBaiduMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {
            @Override
            public void onMapStatusChangeStart(MapStatus status) {
                // updateMapState(status);
                if (overlay != null) {
                    clearOverlay();
                }
                iv_mapCenter.setVisibility(View.VISIBLE);
                //测量高度方法必须放在onresum后面。不然都为0
                int height = iv_mapCenter.getHeight();
                int toolbarHeight = getToolBar().getHeight();
                SetMarginUtils.setMargins(iv_mapCenter, 0, 2 * height + toolbarHeight, 0, 0);
            }

            @Override
            public void onMapStatusChangeFinish(MapStatus status) {
                updateMapState(status);
                Tos("选好后可以点击确定拾取点");
                iv_mapCenter.setVisibility(View.GONE);

            }

            @Override
            public void onMapStatusChange(MapStatus status) {
                // updateMapState(status);
            }
        });
    }

    private void updateMapState(MapStatus status) {
        LatLng mCenterLatLng = status.target;
        /**获取经纬度*/
        double lat = mCenterLatLng.latitude;
        double lng = mCenterLatLng.longitude;
        addoverlay(new LatLng(lat, lng));
    }

    private void findViewById() {
        btnBackLocation = (Button) findViewById(R.id.btn_backLocation);
        btnBack = (Button) findViewById(R.id.btn_finish);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnGetSendLoacation = (Button) findViewById(R.id.btn_Location);
        btnBackLocation.setOnClickListener(this);
        btnGetSendLoacation.setOnClickListener(this);
        iv_mapCenter = (ImageView) findViewById(R.id.iv_mapCenter);

        relativeLayout = (RelativeLayout) findViewById(R.id.rl_getlocaton);
        etAddressStr = (EditText) findViewById(R.id.et_addressStr);
        etAddressStr.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                btnAddressStr.performClick();
            }
        });
        btnAddressStr = (Button) findViewById(R.id.btn_LocationStr);
        ivBackLocation = (ImageView) findViewById(R.id.iv_backlocation);
        ivBackLocation.setOnClickListener(this);
        lv = (ListView) findViewById(R.id.lv_LBS_locations);
        lv.setOnItemClickListener(this);
        btnAddressStr.setOnClickListener(this);
    }

    /*private void initCenterImg() {
        ImageView imageView = new ImageView(this);
        imageView.setImageDrawable(getDrawable(R.drawable.icon_marka));
        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        ViewGroup.LayoutParams para;
        para = imageView.getLayoutParams();
        WindowManager wm = (WindowManager) this
                .getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        para.height = height/2;
        para.width = width/2;
        imageView.setLayoutParams(para);
        relativeLayout.addView(imageView);
    }*/

    private void setListener() {
       /* mBaiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                sendLocation = latLng;
                addoverlay(latLng);
            }

            @Override
            public boolean onMapPoiClick(MapPoi mapPoi) {
                return true;
            }
        });*/
    }

    private String info = "请稍等...";

    private void showWinInfo(LatLng ll) {

        getAddress(ll);
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
                GetLocationActivity.bdLocation = bdLocation;
                MapActivity.bdLocation = bdLocation;
                MyAppLocation.Location = bdLocation;
                LogU.e(bdLocation.getLatitude() + "---" + bdLocation.getLongitude());
                MyLocationData myLocationData = new MyLocationData.Builder()
                        .accuracy(bdLocation.getRadius())//获取定位半径
                        .direction(100)
                        .latitude(bdLocation.getLatitude())
                        .longitude(bdLocation.getLongitude())
                        .build();
                mBaiduMap.setMyLocationData(myLocationData);
                if (isFirstLoc) {
                    isFirstLoc = false;
                    //返回当前初始位置
                    sendLocation = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
                    backLocation(bdLocation);
                    if (MyAppLocation.latLng == null) {
                        addoverlay(new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude()));
                    } else {
                        addoverlay(MyAppLocation.latLng);
                    }
                }
            }
        });
        initLocation();
        mLocationClient.start();
    }

    //线路规划
    public void line() {
        try {
            routePlanSearch = RoutePlanSearch.newInstance();
            routePlanSearch.setOnGetRoutePlanResultListener(this);
            DrivingRoutePlanOption option = new DrivingRoutePlanOption();
            if (bdLocation == null) {
                Toast.makeText(GetLocationActivity.this, "还未定位好，请在次点击", Toast.LENGTH_SHORT).show();
                return;
            }
            PlanNode from = PlanNode.withLocation(new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude()));
            PlanNode to = PlanNode.withLocation(sendLocation);
            option.from(from);
            option.to(to);
            option.policy(DrivingRoutePlanOption.DrivingPolicy.ECAR_DIS_FIRST);
            routePlanSearch.drivingSearch(option);
        } catch (Exception e) {
            Toast.makeText(GetLocationActivity.this, "地址错误无法定位", Toast.LENGTH_SHORT).show();
            finish();
        }
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
        bdicon = BitmapDescriptorFactory
                .fromResource(R.drawable.icon_marka);
        markerOptions = new MarkerOptions()
                .position(latLng)//坐标点
                .icon(bdicon)
                .zIndex(9)//缩放比例
                .draggable(true);
        markerOptions.animateType(MarkerOptions.MarkerAnimateType.none);//设置进入的动画类型
        showWinInfo(latLng);
        overlay = (Marker) mBaiduMap.addOverlay(markerOptions);
    }

    private void getAddress(final LatLng latLng) {
        String url = "http://api.map.baidu.com/geocoder/v2/?ak=sfuQIH09bvPcDELFp0vIkyQ0nGRd07Tw&callback=renderReverse&" +
                "location=" + latLng.latitude + "," + latLng.longitude + "&output=xml&pois=1";
        MyAppLocation.mHttpUtils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                parser = RetruenUtils.getReturnInfo(responseInfo.result, new LocationXmlParser());
                assert parser != null;
                {
                    info = parser.getAddress();
                    MyAppLocation.province = parser.getProvince();
                    MyAppLocation.city = parser.getCity();
                    MyAppLocation.district = parser.getDistrict();
                    MyAppLocation.street = parser.getStreet();
                }
                if (TextUtils.isEmpty(info)) {
                    info = "请等待...";
                }
                ooText = new TextOptions().bgColor(0xAAFFFF00).align(50, 50)
                        .fontSize(30).fontColor(0xFFFF00FF).text(info)
                        .position(latLng);
                mBaiduMap.addOverlay(ooText);
            }

            @Override
            public void onFailure(HttpException error, String msg) {

            }
        });
    }

    //返回定位
    private void backLocation(BDLocation bdLocation) {
        ll = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(ll).zoom(20);
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
            case R.id.btn_LocationStr:
                getBestLocaltion(etAddressStr.getText().toString().trim());
                break;
            case R.id.btn_Location:
                if (sendLocation != null) {
                    MyAppLocation.latLng = markerOptions.getPosition();
                    MyAppLocation.address = info;
                    Tos("获取位置信息成功");
                    //finish();
                }
                Intent resultIntent = new Intent();
                Bundle bundle = new Bundle();
                if (markerOptions == null) {
                    Tos("地图正在加载中。。。");
                    return;
                }
                bundle.putString("latLng", markerOptions.getPosition().latitude + "");
                bundle.putString("longitude", markerOptions.getPosition().longitude + "");
                bundle.putString("Province", parser.getProvince());
                bundle.putString("City", parser.getCity());
                bundle.putString("District", parser.getDistrict());
                bundle.putString("Street", parser.getStreet());
                resultIntent.putExtra("data", bundle);
                setResult(RESULT_OK, resultIntent); //intent为A传来的带有Bundle的intent，当然也可以自己定义新的Bundle
                finish();//此处一定要调用finish()方法
                break;
        }
    }


    private List<BastLocationJson> list;

    private void getBestLocaltion(String shipperStr) {
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
                                    Toast.makeText(GetLocationActivity.this, "无法找到目的地点", Toast.LENGTH_SHORT).show();
                                    lv.setVisibility(View.GONE);
                                    showLbsLocationFlag = false;
                                    return;
                                }
                                strings = new ArrayList<>();
                                for (int i = 0; i < list.size(); i++) {
                                    strings.add(list.get(i).getName());
                                }
                                ItemLbslocationLvAdapter<String> adapter = new ItemLbslocationLvAdapter<>(GetLocationActivity.this, strings);
                                lv.setAdapter(adapter);
                                lv.setVisibility(View.VISIBLE);
                                //line();
                                //LogU.e(list.toString());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(GetLocationActivity.this, "地址不明确无法定位", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        Toast.makeText(GetLocationActivity.this, "无法获取数据", Toast.LENGTH_SHORT).show();
                    }
                });
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
            Toast.makeText(GetLocationActivity.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
        }
        if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
            // 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
            // result.getSuggestAddrInfo()
            return;
        }
        DrivingRouteOverlay overlay = new DrivingRouteOverlay(mBaiduMap);
        //把时间传递类OverLay
        mBaiduMap.setOnMarkerClickListener(overlay);
        overlay.setData(result.getRouteLines().get(0));
        overlay.addToMap();
        overlay.zoomToSpan();
    }

    @Override
    public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        clearOverlay();
        selectIndex(position);
        etAddressStr.setText(strings.get(position));
    }

    private void selectIndex(int position) {
        try {
            BastLocationJson.LocationEntity location = list.get(position).getLocation();
            LatLng latLng = new LatLng(location.getLat(), location.getLng());
            addoverlay(latLng);
            MapStatus.Builder builder = new MapStatus.Builder();
            builder.target(latLng).zoom(18);
            mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            lv.setVisibility(View.GONE);
            showLbsLocationFlag = false;
        } catch (Exception e) {
            Toast.makeText(GetLocationActivity.this, "地址不明确无法定位", Toast.LENGTH_SHORT).show();
        }
    }
}
