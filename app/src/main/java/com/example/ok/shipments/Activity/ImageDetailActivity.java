package com.example.ok.shipments.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ok.shipments.MyAppLocation;
import com.example.ok.shipments.R;
import com.example.ok.shipments.model.DocumentPicture;
import com.example.ok.shipments.urls.Urls;
import com.example.ok.shipments.utils.DocumentPictureXmlparser;
import com.example.ok.shipments.utils.LogU;
import com.example.ok.shipments.utils.RetruenUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.ArrayList;
//查看签收图界面  需要base64解码
public class ImageDetailActivity extends BaseActivity {

    private ImageView ivPic;
    private ArrayList<Bitmap> bitmaps = new ArrayList<>();
    private int currindex = 1;
    private String document;
    private TextView mTvPicNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);
        initBackActionbar("查看图片");
        Intent intent = getIntent();
        document = intent.getStringExtra("document");

        Button btnNext = (Button) findViewById(R.id.btn_next);
        mTvPicNumber = (TextView) findViewById(R.id.tv_picture_number);
        Button btnUpPage = (Button) findViewById(R.id.btn_upPage);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currindex++;
                getData();

            }
        });
        btnUpPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currindex--;
                getData();
            }
        });
        ivPic = (ImageView) findViewById(R.id.iv_pic);
        getData();
    }


    private void getData() {
        String url = Urls.GetDocumentPicturesByNumber;
        RequestParams params = new RequestParams();
        params.addBodyParameter("sessionId", MyAppLocation.login.getSessionId());
        params.addBodyParameter("userId", MyAppLocation.login.getUserId());
        params.addBodyParameter("clientName", MyAppLocation.login.getClientName());
        params.addBodyParameter("pageNumber", currindex + "");
        params.addBodyParameter("pageSize", "10");
        params.addBodyParameter("documentNumber", document);
        showProgressDialog();
        MyAppLocation.mHttpUtils.send(HttpRequest.HttpMethod.POST,
                url,
                params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        DocumentPictureXmlparser parser = RetruenUtils.getReturnInfo(responseInfo.result, new DocumentPictureXmlparser());
                        if (parser != null) {
                            ArrayList<DocumentPicture> list = parser.getList();
                            bitmaps = new ArrayList<>();
                            for (int i = 0; i < list.size(); i++) {
                                Bitmap bitmap = base64ToBitmap(list.get(i).getIamgeData());
                                bitmaps.add(bitmap);
                            }
                            LogU.e(parser.getRecordCount() + "张");
                            if (bitmaps.size() != 0) {
                                //vpPic.setAdapter(new PicPagerAdapter(bitmaps, DocumentDetailActivity.this));
                                ivPic.setImageBitmap(bitmaps.get(0));
                            } else {
                                Tos("无图片");
                                currindex--;
                            }
                            mTvPicNumber.setText(currindex+"/"+parser.getRecordCount());
                        }
                        closeProgressDialog();
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        Tos(msg);
                        closeProgressDialog();
                    }
                });
    }

    public static Bitmap base64ToBitmap(String base64Data) {
        byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }


}
