package com.dormvote.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dormvote.R;
import com.dormvote.app.AppConfig;
import com.dormvote.bean.PublicDataItem;
import com.dormvote.bean.RoomItemAndScore;
import com.dormvote.net.Macros;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Administrator on 2015/1/17.
 */
public class RoomDetailScoreDetailAdapter extends BaseAdapter {
    private Context context;
    private List<RoomItemAndScore> publicDataItems;
    private List<PublicDataItem> publicDataTypeOneVote;
    private String roomStatus;

    public RoomDetailScoreDetailAdapter(Context context, List<RoomItemAndScore> publicDataItemList,String roomStatus) {
        publicDataItems = publicDataItemList;
        this.context = context;
        publicDataTypeOneVote = new ArrayList<>();
        this.roomStatus = roomStatus;
        publicDataTypeOneVote.addAll(AppConfig.sharedAppConfig(context).getPublicDataType(AppConfig.PUBLIC_DATA_TYPE_NORMAL));
        publicDataTypeOneVote.addAll(AppConfig.sharedAppConfig(context).getPublicDataType(AppConfig.PUBLIC_DATA_TYPE_ONE_VOTE));
    }

    @Override
    public int getCount() {

        if(roomStatus.equals(Macros.ROOM_STATUS_PERFECT.getStatus()+"")||roomStatus.equals(Macros.ROOM_STATUS_QUALIFIED.getStatus()+"")||roomStatus.equals(Macros.ROOM_STATUS_UNQUALIFIED.getStatus()+"")){
            return publicDataItems.size();
        }else if(roomStatus.equals(Macros.ROOM_STATUS_NOBODY_IN_ON_FIRST_TURN.getStatus()+"")){
            return 1;
        }else if(roomStatus.equals(Macros.ROOM_STATUS_REFUSED_TO_CHECK.getStatus()+"")){
           return 1;
        }else if(roomStatus.equals(Macros.ROOM_STATUS_ONE_VOTE_VETO.getStatus()+"")){
            return publicDataItems.size();
        }
        return 0;
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

        if(roomStatus.equals(Macros.ROOM_STATUS_PERFECT.getStatus()+"")||roomStatus.equals(Macros.ROOM_STATUS_QUALIFIED.getStatus()+"")||roomStatus.equals(Macros.ROOM_STATUS_UNQUALIFIED.getStatus()+"")){
            ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(context, R.layout.activity_room_detail_score_detail, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }
            RoomItemAndScore item = publicDataItems.get(position);
            for (int i = 0; i < publicDataTypeOneVote.size(); i++) {
                if((item.getItemId()).equals(publicDataTypeOneVote.get(i).getItemId())){
                    holder.mTvRoomDetailItemContent.setText(publicDataTypeOneVote.get(i).getItemContent());
                }
            }
            holder.mTvRoomDetailItemScore.setText(item.getItemScore());
        }else if(roomStatus.equals(Macros.ROOM_STATUS_NOBODY_IN_ON_FIRST_TURN.getStatus()+"")){
            convertView = View.inflate(context,R.layout.activity_room_detail_reason_detail,null);
            TextView textView = (TextView) convertView.findViewById(R.id.tv_reason);
            textView.setText(Macros.ROOM_STATUS_NOBODY_IN_ON_FIRST_TURN.getExplain());
        }else if(roomStatus.equals(Macros.ROOM_STATUS_REFUSED_TO_CHECK.getStatus()+"")){
            convertView = View.inflate(context,R.layout.activity_room_detail_reason_detail,null);
            TextView textView = (TextView) convertView.findViewById(R.id.tv_reason);
            textView.setText(Macros.ROOM_STATUS_REFUSED_TO_CHECK.getExplain());
        }else if(roomStatus.equals(Macros.ROOM_STATUS_ONE_VOTE_VETO.getStatus()+"")){
            convertView = View.inflate(context, R.layout.activity_room_detail_reason_detail, null);
            TextView textView = (TextView) convertView.findViewById(R.id.tv_reason);
            RoomItemAndScore item = publicDataItems.get(position);
            for (int i = 0; i < publicDataTypeOneVote.size(); i++) {
                if((item.getItemId()).equals(publicDataTypeOneVote.get(i).getItemId())){
                    textView.setText(publicDataTypeOneVote.get(i).getItemContent());
                }
            }
        }

        return convertView;
    }

    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'activity_room_detail_score_detail.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Inmite Developers (http://inmite.github.io)
     */
    static class ViewHolder {
        @InjectView(R.id.tv_room_detail_item_score)
        TextView mTvRoomDetailItemScore;
        @InjectView(R.id.tv_room_detail_item_content)
        TextView mTvRoomDetailItemContent;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
