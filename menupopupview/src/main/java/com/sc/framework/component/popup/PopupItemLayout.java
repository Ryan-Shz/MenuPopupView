package com.sc.framework.component.popup;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * @author Ryan
 * create by 17/3/14 下午11:22
 */
public class PopupItemLayout extends LinearLayout {

    private int mDriverColorResId = R.color.menu_popup_driver_default;
    private Paint mPaint;

    public PopupItemLayout(Context context) {
        this(context, null, 0);
    }

    public PopupItemLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PopupItemLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOrientation(HORIZONTAL);
        setWillNotDraw(false);
        mPaint = new Paint();
        mPaint.setStrokeWidth(3);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int count = getChildCount();
        if (count <= 0) {
            return;
        }
        mPaint.setColor(getResources().getColor(mDriverColorResId));
        for (int i = 1; i < count; i++) {
            View childView = getChildAt(i);
            canvas.drawLine(childView.getLeft(), 0, childView.getLeft(), getMeasuredHeight(), mPaint);
        }
    }

    public void setDriverColorResId(int diverColorResId) {
        this.mDriverColorResId = diverColorResId;
    }

}
