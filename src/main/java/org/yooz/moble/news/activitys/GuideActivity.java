package org.yooz.moble.news.activitys;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import org.yooz.moble.news.R;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends Activity {

    private final static int mImas[] = new int[]{R.mipmap.guide_1, R.mipmap.guide_2, R.mipmap.guide_3};
    private List<ImageView> mImageViewLists;
    private LinearLayout ll_point_group;
    private int mPointWidth;
    private View view_red_point;
    private Button btn_start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
        initData();
    }

    private void initUI() {
        setContentView(R.layout.activity_guide);
        ViewPager view_pager = (ViewPager) findViewById(R.id.view_pager);
        ll_point_group = (LinearLayout) findViewById(R.id.ll_point_group);
        view_red_point = findViewById(R.id.view_red_point);
        btn_start = (Button) findViewById(R.id.btn_start);
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences mPre = getSharedPreferences("config", MODE_PRIVATE);
                mPre.edit().putBoolean("is_user_guide_showed", true).commit();
                startActivity(new Intent(GuideActivity.this, MainActivity.class));
                finish();
            }
        });
        view_pager.setAdapter(new GuidePagerAdapter());
        view_pager.addOnPageChangeListener(new GuidePageListener());
    }

    private class GuidePagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mImas.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mImageViewLists.get(position));
            return mImageViewLists.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    //ViewPager滑动事件
    private class GuidePageListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            // System.out.println("当前位置:" + position + ";百分比:" + positionOffset
            // + ";移动距离:" + positionOffsetPixels);
            int len = (int) (mPointWidth * positionOffset) + position
                    * mPointWidth;
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view_red_point
                    .getLayoutParams();// 获取当前红点的布局参数
            params.leftMargin = len;// 设置左边距

            view_red_point.setLayoutParams(params);// 重新给小红点设置布局参数
        }

        @Override
        public void onPageSelected(int position) {
            if (position == mImas.length - 1) {
                btn_start.setVisibility(View.VISIBLE);
            } else {
                btn_start.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    private void initData() {
        mImageViewLists = new ArrayList<ImageView>();
        // 初始化引导页的3个页面
        for (int i = 0; i < mImas.length; i++) {
            ImageView image = new ImageView(this);
            image.setBackgroundResource(mImas[i]);
            mImageViewLists.add(image);
        }

        //初始化小圆点
        for (int i = 0; i < mImas.length; i++) {
            View point = new View(this);
            point.setBackgroundResource(R.drawable.shape_point_gray);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(10, 10);
            if (i > 0) {
                params.leftMargin = 10;
            }
            point.setLayoutParams(params);
            ll_point_group.addView(point);

            // 获取视图树, 对layout结束事件进行监听
            ll_point_group.getViewTreeObserver().addOnGlobalLayoutListener(
                    new ViewTreeObserver.OnGlobalLayoutListener() {

                        // 当layout执行结束后回调此方法
                        @Override
                        public void onGlobalLayout() {
                            System.out.println("layout 结束");
                            ll_point_group.getViewTreeObserver()
                                    .removeGlobalOnLayoutListener(this);
                            mPointWidth = ll_point_group.getChildAt(1).getLeft()
                                    - ll_point_group.getChildAt(0).getLeft();
                            System.out.println("圆点距离:" + mPointWidth);
                        }
                    });
        }
    }
}
