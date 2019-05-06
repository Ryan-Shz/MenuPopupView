package com.ryan.github.popup.sample;

import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ryan.github.popup.MenuPopupAdapter;
import com.ryan.github.popup.PopupLayout;
import com.ryan.github.popup.PopupView;
import com.sc.sample.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Handler handler = new Handler(Looper.getMainLooper());
    EditText menuNameEditText;
    List<String> menus = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        menus.add("default");

        menuNameEditText = (EditText) findViewById(R.id.menu_name_ed);
        findViewById(R.id.add_menu_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String menuName = menuNameEditText.getText().toString().trim();
                if (menuName.equals("")) {
                    Toast.makeText(v.getContext(), "menu name can not be blank", Toast.LENGTH_SHORT).show();
                    return;
                }
                menus.add(menuName);
            }
        });

        findViewById(R.id.popup_up_above_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupView view = getPopupView();
                view.setPopupLocation(PopupLayout.PopupLocation.TOP);
                view.show(v);
            }
        });
        findViewById(R.id.popup_up_below_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupView view = getPopupView();
                view.setPopupLocation(PopupLayout.PopupLocation.Bottom);
                view.show(v);
            }
        });
        findViewById(R.id.update_menus_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PopupView view = getPopupView();
                view.setPopupLocation(PopupLayout.PopupLocation.Bottom);
                view.show(v);
                Toast.makeText(v.getContext(), "Will update after 2 seconds", Toast.LENGTH_SHORT).show();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        TestPopupAdapter adapter = (TestPopupAdapter) view.getAdapter();
                        adapter.getData().add("nice item");
                        adapter.notifyDataSetChanged();
                    }
                }, 2000);
            }
        });
        findViewById(R.id.custom_menus_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupView popupView = getCustomPopupView();
                popupView.show(v);
            }
        });
        findViewById(R.id.clear_menus_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menus.clear();
            }
        });
    }

    public PopupView getPopupView() {
        final MenuPopupAdapter<String> adapter = new TestPopupAdapter(menus);
        final PopupView popupView = new PopupView(this);
        popupView.setAdapter(adapter);
        popupView.setOnPopupItemClickListener(new PopupLayout.OnPopupItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(view.getContext(), adapter.getItem(position), Toast.LENGTH_SHORT).show();
                popupView.dismiss();
            }
        });
        return popupView;
    }

    public PopupView getCustomPopupView() {
        List<TestMenuBean> data = new ArrayList<>();
        data.add(new TestMenuBean("report"));
        data.add(new TestMenuBean("copy"));
        data.add(new TestMenuBean("thumb up"));
        data.add(new TestMenuBean("thumb up"));
        final MenuPopupAdapter<TestMenuBean> adapter = new TestCustomAdapter(data);
        final PopupView popupView = new PopupView(this);
        popupView.setAdapter(adapter);
        popupView.setOnPopupItemClickListener(new PopupLayout.OnPopupItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(view.getContext(), adapter.getItem(position).getMenuText(), Toast.LENGTH_SHORT).show();
                popupView.dismiss();
            }
        });
        return popupView;
    }
}
