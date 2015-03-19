package com.dormvote.ui;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.dormvote.R;
import com.dormvote.app.AppConfig;
import com.dormvote.net.Macros;
import com.dormvote.net.NetRequestClient;
import com.dormvote.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class LoadActivity extends BaseActivity {
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Macros.MSG_GET_PUBLIC_DATA:
                    if (msg.arg1 == Macros.SUCCESS) {
                        JSONObject json = (JSONObject) msg.obj;
                        try {
                            if(!(AppConfig.sharedAppConfig(LoadActivity.this).getPublicDataVersion().equals(json.getString("version")))){
                                Log.e("LoadActivity",json.toString());
                            AppConfig.sharedAppConfig(LoadActivity.this).setPublicData(json.toString());
                            AppConfig.sharedAppConfig(LoadActivity.this).setPublicDataVersion(json.getString("version"));}
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        NetRequestClient.sharedNetRequestClient().getUpToken(AppConfig.sharedAppConfig(LoadActivity.this)
                                .getAccessToken(), handler);
                    } else {
                       // ToastUtils.ToastUtil(msg.toString());
                        startActivity(new Intent(LoadActivity.this, RoomListActivity.class));
                        LoadActivity.this.finish();
                    }

                    break;
                case Macros.MSG_GET_UP_TOKEN:
                    Log.e("net",msg.obj.toString());
                    if(msg.arg1 == Macros.SUCCESS){
                        JSONObject json = (JSONObject) msg.obj;
                        try {
                            AppConfig.sharedAppConfig(LoadActivity.this).setPhotoAccessToken(json.getString("upToken"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    startActivity(new Intent(LoadActivity.this, RoomListActivity.class));
                    LoadActivity.this.finish();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);
        Runnable r = new Runnable() {
            @Override
            public void run() {
                Intent intent = null;
                String accessToken = AppConfig.sharedAppConfig(
                        getApplicationContext()).getAccessToken();
                if(accessToken.equals("")){
                    intent = new Intent(LoadActivity.this,LoginActivity.class);
                    LoadActivity.this.startActivity(intent);
                    LoadActivity.this.finish();
                }else{
                    NetRequestClient.sharedNetRequestClient().getPublicData(AppConfig.sharedAppConfig(LoadActivity.this)
                            .getAccessToken(),AppConfig.sharedAppConfig(LoadActivity.this).getPublicDataVersion(),handler);
                  //  intent = new Intent(LoadActivity.this,MainActivity.class);
                }
//                UserDao userDao = new UserDao(getApplicationContext());
//                User loginUser = userDao.query();

//                if (loginUser != null && !accessToken.isEmpty()) {
//                    AppContext.sharedAppContext().setLoginUser(loginUser);
//                    intent = new Intent(IndexActivity.this, MainActivity.class);
//                } else {
//                    intent = new Intent(IndexActivity.this, LoginActivity.class);
//                }

            }
        };
        new Handler().postDelayed(r,2000);
    }
}
