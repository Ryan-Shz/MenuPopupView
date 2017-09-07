package com.sc.framework.component.popup;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;

/**
 * @author ShamsChu
 * @Date 17/2/19 下午2:43
 */
public class PopupHorizontalScrollView extends HorizontalScrollView {

    private static final String TAG = PopupHorizontalScrollView.class.getName();
    private int mWindowSpacing = 50;
    protected int mMaxWidth;

    public PopupHorizontalScrollView(Context context) {
        this(context, null, 0);
    }

    public PopupHorizontalScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PopupHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager mWindowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        mWindowManager.getDefaultDisplay().getMetrics(metrics);
        mMaxWidth = metrics.widthPixels - (mWindowSpacing << 1);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        ViewGroup childView = (ViewGroup) getChildAt(0);
        childView.measure(widthMeasureSpec, heightMeasureSpec);
        int childWidth = childView.getMeasuredWidth();
        if (mMaxWidth < childWidth + getPaddingLeft() + getPaddingRight()) {
            adjustWidth(childView, childView.getChildCount(), mMaxWidth);
        }
    }

    private void adjustWidth(ViewGroup viewGroup, int size, int width) {
        int measureWith = measureItems(viewGroup, size);
        if (measureWith < width) {
            Log.v(TAG, "final measure width: " + measureWith);
            setMeasuredDimension(measureWith, getMeasuredHeight());
        } else {
            adjustWidth(viewGroup, size - 1, width);
        }
    }

    private int measureItems(ViewGroup viewGroup, int measureNumber) {
        int measureWidth = 0;
        for (int i = 0; i < measureNumber; i++) {
            View view = viewGroup.getChildAt(i);
            view.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
            measureWidth += view.getMeasuredWidth();
        }
        return measureWidth;
    }

    public void setWindowSpacing(int mWindowSpacing) {
        this.mWindowSpacing = mWindowSpacing;
    }
}
