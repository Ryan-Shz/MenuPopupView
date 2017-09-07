package com.sc.framework.component.popup;

import android.content.Context;
import android.graphics.Point;
import android.view.View;

/**
 * 气泡菜单提示控件
 *
 * @author ShamsChu
 * @Date 17/2/14 下午3:32
 */
public class PopupView extends AbsPopupView implements IPopupLayout {

    private PopupLayout mPopupLayout;

    public PopupView(Context context) {
        super(context);
        mPopupLayout = new PopupLayout(context);
        setContentView(mPopupLayout);
    }

    @Override
    public void setOnPopupItemClickListener(PopupLayout.OnPopupItemClickListener onPopupItemClickListener) {
        mPopupLayout.setOnPopupItemClickListener(onPopupItemClickListener);
    }

    @Override
    public void notifyDataSetChanged() {
        mPopupLayout.notifyDataSetChanged();
    }

    @Override
    public void setIndicatorDriverColorResId(int colorResId) {
        if (mPopupLayout != null) {
            mPopupLayout.setIndicatorDriverColorResId(colorResId);
        }
    }

    @Override
    public void setDriverColorResId(int driverColorResId) {
        if (mPopupLayout != null) {
            mPopupLayout.setDriverColorResId(driverColorResId);
        }
    }

    @Override
    public void setAdapter(MenuPopupAdapter adapter) {
        mPopupLayout.setAdapter(adapter);
        measureContentView();
    }

    @Override
    public MenuPopupAdapter getAdapter() {
        return mPopupLayout.getAdapter();
    }

    @Override
    public void show(View anchor) {
        notifyDataSetChanged();
        super.show(anchor);
    }

    @Override
    public void showAtTop(View anchor, Point origin, int xOff, int yOff) {
        mPopupLayout.setPopupLocation(PopupLayout.PopupLocation.TOP);
        mPopupLayout.setOffset(origin.x - xOff);
        super.showAtTop(anchor, origin, xOff, yOff);
    }

    @Override
    public void showAtBottom(View anchor, Point origin, int xOff, int yOff) {
        mPopupLayout.setPopupLocation(PopupLayout.PopupLocation.Bottom);
        mPopupLayout.setOffset(origin.x - xOff);
        super.showAtBottom(anchor, origin, xOff, yOff);
    }

}
