package com.example.ok.shipments.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ok.shipments.Activity.GoodsContorlActivity;
import com.example.ok.shipments.MyAppLocation;
import com.example.ok.shipments.R;
import com.example.ok.shipments.model.DeliveryControlQuery;
import com.example.ok.shipments.urls.Urls;
import com.example.ok.shipments.utils.DividerItemDecoration;
import com.example.ok.shipments.utils.DocumentDeliveryControlXmlParser;
import com.example.ok.shipments.utils.LogU;
import com.example.ok.shipments.utils.RetrueCodeHandler;
import com.example.ok.shipments.utils.RetruenUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.adapter.recyclerview.wrapper.EmptyWrapper;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FragmentGoodcontorlFragment extends BaseFragment {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private List<DeliveryControlQuery> mList;
    private CommonAdapter<DeliveryControlQuery> mAdapter;
    private ImageView mIvDown;
    private EditText mEtDocumentNumber;
    private TextView mTvSeacher;
    private LinearLayout mPopwindow;
    private EmptyWrapper mEmptyWrapper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_goodcontorl, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mList = new ArrayList<>();
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initData();
    }


    private void rotation(View view, float deeg) {
        view.animate().rotation(deeg).setDuration(300);
    }

    //弹出窗是否显示
    private boolean isShow = false;

    private void initView(View view) {
        mIvDown = (ImageView) view.findViewById(R.id.ivToolbarDown);
        mIvDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopwindow.setVisibility(isShow ? View.INVISIBLE : View.VISIBLE);
                rotation(mIvDown, isShow ? 0 : 180);
                isShow = !isShow;
            }
        });
        mPopwindow = (LinearLayout) view.findViewById(R.id.popWindow);
        TextView tvWeek = (TextView) mPopwindow.findViewById(R.id.tvPopwindowWeek);
        TextView tvMonth = (TextView) mPopwindow.findViewById(R.id.tvPopwindowMonth);
        TextView tvMost = (TextView) mPopwindow.findViewById(R.id.tvPopwindowMost);
        tvWeek.setOnClickListener(mListener);
        tvMonth.setOnClickListener(mListener);
        tvMost.setOnClickListener(mListener);
        mEtDocumentNumber = (EditText) view.findViewById(R.id.etToolbarSearch);
        mTvSeacher = (TextView) view.findViewById(R.id.btnToolbarSearch);
        mTvSeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String documentNum = mEtDocumentNumber.getText().toString();
                if (!TextUtils.isEmpty(documentNum)) {
                    documentId = documentNum;
                    mList.clear();
                    initData();
                    mAdapter.notifyDataSetChanged();
                }
            }
        });
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rvGoodContorl);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        mAdapter = new CommonAdapter<DeliveryControlQuery>(getActivity(), R.layout.item_goodscontorl, mList) {
            @Override
            protected void convert(ViewHolder holder, final DeliveryControlQuery entity, int position) {
                try {
                    holder.setText(R.id.tvDate, entity.getBillingTime().substring(0, 10));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                holder.setText(R.id.otherDocument, entity.getDocumentNumber());
                holder.setText(R.id.tvStatusDocument, "订单状态：" + entity.getSignupInfo());
                String contorlStat = "";
                if (Boolean.valueOf(entity.getApplyConfirmFlag())){
                    contorlStat+="已申请放货";
                }else {
                    contorlStat+="未申请放货";
                }

                if (Boolean.valueOf(entity.getReleaseControlFlag())){
                    contorlStat+="    已解控";
                }else {
                    contorlStat+="    未解控";
                }
                holder.setText(R.id.tvStatusContorl, "物控状态：" + contorlStat);
                holder.setText(R.id.isContorl, entity.isDeliveryAfterNotify() ? "物控" : "直发");
                holder.setText(R.id.toCity, entity.getToCity());
                holder.setText(R.id.tvConsiginee, entity.getConsigneePerson());
                holder.setText(R.id.tvSendGoodsContent, entity.getCargoName() + "    " + entity.getCargoQty());
            }
        };
        mEmptyWrapper = new EmptyWrapper(mAdapter);
        mEmptyWrapper.setEmptyView(R.layout.listview_empty_view);
        mRecyclerView.setAdapter(mEmptyWrapper);
        mAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Intent intent = new Intent(getActivity(), GoodsContorlActivity.class);
                intent.putExtra("data", mList.get(position));
                startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swGoodsContorl);
        mSwipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.GREEN);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mEtDocumentNumber.setText("");
                documentId = "";
                mList.clear();
                initData();
            }
        });
    }

    private View.OnClickListener mListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tvPopwindowWeek:
                    queryDays = 7l;
                    break;
                case R.id.tvPopwindowMonth:
                    queryDays = 30l;
                    break;
                case R.id.tvPopwindowMost:
                    queryDays = 45l;
                    break;
            }
            mList.clear();
            initData();
            mPopwindow.setVisibility(View.INVISIBLE);
            rotation(mIvDown, 0);
            isShow = false;
            mAdapter.notifyDataSetChanged();
        }
    };

    @Override
    public void setUserVisibleHint(boolean hidden) {
        LogU.e("setUserVisibleHint=" + hidden);
    }

    private long queryDays = 7l;
    private String documentId = "";
    private String beginTime = "";
    private String endTime = "";

    private void initData() {
        mSwipeRefreshLayout.setRefreshing(true);
        Date endDate = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        endTime = sdf.format(endDate);
        beginTime = new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis() - (long) (queryDays * 1000l * 60l * 60l * 24l)));

        String url = Urls.DeliveryControlQuery + "?" +
                "sessionId=" + MyAppLocation.login.getSessionId() +
                "&userId=" + MyAppLocation.login.getUserId() +
                "&clientName=" + MyAppLocation.login.getClientName() +
                "&stowageItemId=" + "-1" +
                "&beginTime=" + beginTime +
                "&endTime=" + endTime +
                "&fromCity=" + "" +
                "&toCity=" + "" +
                "&customerId=" + "-1" +
                "&shipper=" + "" +
                "&consignee=" + "" +
                "&carrierId=" + "-1" +
                "&networkId=" + "-1" +
                "&stowageNumbers=" + "" +
                "&docNumbers=" + documentId +
                "&applyState=" + "Auto" +
                "&applyConfirmState=" + "Auto" +
                "&notifyState=" + "Auto" +
                "&notifyConfirmState=" + "Auto" +
                "&signUpInfo=" + "Auto";
        RequestParams params = new RequestParams();
        params.addBodyParameter("sessionId", MyAppLocation.login.getSessionId());
        params.addBodyParameter("userId", MyAppLocation.login.getUserId());
        params.addBodyParameter("clientName", MyAppLocation.login.getClientName());
        params.addBodyParameter("stowageItemId", documentId);
        params.addBodyParameter("beginTime", beginTime);
        params.addBodyParameter("endTime", endTime);
        params.addBodyParameter("fromCity", "");
        params.addBodyParameter("toCity", "");
        params.addBodyParameter("customerId", "-1");
        params.addBodyParameter("shipper", "");
        params.addBodyParameter("consignee", "");
        params.addBodyParameter("carrierId", "-1");
        params.addBodyParameter("networkId", "-1");
        params.addBodyParameter("stowageNumbers", "");
        params.addBodyParameter("docNumbers", "");
        params.addBodyParameter("applyState", "Auto");
        params.addBodyParameter("applyConfirmState", "Auto");
        params.addBodyParameter("notifyState", "Auto");
        params.addBodyParameter("notifyConfirmState", "Auto");
        params.addBodyParameter("signUpInfo", "Auto");
        MyAppLocation.mHttpUtils.send(HttpRequest.HttpMethod.GET,
                url,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        RetrueCodeHandler parser = RetruenUtils.getReturnInfo(responseInfo.result, new RetrueCodeHandler());
                        if (parser != null && parser.getString().equals("0")) {
                            String data = responseInfo.result;
                            DocumentDeliveryControlXmlParser info = RetruenUtils.getReturnInfo(data, new DocumentDeliveryControlXmlParser());
                            assert info != null;
                            mList.addAll(info.getList());
                            mAdapter.notifyDataSetChanged();
                        } else if (parser != null && parser.getString().equals("-1")) {
                            Toast.makeText(getActivity(), parser.getError(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "数据获取错误", Toast.LENGTH_SHORT).show();
                        }
                        mSwipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                });

    }

    @Override
    public void flash() {

    }
}
