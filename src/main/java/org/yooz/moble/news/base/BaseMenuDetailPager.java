package org.yooz.moble.news.base;

import android.app.Activity;
import android.view.View;

/**
 * Created by Yooz on 2016/2/24.
 */
public abstract class BaseMenuDetailPager {

    public  Activity mActivity;
    public View mRootView;
    public BaseMenuDetailPager(Activity activity){
        this.mActivity = activity;
        mRootView = initViews();
    }

    public abstract View initViews();

    public void initData(){

    }
}
