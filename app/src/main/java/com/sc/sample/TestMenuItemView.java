package com.sc.sample;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * @author zhushuhang
 * @Date 17/9/6 21:51
 */
public class TestMenuItemView extends AppCompatTextView {

    public TestMenuItemView(Context context) {
        super(context);
        setWidth(100);
        setHeight(100);
    }

    public TestMenuItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TestMenuItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

}
