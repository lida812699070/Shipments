package com.example.ok.shipments.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ok.shipments.R;
import com.example.ok.shipments.model.DeliveryRequest;

public class ItemAboutLvAdapter<T> extends BaseAdapter {

    private List<T> objects = new ArrayList<T>();

    private Context context;
    private LayoutInflater layoutInflater;

    public ItemAboutLvAdapter(Context context, List<T> objects) {
        this.objects = objects;
        this.context = context;
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
            convertView = layoutInflater.inflate(R.layout.item_about_lv, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews((T) getItem(position), (ViewHolder) convertView.getTag());
        return convertView;
    }

    private void initializeViews(T object, ViewHolder holder) {
        DeliveryRequest deliveryRequest = (DeliveryRequest) object;
        holder.tvAppointment.setText("预约时间：" + deliveryRequest.getAppointment());
        holder.tvRequestId.setText("订单号：" + deliveryRequest.getRequestId());
        holder.tvCargoVolumn.setText("体积：" + deliveryRequest.getCargoVolumn() + "方");
        holder.tvStatusDescription.setText("订单状态：" + deliveryRequest.getStatusDescription());
        if (TextUtils.isEmpty(deliveryRequest.getTruckNumber())) {
            holder.tvTruckNumber.setText("司机车牌：" + "尚未接受");
        } else {

            holder.tvTruckNumber.setText("司机车牌：" + deliveryRequest.getTruckNumber());
        }
    }

    protected class ViewHolder {
        private TextView tvRequestId;
        private TextView tvAppointment;
        private TextView tvCargoVolumn;
        private TextView tvStatusDescription;
        private TextView tvTruckNumber;

        public ViewHolder(View view) {
            tvRequestId = (TextView) view.findViewById(R.id.tv_RequestId);
            tvAppointment = (TextView) view.findViewById(R.id.tv_Appointment);
            tvCargoVolumn = (TextView) view.findViewById(R.id.tv_CargoVolumn);
            tvStatusDescription = (TextView) view.findViewById(R.id.tv_StatusDescription);
            tvTruckNumber = (TextView) view.findViewById(R.id.tv_TruckNumber);
        }
    }
}
