package org.yooz.moble.news;

import android.app.Application;
import android.widget.Toast;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Yooz on 2016/3/6.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        Toast.makeText(getApplicationContext(),"推送",0).show();
    }
}
