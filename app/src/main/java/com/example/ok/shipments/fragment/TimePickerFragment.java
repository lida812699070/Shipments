package com.example.ok.shipments.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.example.ok.shipments.MyAppLocation;
import com.example.ok.shipments.R;
import com.example.ok.shipments.urls.Urls;
import com.example.ok.shipments.utils.RetrueCodeHandler;
import com.example.ok.shipments.utils.RetruenUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.Calendar;

/**
 * Created by OK on 2016/4/29.
 */
public class TimePickerFragment extends DialogFragment implements
        DatePickerDialog.OnDateSetListener {
    private DateCallBack dateCallBack;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        return new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                dateCallBack.getDate(hourOfDay+":"+minute);
            }
        },hour,minute,true);
    }

    @Override
    public void onDateSet(DatePicker view, int hour, int minute, int second) {
        if (dateCallBack!=null){
            dateCallBack.getDate(hour+"-"+minute+"-"+second);
        }
    }

    public void setDateCallBack(DateCallBack dateCallBack) {
        this.dateCallBack = dateCallBack;
    }

    interface DateCallBack {
        void getDate(String date);
    }
}

