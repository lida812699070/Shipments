package com.example.ok.shipments.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.ok.shipments.R;
import com.example.ok.shipments.fragment.BaseFragment;
import com.example.ok.shipments.fragment.FragemntDocumentFragment;
import com.example.ok.shipments.fragment.FragmentAboutFragment;
import com.example.ok.shipments.fragment.FragmentSettingFragment;
import com.example.ok.shipments.fragment.FragmentShipFragment;
import com.example.ok.shipments.utils.LogU;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
//主界面  分为四个Fragment
public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";
    private static final int REQUEST_CONTACTS = 1;
    private FrameLayout mainFlContent;
    private RadioGroup grMain;
    private RadioButton homeRbMap;
    private RadioButton homeRbQuery;
    private RadioButton homeRbSetting;
    private RadioButton homeRbAbout;
    private BaseFragment currFragment;
    private FragmentAboutFragment aboutFragment;
    private FragemntDocumentFragment queryFragment;
    private FragmentSettingFragment settingFragment;
    private FragmentShipFragment shipFragment;
    private HashMap<Integer, BaseFragment> fragments;
    private FragmentShipFragment shipFragment1;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        findViewById();
        initData();
        setlistener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void setlistener() {
        grMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.hide(currFragment);
                //懒加载Fragment  防止刚进来的时候加载太多没必要的接口造成卡顿
                switch (checkedId) {
                    case R.id.home_rb_map:
                        setCurrFragment(0, transaction);
                        break;
                    case R.id.home_rb_query:
                        setCurrFragment(1, transaction);
                        break;
                    case R.id.home_rb_setting:
                        setCurrFragment(2, transaction);

                        break;
                    case R.id.home_rb_about:
                        setCurrFragment(3, transaction);
                        break;
                }
                transaction.show(currFragment);
                transaction.commit();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitBy2Click(); //调用双击退出函数
        }
        return false;
    }

    private static Boolean isExit = false;

    private void exitBy2Click() {
        Timer tExit = null;
        if (isExit == false) {
            isExit = true; // 准备退出
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

        } else {
            finish();
            System.exit(0);
        }
    }

    private void setCurrFragment(int index, FragmentTransaction transaction) {
        BaseFragment fragment = fragments.get(index);
        if (fragment == null) {
            switch (index) {
                case 0:
                    //发货Fragment
                    fragment = new FragmentShipFragment();
                    break;
                case 1:
                    //查询
                    fragment = new FragmentAboutFragment();
                    fragments.put(1, fragment);
                    break;
                case 2:
                    //设置
                    fragment = new FragmentSettingFragment();
                    fragments.put(2, fragment);
                    break;
                case 3:
                    //运单
                    fragment = new FragemntDocumentFragment();
                    fragments.put(3, fragment);
                    break;
            }
            transaction.add(R.id.main_fl_content, fragment);
        }
        currFragment = fragment;
        fragment.flash();
    }

    private void initData() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        shipFragment1 = new FragmentShipFragment();
        transaction.add(R.id.main_fl_content, shipFragment1);
        currFragment = shipFragment1;
        fragments = new HashMap<Integer, BaseFragment>();
        fragments.put(0, shipFragment1);
        fragments.put(1, aboutFragment);
        fragments.put(2, settingFragment);
        fragments.put(3, queryFragment);
        transaction.commit();
    }

    private void findViewById() {
        mainFlContent = (FrameLayout) findViewById(R.id.main_fl_content);
        grMain = (RadioGroup) findViewById(R.id.gr_main);
        homeRbMap = (RadioButton) findViewById(R.id.home_rb_map);
        homeRbQuery = (RadioButton) findViewById(R.id.home_rb_query);
        homeRbSetting = (RadioButton) findViewById(R.id.home_rb_setting);
        homeRbAbout = (RadioButton) findViewById(R.id.home_rb_about);
        grMain.getChildAt(0).performClick();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == Activity.RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    //显示扫描到的内容
                    LogU.e(bundle.getString("result"));
                }
                break;
        }
    }

}
