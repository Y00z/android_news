package org.yooz.moble.news.base.implepager;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;

import org.yooz.moble.news.base.BasePager;

public class GovPager extends BasePager {

    public GovPager(Activity activity) {
        super(activity);
    }

    public void initData() {
        super.initData();
        tv_title.setText("人口管理");
        setSlidingMenuEnable(true);
        TextView text = new TextView(mActivity);
        text.setText("政务");
        text.setTextColor(Color.RED);
        text.setTextSize(22);
        text.setGravity(Gravity.CENTER);
        fl_content.addView(text);
    }
}
