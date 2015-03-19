package com.dormvote.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.dormvote.R;
import com.dormvote.app.AppContext;
import com.dormvote.bean.PublicDataItem;
import com.dormvote.biz.PublicDataMapper;
import com.dormvote.net.Macros;
import com.dormvote.net.NetRequestClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class NetTestActivity extends BaseActivity implements OnClickListener {


    String accessToken = "112434";

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case Macros.MSG_GET_PUBLIC_DATA:
//                    try {
//                        List<PublicDataItem> list = PublicDataMapper.parsePublicData(((JSONObject) msg.obj).getJSONArray("checkitem"));
//                        Log.i("net", list.toString());
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                        Log.e("MainActivity", e.toString());
//                    }
//                    break;
//            }
        }
    };

    @InjectView(R.id.bt_net_publicdata)
    Button mBtNetPublicdata;
    @InjectView(R.id.bt_net_login)
    Button mBtNetLogin;
    @InjectView(R.id.bt_net_getRoomInfo)
    Button mBtNetGetRoomInfo;
    @InjectView(R.id.bt_net_teammember)
    Button mBtNetTeammember;
    @InjectView(R.id.bt_net_roomlist)
    Button mBtNetRoomlist;
    @InjectView(R.id.bt_net_voteroom)
    Button mBtNetVoteroom;
    @InjectView(R.id.bt_net_changeroomstatus)
    Button mBtNetChangeroomstatus;
    @InjectView(R.id.bt_net_voteresult)
    Button mBtNetVoteresult;
    @InjectView(R.id.bt_net_getUptoken)
    Button mBtNetGetUptoken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity);
        ButterKnife.inject(this);
        ButterKnife.inject(this);


        mBtNetPublicdata.setOnClickListener(this);
        mBtNetLogin.setOnClickListener(this);
        mBtNetGetRoomInfo.setOnClickListener(this);
        mBtNetTeammember.setOnClickListener(this);
        mBtNetRoomlist.setOnClickListener(this);
        mBtNetVoteroom.setOnClickListener(this);
        mBtNetChangeroomstatus.setOnClickListener(this);
        mBtNetVoteresult.setOnClickListener(this);
        mBtNetGetUptoken.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_net_publicdata:
                NetRequestClient.sharedNetRequestClient().getPublicData(accessToken, "1",
                        handler);
                break;
            case R.id.bt_net_login:
                NetRequestClient.sharedNetRequestClient().login("aa@qq.com", "123", handler);
                break;
            case R.id.bt_net_getRoomInfo:
                NetRequestClient.sharedNetRequestClient().getRoomInfo(accessToken, "1", handler);
                break;
            case R.id.bt_net_teammember:
                NetRequestClient.sharedNetRequestClient()
                        .getTeamMember(accessToken, handler);
                break;
            case R.id.bt_net_roomlist:
                NetRequestClient.sharedNetRequestClient().getRoomList(accessToken, AppContext.ROOM_LIST_TYPE_DONE, handler);
                break;
            case R.id.bt_net_voteroom:
                //NetRequestClient.sharedNetRequestClient().voteRoom(accessToken, scoredata, "1", handler);
                break;
            case R.id.bt_net_changeroomstatus:
                NetRequestClient.sharedNetRequestClient().changeroomstatus(accessToken, Macros.NET_STATUS_22001.getStatus()+"", "12", handler);

                break;
            case R.id.bt_net_voteresult:
                NetRequestClient.sharedNetRequestClient().getVoteresult("", "",
                        handler);
                break;
            case R.id.bt_net_getUptoken:
                NetRequestClient.sharedNetRequestClient().getUpToken("", handler);
                break;

            default:
                break;
        }

    }

}
