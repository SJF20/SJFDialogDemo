package com.shijingfeng.sjf_dialog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    private Button btnTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        initAction();
    }

    private void initView() {
        btnTest = findViewById(R.id.btn_test);
    }

    private void initData() {
//        final ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) btnTest.getLayoutParams();
//
//        layoutParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
//        layoutParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
//        layoutParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
//        layoutParams.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
//        layoutParams.horizontalBias = 0.5F;
//        layoutParams.verticalBias = 0.5F;
//        btnTest.setLayoutParams(layoutParams);

        final RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) btnTest.getLayoutParams();

        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        btnTest.setLayoutParams(layoutParams);
    }

    private void initAction() {
        btnTest.setOnClickListener(v -> {
            final Intent intent = new Intent(this, TestActivity.class);

            startActivity(intent);
        });
    }

}