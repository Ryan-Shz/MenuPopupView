package com.sc.framework.component.popup;

/**
 * @author ShamsChu
 * @Date 17/3/2 下午7:04
 */
interface IPopupLayout {

    void setOnPopupItemClickListener(PopupLayout.OnPopupItemClickListener onPopupItemClickListener);

    void notifyDataSetChanged();

    void setIndicatorDriverColorResId(int colorResId);

    void setDriverColorResId(int driverColorResId);

    void setAdapter(MenuPopupAdapter adapter);

    MenuPopupAdapter getAdapter();

}
