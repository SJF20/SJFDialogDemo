package com.shijingfeng.sjf_dialog.library;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import androidx.annotation.AnimatorRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import com.shijingfeng.sjf_dialog.library.util.EventListenerUtil;

/**
 * Function: 自定义Dialog
 * Date: 2021/3/28 11:17
 * Description:
 *
 * @author ShiJingFeng
 */
public class SJFDialog implements DefaultLifecycleObserver {

    /** 构建器 */
    private final Builder mBuilder;

    /** 当前Dialog是否正在显示 */
    private boolean mIsShow = false;

    /** 是否正在操作 (显示Dialog 或 关闭Dialog)  true: 正在操作 */
    private boolean mIsOperating = false;

    private SJFDialog(@NonNull Builder builder) {
        this.mBuilder = builder;
        if (this.mBuilder.mAttr.activity instanceof AppCompatActivity) {
            // 添加Lifecycle，用于监听Activity或Fragment生命周期
            ((AppCompatActivity) this.mBuilder.mAttr.activity).getLifecycle().addObserver(this);
        }
    }

    /**
     * FrameLayout容器 添加 内容View
     */
    private void frameLayoutAddContentView() {
        final int width = mBuilder.mAttr.width;
        final int height = mBuilder.mAttr.height;
        final int x = mBuilder.mAttr.x;
        final int y = mBuilder.mAttr.y;
        final int gravity = mBuilder.mAttr.gravity;
        final ViewGroup container = mBuilder.mAttr.container;
        final View contentView = mBuilder.mAttr.contentView;

        final FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(width, height);

        layoutParams.gravity = gravity;
        if (x > 0) {
            // 向右偏移
            layoutParams.setMarginStart(x);
        } else if (x < 0) {
            // 向左偏移
            layoutParams.setMarginEnd(-x);
        }
        if (y > 0) {
            // 向下偏移
            layoutParams.topMargin = y;
        } else if (y < 0) {
            // 向上偏移
            layoutParams.bottomMargin = -y;
        }
        // 添加内容View
        container.addView(contentView, layoutParams);
    }

    /**
     * ConstrainLayout容器 添加 内容View
     */
    @SuppressLint("RtlHardcoded")
    private void constrainLayoutAddContentView() {
        final int width = mBuilder.mAttr.width;
        final int height = mBuilder.mAttr.height;
        final int x = mBuilder.mAttr.x;
        final int y = mBuilder.mAttr.y;
        final ViewGroup container = mBuilder.mAttr.container;
        final View contentView = mBuilder.mAttr.contentView;

        final ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(width, height);
        // 布局方向
        final int layoutDirection = container.getLayoutDirection();
        // 根据布局方向计算后的Gravity
        final int gravity = Gravity.getAbsoluteGravity(mBuilder.mAttr.gravity, layoutDirection);
        // 水平方向位置
        final int horizontalGravity = gravity & Gravity.HORIZONTAL_GRAVITY_MASK;
        // 垂直方向位置
        final int verticalGravity = gravity & Gravity.VERTICAL_GRAVITY_MASK;

        layoutParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        layoutParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        layoutParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
        layoutParams.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
        // 水平方向
        switch (horizontalGravity) {
            // 水平居中
            case Gravity.CENTER_HORIZONTAL:
                layoutParams.horizontalBias = 0.5F;
                break;
            // 水平靠右
            case Gravity.RIGHT:
                layoutParams.horizontalBias = 1F;
                break;
            // 水平靠左
            case Gravity.LEFT:
            default:
                layoutParams.horizontalBias = 0F;
        }
        // 垂直方向
        switch (verticalGravity) {
            // 垂直居中
            case Gravity.CENTER_VERTICAL:
                layoutParams.verticalBias = 0.5F;
                break;
            // 垂直靠上
            case Gravity.TOP:
            default:
                layoutParams.verticalBias = 0F;
                break;
            // 垂直靠下
            case Gravity.BOTTOM:
                layoutParams.verticalBias = 1F;
                break;
        }
        if (x > 0) {
            // 向右偏移
            layoutParams.setMarginStart(x);
        } else if (x < 0) {
            // 向左偏移
            layoutParams.setMarginEnd(-x);
        }
        if (y > 0) {
            // 向下偏移
            layoutParams.topMargin = y;
        } else if (y < 0) {
            // 向上偏移
            layoutParams.bottomMargin = -y;
        }
        // 添加内容View
        container.addView(contentView, layoutParams);
    }

