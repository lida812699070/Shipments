package com.example.ok.shipments;

import android.app.Application;
import android.content.res.AssetManager;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.baidu.location.Address;
import com.baidu.location.BDLocation;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.model.LatLng;
import com.example.ok.shipments.model.AddressBook;
import com.example.ok.shipments.model.CityModel;
import com.example.ok.shipments.model.FindInProgressRequest;
import com.example.ok.shipments.model.HandlerCoordinate;
import com.example.ok.shipments.model.Login;
import com.example.ok.shipments.model.ProvinceModel;
import com.example.ok.shipments.utils.LogU;
import com.example.ok.shipments.utils.XmlParserHandler;
import com.lidroid.xutils.HttpUtils;
import com.wanjian.cockroach.Cockroach;

import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import cn.jpush.android.api.JPushInterface;


/**
 * Created by OK on 2016/4/22.
 */
public class MyAppLocation extends Application {

    public static AddressBook getBook;
    public static AddressBook shipBook;
    public static boolean isDebug = true;
    public static HandlerCoordinate coordinate;
    public static FindInProgressRequest progressRequest ;
    public static String address ;
    public static String province ;
    public static String city ;
    public static String district ;
    public static String street ;
    public static HttpUtils mHttpUtils;
    public static Login login;
    private static MyAppLocation mApp;
    public static BDLocation Location;
    public static LatLng latLng;
    public static String apkPath;
    private List<ProvinceModel> provinceList;
    private List<CityModel> cityList;

    @Override
    public void onCreate() {
        mApp = this;
        super.onCreate();
        Cockroach.install(new Cockroach.ExceptionHandler() {

            // handlerException内部建议手动try{  你的异常处理逻辑  }catch(Throwable e){ } ，以防handlerException内部再次抛出异常，导致循环调用handlerException

            @Override
            public void handlerException(final Thread thread, final Throwable throwable) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Log.e("AndroidRuntime", "--->CockroachException:" + thread + "<---", throwable);
                            Toast.makeText(MyAppLocation.this, "Exception Happend\n" + thread + "\n" + throwable.toString(), Toast.LENGTH_SHORT).show();
//                        throw new RuntimeException("..."+(i++));
                        } catch (Throwable e) {

                        }
                    }
                });
            }
        });
        JPushInterface.init(this);
        JPushInterface.setDebugMode(isDebug);
        SDKInitializer.initialize(getApplicationContext());
        configUtils();
    }
    public static MyAppLocation getApplication() {
        return mApp;
    }
    public List<ProvinceModel> getProvinceList() {
        if (provinceList == null)
            parserProvince();
        return provinceList;
    }

    public List<CityModel> getCtiyList() {
        if (cityList == null)
            parserProvince();
        return cityList;
    }

    private void parserProvince() {
        AssetManager asset = this.getAssets();
        try {
            InputStream input = asset.open("province_data.xml");
            // 创建一个解析xml的工厂对象
            SAXParserFactory spf = SAXParserFactory.newInstance();
            // 解析xml
            SAXParser parser = spf.newSAXParser();
            XmlParserHandler handler = new XmlParserHandler();
            parser.parse(input, handler);
            input.close();
            provinceList = handler.getDataList();
            cityList = handler.getCityList();
        } catch (Throwable e) {

        }
    }

    protected void configUtils() {

        mHttpUtils = new HttpUtils();//最好整个应用一个实例
//设置线程池数量
        mHttpUtils.configRequestThreadPoolSize(4);

//设置请求重试次数
        mHttpUtils.configRequestRetryCount(3);

//设置响应编码
        mHttpUtils.configResponseTextCharset("utf-8");

//设置请求超时时间
        mHttpUtils.configTimeout(30000);

    }
}
