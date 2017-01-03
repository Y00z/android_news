package org.yooz.moble.news.base.implepager;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import org.yooz.moble.news.base.BasePager;

public class HomePager extends BasePager {

    public HomePager(Activity activity) {
        super(activity);
    }

    public void initData() {
        super.initData();


        tv_title.setText("智慧武汉");
        bgn_menu.setVisibility(View.GONE);
        setSlidingMenuEnable(false);
        TextView text = new TextView(mActivity);
        text.setText("首页");
        text.setTextColor(Color.RED);
        text.setTextSize(22);
        text.setGravity(Gravity.CENTER);
        fl_content.addView(text);
    }
}
