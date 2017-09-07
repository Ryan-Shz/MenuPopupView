package com.sc.sample;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sc.framework.component.popup.MenuPopupAdapter;

import java.util.List;

/**
 * @author ShamChu
 * @Date 17/9/6 23:15
 */
public class TestPopupAdapter extends MenuPopupAdapter<String> {

    private List<String> mData;

    public TestPopupAdapter(List<String> data) {
        mData = data;
    }

    // 返回Item View视图对象
    @Override
    public View getView(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.test_item, null);
        TextView tv = (TextView) view.findViewById(R.id.label);
        tv.setText(getItem(position));
        return view;
    }

    /**
     * 获取item数
     *
     * @return item数量
     */
    @Override
    public int getItemCount() {
        return mData.size();
    }

    /**
     * 获取position位置的数据对象
     *
     * @param position 位置索引
     * @return 数据对象
     */
    @Override
    public String getItem(int position) {
        return mData.get(position);
    }

    public List<String> getData() {
        return mData;
    }
}
