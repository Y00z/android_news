package org.yooz.moble.news.base.menudetail;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import org.yooz.moble.news.base.BaseMenuDetailPager;

/**
 * Created by Yooz on 2016/2/24.
 */
public class TopicMenuDetailPager extends BaseMenuDetailPager {

    public TopicMenuDetailPager(Activity activity) {
        super(activity);
    }

    @Override
    public View initViews() {
        TextView text = new TextView(mActivity);
        text.setText("菜单详情页-专题");
        text.setTextColor(Color.RED);
        text.setTextSize(22);
        text.setGravity(Gravity.CENTER);
        return text;
    }
}
