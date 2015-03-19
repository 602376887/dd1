package com.dormvote.biz;

import android.util.Log;

import com.dormvote.bean.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/1/8.
 */
public class UserMapper {
    public static User parseUser(JSONObject response){

        User user = new User("","","","","");
        try {
            String access_token = response.getString("access_token");
            JSONObject userJson = response.getJSONObject("user");
            user.setUserId(userJson.getString("user_id"));
            user.setUserRealname(userJson.getString("realname"));
            user.setUserRoleInTeam(userJson.getString("role_in_team"));
            user.setUserTel(userJson.getString("tel"));
            user.setAccess_token(access_token);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("UserMapper",e.toString());
        }
        return user;
    }
    public static List<User> parseMemberList(JSONArray jsonArray){
        List<User> userList = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                User user = new User("","","","","");
                JSONObject response = (JSONObject) jsonArray.get(i);
                user.setUserId(response.getString("user_id"));
                user.setUserRealname(response.getString("realname"));
                user.setUserRoleInTeam(response.getString("role_in_team"));
                user.setUserTel(response.getString("tel"));
                userList.add(user);
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("UserMapper",e.toString());
            }
        }

        return userList;
    }
}
