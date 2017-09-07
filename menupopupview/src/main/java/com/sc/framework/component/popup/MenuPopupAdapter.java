package com.sc.framework.component.popup;

import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author ShamsChu
 * @Date 17/9/6 22:16
 */
public abstract class MenuPopupAdapter<T> {

    private DataSetObservable mDataSetObservable = new DataSetObservable();

    // 返回Item View视图对象
    public abstract View getView(ViewGroup container, int position);

    // 获取item数量
    public abstract int getItemCount();

    // 获取指定索引的item数据
    public abstract T getItem(int position);

    // 数据改变通知，刷新菜单并保存当前滚动状态
    public void notifyDataSetChanged() {
        mDataSetObservable.notifyChanged();
    }

    // 数据改变通知，刷新菜单并重置
    public void notifyDataSetInvalidated() {
        mDataSetObservable.notifyInvalidated();
    }

    public void registerDataSetObserver(DataSetObserver observer) {
        mDataSetObservable.registerObserver(observer);
    }

    void unregisterDataSetObserver(DataSetObserver observer) {
        mDataSetObservable.unregisterObserver(observer);
    }

}
