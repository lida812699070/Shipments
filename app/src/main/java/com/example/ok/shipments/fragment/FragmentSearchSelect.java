package com.example.ok.shipments.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.ok.shipments.R;
import com.example.ok.shipments.utils.FragmentCallBack;


/**
 * Created by OK on 2016/4/29.
 */
public class FragmentSearchSelect extends Fragment {

    private EditText etPopSarech;
    private Button btnPopSarech;
    private String documentNumber;
    private FragmentCallBack callBack;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.layout_popwindow_search, null);
        return inflate;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        etPopSarech = (EditText) view.findViewById(R.id.et_popwin_findDocumentByNum);
        btnPopSarech = (Button) view.findViewById(R.id.btn_pop_search_ok);
        btnPopSarech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 订单号搜索
                documentNumber = etPopSarech.getText().toString();
                if (callBack!=null){
                    callBack.initData(documentNumber);
                }
            }
        });
    }
    public void setCallBack(FragmentCallBack callBack){
        this.callBack=callBack;
    }
}
