package com.example.ok.shipments.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.example.ok.shipments.R;
import com.example.ok.shipments.utils.FragmentCallBack;


/**
 * Created by OK on 2016/4/29.
 */
public class FragmentStateSelect extends Fragment {
    private FragmentCallBack callBack;
    private int state=-1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.layout_popwindow_state, null);
        return inflate;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RadioGroup popradioGroup = (RadioGroup) view.findViewById(R.id.rg_popwin_state_selector);
        popradioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //TODO 状态选择
                switch (checkedId) {
                    case R.id.rb_popwin_state_selector_all:
                        state = -1;
                        break;
                    case R.id.rb_popwin_state_selector_finished:
                        state = 3;
                        break;
                    case R.id.rb_popwin_state_selector_no_assign:
                        state = 0;
                        break;
                    case R.id.rb_popwin_state_selector_no_finish:
                        state = 2;
                        break;

                }
                if (callBack!=null){
                    callBack.initData(state);
                }
            }
        });
    }

    public void setCallBack(FragmentCallBack callBack){
        this.callBack=callBack;
    }
}
