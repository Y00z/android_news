package org.yooz.moble.news.bean;

import java.util.ArrayList;

/**
 * Created by Yooz on 2016/2/23.
 */
public class NewsData {
    public int retcode;
    public ArrayList<NewsMenuData> data;

    public class NewsMenuData{
        public int id;
        public String title;
        public int type;
        public String url;
        public ArrayList<NewsCityData> children;

        @Override
        public String toString() {
            return "NewsMenuData{" +
                    "title='" + title + '\'' +
                    ", children=" + children +
                    '}';
        }
    }

    public class NewsCityData{
        public int id;
        public String title;
        public int type;
        public String url;

        @Override
        public String toString() {
            return "NewsCityData{" +
                    "title='" + title + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "NewsData{" +
                "data=" + data +
                '}';
    }
}
