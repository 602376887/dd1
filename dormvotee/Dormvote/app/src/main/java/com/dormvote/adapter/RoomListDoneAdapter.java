package com.dormvote.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dormvote.R;
import com.dormvote.bean.Room;
import com.dormvote.net.Macros;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Administrator on 2015/1/11.
 */
public class RoomListDoneAdapter extends BaseAdapter {
    private List<Room> rooms;
    private Context context;

    public RoomListDoneAdapter(Context context, List<Room> rooms) {
        this.context = context;
        this.rooms = rooms;
    }

    @Override
    public int getCount() {
        return rooms.size();
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
        if (convertView==null) {
            convertView = View.inflate(context, R.layout.activity_room_list_done_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        Room room = rooms.get(position);
        holder.mTvPicNum.setText(room.getRoomPic().size()+"张照片");
        holder.mTvRoomName.setText(room.getRoomTitle());
        holder.mTvRoomScore.setText(room.getRoomScore());
        String roomStatus = room.getRoomStatus();
        if(roomStatus.equals(Macros.ROOM_STATUS_QUALIFIED.getStatus() + "")){
            holder.mTvRoomStatus.setText(Macros.ROOM_STATUS_QUALIFIED.getExplain());
        }else if(roomStatus.equals(Macros.ROOM_STATUS_PERFECT.getStatus() + "")){
            holder.mTvRoomStatus.setText(Macros.ROOM_STATUS_PERFECT.getExplain());
        }else if(roomStatus.equals(Macros.ROOM_STATUS_UNQUALIFIED.getStatus() + "")){
            holder.mTvRoomStatus.setText(Macros.ROOM_STATUS_UNQUALIFIED.getExplain());
        }else if(roomStatus.equals(Macros.ROOM_STATUS_REFUSED_TO_TAKE_PICTURES.getStatus() + "")){
            holder.mTvRoomStatus.setText(Macros.ROOM_STATUS_REFUSED_TO_TAKE_PICTURES.getExplain());
        }else if(roomStatus.equals(Macros.ROOM_STATUS_NOBODY_IN_ON_FIRST_TURN.getStatus() + "")){
            holder.mTvRoomStatus.setText(Macros.ROOM_STATUS_NOBODY_IN_ON_FIRST_TURN.getExplain());
        }else if(roomStatus.equals(Macros.ROOM_STATUS_REFUSED_TO_CHECK.getStatus() + "")){
            holder.mTvRoomStatus.setText(Macros.ROOM_STATUS_REFUSED_TO_CHECK.getExplain());
        }else if(roomStatus.equals(Macros.ROOM_STATUS_ONE_VOTE_VETO.getStatus() + "")){
            holder.mTvRoomStatus.setText(Macros.ROOM_STATUS_ONE_VOTE_VETO.getExplain() );
        }else{
            Log.e("DoneList",roomStatus);
            holder.mTvRoomStatus.setText("未知原因");
        }
        return convertView;
    }

    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'activity_room_list_done_item.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Inmite Developers (http://inmite.github.io)
     */
    static class ViewHolder {
        @InjectView(R.id.tv_room_name)
        TextView mTvRoomName;
        @InjectView(R.id.tv_room_score)
        TextView mTvRoomScore;
        @InjectView(R.id.tv_pic_num)
        TextView mTvPicNum;
        @InjectView(R.id.tv_room_status)
        TextView mTvRoomStatus;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
