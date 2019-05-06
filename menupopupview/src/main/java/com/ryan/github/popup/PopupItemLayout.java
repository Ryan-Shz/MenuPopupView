package com.ryan.github.popup;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * create by 17/3/14 下午11:22
 *
 * @author Ryan
 */
public class PopupItemLayout extends LinearLayout {

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
        mPaint.setColor(getResources().getColor(R.color.menu_popup_driver_default));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        drawItemDriver(canvas);
    }

    private void drawItemDriver(Canvas canvas) {
        int count = getChildCount();
        if (count <= 0) {
            return;
        }
        for (int i = 1; i < count; i++) {
            View childView = getChildAt(i);
            canvas.drawLine(childView.getLeft(), childView.getTop(), childView.getLeft(), childView.getBottom(), mPaint);
        }
    }

    public void setDriverColorResId(int diverColorResId) {
        mPaint.setColor(getResources().getColor(diverColorResId));
    }

}
