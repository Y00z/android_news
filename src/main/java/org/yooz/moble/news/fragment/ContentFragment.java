package org.yooz.moble.news.fragment;


import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import org.yooz.moble.news.R;
import org.yooz.moble.news.base.BasePager;
import org.yooz.moble.news.base.implepager.GovPager;
import org.yooz.moble.news.base.implepager.HomePager;
import org.yooz.moble.news.base.implepager.NewsPager;
import org.yooz.moble.news.base.implepager.SettingPager;
import org.yooz.moble.news.base.implepager.SmartPager;
import org.yooz.moble.news.view.NoScrollViewPager;

import java.util.ArrayList;

public class ContentFragment extends BaseFragment {

    private RadioGroup rg_group;
    private NoScrollViewPager view_pager;
    private ArrayList<BasePager> mPagerLists;

    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.fragment_content, null);
        rg_group = (RadioGroup) view.findViewById(R.id.rg_group);
        view_pager = (NoScrollViewPager) view.findViewById(R.id.view_pager);
        return view;
    }

    public void initData() {
        rg_group.check(R.id.rb_home);
        mPagerLists = new ArrayList<BasePager>();
//        for(int i=0; i<5 ;i++){
//            BasePager pager = new BasePager(mActivity);
//            mPagerLists.add(pager);
//        }
        mPagerLists.add(new HomePager(mActivity));
        mPagerLists.add(new NewsPager(mActivity));
        mPagerLists.add(new SmartPager(mActivity));
        mPagerLists.add(new GovPager(mActivity));
        mPagerLists.add(new SettingPager(mActivity));

        view_pager.setAdapter(new ContentViewPagerAdapter());

        rg_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_home:
                        view_pager.setCurrentItem(0);
                        break;
                    case R.id.rb_news:
                        view_pager.setCurrentItem(1);
                        break;
                    case R.id.rb_smart:
                        view_pager.setCurrentItem(2);
                        break;
                    case R.id.rb_gov:
                        view_pager.setCurrentItem(3);
                        break;
                    case R.id.rb_setting:
                        view_pager.setCurrentItem(4);
                        break;
                }
            }
        });

        view_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mPagerLists.get(position).initData();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mPagerLists.get(0).initData();
    }

    class ContentViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mPagerLists.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            BasePager pager = mPagerLists.get(position);
            container.addView(pager.mRootView);
//            pager.initData();
            return pager.mRootView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    public NewsPager getNewsPager(){
        return (NewsPager) mPagerLists.get(1);
    }
}
