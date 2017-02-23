package com.example.ok.shipments.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.ok.shipments.Activity.UpdateCargoInfosActivity;
import com.example.ok.shipments.MyAppLocation;
import com.example.ok.shipments.R;
import com.example.ok.shipments.adapter.ItemAboutLvAdapter;
import com.example.ok.shipments.model.DeliveryRequest;
import com.example.ok.shipments.model.Login;
import com.example.ok.shipments.urls.Urls;
import com.example.ok.shipments.utils.CallBackString;
import com.example.ok.shipments.utils.GetUnfinishRequestXmlParser;
import com.example.ok.shipments.utils.LogU;
import com.example.ok.shipments.utils.RetruenUtils;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.http.RequestParams;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class FragmentAboutFragment extends BaseFragment {

    private Spinner spinner;
    private PullToRefreshListView lvDocument;
    private ArrayAdapter<String> arr_adapter;
    private ArrayList<String> data_list;
    private ArrayList<DeliveryRequest> list;
    private ItemAboutLvAdapter<DeliveryRequest> adapter;
    private SimpleDateFormat format;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_about, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViewById(view);
        initToolbar(view);
        initData();
    }

    private void initToolbar(View view) {
        Toolbar toolBar = getToolBar(view);
        toolBar.inflateMenu(R.menu.menu_aboutfragment);
        toolBar.setTitle("查询");
        toolBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                page = 1;
                switch (item.getItemId()) {
                    case R.id.action_day:
                        startTime = format.format(new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24 - 1000 * 60 * 60 * 2));
                        break;
                    case R.id.action_weekday:
                        startTime = format.format(new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24 * 7 - 1000 * 60 * 60 * 2));
                        break;
                    case R.id.action_monthday:
                        startTime = format.format(new Date(System.currentTimeMillis() - (long) 1000 * (long) 60 * (long) 60 * (long) 24 * (long) 30));
                        break;
                }
                endTime = format.format(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 2));
                list.clear();
                initData();
                return true;
            }
        });
        toolBar.setTitleTextColor(Color.WHITE);
        //((AppCompatActivity) getActivity()).setSupportActionBar(toolBar);
    }

    private int page = 1;
    private int state = -1;
    private String startTime = "";
    private String endTime = "";

    private void initData() {
        String url = Urls.QueryDeliveryRequest;
        RequestParams params = new RequestParams();
        Login login = MyAppLocation.login;
        LogU.e(login.toString());
        params.addBodyParameter("sessionId", login.getSessionId());
        params.addBodyParameter("userId", login.getUserId());
        params.addBodyParameter("clientName", login.getClientName());
        params.addBodyParameter("pageNumber", "" + page);
        params.addBodyParameter("pageSize", "10");
        params.addBodyParameter("sortProperty", "");
        params.addBodyParameter("sortDescending", "false");
        params.addBodyParameter("returnList", "true");
        params.addBodyParameter("requestId", "0");
        params.addBodyParameter("beginRequestTime", startTime);
        params.addBodyParameter("endRequestTime", endTime);
        params.addBodyParameter("requestStatus", state + "");
        params.addBodyParameter("handleState", state + "");
        params.addBodyParameter("scoreState", state + "");
        params.addBodyParameter("shipperInfo", "");
        getData(url, params, new CallBackString() {
            @Override
            public void httFinsh(String data) {
                GetUnfinishRequestXmlParser parser = RetruenUtils.getReturnInfo(data, new GetUnfinishRequestXmlParser());
                if (parser != null) {
                    list.addAll(parser.getList());
                    adapter.notifyDataSetChanged();
                    lvDocument.onRefreshComplete();
                }
            }
        });
    }

    private void findViewById(View view) {

        data_list = new ArrayList<String>();
        data_list.add("24小时内");
        data_list.add("一周内");
        data_list.add("一个月内");
        //适配器
        arr_adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, data_list);
        //设置样式
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner = (Spinner) view.findViewById(R.id.spn_about);
        spinner.setAdapter(arr_adapter);

        lvDocument = (PullToRefreshListView) view.findViewById(R.id.lv_about_content);
        lvDocument.setMode(PullToRefreshBase.Mode.BOTH);
        View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.listview_empty_view, null);
        lvDocument.setEmptyView(inflate);
        list = new ArrayList<>();
        adapter = new ItemAboutLvAdapter<>(getActivity(), list);
        lvDocument.setAdapter(adapter);
        lvDocument.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
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
        lvDocument.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), UpdateCargoInfosActivity.class);
                intent.putExtra("requestId", list.get(position - 1).getRequestId());
                startActivity(intent);
            }
        });
        format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    startTime = format.format(new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24 - 1000 * 60 * 60 * 2));
                } else if (position == 1) {
                    startTime = format.format(new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24 * 7 - 1000 * 60 * 60 * 2));
                } else if (position == 2) {
                    startTime = format.format(new Date(System.currentTimeMillis() - (long) 1000 * (long) 60 * (long) 60 * (long) 24 * (long) 30));
                }
                endTime = format.format(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 2));
                list.clear();
                initData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    @Override
    public void flash() {

    }
}