    /**
     * RelativeLayout容器 添加 内容View
     */
    @SuppressLint("RtlHardcoded")
    private void relativeLayoutAddContentView() {
        final int width = mBuilder.mAttr.width;
        final int height = mBuilder.mAttr.height;
        final int x = mBuilder.mAttr.x;
        final int y = mBuilder.mAttr.y;
        final ViewGroup container = mBuilder.mAttr.container;
        final View contentView = mBuilder.mAttr.contentView;

        final RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(width, height);
        // 布局方向
        final int layoutDirection = container.getLayoutDirection();
        // 根据布局方向计算后的Gravity
        final int gravity = Gravity.getAbsoluteGravity(mBuilder.mAttr.gravity, layoutDirection);
        // 水平方向位置
        final int horizontalGravity = gravity & Gravity.HORIZONTAL_GRAVITY_MASK;
        // 垂直方向位置
        final int verticalGravity = gravity & Gravity.VERTICAL_GRAVITY_MASK;

        // 水平方向
        switch (horizontalGravity) {
            // 水平居中
            case Gravity.CENTER_HORIZONTAL:
                layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
                break;
            // 水平靠右
            case Gravity.RIGHT:
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                break;
            // 水平靠左
            case Gravity.LEFT:
            default:
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        }
        // 垂直方向
        switch (verticalGravity) {
            // 垂直居中
            case Gravity.CENTER_VERTICAL:
                layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
                break;
            // 垂直靠上
            case Gravity.TOP:
            default:
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                break;
            // 垂直靠下
            case Gravity.BOTTOM:
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                break;
        }
        if (x > 0) {
            // 向右偏移
            layoutParams.setMarginStart(x);
        } else if (x < 0) {
            // 向左偏移
            layoutParams.setMarginEnd(-x);
        }
        if (y > 0) {
            // 向下偏移
            layoutParams.topMargin = y;
        } else if (y < 0) {
            // 向上偏移
            layoutParams.bottomMargin = -y;
        }
        // 添加内容View
        container.addView(contentView, layoutParams);
    }

