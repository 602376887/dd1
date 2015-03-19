package com.dormvote.net;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.dormvote.R;
import com.dormvote.app.AppContext;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;

import com.dormvote.utils.ToastUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.UUID;
//import com.loopj.android.http.AsyncHttpClient;
//import com.loopj.android.http.JsonHttpResponseHandler;
//import com.loopj.android.http.RequestParams;
//import com.loopj.android.http.ResponseHandlerInterface;

public class NetRequestClient {

	private static NetRequestClient apiClient;
	private static AsyncHttpClient httpClient = new AsyncHttpClient();
	private static StringBuffer authCode; // 短信验证�?

	public static StringBuffer getAuthCode() {
		return authCode;
	}

	public static NetRequestClient sharedNetRequestClient() {
		if (apiClient == null) {
			apiClient = new NetRequestClient();
		}
//		if (!AppContext.sharedAppContext().isNetworkConnected()) {
//			ToastUtils.ToastUtil(AppContext.sharedAppContext()
//					.getApplicationContext().getResources()
//					.getString(R.string.network_error));
//			//return null;
//		}
		return apiClient;
	}

	private NetRequestClient() {

	}

	/**
	 * 发送get请求
	 * 
	 * @param url
	 * @param responseHandler
	 */
	private void get(String url, ResponseHandlerInterface responseHandler) {
        httpClient.setTimeout(5000);
		httpClient.get(url, responseHandler);
	}

	// private void post(Context context,String url, HttpEntity entity,
	// ResponseHandlerInterface responseHandler) {
	// httpClient.post(AppContext.sharedAppContext().getApplicationContext(),
	// url, entity, "application/json", responseHandler);
	// }
	/**
	 * 发送post请求
	 * 
	 * @param url
	 * @param params
	 * @param responseHandler
	 */
	private void post(String url, RequestParams params,
			ResponseHandlerInterface responseHandler) {
        httpClient.setTimeout(5000);
		httpClient.post(url, params, responseHandler);
	}

	/**
	 * 获取公共数据
	 * 
	 * @param accessToken
	 * @param version
	 * @param handler
	 */
	public void getPublicData(String accessToken, String version,
			final Handler handler) {
		String url = Macros.GET_PUBLIC_DATA + "?access_token=" + accessToken
				+ "&version=" + version;
		apiClient.get(url, new BaseJsoJsonHttpResponseHandler(
				Macros.MSG_GET_PUBLIC_DATA, "获取公共数据", handler));
	}

	/**
	 * 获取七牛图片上传Token
	 * 
	 * @param accessToken
	 * @param handler
	 */
	public void getUpToken(String accessToken, final Handler handler) {
		String url = Macros.GET_UP_TOKEN + "?access_token=" + accessToken;
		apiClient.get(url, new BaseJsoJsonHttpResponseHandler(
				Macros.MSG_GET_UP_TOKEN, "七牛Token", handler));
	}

	/**
	 * 登陆
	 * 
	 * @param username
	 * @param password
	 * @param handler
	 */
	public void login(String username, String password, final Handler handler) {
		RequestParams params = new RequestParams();
		params.put("username", username);
		params.put("password", password);

		apiClient.post(Macros.LOGIN, params, new BaseJsoJsonHttpResponseHandler(
				Macros.MSG_LOGIN, "登陆", handler));
	}

	// public void updateclientid(String accessToken, String clientId,
	// final Handler handler) {
	// String url = Macros.UPDATE_CLIENT_ID + "?access_token=" + accessToken
	// + "&client_id=" + clientId;
	// apiClient.get(url, new GetJsoJsonHttpResponseHandler(
	// Macros.MSG_UPDATE_CLIENT_ID, "更新设备ID", handler));
	// }

	// public void getUserInfo(String accessToken, final Handler handler) {
	// String url = Macros.GET_USERINFO + "?access_token=" + accessToken;
	// apiClient.get(url, new GetJsoJsonHttpResponseHandler(
	// Macros.MSG_GET_USERINFO, "获取用户信息", handler));
	// }

