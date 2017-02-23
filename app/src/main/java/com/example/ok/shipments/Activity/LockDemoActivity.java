package com.example.ok.shipments.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.ok.shipments.R;
import com.example.ok.shipments.view.LockPatternView;

import java.util.List;

public class LockDemoActivity extends AppCompatActivity {

    private LockPatternView lockPatternView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_demo);

        lockPatternView = (LockPatternView) findViewById(R.id.lockSetup_pattern);

        lockPatternView.setOnPatternListener(new LockPatternView.OnPatternListener() {
            @Override
            public void onPatternStart() {

            }

            @Override
            public void onPatternCleared() {

            }

            @Override
            public void onPatternCellAdded(List<LockPatternView.Cell> pattern) {

            }

            @Override
            public void onPatternDetected(List<LockPatternView.Cell> pattern) {
                lockPatternView.setDisplayMode(LockPatternView.DisplayMode.Wrong);
                lockPatternView.clearPattern();//清楚
            }
        });
    }
}
