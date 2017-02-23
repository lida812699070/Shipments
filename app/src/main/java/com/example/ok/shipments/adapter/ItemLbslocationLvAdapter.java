package com.example.ok.shipments.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ok.shipments.R;

import java.util.ArrayList;
import java.util.List;


public class ItemLbslocationLvAdapter<T> extends BaseAdapter {

    private List<T> objects = new ArrayList<T>();

    private Context context;
    private LayoutInflater layoutInflater;

    public ItemLbslocationLvAdapter(Context context, List<T> objects) {
        this.context = context;
        this.objects=objects;
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
            convertView = layoutInflater.inflate(R.layout.item_lbslocation_lv, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews((T)getItem(position), (ViewHolder) convertView.getTag());
        return convertView;
    }

    private void initializeViews(T object, ViewHolder holder) {
        holder.tvAddress.setText((String) object);
    }

    protected class ViewHolder {
        private TextView tvAddress;

        public ViewHolder(View view) {
            tvAddress = (TextView) view.findViewById(R.id.tv_address);
        }
    }
}
