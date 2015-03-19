package com.dormvote.ui;

import android.app.ActionBar;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MenuItem;
import android.widget.ListView;

import com.dormvote.R;
import com.dormvote.adapter.TeamMemberAdapter;
import com.dormvote.app.AppConfig;
import com.dormvote.bean.User;
import com.dormvote.biz.UserMapper;
import com.dormvote.net.Macros;
import com.dormvote.net.NetRequestClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Administrator on 2015/1/9.
 */
public class TeamMemberActivity extends BaseActivity {
    @InjectView(R.id.lv_team_member)
    ListView mLvTeamMember;
    private TeamMemberAdapter memberAdapter;
    private List<User> memberList;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch(msg.what){
                case Macros.MSG_GET_TEAM_MEMBER:
                    if(msg.arg1 == Macros.SUCCESS){
                        try {
                            List<User> list = UserMapper.parseMemberList(((JSONObject)msg.obj).getJSONArray("memberdata"));
                            memberList.addAll(list);
                            memberAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_member);
        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setTitle("团队成员");
        ButterKnife.inject(this);
        memberList = new ArrayList<>();
        memberAdapter = new TeamMemberAdapter(getApplicationContext(),memberList);
        mLvTeamMember.setAdapter(memberAdapter);
        NetRequestClient.sharedNetRequestClient().getTeamMember(AppConfig.sharedAppConfig(getApplicationContext()).getAccessToken(),handler);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
