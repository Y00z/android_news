package org.yooz.moble.news.base.implepager;

import android.app.Activity;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.yooz.moble.news.activitys.MainActivity;
import org.yooz.moble.news.base.BaseMenuDetailPager;
import org.yooz.moble.news.base.BasePager;
import org.yooz.moble.news.base.menudetail.InteractMenuDetailPager;
import org.yooz.moble.news.base.menudetail.NewsMenuDetailPager;
import org.yooz.moble.news.base.menudetail.PhoneMenuDetailPager;
import org.yooz.moble.news.base.menudetail.TopicMenuDetailPager;
import org.yooz.moble.news.bean.NewsData;
import org.yooz.moble.news.fragment.LeftMenuFragment;
import org.yooz.moble.news.global.GlobalContents;

import java.util.ArrayList;

public class NewsPager extends BasePager {
    private NewsData newsData;
    private SharedPreferences mPref;


    public NewsPager(Activity activity) {
        super(activity);
        mPref = mActivity.getSharedPreferences("config", mActivity.MODE_PRIVATE);
    }

    public void initData() {
        super.initData();
        tv_title.setText("新闻中心");
        setSlidingMenuEnable(true);
        String cache = mPref.getString(GlobalContents.URL_CONTENTS, "");
        if(!TextUtils.isEmpty(cache)) {
            parsData(cache);
        }
        getDataFormServer();
    }

    public void getDataFormServer() {

        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET, GlobalContents.URL_CONTENTS, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                mPref.edit().putString(GlobalContents.URL_CONTENTS,result);
                parsData(result);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Log.e("Menu_ITEM", "网络错误");
                Toast.makeText(mActivity, "网络错误+" + s, Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        });
    }

    public void parsData(String result) {
        Gson gson = new Gson();
        newsData = gson.fromJson(result, NewsData.class);

        MainActivity mainUi = (MainActivity) mActivity;
        LeftMenuFragment leftMenuFragment = mainUi.getLeftMenuFragment();
        leftMenuFragment.setMenuData(newsData);

        menuDetailPagerLists = new ArrayList<BaseMenuDetailPager>();
        menuDetailPagerLists.add(new NewsMenuDetailPager(mActivity, newsData.data.get(0).children));
        menuDetailPagerLists.add(new TopicMenuDetailPager(mActivity));
        menuDetailPagerLists.add(new PhoneMenuDetailPager(mActivity,iv_mode));
        menuDetailPagerLists.add(new InteractMenuDetailPager(mActivity));

        setCurrentMenuDetailPager(0);
    }

    public void setCurrentMenuDetailPager(int position) {
        BaseMenuDetailPager baseMenuDetailPager = menuDetailPagerLists.get(position);
        fl_content.removeAllViews();

        if (baseMenuDetailPager instanceof PhoneMenuDetailPager ){
            iv_mode.setVisibility(View.VISIBLE);
        } else {
            iv_mode.setVisibility(View.GONE);
        }

        fl_content.addView(baseMenuDetailPager.mRootView);
        NewsData.NewsMenuData newsMenuData = newsData.data.get(position);
        tv_title.setText(newsMenuData.title);
        baseMenuDetailPager.initData();
    }
}