    /**
     * 显示Dialog
     */
    @SuppressLint("RtlHardcoded")
    public void show() {
        if (mIsShow || mIsOperating) {
            // 如果当前Dialog已显示，则不再执行显示操作
            return;
        }

        // 设置为正在操作中
        mIsOperating = true;

        final Activity activity = mBuilder.mAttr.activity;
        final ViewGroup container = mBuilder.mAttr.container;

        // 添加内容View
        if (container instanceof FrameLayout) {
            frameLayoutAddContentView();
        } else if (container instanceof ConstraintLayout) {
            constrainLayoutAddContentView();
        } else {
            relativeLayoutAddContentView();
        }

        // 背景View
        final View backgroundView = new View(activity);
        final ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        final float backgroundAlpha = mBuilder.mAttr.backgroundAlpha;
        final boolean isCancelable = mBuilder.mAttr.isCancelable;

        // 设置ID
        backgroundView.setId(R.id.id_sjf_dialog_background);
        // 背景View设置可聚焦
        backgroundView.setFocusable(true);
        // 背景View设置可点击
        backgroundView.setClickable(true);
        // 背景View设置可长按
        backgroundView.setLongClickable(true);
        // 设置背景View背景为纯黑色
        backgroundView.setBackgroundColor(Color.BLACK);
        // 设置背景View背景透明度 (注意：如果设置透明度的是ViewGroup, 那么它的子View也会被修改透明度)
        backgroundView.setAlpha(backgroundAlpha);
        // 设置背景View点击监听
        EventListenerUtil.setOnClickListener(backgroundView, v -> {
            if (isCancelable) {
                // 点击内容View外部，关闭Dialog
                hide();
            }
        });

        final Animator enterAnimator = mBuilder.mAttr.enterAnimator;

        if (enterAnimator != null) {
            enterAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    // 必须移除当前监听器，否则会多次回调(不移除会有多个监听器)
                    enterAnimator.removeListener(this);
                    // 添加背景View
                    container.addView(backgroundView, container.getChildCount() - 1, layoutParams);
                    // 设置当前Dialog已显示
                    mIsShow = true;
                    // 设置为已操作完成
                    mIsOperating = false;
                }
            });
            // 开启Dialog进入动画
            enterAnimator.start();
        } else {
            // 添加背景View
            container.addView(backgroundView, container.getChildCount() - 1, layoutParams);
            // 设置当前Dialog已显示
            mIsShow = true;
            // 设置为已操作完成
            mIsOperating = false;
        }
    }

    /**
     * 关闭Dialog
     */
    public void hide() {
        if (!mIsShow || mIsOperating) {
            // 如果当前Dialog已关闭，则不再执行关闭操作
            return;
        }

        // 设置为正在操作中
        mIsOperating = true;

        final Animator enterAnimator = mBuilder.mAttr.enterAnimator;
        final Animator exitAnimator = mBuilder.mAttr.exitAnimator;
        final ViewGroup container = mBuilder.mAttr.container;
        final View backgroundView = container.findViewById(R.id.id_sjf_dialog_background);
        final View contentView = mBuilder.mAttr.contentView;
        final OnDismissListener onDismissListener = mBuilder.mListener.onDismissListener;

        // 移除背景View
        if (backgroundView != null) {
            container.removeView(backgroundView);
        }
        if (enterAnimator != null) {
            // 因为存在Dialog进入动画循环播放，故关闭Dialog时销毁Dialog进入动画
            enterAnimator.cancel();
        }
        if (exitAnimator != null) {
            exitAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    // 必须移除当前监听器，否则会多次回调(不移除会有多个监听器)
                    exitAnimator.removeListener(this);
                    // 必须在removeView之前，否则如果在onDismissListener中使用View关闭键盘就会失效
                    if (onDismissListener != null) {
                        onDismissListener.onDismiss();
                    }
                    // 移除内容View
                    container.removeView(contentView);
                    // 设置当前Dialog已关闭
                    mIsShow = false;
                    // 设置为已操作完成
                    mIsOperating = false;
                }
            });
            // 开启Dialog关闭动画
            exitAnimator.start();
        } else {
            // 必须在removeView之前，否则如果在onDismissListener中使用View关闭键盘就会失效
            if (onDismissListener != null) {
                onDismissListener.onDismiss();
            }
            // 移除内容View
            container.removeView(contentView);
            // 设置当前Dialog已关闭
            mIsShow = false;
            // 设置为已操作完成
            mIsOperating = false;
        }
    }

    /**
     * 关闭Dialog (不回调 onDismissListener)
     */
    public void hideNoCallback() {
        if (!mIsShow || mIsOperating) {
            // 如果当前Dialog已关闭，则不再执行关闭操作
            return;
        }

        // 设置为正在操作中
        mIsOperating = true;

        final Animator enterAnimator = mBuilder.mAttr.enterAnimator;
        final Animator exitAnimator = mBuilder.mAttr.exitAnimator;
        final ViewGroup container = mBuilder.mAttr.container;
        final View backgroundView = container.findViewById(R.id.id_sjf_dialog_background);
        final View contentView = mBuilder.mAttr.contentView;

        // 移除背景View
        if (backgroundView != null) {
            container.removeView(backgroundView);
        }
        if (enterAnimator != null) {
            // 因为存在Dialog进入动画循环播放，故关闭Dialog时销毁Dialog进入动画
            enterAnimator.cancel();
        }
        if (exitAnimator != null) {
            exitAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    // 必须移除当前监听器，否则会多次回调(不移除会有多个监听器)
                    exitAnimator.removeListener(this);
                    // 移除内容View
                    container.removeView(contentView);
                    // 设置当前Dialog已关闭
                    mIsShow = false;
                    // 设置为已操作完成
                    mIsOperating = false;
                }
            });
            // 开启Dialog关闭动画
            exitAnimator.start();
        } else {
            // 移除内容View
            container.removeView(contentView);
            // 设置当前Dialog已关闭
            mIsShow = false;
            // 设置为已操作完成
            mIsOperating = false;
        }
    }

    /**
     * 当前Dialog是否正在显示
     *
     * @return true: 当前Dialog正在显示
     */
    public boolean isShowing() {
        return mIsShow;
    }

    /**
     * 设置 Dialog取消监听器
     *
     * @param listener Dialog取消监听器
     * @return SJFDialog
     */
    public SJFDialog setOnDismissListener(@Nullable OnDismissListener listener) {
        mBuilder.mListener.onDismissListener = listener;
        return this;
    }

    /**
     * 销毁 (如果传入的Activity不是AppCompatActivity, 则需手动调用该方法, 否则不需要)
     */
    public void destroy() {
        // 因为存在Dialog进入动画循环播放，故销毁Dialog进入动画，防止内存泄漏
        if (mBuilder.mAttr.enterAnimator != null) {
            mBuilder.mAttr.enterAnimator.cancel();
            mBuilder.mAttr.enterAnimator.removeAllListeners();
        }
        // 因为存在Dialog退出动画循环播放，故销毁Dialog退出动画，防止内存泄漏
        if (mBuilder.mAttr.exitAnimator != null) {
            mBuilder.mAttr.exitAnimator.cancel();
            mBuilder.mAttr.exitAnimator.removeAllListeners();
        }
    }

    /**
     * Activity 或 Fragment 销毁回调
     *
     * @param owner LifecycleOwner
     */
    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
        destroy();
    }

    /**
     * 构建器
     */
    public static class Builder {

        /** 属性数据 */
        private final Attr mAttr = new Attr();
        /** 监听器数据 */
        private final Listener mListener = new Listener();

        /**
         * 构建器构造方法
         *
         * @param activity 使用Activity内容根布局(不是DecorView，是id为android.R.id.content的FrameLayout)作为容器
         * @param contentView 内容View
         */
        public Builder(@NonNull Activity activity, @NonNull View contentView) {
            // 内容View设置可聚焦
            contentView.setFocusable(true);
            // 内容View设置可点击
            contentView.setClickable(true);
            // 内容View设置可长按
            contentView.setLongClickable(true);

            this.mAttr.activity = activity;
            this.mAttr.container = activity.findViewById(android.R.id.content);
            this.mAttr.contentView = contentView;
        }

        /**
         * 构建器构造方法
         *
         * @param activity Activity
         * @param container 容器ViewGroup
         * @param contentView 内容View
         */
        public Builder(@NonNull Activity activity, @NonNull ViewGroup container, @NonNull View contentView) {
            if (!(container instanceof FrameLayout)
                    && !(container instanceof ConstraintLayout)
                    && !(container instanceof RelativeLayout)) {
                throw new IllegalArgumentException("容器必须为 FrameLayout, ConstraintLayout, RelativeLayout 三者之一");
            }

            // 内容View设置可聚焦
            contentView.setFocusable(true);
            // 内容View设置可点击
            contentView.setClickable(true);
            // 内容View设置可长按
            contentView.setLongClickable(true);

            this.mAttr.activity = activity;
            this.mAttr.container = container;
            this.mAttr.contentView = contentView;
        }

        /**
         * 设置内容View宽 (会覆盖掉内容View动态创建或XML中设置的宽)
         *
         * @param width 内容View宽
         * @return Builder
         */
        public Builder setWidth(int width) {
            this.mAttr.width = width;
            return this;
        }

        /**
         * 设置内容View高 (会覆盖掉内容View动态创建或XML中设置的高)
         *
         * @param height 内容View高
         * @return Builder
         */
        public Builder setHeight(int height) {
            this.mAttr.height = height;
            return this;
        }

        /**
         * 设置内容View所处的位置 和 X轴以及Y轴的偏移距离
         *
         * @param gravity 内容View所处的位置
         * @param x X轴偏移距离
         * @param y Y轴偏移距离
         * @return Builder
         */
        public Builder setGravity(int gravity, int x, int y) {
            this.mAttr.gravity = gravity;
            this.mAttr.x = x;
            this.mAttr.y = y;
            return this;
        }

        /**
         * 设置背景透明度 (宽高为容器ViewGroup的宽高)
         *
         * @param alpha 背景透明度
         * @return Builder
         */
        public Builder setBackgroundAlpha(float alpha) {
            this.mAttr.backgroundAlpha = alpha;
            return this;
        }

        /**
         * 设置 Dialog进入动画
         *
         * @param enterAnimatorRes Dialog进入动画资源
         * @return Builder
         */
        public Builder setEnterAnimator(@AnimatorRes int enterAnimatorRes) {
            final Context context = mAttr.activity.getApplicationContext();
            final View contentView = mAttr.contentView;
            final Animator enterAnimator = AnimatorInflater.loadAnimator(context.getApplicationContext(), enterAnimatorRes);

            enterAnimator.setTarget(contentView);
            this.mAttr.enterAnimator = enterAnimator;
            return this;
        }

        /**
         * 设置 Dialog进入动画
         *
         * @param enterAnimator Dialog进入动画
         * @return Builder
         */
        public Builder setEnterAnimator(@NonNull Animator enterAnimator) {
            final View contentView = mAttr.contentView;

            enterAnimator.setTarget(contentView);
            this.mAttr.enterAnimator = enterAnimator;
            return this;
        }

        /**
         * 设置 Dialog退出动画
         *
         * @param exitAnimatorRes Dialog退出动画资源
         * @return Builder
         */
        public Builder setExitAnimator(@AnimatorRes int exitAnimatorRes) {
            final Context context = mAttr.activity.getApplicationContext();
            final View contentView = mAttr.contentView;
            final Animator exitAnimator = AnimatorInflater.loadAnimator(context.getApplicationContext(), exitAnimatorRes);

            exitAnimator.setTarget(contentView);
            this.mAttr.exitAnimator = exitAnimator;
            return this;
        }

        /**
         * 设置 Dialog退出动画
         *
         * @param exitAnimator Dialog退出动画
         * @return Builder
         */
        public Builder setExitAnimator(@NonNull Animator exitAnimator) {
            final View contentView = mAttr.contentView;

            exitAnimator.setTarget(contentView);
            this.mAttr.exitAnimator = exitAnimator;
            return this;
        }

        /**
         * 设置点击内容View外部是否可取消
         *
         * @param cancelable true: 点击内容View外部取消
         * @return Builder
         */
        public Builder setCancelable(boolean cancelable) {
            this.mAttr.isCancelable = cancelable;
            return this;
        }

        /**
         * 设置 Dialog关闭监听器
         *
         * @param listener Dialog关闭监听器
         * @return Builder
         */
        public Builder setOnDismissListener(@NonNull OnDismissListener listener) {
            this.mListener.onDismissListener = listener;
            return this;
        }

        /**
         * 构建Dialog
         *
         * @return SJFDialog
         */
        public SJFDialog build() {
            return new SJFDialog(this);
        }

        /**
         * 构建Dialog 并 显示
         *
         * @return SJFDialog
         */
        public SJFDialog show() {
            final SJFDialog sjfDialog = new SJFDialog(this);

            sjfDialog.show();
            return sjfDialog;
        }

    }

    /**
     * 属性数据
     */
    private static class Attr {

        /** Activity环境 */
        private Activity activity;

        /** 容器ViewGroup (必须是 FrameLayout, ConstraintLayout, RelativeLayout 这三种之一) */
        private ViewGroup container;

        /** 内容View */
        private View contentView;

        /** 内容View宽 (会覆盖掉内容View动态创建或XML中设置的宽) */
        private int width = ViewGroup.LayoutParams.WRAP_CONTENT;

        /** 内容View高 (会覆盖掉内容View动态创建或XML中设置的高) */
        private int height = ViewGroup.LayoutParams.WRAP_CONTENT;

        /** 内容View所处位置 (默认居中) (会覆盖掉内容View动态创建或XML中设置的所处的位置) */
        private int gravity = Gravity.CENTER;

        /** X轴偏移距离 (以gravity所处的位置为起点，向左为负数，向右为正数) */
        private int x = 0;

        /** Y轴偏移距离 (以gravity所处的位置为起点，向上为负数，向下为正数) */
        private int y = 0;

        /** 背景透明度 (0F到1F, 从完全透明到完全不透明) (注意：如果设置透明度的是ViewGroup, 那么它的子View也会被修改透明度) */
        private float backgroundAlpha = 0F;

        /** Dialog进入动画 */
        private Animator enterAnimator;

        /** Dialog退出动画 */
        private Animator exitAnimator;

        /** 点击内容View外部是否关闭Dialog  true:关闭 */
        private boolean isCancelable = true;

    }

    /**
     * 监听器数据
     */
    private static class Listener {

        /** Dialog关闭回调 */
        private OnDismissListener onDismissListener;

    }

    /**
     * Dialog关闭监听器
     */
    @FunctionalInterface
    public interface OnDismissListener {

        /**
         * Dialog关闭回调
         */
        void onDismiss();

    }

}
