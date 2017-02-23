package com.example.ok.shipments.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import com.example.ok.shipments.R;
import com.example.ok.shipments.utils.FragmentCallBack;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by OK on 2016/4/29.
 */
public class FragmentTimeSelect extends Fragment {

    private Button btnStartTime;
    private Button btnEndTime;
    private FragmentCallBack callBack;
    private Button currBtn;
    private Button btnClear;
    private Button btnOk;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.layout_popwindow_time, null);
        return inflate;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnStartTime = (Button) view.findViewById(R.id.btn_pop_startTime);
        btnEndTime = (Button) view.findViewById(R.id.btn_pop_endTime);
        btnClear = (Button) view.findViewById(R.id.btn_time_clear);
        setListener(view);
    }

    private void setListener(View view) {
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnStartTime.setText("");
                btnEndTime.setText("");
            }
        });
        btnOk = (Button) view.findViewById(R.id.btn_time_ok);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callBack != null) {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String strStartTime = btnStartTime.getText().toString();
                    String strEndTime = btnEndTime.getText().toString();
                    if (TextUtils.isEmpty(strStartTime) ||TextUtils.isEmpty(strEndTime)){
                        callBack.initData("", "");
                        return;
                    }
                    String timeStart = strStartTime + " 00:00:00";
                    String timeEnd = strEndTime + " 00:00:00";
                    try {
                        Date dateS = format.parse(timeStart);
                        Date dateE = format.parse(timeEnd);
                        callBack.initData(dateS.getTime() + "", dateE.getTime() + "");
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
        btnStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currBtn = btnStartTime;
                showDatePickerDialog();

            }
        });
        btnEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currBtn = btnEndTime;
                showDatePickerDialog();
            }
        });
    }

    public void setCallBack(FragmentCallBack callBack) {
        this.callBack = callBack;
    }

    public void showDatePickerDialog() {
        DatePickerFragment datePicker = new DatePickerFragment();
        datePicker.setDateCallBack(new DatePickerFragment.DateCallBack() {
            @Override
            public void getDate(String date) {
                currBtn.setText(date);
            }
        });
        datePicker.show(getFragmentManager(), "datePicker");
    }
}
