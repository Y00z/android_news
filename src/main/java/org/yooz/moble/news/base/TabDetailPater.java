package org.yooz.moble.news.base;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.viewpagerindicator.CirclePageIndicator;

import org.yooz.moble.news.R;
import org.yooz.moble.news.activitys.NewDetailActivity;
import org.yooz.moble.news.bean.NewsData;
import org.yooz.moble.news.bean.TabData;
import org.yooz.moble.news.global.GlobalContents;
import org.yooz.moble.news.view.RefreshListView;

import java.util.ArrayList;

/**
 * Created by Yooz on 2016/2/25.
 */
public class TabDetailPater extends BaseMenuDetailPager {

    private NewsData.NewsCityData newsCityData;
    private String mUrl;
    private String mMoveUrl;
    private TabData tabData;
    private ViewPager view_pager;
    private TextView tv_title;
    private CirclePageIndicator indicator;
    private RefreshListView list_view;
    private ArrayList<TabData.TabNewsData> mNewsLists;
    private NewsAdapter adapter;
    private SharedPreferences mPre;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int currentItem = view_pager.getCurrentItem();
            if (currentItem < tabData.data.topnews.size() - 1) {
                currentItem++;
            } else {
                currentItem = 0;
            }
            view_pager.setCurrentItem(currentItem);
            handler.sendEmptyMessageDelayed(0, 3000);
        }
    };

    public TabDetailPater(Activity activity, NewsData.NewsCityData newsCityData) {
        super(activity);
        this.newsCityData = newsCityData;
        mUrl = GlobalContents.SERVER_URL + newsCityData.url;
    }

    @Override
    public View initViews() {
        View view = View.inflate(mActivity, R.layout.tab_detail_pager, null);
        View header = View.inflate(mActivity, R.layout.header_news_list, null);
        mPre = mActivity.getSharedPreferences("config", mActivity.MODE_PRIVATE);
        tv_title = (TextView) header.findViewById(R.id.tv_title);
        indicator = (CirclePageIndicator) header.findViewById(R.id.indicator);
        System.out.println("ss");
        list_view = (RefreshListView) view.findViewById(R.id.list_view);
        view_pager = (ViewPager) header.findViewById(R.id.view_pager);
        list_view.addHeaderView(header);

        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String ids = mPre.getString("read_ids", "");
                String readId = mNewsLists.get(position).id;
                System.out.println(readId);
                System.out.println(ids + ":ids");
                if (!ids.contains(readId)) {
                    ids = ids + readId + ",";
                    System.out.println("----------------------");
                    mPre.edit().putString("read_ids", ids).commit();
                }

                changeReadTitleColor(view);


                Intent intent = new Intent(mActivity, NewDetailActivity.class);
                intent.putExtra("url", mNewsLists.get(position).url);
                mActivity.startActivity(intent);
            }
        });

        view_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tv_title.setText(tabData.data.topnews.get(position).title);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        list_view.setOnRefreshListener(new RefreshListView.OnRefreshListener() {
            @Override   //刷新
            public void onRefresh() {
                getDataFromServer();
            }

            @Override   //加载更多
            public void onLoadMore() {
                if (mMoveUrl != null) {
                    getMoveDataFromServer();
                } else {
                    Toast.makeText(mActivity, "已经最后一页了", Toast.LENGTH_SHORT).show();
                    list_view.onRefreshComplete(false);
                }
            }
        });
        return view;
    }

    private void changeReadTitleColor(View view) {
        TextView tv_content = (TextView) view.findViewById(R.id.tv_content);
        tv_content.setTextColor(Color.GRAY);
    }

    private class NewsAdapter extends BaseAdapter {

        private final BitmapUtils utils;

        public NewsAdapter() {
            utils = new BitmapUtils(mActivity);
            utils.configDefaultLoadingImage(R.mipmap.pic_item_list_default);
        }

        @Override
        public int getCount() {
            return mNewsLists.size();
        }

        @Override
        public Object getItem(int position) {
            return mNewsLists.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(mActivity, R.layout.item_news, null);
                holder = new ViewHolder();
                holder.iv_image = (ImageView) convertView.findViewById(R.id.iv_image);
                holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
                holder.tv_content = (TextView) convertView.findViewById(R.id.tv_content);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tv_time.setText(mNewsLists.get(position).pubdate);
            holder.tv_content.setText(mNewsLists.get(position).title);
            utils.display(holder.iv_image, mNewsLists.get(position).listimage);

            String read_ids = mPre.getString("read_ids", "");
            if (read_ids.contains(mNewsLists.get(position).id)) {
                holder.tv_content.setTextColor(Color.GRAY);
                System.out.println("灰色灰色灰色灰色灰色灰色-----");
            } else {
                holder.tv_content.setTextColor(Color.BLACK);
                System.out.println("黑色黑色黑色黑色黑色黑色-----");
            }

            return convertView;
        }

    }


    static class ViewHolder {
        ImageView iv_image;
        TextView tv_time;
        TextView tv_content;
    }

    @Override
    public void initData() {
        super.initData();
        getDataFromServer();
    }

    private void getDataFromServer() {

        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET, mUrl, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                parseData(result, false);

                list_view.onRefreshComplete(true);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                list_view.onRefreshComplete(false);
                Toast.makeText(mActivity, s, Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void getMoveDataFromServer() {
        HttpUtils utils = new HttpUtils();
        System.out.println("getMoveDataFromServer" + mUrl);
        utils.send(HttpRequest.HttpMethod.GET, mMoveUrl, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                parseData(result, true);
                list_view.onRefreshComplete(true);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                list_view.onRefreshComplete(false);
                Toast.makeText(mActivity, s, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void parseData(String result, boolean flag) {
        Gson gson = new Gson();
        tabData = gson.fromJson(result, TabData.class);
        String more = tabData.data.more;
        System.out.println("more:" + more);
        System.out.println("新闻" + mNewsLists);
        if (!TextUtils.isEmpty(more)) {
            mMoveUrl = GlobalContents.SERVER_URL + more;
        } else {
            mMoveUrl = null;
        }

        if (!flag) {
            mNewsLists = tabData.data.news;

            tv_title.setText(tabData.data.topnews.get(0).title);
            view_pager.setAdapter(new TopNewsAdapter());
            indicator.setViewPager(view_pager);
            indicator.setSnap(true);
            indicator.onPageSelected(0);

            if (mNewsLists != null) {
                adapter = new NewsAdapter();
                list_view.setAdapter(adapter);
            }
        } else {
            ArrayList<TabData.TabNewsData> news = tabData.data.news;
            mNewsLists.addAll(news);
            adapter.notifyDataSetChanged();
        }

        handler.sendEmptyMessageDelayed(0, 3000);
    }

    class TopNewsAdapter extends PagerAdapter {

        private final BitmapUtils utils;

        public TopNewsAdapter() {
            utils = new BitmapUtils(mActivity);
            utils.configDefaultLoadingImage(R.mipmap.topnews_item_default);
        }

        @Override
        public int getCount() {
            return tabData.data.topnews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView image = new ImageView(mActivity);
            image.setScaleType(ImageView.ScaleType.FIT_XY);
            TabData.TabTopNewsData tabTopNewsData = tabData.data.topnews.get(position);
            utils.display(image, tabTopNewsData.topimage);
            container.addView(image);
            ViewPagerOnTouchListener viewPagerOnTouchListener = new ViewPagerOnTouchListener();
            image.setOnTouchListener(viewPagerOnTouchListener);
            return image;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    class ViewPagerOnTouchListener implements View.OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    handler.removeCallbacksAndMessages(null);
                    break;
                case MotionEvent.ACTION_CANCEL:
                    handler.sendEmptyMessageDelayed(0, 3000);
                    break;
                case MotionEvent.ACTION_UP:
                    handler.sendEmptyMessageDelayed(0, 3000);
                    break;
            }
            return true;
        }
    }


}
