package com.dormvote.biz;

import android.util.Log;

import com.dormvote.bean.Picture;
import com.dormvote.bean.Room;
import com.dormvote.bean.RoomItemAndScore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/1/9.
 */
public class RoomListMapper {
    public static List<Room> parseRoomList(JSONArray response){
        Log.e("RoomListMapper response",response.toString());
        List<Room> list = new ArrayList<>();
        for (int i = 0; i < response.length(); i++) {
            try {
                JSONObject room = ((JSONObject) response.get(i)).getJSONObject("room_info");
                List<Picture> picList = new ArrayList<>();
                List<RoomItemAndScore> scoreList = new ArrayList<>();
                Room item = new Room("","","","",picList,scoreList);
                item.setRoomId(room.getString("room_id"));
                item.setRoomScore(room.getString("total_av_score"));
                item.setRoomStatus(room.getString("room_status"));
                item.setRoomTitle(room.getString("room_title"));
                JSONArray jsonPic = room.getJSONArray("pic");
                JSONArray jsonscore = room.getJSONArray("item_score");
                for (int j = 0; j < jsonPic.length(); j++) {
                    JSONObject jsonPicItem = jsonPic.getJSONObject(j);
                    Picture pic = new Picture("","","");
                    pic.setRemark(jsonPicItem.getString("remark"));
                    pic.setUploadBy(jsonPicItem.getString("upload_by"));
                    pic.setUrl(jsonPicItem.getString("url"));
                    picList.add(pic);
                }
                for (int k = 0; k < jsonscore.length(); k++) {
                    JSONObject jsonScoreItem = jsonscore.getJSONObject(k);
                    RoomItemAndScore roomScore = new RoomItemAndScore("","");
                    roomScore.setItemScore(jsonScoreItem.getString("item_score"));
                    roomScore.setItemId(jsonScoreItem.getString("item_id"));
                    scoreList.add(roomScore);
                }
                list.add(item);

            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("RoomListMapper",e.toString());
            }
        }
        return list;
    }
}
