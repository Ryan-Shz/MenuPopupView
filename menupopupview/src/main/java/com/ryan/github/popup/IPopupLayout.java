package com.ryan.github.popup;

/**
 * create by 17/3/2 下午7:04
 *
 * @author Ryan
 */
interface IPopupLayout {

    void setOnPopupItemClickListener(PopupLayout.OnPopupItemClickListener onPopupItemClickListener);

    void notifyDataSetChanged();

    void setIndicatorDriverColorResId(int colorResId);

    void setDriverColorResId(int driverColorResId);

    void setAdapter(MenuPopupAdapter adapter);

    MenuPopupAdapter getAdapter();

}
