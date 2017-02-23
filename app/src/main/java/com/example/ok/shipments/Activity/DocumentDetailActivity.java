package com.example.ok.shipments.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ok.shipments.adapter.ItemSimple2Adapter;
import com.example.ok.shipments.adapter.PicPagerAdapter;
import com.example.ok.shipments.model.DocumentPicture;
import com.example.ok.shipments.model.ShippingTraceData;
import com.example.ok.shipments.model.ShippingTraceItem;
import com.example.ok.shipments.soapf.Infos;
import com.example.ok.shipments.MyAppLocation;
import com.example.ok.shipments.R;
import com.example.ok.shipments.model.Login;
import com.example.ok.shipments.soapf.SoapAction;
import com.example.ok.shipments.soapf.SoapEndpoint;
import com.example.ok.shipments.urls.Urls;
import com.example.ok.shipments.utils.CallBackString;
import com.example.ok.shipments.utils.DocumentPictureXmlparser;
import com.example.ok.shipments.utils.LogU;
import com.example.ok.shipments.utils.MySoapHttpRequest;
import com.example.ok.shipments.utils.OnClickutils;
import com.example.ok.shipments.utils.ReadImgToBinary2;
import com.example.ok.shipments.utils.RetrueCodeHandler;
import com.example.ok.shipments.utils.RetruenUtils;
import com.example.ok.shipments.utils.ShippingTraceDataXmlparser;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.ArrayList;

public class DocumentDetailActivity extends BaseActivity {

    private String documentNumber;
    private TextView tvDocumentNum;
    private ImageView ivLookPic;
    private ShippingTraceDataXmlparser xmlparser;
    private ShippingTraceData traceData;
    private ListView lv;
    private ArrayList<ShippingTraceItem> items;
    private ItemSimple2Adapter<ShippingTraceItem> adapter;
    private ArrayList<Bitmap> bitmaps;
    private ViewPager vpPic;
    private TextView tv_picture;
    private Button btnPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document_detail);
        setTitle("订单详情");
        documentNumber = getIntent().getStringExtra("documentNumber");
        findViewById();
        setListener();
        initData();
    }

    private void initData() {
        showProgressDialog();
        final String endPoint = SoapEndpoint.SET_IS_SIGNUP;
        final String soapAction = SoapAction.QueryTraceData;
        final Login loginInfo = MyAppLocation.login;
        String info = Infos.QueryTraceData;
        info = info.replace("userIdValue", loginInfo.getUserId() + "");
        info = info.replace("clientNameValue", loginInfo.getClientName());
        info = info.replace("sessionIdValue", loginInfo.getSessionId());
        info = info.replace("stringValue", documentNumber);
        final String finalInfo = info;
        MySoapHttpRequest.getString(new CallBackString() {
            @Override
            public void httFinsh(final String data) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //LogU.e(data);
                        RetrueCodeHandler returnInfo = RetruenUtils.getReturnInfo(data, new RetrueCodeHandler());
                        if (returnInfo != null && returnInfo.getString().equals("0")) {
                            xmlparser = RetruenUtils.getReturnInfo(data, new ShippingTraceDataXmlparser());
                            if (xmlparser != null) {
                                traceData = xmlparser.getUser();
                                tvDocumentNum.setText("订单号：" + traceData.getDocumentNumber());
                                items.addAll(traceData.getShippingMessage().getList());
                                adapter.notifyDataSetChanged();
                                LogU.e(traceData.toString());
                            } else {
                                Tos("解析错误");
                            }
                        } else if (returnInfo != null && returnInfo.equals("-1")) {
                            Tos(returnInfo.getError());
                        } else {
                            Tos("数据获取错误");
                        }
                        closeProgressDialog();
                    }
                });
            }
        }, endPoint, soapAction, finalInfo);
    }


    private void setListener() {

    }

    private void findViewById() {
        tvDocumentNum = (TextView) findViewById(R.id.tvDocumentNum);
        tv_picture = (TextView) findViewById(R.id.tv_picture);
        vpPic = (ViewPager) findViewById(R.id.vp_pic);
        lv = (ListView) findViewById(R.id.lv_SpMessage);
        items = new ArrayList<>();
        adapter = new ItemSimple2Adapter<>(this, items);
        lv.setAdapter(adapter);
        btnPay = (Button) findViewById(R.id.btn_activity_document_detail_pay);
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (OnClickutils.isFastDoubleClick())
                    return;
                startActivity(new Intent(DocumentDetailActivity.this,WXPayActivity.class));
            }
        });

    }

    private boolean isVisable = false;

    public void lookPic(View view) {
        Intent intent = new Intent(DocumentDetailActivity.this,ImageDetailActivity.class);
        intent.putExtra("document",documentNumber);
        startActivity(intent);
    }

    public static Bitmap base64ToBitmap(String base64Data) {
        byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    public Bitmap stringtoBitmap(String string) {
        //将字符串转换成Bitmap类型
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(string, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmap;
    }


}
