package com.sc.framework.component.popup;

import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.view.View;

/**
 * @author ShamsChu
 * @Date 17/9/6 22:16
 */
public abstract class MenuPopupAdapter<T> {

    private DataSetObservable mDataSetObservable = new DataSetObservable();

    public abstract View getView(View container, int position);

    public abstract int getItemCount();

    public abstract T getItem(int position);

    public void notifyDataSetChanged() {
        mDataSetObservable.notifyChanged();
    }

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
