package org.yooz.moble.news.bean;

import java.util.ArrayList;

/**
 * Created by Yooz on 2016/3/3.
 */
public class PhoneData {
    public int retcode;
    public PhoneDetailData data;

    public class PhoneDetailData{
        public ArrayList<PhoneNewsData> news;
    }

    public class PhoneNewsData{
        public String listimage;
        public String title;
        public String id;
        public String pubdate;
    }
}
