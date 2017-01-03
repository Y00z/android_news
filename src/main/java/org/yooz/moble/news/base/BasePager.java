package org.yooz.moble.news.base;

import android.app.Activity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import org.yooz.moble.news.R;
import org.yooz.moble.news.activitys.MainActivity;

import java.util.ArrayList;

/**
 * Created by Yooz on 2016/2/23.
 */
public class BasePager {

    public Activity mActivity;
    public View mRootView;
    public TextView tv_title;
    public FrameLayout fl_content;
    public ImageButton bgn_menu;
    public ArrayList<BaseMenuDetailPager> menuDetailPagerLists;
    public ImageView iv_mode;

    public BasePager(Activity activity) {
        this.mActivity = activity;
        initView();
    }

    public void initView() {
        mRootView = View.inflate(mActivity, R.layout.base_pager, null);
        tv_title = (TextView) mRootView.findViewById(R.id.tv_title);
        fl_content = (FrameLayout) mRootView.findViewById(R.id.fl_content);
        bgn_menu = (ImageButton) mRootView.findViewById(R.id.bgn_menu);
        iv_mode = (ImageView) mRootView.findViewById(R.id.iv_mode);
        bgn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleSlidingMenu();
            }
        });
    }

    public void toggleSlidingMenu() {
        MainActivity mainUi = (MainActivity) mActivity;
        SlidingMenu slidingMenu = mainUi.getSlidingMenu();
        slidingMenu.toggle();
    }

    public void initData() {

    }

    public void setSlidingMenuEnable(boolean enable) {
        MainActivity mainUi = (MainActivity) mActivity;
        if (enable) {
            mainUi.getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        } else {
            mainUi.getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        }

    }

}
