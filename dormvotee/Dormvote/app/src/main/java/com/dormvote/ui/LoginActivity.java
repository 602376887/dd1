package com.dormvote.ui;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dormvote.R;
import com.dormvote.app.AppConfig;
import com.dormvote.bean.User;
import com.dormvote.biz.UserMapper;
import com.dormvote.db.UserDao;
import com.dormvote.net.Macros;
import com.dormvote.net.NetRequestClient;
import com.dormvote.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Administrator on 2015/1/8.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {
    @InjectView(R.id.et_username)
    EditText mEtUsername;
    @InjectView(R.id.et_password)
    EditText mEtPassword;
    @InjectView(R.id.bt_load)
    Button mBtLoad;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case Macros.MSG_LOGIN:
                    if(msg.arg1==Macros.SUCCESS) {
                        JSONObject json = (JSONObject) msg.obj;
                        try {
                            AppConfig.sharedAppConfig(LoginActivity.this).setAccessToken(json.getString("access_token"));
                            Log.e("publicdataversion",AppConfig.sharedAppConfig(LoginActivity.this).getPublicDataVersion());
                            User user = UserMapper.parseUser((JSONObject)msg.obj);
                            UserDao dao = new UserDao(LoginActivity.this);
                            dao.add(user);
                            NetRequestClient.sharedNetRequestClient().getPublicData(AppConfig.sharedAppConfig(LoginActivity.this)
                                    .getAccessToken(), AppConfig.sharedAppConfig(LoginActivity.this).getPublicDataVersion(), handler);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    break;
                case Macros.MSG_GET_PUBLIC_DATA:
                    if(msg.arg1 == Macros.SUCCESS){
                        JSONObject json = (JSONObject) msg.obj;
                        try {
                            if(!(AppConfig.sharedAppConfig(LoginActivity.this).getPublicDataVersion().equals(json.getString("version")))) {
                                AppConfig.sharedAppConfig(LoginActivity.this).setPublicData(json.toString());
                                AppConfig.sharedAppConfig(LoginActivity.this).setPublicDataVersion(json.getString("version"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        NetRequestClient.sharedNetRequestClient().getUpToken(AppConfig.sharedAppConfig(LoginActivity.this)
                                .getAccessToken(), handler);
                    }else{
                    startActivity(new Intent(LoginActivity.this,RoomListActivity.class));
                    LoginActivity.this.finish();}
                    break;
                case Macros.MSG_GET_UP_TOKEN:
                    if(msg.arg1 == Macros.SUCCESS){
                        JSONObject json = (JSONObject) msg.obj;
                        try {
                            AppConfig.sharedAppConfig(LoginActivity.this).setPhotoAccessToken(json.getString("upToken"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    startActivity(new Intent(LoginActivity.this,RoomListActivity.class));
                    LoginActivity.this.finish();
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
//        final ActionBar actionBar = getActionBar();
//        actionBar.setTitle("登录");
//        actionBar.setDisplayShowHomeEnabled(false);
        ButterKnife.inject(this);
        mBtLoad.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_load:
                String username = mEtUsername.getText().toString();
                String password = mEtPassword.getText().toString();
                if(username.equals("")||password.equals("")){
                    ToastUtils.ToastUtil("用户名或密码不能为空");
                }else {
                    NetRequestClient.sharedNetRequestClient().login(username,password,handler);
                }
                break;
        }
    }
}
