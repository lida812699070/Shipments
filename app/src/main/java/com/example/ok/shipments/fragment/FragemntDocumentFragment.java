package com.example.ok.shipments.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ok.shipments.R;
import com.example.ok.shipments.utils.LogU;

import java.util.ArrayList;
import java.util.List;

public class FragemntDocumentFragment extends BaseFragment {

    private TabLayout mTabLayout;
    private ViewPager vpDocuemnt;
    private List<String> mTitleList = new ArrayList<>();//页卡标题集合
    private List<Fragment> mViewList = new ArrayList<>();//页卡视图集合

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragemnt_document, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initToolbar(view,"运单");
        mTabLayout = (TabLayout) view.findViewById(R.id.tabs);
        vpDocuemnt = (ViewPager) view.findViewById(R.id.vp_docuemnt);
        vpDocuemnt.setOffscreenPageLimit(3);
        init();
    }

    private void init() {
        //添加页卡视图
        mViewList.add(new FragmentQueryByNum());
        mViewList.add(new FragmentQueryFragment());
        mViewList.add(new FragmentGoodcontorlFragment());

        //添加页卡标题
        mTitleList.add("跟踪订单");
        mTitleList.add("查询");
        mTitleList.add("物控");

        mTabLayout.setTabMode(TabLayout.MODE_FIXED);//设置tab模式，当前为系统默认模式
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(0)));//添加tab选项卡
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(1)));
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(2)));
        MyPagerAdapter mAdapter = new MyPagerAdapter(getChildFragmentManager());
        vpDocuemnt.setAdapter(mAdapter);//给ViewPager设置适配器
        mTabLayout.setupWithViewPager(vpDocuemnt);//将TabLayout和ViewPager关联起来。
        mTabLayout.setTabsFromPagerAdapter(mAdapter);//给Tabs设置适配器
    }

    @Override
    public void flash() {

    }

    //ViewPager适配器
    class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            return mViewList.get(position);
        }

        @Override
        public int getCount() {
            return mViewList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitleList.get(position);//页卡标题
        }
    }



}
