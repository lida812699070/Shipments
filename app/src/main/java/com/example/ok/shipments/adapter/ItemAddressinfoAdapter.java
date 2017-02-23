package com.example.ok.shipments.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ok.shipments.Activity.AddAddressActivity;
import com.example.ok.shipments.Activity.SelectAddressActivity;
import com.example.ok.shipments.MyAppLocation;
import com.example.ok.shipments.R;
import com.example.ok.shipments.fragment.FragmentNewDocumentFragment;
import com.example.ok.shipments.fragment.FragmentShipFragment;
import com.example.ok.shipments.model.AddressBook;
import com.example.ok.shipments.urls.Urls;
import com.example.ok.shipments.utils.CallBackString;
import com.example.ok.shipments.utils.DialogUtils;
import com.example.ok.shipments.utils.DialoginOkCallBack;
import com.example.ok.shipments.utils.RetrueCodeHandler;
import com.example.ok.shipments.utils.RetruenUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

public class ItemAddressinfoAdapter<T> extends BaseAdapter {

    private List<T> objects = new ArrayList<T>();

    private Context context;
    private LayoutInflater layoutInflater;

    public ItemAddressinfoAdapter(Context context, List<T> objects) {
        this.context = context;
        this.objects = objects;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public T getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_addressinfo_list, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews((T) getItem(position), (ViewHolder) convertView.getTag());
        return convertView;
    }

    private void initializeViews(T object, ViewHolder holder) {
        final AddressBook addressBook = (AddressBook) object;
        holder.tvAddress.setText(addressBook.getAddress());
        holder.tvCallNum.setText(addressBook.getMobileNumber());
        holder.tvComName.setText(addressBook.getCompanyName());
        holder.tvName.setText(addressBook.getContactName());
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete(addressBook.getRecordId() + "");
            }
        });
        holder.btnUpdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddAddressActivity.class);
                intent.putExtra("addressBook", addressBook);
                context.startActivity(intent);
            }
        });
        holder.btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SelectAddressActivity.isSend == FragmentNewDocumentFragment.SEND) {
                    MyAppLocation.shipBook = addressBook;
                } else if (SelectAddressActivity.isSend == FragmentNewDocumentFragment.GET) {
                    MyAppLocation.getBook = addressBook;
                } else if (SelectAddressActivity.isSend == FragmentShipFragment.SHIP) {
                    MyAppLocation.shipBook = addressBook;
                }
                Activity context = (Activity) ItemAddressinfoAdapter.this.context;
                context.finish();
            }
        });
    }

    protected class ViewHolder {
        private final Button btnSelect;
        private TextView tvName;
        private TextView tvComName;
        private TextView tvCallNum;
        private TextView tvAddress;
        private Button btnDelete;
        private Button btnUpdata;

        public ViewHolder(View view) {
            tvName = (TextView) view.findViewById(R.id.tvName);
            tvComName = (TextView) view.findViewById(R.id.tvComName);
            tvCallNum = (TextView) view.findViewById(R.id.tvCallNum);
            tvAddress = (TextView) view.findViewById(R.id.tvAddress);
            btnDelete = (Button) view.findViewById(R.id.btn_delete);
            btnUpdata = (Button) view.findViewById(R.id.btn_upData);
            btnSelect = (Button) view.findViewById(R.id.btn_Selected);
        }
    }

    private void delete(final String deleteRecordId) {
        DialogUtils.showDialog(new DialoginOkCallBack() {
            @Override
            public void onClickOk(DialogInterface dialog, int which) {
                RequestParams params = new RequestParams();
                params.addBodyParameter("sessionId", MyAppLocation.login.getSessionId());
                params.addBodyParameter("userId", MyAppLocation.login.getUserId());
                params.addBodyParameter("clientName", MyAppLocation.login.getClientName());
                params.addBodyParameter("deleteRecordId", deleteRecordId + "");
                getData(Urls.Delete, params, new CallBackString() {
                    @Override
                    public void httFinsh(String data) {
                        Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT).show();
                        SelectAddressActivity selectAddressActivity = (SelectAddressActivity) ItemAddressinfoAdapter.this.context;
                        selectAddressActivity.books.clear();
                        selectAddressActivity.initData();
                        ItemAddressinfoAdapter.this.notifyDataSetChanged();
                    }
                });
            }
        }, "是否删除", context);
    }

    public void getData(String url, RequestParams params, final CallBackString callBack) {
        DialogUtils.showProgressDialog(context);
        MyAppLocation.mHttpUtils.send(HttpRequest.HttpMethod.POST,
                url,
                params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        RetrueCodeHandler parser = RetruenUtils.getReturnInfo(responseInfo.result, new RetrueCodeHandler());
                        if (parser != null && parser.getString().equals("0")) {
                            callBack.httFinsh(responseInfo.result);
                        } else if (parser != null && parser.getString().equals("-1")) {
                            Toast.makeText(context, parser.getError(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "数据获取错误", Toast.LENGTH_SHORT).show();
                        }
                        DialogUtils.closeProgressDialog();
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {

                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                        DialogUtils.closeProgressDialog();
                    }
                });
    }


}