	/**
	 * 获取团队成员
	 * 
	 * @param accessToken
	 * @param handler
	 */
	public void getTeamMember(String accessToken, final Handler handler) {
		String url = Macros.GET_TEAM_MEMBER + "?access_token=" + accessToken;
		apiClient.get(url, new BaseJsoJsonHttpResponseHandler(
				Macros.MSG_GET_TEAM_MEMBER, "获取成员列表", handler));
	}

	/**
	 * 获取宿舍列表
	 * 
	 * @param accessToken
	 * @param type
	 * @param handler
	 */
	public void getRoomList(String accessToken, String type,
			final Handler handler) {
        if(type.equals("100")) {
            String url = Macros.GET_ROOMLIST + "?access_token=" + accessToken
                    + "&type=" + type;
            apiClient.get(url, new BaseJsoJsonHttpResponseHandler(
                    Macros.MSG_GET_ROOMLIST_DONE, "获取任务列表", handler));
        }else if(type.equals("200")) {
            String url = Macros.GET_ROOMLIST + "?access_token=" + accessToken
                    + "&type=" + type;
            apiClient.get(url, new BaseJsoJsonHttpResponseHandler(
                    Macros.MSG_GET_ROOMLIST_UNDONE, "获取任务列表", handler));
        }
	}

	/**
	 * 获取宿舍详细信息
	 * 
	 * @param accessToken
	 * @param roomId
	 * @param handler
	 */
	public void getRoomInfo(String accessToken, String roomId,
			final Handler handler) {
		String url = Macros.GET_ROOM_INFO + "?access_token=" + accessToken
				+ "&room_id=" + roomId;
		apiClient.get(url, new BaseJsoJsonHttpResponseHandler(
				Macros.MSG_GET_ROOM_INFO, "获取房间详情", handler));
	}

	/**
	 * 改变改变宿舍状态
	 * 
	 * @param accessToken
	 * @param status
	 * @param roomId
	 * @param handler
	 */
	public void changeroomstatus(String accessToken,
			String roomId, String status, final Handler handler) {
		RequestParams params = new RequestParams();
		params.put("access_token", accessToken);
        params.put("room_id", roomId);
		params.put("status", status);


		apiClient.post(Macros.CHANGE_ROOM_STATUS, params,
				new BaseJsoJsonHttpResponseHandler(Macros.MSG_CHANGE_ROOM_STATUS,
						"改变宿舍状态", handler));
	}

	/**
	 * 给宿舍打分
	 * 
	 * @param accessToken
	 * @param scoredata
	 * @param roomId
	 * @param handler
	 */
	public void voteRoom(String accessToken, JSONObject scoredata,
			String roomId, final Handler handler) {
		RequestParams params = new RequestParams();
		params.put("access_token", accessToken);
		params.put("scoredata", scoredata.toString());
		params.put("room_id", roomId);

		apiClient.post(Macros.VOTEROOM, params,
				new BaseJsoJsonHttpResponseHandler(Macros.MSG_VOTEROOM, "打分",
						handler));
	}

	/**
	 * 获取投票的结果
	 * 
	 * @param accessToken
	 * @param roomId
	 * @param handler
	 */
	public void getVoteresult(String accessToken, String roomId,
			final Handler handler) {
		String url = Macros.GET_VOTERESULT + "?access_token=" + accessToken
				+ "&room_id=" + roomId;
		apiClient.get(url, new BaseJsoJsonHttpResponseHandler(
				Macros.MSG_GET_VOTERESULT, "获取宿舍打分结果", handler));
	}

