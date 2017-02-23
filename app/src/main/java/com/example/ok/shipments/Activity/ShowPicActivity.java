package com.example.ok.shipments.Activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.example.ok.shipments.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ShowPicActivity extends AppCompatActivity {

    @Bind(R.id.iv_show_pic)
    ImageView mIvShowPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_pic);
        ButterKnife.bind(this);
        String path = getIntent().getStringExtra("path");
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        mIvShowPic.setImageBitmap(bitmap);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityCompat.finishAfterTransition(this);
    }
}
