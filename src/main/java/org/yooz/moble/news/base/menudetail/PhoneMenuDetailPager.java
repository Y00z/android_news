package org.yooz.moble.news.base.menudetail;

import android.app.Activity;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.yooz.moble.news.R;
import org.yooz.moble.news.base.BaseMenuDetailPager;
import org.yooz.moble.news.bean.PhoneData;
import org.yooz.moble.news.global.GlobalContents;

/**
 * Created by Yooz on 2016/2/24.
 */
public class PhoneMenuDetailPager extends BaseMenuDetailPager {

    private ListView list_view;
    private GridView grid_view;
    private PhoneData phoneData;
    private ImageView iv_mode;
    private boolean isListShow = true;
    private final SharedPreferences mPref;

    public PhoneMenuDetailPager(Activity activity, ImageView iv_mode) {
        super(activity);
        this.iv_mode = iv_mode;
        mPref = mActivity.getSharedPreferences("config", mActivity.MODE_PRIVATE);
    }

    @Override
    public View initViews() {
        View view = View.inflate(mActivity, R.layout.phone_mode, null);
        grid_view = (GridView) view.findViewById(R.id.grid_view);
        list_view = (ListView) view.findViewById(R.id.list_view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        String cache = mPref.getString(GlobalContents.PHOTOS_URL, "");
        if(!TextUtils.isEmpty(cache)){
            parseData(cache);
        }
        getDataFromServet();
    }

    private void getDataFromServet() {
        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET, GlobalContents.PHOTOS_URL, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                mPref.edit().putString(GlobalContents.PHOTOS_URL,result);
                parseData(result);
            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
    }

    private void parseData(String result) {
        Gson gson = new Gson();
        phoneData = gson.fromJson(result, PhoneData.class);
        if (phoneData != null) {
            ImageListAdapter imageListAdapter = new ImageListAdapter();
            list_view.setAdapter(imageListAdapter);
            grid_view.setAdapter(imageListAdapter);
        }

        iv_mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeImageMode();
            }
        });
    }

    private void changeImageMode() {
        if(isListShow) {
            isListShow = false;
            list_view.setVisibility(View.GONE);
            grid_view.setVisibility(View.VISIBLE);
            iv_mode.setImageResource(R.mipmap.icon_pic_list_type);
        } else {
            isListShow = true;
            list_view.setVisibility(View.VISIBLE);
            grid_view.setVisibility(View.GONE);
            iv_mode.setImageResource(R.mipmap.icon_pic_grid_type);
        }
    }

    class ImageListAdapter extends BaseAdapter {

        private final BitmapUtils bitmapUtils;

        public ImageListAdapter() {
            bitmapUtils = new BitmapUtils(mActivity);
            bitmapUtils.configDefaultLoadingImage(R.mipmap.image_demo);
        }

        @Override
        public int getCount() {
            return phoneData.data.news.size();
        }

        @Override
        public Object getItem(int position) {
            return phoneData.data.news.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = View.inflate(mActivity, R.layout.item_phone, null);
                holder.iv_image = (ImageView) convertView.findViewById(R.id.iv_image);
                holder.iv_line = (ImageView) convertView.findViewById(R.id.iv_line);
                holder.tv_desc = (TextView) convertView.findViewById(R.id.tv_desc);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.tv_desc.setText(phoneData.data.news.get(position).title);
            bitmapUtils.display(holder.iv_image, phoneData.data.news.get(position).listimage);

            return convertView;
        }
    }

    static class ViewHolder {
        ImageView iv_image;
        ImageView iv_line;
        TextView tv_desc;
    }
}
