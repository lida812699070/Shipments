package com.example.ok.shipments.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ok.shipments.R;
import com.example.ok.shipments.model.ShippingTraceItem;

public class ItemSimple2Adapter<T> extends BaseAdapter {

    private List<T> objects = new ArrayList<T>();

    private Context context;
    private LayoutInflater layoutInflater;

    public ItemSimple2Adapter(Context context, List<T> objects) {
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
            convertView = layoutInflater.inflate(R.layout.item_simple_2, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews((T) getItem(position), (ViewHolder) convertView.getTag());
        return convertView;
    }

    private void initializeViews(T object, ViewHolder holder) {
        ShippingTraceItem traceItem = (ShippingTraceItem) object;
        holder.tvDocumentState.setText(traceItem.getMessage());
        String[] split = traceItem.getTime().split("T");
        holder.tvDocumentTime.setText(split[0]);
        holder.tvDocumentTimes.setText(split[1].substring(0,8));
    }

    protected class ViewHolder {
        private TextView tvDocumentTime;
        private TextView tvDocumentTimes;
        private TextView tvDocumentState;

        public ViewHolder(View view) {
            tvDocumentTime = (TextView) view.findViewById(R.id.tv_documentTime);
            tvDocumentTimes = (TextView) view.findViewById(R.id.tv_documentTimes);
            tvDocumentState = (TextView) view.findViewById(R.id.tv_documentState);
        }
    }
}
