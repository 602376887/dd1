package com.dormvote.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.util.Log;

import com.dormvote.bean.PublicData;
import com.dormvote.bean.PublicDataItem;
import com.dormvote.biz.PublicDataMapper;
import com.dormvote.net.Macros;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class AppConfig {
	/** 配置xml文件中属性的键 ***/
	private final static String APP_CONFIG = "config";
	private final static String IS_FIRST_LOGIN = "is_first_login";
	public final static String CONF_ACCESSTOKEN = "accessToken";
	public final static String PHOTO_UPTOKEN = "photo_up_token";
	public final static String USER_PHONE_NUMBER = "user_phone_number";
	public final static String GETTUI_CID = "gettui_cid";
	public final static String LOCATION_X = "LOCATION_X";
	public final static String LOCATION_Y = "LOCATION_Y";
	public final static String IS_NEW_INSTALL = "is_new_install";//是否新安装

    public final static String PUBLIC_DATA_VERSION = "public_data_version";
    public final static String PUBLIC_DATA = "public_data";

    public final static String PUBLIC_DATA_TYPE_NORMAL = "1";
    public final static String PUBLIC_DATA_TYPE_ONE_VOTE = "2";

	
	private Context mContext;
	private static AppConfig appConfig;
	
	public static AppConfig sharedAppConfig(Context context) {
		if (appConfig == null) {
			appConfig = new AppConfig();
			appConfig.mContext = context;
		}
		return appConfig;
	}
	
	/**
	 * 获取Preference设置
	 */
	private SharedPreferences getPreferences(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context);
	}
	
	public void setAccessToken(String accessToken) {
		Editor editor = getPreferences(mContext).edit();
		editor.putString(CONF_ACCESSTOKEN, accessToken);
		editor.commit();
	}

	public String getAccessToken() {
		return getPreferences(mContext).getString(CONF_ACCESSTOKEN, "");
	}

    public void setPublicDataVersion(String publicDataVersion) {
        Editor editor = getPreferences(mContext).edit();
        editor.putString(PUBLIC_DATA_VERSION, publicDataVersion);
        editor.commit();
    }

    public String getPublicDataVersion() {
      return getPreferences(mContext).getString(PUBLIC_DATA_VERSION, "1");
    }

    public void setPublicData(String publicData) {
        Editor editor = getPreferences(mContext).edit();
        editor.putString(PUBLIC_DATA, publicData);
        editor.commit();
    }

    public String getPublicData() {
            return  getPreferences(mContext).getString(PUBLIC_DATA, "");
    }

	//是否新安装
	public void setIsNewInstall(Boolean isNewInstall) {
		Editor editor = getPreferences(mContext).edit();
		editor.putBoolean(IS_NEW_INSTALL, isNewInstall);
		editor.commit();
	}
	public Boolean getIsNewInstall() {
		return getPreferences(mContext).getBoolean(IS_NEW_INSTALL, true);
	}
	//保存地理位置
	public void setLocationX(double location_x){
		Editor editor = getPreferences(mContext).edit();
		editor.putFloat(LOCATION_X, (float) location_x);
		editor.commit();
	}
	public float getLocationX(){
		return getPreferences(mContext).getFloat(LOCATION_X, (float) 0.0);
	}
	public void setLocationY(double location_y){
		Editor editor = getPreferences(mContext).edit();
		editor.putFloat(LOCATION_Y, (float) location_y);
		editor.commit();
	}
	public float getLocationY(){
		return getPreferences(mContext).getFloat(LOCATION_Y, (float) 0.0);
	}
	
	//获取图片上传token
	public void setPhotoAccessToken(String accessToken){
		Editor editor = getPreferences(mContext).edit();
		editor.putString(PHOTO_UPTOKEN, accessToken);
		editor.commit();
	}
	public String getPhotoAccessToken() {
		return getPreferences(mContext).getString(PHOTO_UPTOKEN, "");
	}
	public void setPhoneNumber(String phone){
		Editor editor = getPreferences(mContext).edit();
		editor.putString(USER_PHONE_NUMBER, phone);
		editor.commit();
	}
	public String getPhoneNumber() {
		return getPreferences(mContext).getString(USER_PHONE_NUMBER, "");
	}
	
	public void setGeTuiCID(String cid){
		Editor editor = getPreferences(mContext).edit();
		editor.putString(GETTUI_CID, cid);
		editor.commit();
	}
	public String getGeTuiCid(){
		return getPreferences(mContext).getString(GETTUI_CID, "");
	}

	public boolean isFirstLogin(){
		return getPreferences(mContext).getBoolean(IS_FIRST_LOGIN, true);
	}
	
	public void setLogined(){
		Editor editor = getPreferences(mContext).edit();
		editor.putBoolean(IS_FIRST_LOGIN, false);
		editor.commit();
	}
    public List<PublicDataItem> getPublicDataType(String publicDataType){
        try {
            JSONObject json = new JSONObject(getPublicData());
            Log.e("aaa", json.toString());
            List<PublicData> publicDataList = PublicDataMapper.parsePublicData(json.getJSONArray("checkitem"));
            for (int i = 0; i < publicDataList.size(); i++) {
                if (publicDataList.get(i).getDataType().equals(publicDataType)) {
                    return publicDataList.get(i).getItemList();
                } else if (publicDataList.get(i).getDataType().equals(publicDataType)) {
                    return publicDataList.get(i).getItemList();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("getPublicDataType", e.toString());
        }
        return  null;
    }
    public String getExplainFormStatus(String status){
        if(status.equals(Macros.ROOM_STATUS_QUALIFIED.getStatus()+"")){
            return Macros.ROOM_STATUS_QUALIFIED.getExplain();
        }else if(status.equals(Macros.ROOM_STATUS_PERFECT.getStatus()+"")){
            return Macros.ROOM_STATUS_PERFECT.getExplain();
        }else if(status.equals(Macros.ROOM_STATUS_UNQUALIFIED.getStatus()+"")){
            return Macros.ROOM_STATUS_UNQUALIFIED.getExplain();
        }else if(status.equals(Macros.ROOM_STATUS_REFUSED_TO_TAKE_PICTURES.getStatus()+"")){
            return Macros.ROOM_STATUS_REFUSED_TO_TAKE_PICTURES.getExplain();
        }else if(status.equals(Macros.ROOM_STATUS_NOBODY_IN_ON_FIRST_TURN.getStatus()+"")){
            return Macros.ROOM_STATUS_NOBODY_IN_ON_FIRST_TURN.getExplain();
        }else if(status.equals(Macros.ROOM_STATUS_REFUSED_TO_CHECK.getStatus()+"")){
            return Macros.ROOM_STATUS_REFUSED_TO_CHECK.getExplain();
        }else if(status.equals(Macros.ROOM_STATUS_ONE_VOTE_VETO.getStatus()+"")){
            return Macros.ROOM_STATUS_ONE_VOTE_VETO.getExplain();
        }
        return "";
    }

}
