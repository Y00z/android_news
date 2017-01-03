package org.yooz.moble.news.base.menudetail;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.viewpagerindicator.TabPageIndicator;

import org.yooz.moble.news.R;
import org.yooz.moble.news.activitys.MainActivity;
import org.yooz.moble.news.base.BaseMenuDetailPager;
import org.yooz.moble.news.base.TabDetailPater;
import org.yooz.moble.news.bean.NewsData;

import java.util.ArrayList;

/**
 * Created by Yooz on 2016/2/24.
 */
public class NewsMenuDetailPager extends BaseMenuDetailPager {

    private ViewPager view_pager;
    private ArrayList<TabDetailPater> tabDetailPaterLists;
    private ArrayList<NewsData.NewsCityData> cityDataLists;
    private TabPageIndicator indicator;

    public NewsMenuDetailPager(Activity activity, ArrayList<NewsData.NewsCityData> children) {
        super(activity);
        cityDataLists = children;
    }

    @Override
    public View initViews() {
        View view = View.inflate(mActivity, R.layout.news_menu_detail, null);
        view_pager = (ViewPager) view.findViewById(R.id.view_pager);
        indicator = (TabPageIndicator) view.findViewById(R.id.indicator);
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                MainActivity mainUi = (MainActivity) mActivity;
                SlidingMenu slidingMenu = mainUi.getSlidingMenu();
                if(position==0){
                    slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
                } else {
                    slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        return view;
    }

    @Override
    public void initData() {
        Log.e("NewsMenuDetailPager", "initData");
        tabDetailPaterLists = new ArrayList<TabDetailPater>();

        for (int i = 0; i < cityDataLists.size(); i++) {
            TabDetailPater tabDetailPater = new TabDetailPater(mActivity, cityDataLists.get(i));
            tabDetailPaterLists.add(tabDetailPater);
        }
        MenuDetailAdapter menuDetailAdapter = new MenuDetailAdapter();
        view_pager.setAdapter(menuDetailAdapter);
        indicator.setViewPager(view_pager);

    }

    private class MenuDetailAdapter extends PagerAdapter {

        @Override
        public CharSequence getPageTitle(int position) {
            return cityDataLists.get(position).title;
        }

        @Override
        public int getCount() {
            return tabDetailPaterLists.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            TabDetailPater tabDetailPater = tabDetailPaterLists.get(position);
            container.addView(tabDetailPater.mRootView);
            tabDetailPater.initData();
            return tabDetailPater.mRootView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }


}
