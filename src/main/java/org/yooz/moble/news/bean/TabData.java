package org.yooz.moble.news.bean;

import java.util.ArrayList;

/**
 * Created by Yooz on 2016/2/26.
 */
public class TabData {
    public int retcode;

    public TabDetailData data;

    public class TabDetailData {
        public String countcommenturl;
        public String more;
        public String title;
        public ArrayList<TabNewsData> news;
        public ArrayList<TabTopData> topic;
        public ArrayList<TabTopNewsData> topnews;

        @Override
        public String toString() {
            return "TabDetailData{" +
                    "title='" + title + '\'' +
                    ", news=" + news +
                    ", topic=" + topic +
                    ", topnews=" + topnews +
                    '}';
        }
    }

    public class TabNewsData {
        public String id;
        public String listimage;
        public String pubdate;
        public String title;
        public String url;
        public String type;

        @Override
        public String toString() {
            return "TabNewsData{" +
                    "title='" + title + '\'' +
                    '}';
        }
    }

    public class TabTopData {
        public int id;
        public String listimage;
        public String title;
        public String url;
        public String description;

        @Override
        public String toString() {
            return "TabTopData{" +
                    "title='" + title + '\'' +
                    ", description='" + description + '\'' +
                    '}';
        }
    }

    public class TabTopNewsData {
        public int id;
        public String topimage;
        public String pubdate;
        public String title;
        public String url;
        public String type;

        @Override
        public String toString() {
            return "TabTopNewsData{" +
                    "title='" + title + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "TabData{" +
                "data=" + data +
                '}';
    }
}
