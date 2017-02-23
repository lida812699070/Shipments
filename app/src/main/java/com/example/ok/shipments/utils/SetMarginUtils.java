package com.example.ok.shipments.utils;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by ${lida} on 2016/7/26.
 */
public class SetMarginUtils {
    public static void setMargins (View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }
}
