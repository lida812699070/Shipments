package com.example.ok.shipments.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.example.ok.shipments.R;
import com.example.ok.shipments.model.SimpleDocument;

import java.util.ArrayList;
import java.util.List;

public class ItemQueryLvAdapter<T> extends BaseAdapter {

    private List<T> objects = new ArrayList<T>();

    private Context context;
    private LayoutInflater layoutInflater;

    public ItemQueryLvAdapter(Context context, List<T> objects) {
        this.objects=objects;
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
            convertView = layoutInflater.inflate(R.layout.item_query_lv, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews((T) getItem(position), (ViewHolder) convertView.getTag());
        return convertView;
    }

    private void initializeViews(T object, ViewHolder holder) {
        SimpleDocument document = (SimpleDocument) object;
        holder.tvDocumentNumber.setText("订单号："+document.getDocumentNumber());
        holder.tvFrameNum.setText("件数："+document.getQuantity());
        if (TextUtils.isEmpty(document.getConsigneeContactPerson())){
            holder.tvDocumentCreatorName.setText("接收人："+"订单尚未接受");
        }else {
            holder.tvDocumentCreatorName.setText("接收人："+document.getConsigneeContactPerson());

        }
        holder.tvDocumentState.setText("订单状态："+document.getShippingStatus());
        String time = document.getCreateTime().replace("T", "  ");
        time=time.substring(0,17);
        holder.tvDocumentCreatorTime.setText("创建时间："+time);
    }

    protected class ViewHolder {
        private final TextView tvDocumentCreatorTime;
        private TextView tvDocumentNumber;
        private TextView tvFrameNum;
        private TextView tvDocumentCreatorName;
        private TextView tvDocumentState;

        public ViewHolder(View view) {
            tvDocumentNumber = (TextView) view.findViewById(R.id.tv_document_number);
            tvFrameNum = (TextView) view.findViewById(R.id.tv_frame_num);
            tvDocumentCreatorName = (TextView) view.findViewById(R.id.tv_document_CreatorName);
            tvDocumentState = (TextView) view.findViewById(R.id.tv_document_state);
            tvDocumentCreatorTime = (TextView) view.findViewById(R.id.tv_document_creatTime);
        }
    }
}
