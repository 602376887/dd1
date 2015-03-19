package com.dormvote.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dormvote.R;
import com.dormvote.app.AppConfig;
import com.dormvote.bean.Picture;
import com.dormvote.bean.PublicDataItem;
import com.dormvote.bean.RoomItemAndScore;
import com.dormvote.net.Macros;
import com.dormvote.ui.UpPicActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Administrator on 2015/1/17.
 */
public class RoomDetailPicAdapter extends BaseAdapter {
    private Context context;
    private List<Picture> pictures;
    protected ImageLoader imageLoader = ImageLoader.getInstance();

    protected DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showImageOnLoading(R.drawable.ic_launcher)
            .showImageForEmptyUri(R.drawable.ic_launcher)
            .showImageOnFail(R.drawable.ic_launcher).cacheInMemory(true)
            .cacheOnDisk(true).considerExifParams(true).build();
    public RoomDetailPicAdapter(Context context, List<Picture> pictures) {
        this.context = context;
        this.pictures = pictures;
    }

    @Override
    public int getCount() {
        return pictures.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Picture picture = pictures.get(position);
        ImageView imageView = new ImageView(context);
        imageView.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT,AbsListView.LayoutParams.MATCH_PARENT));
        imageView.setImageResource(R.drawable.ic_launcher);
        imageLoader.displayImage(picture.getUrl(),imageView,options);

        return imageView;
    }
}
//http://7sbyh6.com1.z0.glb.clouddn.com/d11d8b22-25b5-46c7-9cb2-c55e1d393d36.jpg?e=1421719513&token=DKpodtt-tppCw5Qt9HZQU4rrfrmfPoho59pEmu_C:ucJl8rTMiJY1ASrVj8Jy2mwHwts=