
package com.example.ok.shipments.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.ok.shipments.Activity.DocumentDetailActivity;
import com.example.ok.shipments.MyAppLocation;
import com.example.ok.shipments.R;
import com.example.ok.shipments.adapter.ItemQueryLvAdapter;
import com.example.ok.shipments.model.Login;
import com.example.ok.shipments.model.SimpleDocument;
import com.example.ok.shipments.urls.Urls;
import com.example.ok.shipments.utils.FragmentCallBack;
import com.example.ok.shipments.utils.LogU;
import com.example.ok.shipments.utils.RetruenUtils;
import com.example.ok.shipments.utils.SimpleDocumentXmlParser;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class FragmentQueryFragment extends BaseFragment implements View.OnClickListener, FragmentCallBack {

    private PullToRefreshListView lvQuery;
    private Button btnState;
    private Button btnSearch;
    private Button btnTime;
    private FrameLayout flContent;
    private FragmentStateSelect fragmentStateSelect;
    private FragmentTimeSelect fragmentTimeSelect;
    private FragmentSearchSelect fragmentSearchSelect;
    private LinearLayout llContent;
    private Button btnhideLl;
    private String startTime = "";
    private String endTime = "";
    private ArrayList<SimpleDocument> list;
    private ItemQueryLvAdapter<SimpleDocument> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_query, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViewById(view);
    }

    private void findViewById(View view) {
        initView(view);
        initData();
        setListener();
    }

    private void setListener() {
        lvQuery.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                page = 1;
                list.clear();
                initData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                page++;
                initData();
            }
        });
        lvQuery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), DocumentDetailActivity.class);
                intent.putExtra("documentNumber", list.get(position - 1).getDocumentNumber());
                startActivity(intent);
            }
        });
        btnState.setOnClickListener(this);
        btnState.setVisibility(View.GONE);
        btnTime.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
    }

    private int state = -1;
    private int page = 1;
    private String documentNumber = "";

    private void initData() {
        String url = Urls.QueryDocument;
        final Login login = MyAppLocation.login;
        final RequestParams params = new RequestParams();
        params.addBodyParameter("sessionId", login.getSessionId());
        params.addBodyParameter("userId", login.getUserId());
        params.addBodyParameter("clientName", login.getClientName());
        params.addBodyParameter("returnQueryResult", true+"");
        params.addBodyParameter("pageNumber", "" + page);
        params.addBodyParameter("pageSize", "10");
        params.addBodyParameter("sortProperty", "");
        params.addBodyParameter("descend", "true");
        params.addBodyParameter("documentNumber", documentNumber + "");
        params.addBodyParameter("beginTime", startTime);
        params.addBodyParameter("endTime", endTime);
        params.addBodyParameter("checkStatus", state+"");
        params.addBodyParameter("findShipper", "");
        params.addBodyParameter("findConsigneer", "");
        params.addBodyParameter("documentState", "-1");
        params.addBodyParameter("fromCity", "");
        params.addBodyParameter("toCity", "");
        params.addBodyParameter("pickupBy", "");
        params.addBodyParameter("deliveryBy", "");
        params.addBodyParameter("balanceMode", "-1");
        params.addBodyParameter("balanceState", "-1");
        showProgressDialog();
        MyAppLocation.mHttpUtils.send(HttpRequest.HttpMethod.POST,
                url,
                params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        SimpleDocumentXmlParser parser = RetruenUtils.getReturnInfo(responseInfo.result, new SimpleDocumentXmlParser());
                        if (parser!=null){
                            list.addAll(parser.getList());
                            adapter.notifyDataSetChanged();
                            Tos("目前找到"+list.size()+"条记录");
                        }
                        LogU.e(list.toString());
                        closeProgressDialog();
                        lvQuery.onRefreshComplete();
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        Toast(getActivity(), msg);
                        closeProgressDialog();
                        lvQuery.onRefreshComplete();
                    }
                });

    }

    private void initView(View view) {
        lvQuery = (PullToRefreshListView) view.findViewById(R.id.lv_query);
        lvQuery.setMode(PullToRefreshBase.Mode.BOTH);
        list = new ArrayList<>();
        adapter = new ItemQueryLvAdapter<>(getActivity(), list);
        lvQuery.setAdapter(adapter);
        btnState = (Button) view.findViewById(R.id.btn_select_state);
        btnTime = (Button) view.findViewById(R.id.btn_select_time);
        btnhideLl = (Button) view.findViewById(R.id.btn_hidell);
        btnhideLl.setVisibility(View.GONE);
        btnSearch = (Button) view.findViewById(R.id.btn_select_search);
        flContent = (FrameLayout) view.findViewById(R.id.fl_content);
        llContent = (LinearLayout) view.findViewById(R.id.ll_content);
        initFragment();
        btnhideLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llContent.setVisibility(View.GONE);
            }
        });
    }

    private void initFragment() {
        FragmentManager childFragmentManager = getChildFragmentManager();
        FragmentTransaction transaction = childFragmentManager.beginTransaction();
        fragmentStateSelect = new FragmentStateSelect();
        fragmentStateSelect.setCallBack(this);
        fragmentTimeSelect = new FragmentTimeSelect();
        fragmentTimeSelect.setCallBack(this);
        fragmentSearchSelect = new FragmentSearchSelect();
        fragmentSearchSelect.setCallBack(this);
        transaction.add(R.id.fl_content, fragmentStateSelect).hide(fragmentStateSelect);
        transaction.add(R.id.fl_content, fragmentTimeSelect).hide(fragmentTimeSelect);
        transaction.add(R.id.fl_content, fragmentSearchSelect).hide(fragmentSearchSelect);
        transaction.commit();
        llContent.setVisibility(View.GONE);
    }

    private Fragment currFragment = null;

    @Override
    public void onClick(View v) {
        btnhideLl.setVisibility(View.VISIBLE);
        llContent.setVisibility(View.VISIBLE);
        FragmentManager childFragmentManager = getChildFragmentManager();
        FragmentTransaction transaction = childFragmentManager.beginTransaction();
        if (currFragment != null) {
            transaction.hide(currFragment);
        }
        switch (v.getId()) {
            case R.id.btn_select_state:
                currFragment = fragmentStateSelect;
                break;
            case R.id.btn_select_time:
                currFragment = fragmentTimeSelect;
                break;
            case R.id.btn_select_search:
                currFragment = fragmentSearchSelect;
                break;
        }
        transaction.show(currFragment);
        transaction.commit();
    }

    @Override
    public void initData(int state) {
        page = 1;
        documentNumber = "";
        list.clear();
        this.state = state;
        initData();
    }

    @Override
    public void initData(String documentNum) {
        page = 1;
        this.documentNumber = documentNum;
        list.clear();
        initData();
    }

    @Override
    public void initData(String timeStart, String timeEnd) {
        list.clear();
        adapter.notifyDataSetChanged();
        page = 1;
        documentNumber = "";
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            startTime=sdf.format(new Date(Long.valueOf(timeStart)));
            endTime=sdf.format(new Date(Long.valueOf(timeEnd)));
        }catch (Exception e){
            startTime="";
            endTime="";
        }

        initData();
    }

    @Override
    public void onResume() {
        flash();
        super.onResume();
    }

    public void flash() {
        try {

        } catch (Exception e) {

        }

    }
}


