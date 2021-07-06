package com.shijingfeng.sjf_dialog;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.shijingfeng.sjf_dialog.library.SJFDialog;

/**
 * Function:
 * Date: 2021/3/28 17:00
 * Description:
 *
 * @author ShiJingFeng
 */
public class TestActivity extends AppCompatActivity {

    private TextView tvBack;
//    private FrameLayout flContent;
    private ConstraintLayout clContent;
    private Button btnShowDialog;
    private TextView tvShowDialog;
    private SJFDialog mDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        initView();
        initData();
        initAction();
    }

    private void initView() {
        tvBack = findViewById(R.id.tv_back);
//        flContent = findViewById(R.id.fl_content);
        clContent = findViewById(R.id.cl_content);
        btnShowDialog = findViewById(R.id.btn_show_dialog);
        tvShowDialog = findViewById(R.id.tv_show_dialog);
    }

    private void initData() {

    }

    @SuppressLint("InflateParams")
    private void initAction() {
        tvBack.setOnClickListener(v -> finish());
        btnShowDialog.setOnClickListener(v -> {
            showDialog();
        });
        tvShowDialog.setOnClickListener(v -> {
            showDialog();
        });
    }

    @SuppressLint("InflateParams")
    private void showDialog() {
        if (mDialog != null) {
            mDialog.show();
            return;
        }

        final View contentView = LayoutInflater.from(this).inflate(R.layout.dialog_test, null);
        final TextView tvCancel = contentView.findViewById(R.id.tv_cancel);
        final TextView tvEnsure = contentView.findViewById(R.id.tv_ensure);

        mDialog = new SJFDialog.Builder(this, clContent, contentView)
                .setWidth(ViewGroup.LayoutParams.WRAP_CONTENT)
                .setHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                .setGravity(Gravity.CENTER, 0, 0)
                .setBackgroundAlpha(0.3F)
                .setEnterAnimator(R.animator.animator_dialog_enter)
                .setExitAnimator(R.animator.animator_dialog_exit)
                .setCancelable(true)
                .setOnDismissListener(() -> {
                    Log.e("测试", "OnDismissListener");
                })
                .build();

        tvCancel.setOnClickListener(view -> {
            mDialog.hide();
            Log.e("测试", "取消");
        });
        tvEnsure.setOnClickListener(view -> {
            mDialog.hide();
            Log.e("测试", "确认");
        });
        mDialog.show();
    }

}
