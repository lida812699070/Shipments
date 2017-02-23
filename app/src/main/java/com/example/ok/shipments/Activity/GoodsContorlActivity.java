package com.example.ok.shipments.Activity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ok.shipments.MyAppLocation;
import com.example.ok.shipments.R;
import com.example.ok.shipments.model.DeliveryControlQuery;
import com.example.ok.shipments.urls.Urls;
import com.example.ok.shipments.utils.BitmapUtils;
import com.example.ok.shipments.utils.CallBackString;
import com.example.ok.shipments.utils.ReadImgToBinary2;
import com.lidroid.xutils.http.RequestParams;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

import butterknife.Bind;
import butterknife.ButterKnife;

public class GoodsContorlActivity extends BaseActivity implements View.OnClickListener {


    private static final int SELECT_CAMER = 1;
    private static final int SELECT_PICTURE = 2;
    @Bind(R.id.documentNumber)
    TextView mDocumentNumber;
    @Bind(R.id.good_contorl_toCity)
    TextView mGoodContorlToCity;
    @Bind(R.id.good_contorl_consignee)
    TextView mGoodContorlConsignee;
    @Bind(R.id.good_contorl_tel)
    TextView mGoodContorlTel;
    @Bind(R.id.good_contorl_shipper)
    TextView mGoodContorlShipper;
    @Bind(R.id.good_contorl_qty)
    TextView mGoodContorlQty;
    @Bind(R.id.good_contorl_vol)
    TextView mGoodContorlVol;
    @Bind(R.id.good_contorl_address)
    TextView mGoodContorlAddress;
    @Bind(R.id.good_contorl_btn)
    Button mGoodContorlBtn;
    @Bind(R.id.et_content)
    EditText mEtContent;
    @Bind(R.id.btn_upload_image)
    Button mBtnUploadImage;
    @Bind(R.id.iv_upload_image)
    ImageView mIvUploadImage;
    private DeliveryControlQuery mEntity;
    public static final String mFilePath = "sdcard/upload.jpg";
    private File myCaptureFile;
    private PopupWindow popupWindow;
    private String imgToBase64;
    private Bitmap mBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_contorl);
        ButterKnife.bind(this);
        mEntity = (DeliveryControlQuery) getIntent().getSerializableExtra("data");
        initBackActionbar("物控详情页");
        bindData();
    }

    private int mDegree = 90;

    public void rotate(View view) {
        if (mBitmap == null) {
            Toast.makeText(GoodsContorlActivity.this, "您还没有拍摄签收图", Toast.LENGTH_SHORT).show();
            return;
        }
        mBitmap = rotateBitmap(this, mBitmap, mDegree);
        saveBitmap2file(mBitmap, mFilePath);
        imgToBase64 = ReadImgToBinary2.imgToBase64(GoodsContorlActivity.mFilePath, mBitmap, "");
        mIvUploadImage.setImageBitmap(mBitmap);
    }

    //保存成本地实例化文件
    static boolean saveBitmap2file(Bitmap bmp, String filename) {
        Bitmap.CompressFormat format = Bitmap.CompressFormat.JPEG;
        int quality = 50;
        OutputStream stream = null;
        try {
            stream = new FileOutputStream(filename);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return bmp.compress(format, quality, stream);
    }

    private Bitmap rotateBitmap(Context context, Bitmap bitmap, float degree) {
        Matrix matrix = new Matrix();
        matrix.reset();
        matrix.setRotate(degree, bitmap.getWidth() / 2, bitmap.getHeight() / 2);
        Bitmap bitmapr = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return bitmapr;
    }

    private void bindData() {
        if (mEntity != null) {
            mDocumentNumber.setText(mEntity.getDocumentNumber());
            mGoodContorlToCity.setText(mEntity.getToCity());
            mGoodContorlConsignee.setText(mEntity.getConsigneePerson());
            mGoodContorlTel.setText(mEntity.getConsigneeTel());
            mGoodContorlShipper.setText(mEntity.getShipperName());
            mGoodContorlQty.setText("件数：" + mEntity.getCargoQty());
            mGoodContorlVol.setText("重量：" + mEntity.getWeight());
            mGoodContorlAddress.setText(mEntity.getConsigneeAddress());
        }

        mGoodContorlBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBitmap == null) {
                    Tos("请选择图片");
                    return;
                }
                RequestParams params = new RequestParams();
                params.addBodyParameter("sessionId", MyAppLocation.login.getSessionId());
                params.addBodyParameter("userId", MyAppLocation.login.getUserId());
                params.addBodyParameter("clientName", MyAppLocation.login.getClientName());
                params.addBodyParameter("stowageItemId", mEntity.getStowageItemId());
                params.addBodyParameter("isReapply", "true");
                params.addBodyParameter("applyComment", mEtContent.getText().toString());//备注
                params.addBodyParameter("applyAttachment", imgToBase64);
                getData(Urls.DeliveryControlApply, params, new CallBackString() {
                    @Override
                    public void httFinsh(String data) {
                        Tos("上传成功");
                        finish();
                    }
                });
            }
        });
        mIvUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBitmap == null) {

                    return;
                }
                ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(GoodsContorlActivity.this, v, GoodsContorlActivity.this.getString(R.string.image));
                Intent intent = new Intent(GoodsContorlActivity.this, ShowPicActivity.class);
                intent.putExtra("path", mFilePath);
                ActivityCompat.startActivity(GoodsContorlActivity.this, intent, compat.toBundle());
                /*Intent intent = new Intent(GoodsContorlActivity.this, ShowPicActivity.class);
                intent.putExtra("path",mFilePath);
                startActivity(intent);*/
            }
        });
        mBtnUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopWindow();
            }
        });
    }

    private void showPopWindow() {
        if (popupWindow == null) {
            initPopWindow();
        }
        //popupWindow.showAsDropDown(btShow);

        popupWindow.showAtLocation(mGoodContorlBtn, Gravity.BOTTOM, 0, 0);
    }

    private void initPopWindow() {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_popwindow_select_pic, null);
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

    private void selectIconBySd() {
        try {
            //检查权限
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//如果没有权限就去申请  回调在onRequestPermissionsResult方法中
                ActivityCompat.requestPermissions(GoodsContorlActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);//1是回调码switch中判断
            } else {
//如果有权限开始操作
                selectPic();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void selectPic() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            //使用下面的intent有些手机返回data为null
            Intent picture = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(picture, SELECT_PICTURE);
            /*  如果是6.0的手机   回去查询最近的手机使用照片  这样返回url和我们用cusro的url不一样  就会找不到
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image*//*");
            startActivityForResult(Intent.createChooser(intent, "选择图片"), SELECT_PICTURE);*/
        } else {
            Toast.makeText(this, "请确认已经插入SD卡", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
//是否同意了权限
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//同意
                    selectPic();
                } else {
//拒绝 已经拒绝过也走这边
                    Toast.makeText(this, "拒绝了权限", 1).show();
                }
                break;
            case 2:
                if (grantResults.length == 2) {
//同意
                    cameraGetPic();
                } else {
//拒绝 已经拒绝过也走这边
                    Toast.makeText(this, "请同意相关权限避免以后功能出现问题", 1).show();
                }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void tackPic() {
        try {
            //检查权限
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//如果没有权限就去申请  回调在onRequestPermissionsResult方法中
                ActivityCompat.requestPermissions(GoodsContorlActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 2);//1是回调码switch中判断
            } else {
//如果有权限开始操作
                cameraGetPic();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void cameraGetPic() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            myCaptureFile = new File(mFilePath);
            Uri uri = Uri.fromFile(myCaptureFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            startActivityForResult(intent, SELECT_CAMER);
        } else {
            Toast.makeText(this, "请确认已经插入SD卡", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_select_sdcard:

                selectIconBySd();
                break;
            case R.id.btn_tack_pic:

                tackPic();
                break;
        }
    }

    //通过uri获取path
    public static String getRealFilePath(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                //选择图片
                Uri uri = data.getData();
                String realFilePath = getRealFilePath(this, uri);
                //压缩图片  二次采样
                if (mBitmap != null) {
                    mBitmap.recycle();
                }
                mBitmap = BitmapUtils.getBitmap(realFilePath, 600, 800);
                BitmapUtils.saveFile(mBitmap, mFilePath);
                imgToBase64 = ReadImgToBinary2.imgToBase64(mFilePath, mBitmap, "");
                mIvUploadImage.setImageBitmap(mBitmap);
            } else if (requestCode == SELECT_CAMER) {
                if (mBitmap != null) {
                    mBitmap.recycle();
                }
                //拍照 压缩保存
                mBitmap = BitmapUtils.getBitmap(mFilePath, 600, 800);
                BitmapUtils.saveFile(mBitmap, mFilePath);
                imgToBase64 = ReadImgToBinary2.imgToBase64(mFilePath, mBitmap, "");
                mIvUploadImage.setImageBitmap(mBitmap);
            }

        }

    }

    @Override
    protected void onDestroy() {
        if (mBitmap != null) {
            mBitmap.recycle();
        }
        super.onDestroy();
    }
}
