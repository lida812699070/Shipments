package com.example.ok.shipments.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ok.shipments.MyAppLocation;
import com.example.ok.shipments.R;
import com.example.ok.shipments.adapter.ItemAddressinfoAdapter;
import com.example.ok.shipments.fragment.FragmentNewDocumentFragment;
import com.example.ok.shipments.fragment.FragmentShipFragment;
import com.example.ok.shipments.model.AddressBook;
import com.example.ok.shipments.urls.Urls;
import com.example.ok.shipments.utils.AddressBookXmlParser;
import com.example.ok.shipments.utils.CallBackString;
import com.example.ok.shipments.utils.LogU;
import com.example.ok.shipments.utils.RetruenUtils;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.http.RequestParams;

import java.util.ArrayList;
import java.util.List;
//选择地址界面
public class SelectAddressActivity extends BaseActivity implements View.OnClickListener {

    public static final String ACTION = "com.example.ok.shipments.Activity.SelectAddressActivity.refreshdata";
    private PullToRefreshListView lv;
    public ArrayList<AddressBook> books;
    private ItemAddressinfoAdapter<AddressBook> adapter;
    public static int isSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_address);
        initToolbar();
        isSend = getIntent().getIntExtra("IsSend", 0);
        findViewById();
        initData();
        registRecive();
    }

    private void registRecive() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION);
        registerReceiver(myReceiver, filter);
    }

    private BroadcastReceiver myReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (books != null) {
                    books.clear();
                }
                initData();
            } catch (Exception e) {

            }
        }
    };

    @Override
    protected void onDestroy() {
        unregisterReceiver(myReceiver);
        super.onDestroy();
    }

    private void initToolbar() {
        Toolbar toolBar = getToolBar();
        toolBar.setNavigationIcon(R.drawable.ic_flipper_head_back);
        toolBar.setTitle("选择地址");
        toolBar.setTitleTextColor(Color.WHITE);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolBar.inflateMenu(R.menu.menu_add);
        toolBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                addNewAddress();
                return true;
            }
        });

    }

    private int page = 1;

    public void initData() {
        RequestParams params = new RequestParams();
        params.addBodyParameter("sessionId", MyAppLocation.login.getSessionId());
        params.addBodyParameter("userId", MyAppLocation.login.getUserId());
        params.addBodyParameter("clientName", MyAppLocation.login.getClientName());
        params.addBodyParameter("pageNumber", page + "");
        params.addBodyParameter("pageSize", 10 + "");
        params.addBodyParameter("searchData", "");
        getData(Urls.Query, params, new CallBackString() {
            @Override
            public void httFinsh(String data) {
                LogU.e(data);
                lv.onRefreshComplete();
                AddressBookXmlParser parser = RetruenUtils.getReturnInfo(data, new AddressBookXmlParser());
                if (parser != null) {
                    List<AddressBook> list = parser.getList();
                    LogU.e(list.toString());
                    books.addAll(list);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void findViewById() {
        lv = (PullToRefreshListView) findViewById(R.id.lv_selectAddress);
        lv.setMode(PullToRefreshBase.Mode.BOTH);
        books = new ArrayList<>();
        adapter = new ItemAddressinfoAdapter<>(this, books);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                position = position - 1;
                if (isSend == FragmentNewDocumentFragment.SEND) {
                    MyAppLocation.shipBook = books.get(position);
                } else if (isSend == FragmentNewDocumentFragment.GET) {
                    MyAppLocation.getBook = books.get(position);
                } else if (isSend == FragmentShipFragment.SHIP) {
                    MyAppLocation.shipBook = books.get(position);
                }
                finish();//此处一定要调用finish()方法
            }
        });
        lv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                page = 1;
                books.clear();
                initData();

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                page++;
                initData();
            }
        });
    }

    public void addNewAddress() {
        startActivity(new Intent(this, AddAddressActivity.class));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.head_flip:
                finish();
                break;
        }
    }
}
