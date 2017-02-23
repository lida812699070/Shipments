package com.example.ok.shipments.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.ok.shipments.Activity.ImageDetailActivity;
import com.example.ok.shipments.Activity.ScannerActivity;
import com.example.ok.shipments.R;
import com.example.ok.shipments.adapter.ItemSimple2Adapter;
import com.example.ok.shipments.model.ShippingTraceData;
import com.example.ok.shipments.urls.Urls;
import com.example.ok.shipments.utils.CallBackString;
import com.example.ok.shipments.utils.LogU;
import com.example.ok.shipments.utils.RetruenUtils;
import com.example.ok.shipments.utils.ShippingTraceDataXmlparser;
import com.lidroid.xutils.http.RequestParams;

import java.util.ArrayList;

/**
 * Created by ${lida} on 2016/7/12.
 */
public class FragmentQueryByNum extends BaseFragment {

    private static final int SCANNING_REQUEST_CODE = 1;
    private EditText etDocumentNum;
    private ImageButton ivScanner;
    private Button btnQueryDocumentByNum;
    private ShippingTraceDataXmlparser xmlparser;
    private ShippingTraceData traceData;
    private ListView lv;
    private ArrayList<Object> items;
    private ItemSimple2Adapter<Object> adapter;
    private Button btnQueryPicByNum;

    @Override
    public void flash() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_documentnum, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        initListener();
    }

    private void initListener() {
        //点击扫描
        ivScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setClass(getActivity(), ScannerActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                getRootFragment().startActivityForResult(intent, SCANNING_REQUEST_CODE);
            }
        });
        btnQueryDocumentByNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String documentNum = etDocumentNum.getText().toString();
                if (!TextUtils.isEmpty(documentNum)) {

                    queryByNum(documentNum);
                }
            }
        });
    }

    //获取根fragment才可以启动
    private Fragment getRootFragment() {
        Fragment fragment = getParentFragment();
        while (fragment.getParentFragment() != null) {
            fragment = fragment.getParentFragment();
        }
        return fragment;

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void queryByNum(String documentNum) {
        RequestParams params = new RequestParams();
        params.addBodyParameter("securityKey", "c40e6714cefd48caaa0bbe13180c983d");
        params.addBodyParameter("documentNumbers", documentNum);
        getData(Urls.GetTraceDataByDocNumber, params, new CallBackString() {
            @Override
            public void httFinsh(String data) {
                xmlparser = RetruenUtils.getReturnInfo(data, new ShippingTraceDataXmlparser());
                if (xmlparser != null) {
                    traceData = xmlparser.getUser();
                    if (traceData == null || traceData.getShippingMessage() == null || traceData.getShippingMessage().getList() == null) {
                        Tos("订单目前没有相关记录");
                        return;
                    }
                    items.clear();
                    items.addAll(traceData.getShippingMessage().getList());
                    adapter.notifyDataSetChanged();
                    LogU.e(traceData.toString());
                } else {
                    Tos("解析错误");
                }
            }
        });
    }

    private void init(View view) {
        etDocumentNum = (EditText) view.findViewById(R.id.et_documentBynum);
        ivScanner = (ImageButton) view.findViewById(R.id.iv_scanner);
        btnQueryDocumentByNum = (Button) view.findViewById(R.id.btn_queryByNum);
        btnQueryPicByNum = (Button) view.findViewById(R.id.btn_queryPicByNum);
        lv = (ListView) view.findViewById(R.id.lv_fragmentByNum_msg);
        items = new ArrayList<>();
        adapter = new ItemSimple2Adapter<>(getActivity(), items);
        lv.setAdapter(adapter);
        btnQueryPicByNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(etDocumentNum.getText().toString().trim())) {
                    return;
                }
                Intent intent = new Intent(getActivity(),ImageDetailActivity.class);
                intent.putExtra("document",etDocumentNum.getText().toString().trim());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SCANNING_REQUEST_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    //显示扫描到的内容
                    etDocumentNum.setText(bundle.getString("result"));
                    LogU.e(bundle.getString("result"));
                }
                break;
        }
    }

}
