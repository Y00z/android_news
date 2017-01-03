package org.yooz.moble.news.activitys;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.networkbench.agent.impl.NBSAppAgent;

import org.yooz.moble.news.MyApplication;
import org.yooz.moble.news.R;
import org.yooz.moble.news.fragment.ContentFragment;
import org.yooz.moble.news.fragment.LeftMenuFragment;

public class MainActivity extends SlidingFragmentActivity {

    private static final String FRAGMENT_LEFT_MENU = "fragment_left_menu";
    private static final String FRAGMENT_CONTENT = "fragment_content";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        NBSAppAgent.setLicenseKey("862ad402ac124c8ca2e056beebd42e92").withLocationServiceEnabled(true).start(this.getApplicationContext());
        MyApplication application = (MyApplication) getApplication();

        setBehindContentView(R.layout.fragment_left_menu);//设置左侧边栏布局

        SlidingMenu slidingMenu = getSlidingMenu();
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);  //全屏触摸
//        slidingMenu.setSecondaryMenu(R.layout.left_menu);//设置左侧边栏图片
//        slidingMenu.setMode(SlidingMenu.LEFT_RIGHT); //设置支持左右两边侧边栏。
        slidingMenu.setBehindOffset(100);  //设置住屏幕预留宽度
        initFragment();

    }

    public void initFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fl_left_menu, new LeftMenuFragment(), FRAGMENT_LEFT_MENU);
        fragmentTransaction.replace(R.id.fl_content, new ContentFragment(), FRAGMENT_CONTENT);
        fragmentTransaction.commit();
    }

    public LeftMenuFragment getLeftMenuFragment() {
        FragmentManager fm = getSupportFragmentManager();
        LeftMenuFragment fragment = (LeftMenuFragment) fm.findFragmentByTag(FRAGMENT_LEFT_MENU);
        return fragment;
    }

    public ContentFragment getContentFragment() {
        FragmentManager fm = getSupportFragmentManager();
        ContentFragment fragment = (ContentFragment) fm.findFragmentByTag(FRAGMENT_CONTENT);
        return fragment;
    }

}
