package com.ryan.github.popup.sample;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ryan.github.popup.MenuPopupAdapter;
import com.sc.sample.R;

import java.util.List;

/**
 * @author Ryan
 * create by 17/9/7 09:53
 */
public class TestCustomAdapter extends MenuPopupAdapter<TestMenuBean> {

    List<TestMenuBean> mData;

    public TestCustomAdapter(List<TestMenuBean> data) {
        mData = data;
    }

    @Override
    public View getView(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.test_custom_item, null);
        TextView tv = (TextView) view.findViewById(R.id.label);
        TestMenuBean bean = getItem(position);
        tv.setText(bean.getMenuText());
        return view;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public TestMenuBean getItem(int position) {
        return mData.get(position);
    }
}