    /**
     * 上传图片

     * @param photoUpToken
     * @param file
     * @param handler
     */
    public void uploadUserPhotosToQiniu(String fileKey,String photoUpToken,File file,Handler handler) {
        RequestParams params = new RequestParams();
     //   params.put("x:member_id", userId);
     //   params.put("x:room_id", roomId);
     //   params.put("x:remark", remark);

        params.put("token", photoUpToken);
        params.put("key",fileKey);

        try {
            params.put("file", file);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        apiClient.post(Macros.UPLOAD_PHOTO_TO_QINIU, params,
                new BaseJsoJsonHttpResponseHandler(Macros.MSG_UPLOAD_PHOTO_TO_QINIU,
                        "上传图片", handler));
    }
    public void uploadUserPhotos(String accessToken,String roomId,String remark,String fileKey,Handler handler) {
        RequestParams params = new RequestParams();
        //   params.put("x:member_id", userId);
        //   params.put("x:room_id", roomId);
        //   params.put("x:remark", remark);
        params.put("access_token", accessToken);
        params.put("room_id", roomId);
        params.put("remark", remark);
        params.put("file_key", fileKey);
        apiClient.post(Macros.UPLOAD_MINE_PHOTO, params,
                new BaseJsoJsonHttpResponseHandler(Macros.MSG_UPLOAD_MINE_PHOTO,
                        "上传图片", handler));
    }

	class BaseJsoJsonHttpResponseHandler extends JsonHttpResponseHandler {
		protected int msgID;
		protected String name;
		protected Handler handler;

		public BaseJsoJsonHttpResponseHandler(int msgID, String name,
				Handler handler) {
			super();
			this.msgID = msgID;
			this.name = name;
			this.handler = handler;
		}

		@Override
		public void onSuccess(JSONObject response) {
			super.onSuccess(response);
            Log.e("netRequestClient",response.toString());
            Message msg = Message.obtain();
            msg.what = this.msgID;
            if(msg.what == Macros.MSG_UPLOAD_PHOTO_TO_QINIU){
                try {
                    String key = response.getString("key");
                    if(!(key==null||key.equals(""))){
                        msg.arg1 = Macros.SUCCESS;
                        msg.obj = response;
                    }else{
                        ToastUtils.ToastUtil("不知道怎么了");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else{
            try {
                int errorCode = response.getInt("error_code");
                Log.e("net",response.toString());
                if (errorCode == Macros.SUCCESS){
                    msg.obj = response;
                    msg.arg1 = Macros.SUCCESS;
                } else if(errorCode == Macros.NET_STATUS_10001.getStatus()){
                    ToastUtils.ToastUtil(Macros.NET_STATUS_10001.getExplain());
                    msg.obj = Macros.NET_STATUS_10001.getExplain();
                    msg.arg1 = Macros.NET_STATUS_10001.getStatus();
                } else if(errorCode == Macros.NET_STATUS_10002.getStatus()){
                    ToastUtils.ToastUtil(Macros.NET_STATUS_10002.getExplain());
                    msg.obj = Macros.NET_STATUS_10002.getExplain();
                    msg.arg1 = Macros.NET_STATUS_10002.getStatus();
                } else if(errorCode == Macros.NET_STATUS_10003.getStatus()){
                    ToastUtils.ToastUtil(Macros.NET_STATUS_10003.getExplain());
                    msg.obj = Macros.NET_STATUS_10003.getExplain();
                    msg.arg1 = Macros.NET_STATUS_10003.getStatus();
                } else if(errorCode == Macros.NET_STATUS_10013.getStatus()){
                    ToastUtils.ToastUtil(Macros.NET_STATUS_10013.getExplain());
                    msg.obj = Macros.NET_STATUS_10013.getExplain();
                    msg.arg1 = Macros.NET_STATUS_10013.getStatus();
                } else if(errorCode == Macros.NET_STATUS_10014.getStatus()){
                    ToastUtils.ToastUtil(Macros.NET_STATUS_10014.getExplain());
                    msg.obj = Macros.NET_STATUS_10014.getExplain();
                    msg.arg1 = Macros.NET_STATUS_10014.getStatus();
                } else if(errorCode == Macros.NET_STATUS_10016.getStatus()){
                    ToastUtils.ToastUtil(Macros.NET_STATUS_10016.getExplain());
                    msg.obj = Macros.NET_STATUS_10016.getExplain();
                    msg.arg1 = Macros.NET_STATUS_10016.getStatus();
                } else if(errorCode == Macros.NET_STATUS_10017.getStatus()){
                    ToastUtils.ToastUtil(Macros.NET_STATUS_10017.getExplain());
                    msg.obj = Macros.NET_STATUS_10017.getExplain();
                    msg.arg1 = Macros.NET_STATUS_10017.getStatus();
                } else if(errorCode == Macros.NET_STATUS_10020.getStatus()){
                    ToastUtils.ToastUtil(Macros.NET_STATUS_10020.getExplain());
                    msg.obj = Macros.NET_STATUS_10020.getExplain();
                    msg.arg1 = Macros.NET_STATUS_10020.getStatus();
                } else if(errorCode == Macros.NET_STATUS_10021.getStatus()){
                    ToastUtils.ToastUtil(Macros.NET_STATUS_10021.getExplain());
                    msg.obj = Macros.NET_STATUS_10021.getExplain();
                    msg.arg1 = Macros.NET_STATUS_10021.getStatus();
                } else if(errorCode == Macros.NET_STATUS_20003.getStatus()){
                    ToastUtils.ToastUtil(Macros.NET_STATUS_20003.getExplain());
                    msg.obj = Macros.NET_STATUS_20003.getExplain();
                    msg.arg1 = Macros.NET_STATUS_20003.getStatus();
                } else if(errorCode == Macros.NET_STATUS_20005.getStatus()){
                    ToastUtils.ToastUtil(Macros.NET_STATUS_20005.getExplain());
                    msg.obj = Macros.NET_STATUS_20005.getExplain();
                    msg.arg1 = Macros.NET_STATUS_20005.getStatus();
                } else if(errorCode == Macros.NET_STATUS_20006.getStatus()){
                    ToastUtils.ToastUtil(Macros.NET_STATUS_20006.getExplain());
                    msg.obj = Macros.NET_STATUS_20006.getExplain();
                    msg.arg1 = Macros.NET_STATUS_20006.getStatus();
                } else if(errorCode == Macros.NET_STATUS_21301.getStatus()){
                    ToastUtils.ToastUtil(Macros.NET_STATUS_21301.getExplain());
                    msg.obj = Macros.NET_STATUS_21301.getExplain();
                    msg.arg1 = Macros.NET_STATUS_21301.getStatus();
                    AppContext.sharedAppContext().getUnLoginHandler().sendEmptyMessage(Macros.MSG_LOGOUT);
                } else if(errorCode == Macros.NET_STATUS_21302.getStatus()){
                    ToastUtils.ToastUtil(Macros.NET_STATUS_21302.getExplain());
                    msg.obj = Macros.NET_STATUS_21302.getExplain();
                    msg.arg1 = Macros.NET_STATUS_21302.getStatus();
                } else if(errorCode == Macros.NET_STATUS_21303.getStatus()){
                    ToastUtils.ToastUtil(Macros.NET_STATUS_21303.getExplain());
                    msg.obj = Macros.NET_STATUS_21303.getExplain();
                    msg.arg1 = Macros.NET_STATUS_21303.getStatus();
                } else if(errorCode == Macros.NET_STATUS_21304.getStatus()){
                    ToastUtils.ToastUtil(Macros.NET_STATUS_21304.getExplain());
                    msg.obj = Macros.NET_STATUS_21304.getExplain();
                    msg.arg1 = Macros.NET_STATUS_21304.getStatus();
                    AppContext.sharedAppContext().getUnLoginHandler().sendEmptyMessage(Macros.MSG_LOGOUT);
                }else{
                    msg.obj = response;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }}
            handler.sendMessage(msg);
		}

		@Override
		public void onFailure(Throwable e, JSONObject errorResponse) {
			super.onFailure(e, errorResponse);

			Message msg = Message.obtain();

			msg.what = this.msgID;
			msg.arg1 = Macros.FAILED_MSG;

			msg.obj = e;
			// msg.obj = e.toString();
            ToastUtils.ToastUtil("网络连接不稳定，请检查网络连接");
            Log.e("NetRequestClient",e.toString());

			if (this.handler != null) {
				this.handler.sendMessage(msg);
			}
		}
	}



}
