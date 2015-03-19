package com.dormvote.biz;

import android.util.Log;

import com.dormvote.bean.PublicData;
import com.dormvote.bean.PublicDataItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/1/8.
 */
public class PublicDataMapper {
    public static List<PublicData> parsePublicData(JSONArray response){
        List<PublicData> list = new ArrayList<>();
        for (int i = 0; i < response.length(); i++) {
            try {
                JSONObject item = response.getJSONObject(i);
                List<PublicDataItem>  dataChild = new ArrayList<>();
                PublicData dataItem = new PublicData("","",dataChild);
                dataItem.setDataName(item.getString("cat_name"));
                dataItem.setDataType(item.getString("cat_id"));
                JSONArray child = item.getJSONArray("child_item");
                for (int j=0;j<child.length();j++){
                    JSONObject childItem =child.getJSONObject(j);
                    PublicDataItem dataChildItem = new PublicDataItem("","","","");
                    dataChildItem.setItemContent(childItem.getString("item_content"));
                    dataChildItem.setItemId(childItem.getString("item_id"));
                    dataChildItem.setItemValue(childItem.getString("item_value"));
                    dataChildItem.setItemName(childItem.getString("item_name"));
                    dataChild.add(dataChildItem);
                }
                dataItem.setItemList(dataChild);

                list.add(dataItem);
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("PublicDataMapper",e.toString());
            }
        }
        return list;
    }
}
//{"error_code":"200","error":"operation success","version":"2",
// "checkitem":[{"item_id":"1","parent_id":"0","item_content":" ","item_value":""},
// {"item_id":"2","parent_id":"0","item_content":" ","item_value":""},
// {"item_id":"3","parent_id":"1","item_content":"\u8fd9\u91cc\u8fd9\u91cc\u8fd9\u91cc\u4e0d\u5408\u683c","item_value":"100"},
// {"item_id":"4","parent_id":"1","item_content":"\u90a3\u91cc\u90a3\u91cc\u90a3\u91cc\u4e0d\u5408\u683c","item_value":"100"},
// {"item_id":"5","parent_id":"2","item_content":"\u4e25\u91cd\u95ee\u9898\uff0c\u4e0d\u89e3\u91ca","item_value":""},
// {"item_id":"6","parent_id":"2","item_content":"\u7070\u5e38\u4e25\u91cd","item_value":""}]}