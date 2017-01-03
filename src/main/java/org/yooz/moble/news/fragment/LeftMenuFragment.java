package org.yooz.moble.news.fragment;


import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import org.yooz.moble.news.R;
import org.yooz.moble.news.activitys.MainActivity;
import org.yooz.moble.news.base.implepager.NewsPager;
import org.yooz.moble.news.bean.NewsData;

import java.util.ArrayList;


public class LeftMenuFragment extends BaseFragment {

    private ListView list_view;
    private ArrayList<NewsData.NewsMenuData> menuLists;
    private int mCrrentPos;
    private MyAdapater myAdapater;

    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.fragment_left_menu, null);
        list_view = (ListView) view.findViewById(R.id.list_view);
        return view;
    }

    @Override
    public void initData() {
        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCrrentPos = position;
                myAdapater.notifyDataSetChanged();
                setCurrentMenuDetailPager(position);
                toggleSlidingMenu();
            }
        });
    }

    public void setCurrentMenuDetailPager(int position) {
        MainActivity mainUi = (MainActivity) mActivity;
        ContentFragment contentFragment = mainUi.getContentFragment();
        NewsPager newsPager = contentFragment.getNewsPager();
        newsPager.setCurrentMenuDetailPager(position);
    }

    private void toggleSlidingMenu(){
        MainActivity mainUi = (MainActivity) mActivity;
        SlidingMenu slidingMenu = mainUi.getSlidingMenu();
        slidingMenu.toggle();
    }

    public void setMenuData(NewsData newsData){
        menuLists = newsData.data;
        System.out.println(newsData.data);
        myAdapater = new MyAdapater();
        list_view.setAdapter(myAdapater);
    }


    class MyAdapater extends BaseAdapter{

        @Override
        public int getCount() {
            return menuLists.size();
        }

        @Override
        public Object getItem(int position) {
            return menuLists.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(mActivity, R.layout.item_left_menu, null);
            TextView tv_title = (TextView) view.findViewById(R.id.tv);
            NewsData.NewsMenuData newsMenuData = menuLists.get(position);
            if(mCrrentPos == position) {
                tv_title.setEnabled(true);
            } else {
                tv_title.setEnabled(false);
            }
            tv_title.setText(newsMenuData.title);
            return view;
        }
    }
}
