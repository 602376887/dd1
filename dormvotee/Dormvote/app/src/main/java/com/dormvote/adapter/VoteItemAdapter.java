package com.dormvote.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SeekBar;
import android.widget.TextView;

import com.dormvote.R;
import com.dormvote.bean.PublicDataItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Administrator on 2015/1/11.
 */
public class VoteItemAdapter extends BaseAdapter {
    Context context;
    List<PublicDataItem> dataItemList;
    List<Integer> scores = new ArrayList<>();

    public VoteItemAdapter(Context context, List<PublicDataItem> dataList) {
        this.context = context;
        this.dataItemList = dataList;
        for (int i = 0; i < dataItemList.size(); i++) {
            scores.add(i,Integer.parseInt(dataItemList.get(i).getItemValue())/2);
        }
    }

    @Override
    public int getCount() {
        return dataItemList.size();
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
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.activity_vote_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        PublicDataItem dataItem = dataItemList.get(position);
        holder.tvItemName.setText(dataItem.getItemName());
        holder.tvVoteDetail.setText(dataItem.getItemContent());
        holder.seekBar.setMax(Integer.parseInt(dataItem.getItemValue()));
        holder.seekBar.setProgress(scores.get(position));
        holder.tvProgress.setText(scores.get(position)+"");

        final TextView tvProgress = holder.tvProgress;
        final int pos = position;

        holder.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvProgress.setText(progress + "");

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                scores.remove(pos);
                scores.add(pos,seekBar.getProgress());
            }
        });
        return convertView;
    }


    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'activity_vote_item.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
     */
    static  class ViewHolder {
        @InjectView(R.id.tv_item_name)
         TextView tvItemName;
        @InjectView(R.id.tv_vote_detail)
        TextView tvVoteDetail;
        @InjectView(R.id.seekBar)
        SeekBar seekBar;
        @InjectView(R.id.tv_progress)
         TextView tvProgress;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
