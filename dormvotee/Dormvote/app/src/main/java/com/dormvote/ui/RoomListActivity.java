package com.dormvote.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.dormvote.R;
import com.dormvote.adapter.RoomListDoneAdapter;
import com.dormvote.adapter.RoomListUndoneAdapter;
import com.dormvote.adapter.ViewPagerAdapter;
import com.dormvote.app.AppConfig;
import com.dormvote.app.AppContext;
import com.dormvote.app.AppManager;
import com.dormvote.bean.Room;
import com.dormvote.biz.RoomListMapper;
import com.dormvote.net.Macros;
import com.dormvote.net.NetRequestClient;
import com.dormvote.utils.ToastUtils;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

public class RoomListActivity extends ActionBarActivity implements ActionBar.TabListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
  private ViewPagerAdapter mAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    private TextView mTvRoom;
    private PullToRefreshListView mLvRoomList1;
    private PullToRefreshListView mLvRoomList2;
    private ListView mLvRoomListUnDone;
    private ListView mLvRoomListDone;
    private RoomListDoneAdapter roomDoneAdapter;
    private RoomListUndoneAdapter roomUndoneAdapter;
    public static List<Room> doneRooms;
    private List<Room> unDoneRooms;
    List<View> viewLists;
    private String roomTitle = "";
    private String[] title={"未完成","已完成"};

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.e("net","请求成功");
            switch (msg.what){
                case Macros.MSG_GET_ROOMLIST_UNDONE:
                    if(msg.arg1 == Macros.SUCCESS){
                        try {
                            roomTitle = ((JSONObject)msg.obj).getString("room");
                            mTvRoom.setText(roomTitle);
                            List<Room> list = RoomListMapper.parseRoomList(((JSONObject)msg.obj).getJSONArray("roomlist"));
                            unDoneRooms.clear();
                            unDoneRooms.addAll(list);
                            roomUndoneAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    mLvRoomList1.onRefreshComplete();
                    break;
                case Macros.MSG_GET_ROOMLIST_DONE:
                    if(msg.arg1 == Macros.SUCCESS){
                        try {
                            roomTitle = ((JSONObject)msg.obj).getString("room");
                            mTvRoom.setText(roomTitle);
                            List<Room> list = RoomListMapper.parseRoomList(((JSONObject)msg.obj).getJSONArray("roomlist"));
                            doneRooms.clear();
                            doneRooms.addAll(list);
                            roomDoneAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    mLvRoomList2.onRefreshComplete();
                    break;
                case Macros.MSG_CHANGE_ROOM_STATUS:
                    if(msg.arg1 == Macros.SUCCESS){
                        ToastUtils.ToastUtil("打分成功！");
                        NetRequestClient.sharedNetRequestClient().getRoomList(AppConfig.sharedAppConfig(getApplicationContext()).getAccessToken(),"200",handler);
                        NetRequestClient.sharedNetRequestClient().getRoomList(AppConfig.sharedAppConfig(getApplicationContext()).getAccessToken(),"100",handler);
                    }
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        setContentView(R.layout.activity_room_list);
        // Set up the action bar.
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setTitle("宿舍打分系统");

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        viewLists = new ArrayList<>();
        viewLists.add(View.inflate(RoomListActivity.this,R.layout.fragment_room_list,null));
        viewLists.add(View.inflate(RoomListActivity.this,R.layout.fragment_room_list,null));
        mLvRoomList1 = (PullToRefreshListView) viewLists.get(0).findViewById(R.id.lv_room_list);
        mLvRoomList2 = (PullToRefreshListView) viewLists.get(1).findViewById(R.id.lv_room_list);
        mLvRoomListUnDone = mLvRoomList1.getRefreshableView();
        mLvRoomListDone = mLvRoomList2.getRefreshableView();
        mAdapter = new ViewPagerAdapter(viewLists,RoomListActivity.this);
        mLvRoomList1.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        mLvRoomList2.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        mLvRoomList1.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                NetRequestClient.sharedNetRequestClient().getRoomList(AppConfig.sharedAppConfig(getApplicationContext()).getAccessToken(),"200",handler);
            }
        });
        mLvRoomList2.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                NetRequestClient.sharedNetRequestClient().getRoomList(AppConfig.sharedAppConfig(getApplicationContext()).getAccessToken(),"100",handler);
            }
        });
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mTvRoom = (TextView) findViewById(R.id.tv_room);
        mViewPager.setAdapter(mAdapter);
//        mLvRoomList1 = (PullToRefreshListView) mSectionsPagerAdapter.getItem(0).getView().findViewById(R.id.lv_room_list);
//        mLvRoomList2= (PullToRefreshListView) mSectionsPagerAdapter.getItem(1).getView().findViewById(R.id.lv_room_list);
        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < viewLists.size(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(title[i])
                            .setTabListener(this));
        }

        doneRooms = new ArrayList<>();
        unDoneRooms = new ArrayList<>();
        roomDoneAdapter = new RoomListDoneAdapter(getApplicationContext(),doneRooms);
        roomUndoneAdapter = new RoomListUndoneAdapter(getApplicationContext(),unDoneRooms);
        mLvRoomListUnDone.setAdapter(roomUndoneAdapter);
        mLvRoomListDone.setAdapter(roomDoneAdapter);

        mLvRoomListUnDone.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(!(position==0||position==(unDoneRooms.size()+1))){
                    Intent intent = new Intent(RoomListActivity.this,VoteActivity.class);
                    intent.putExtra("roomId",unDoneRooms.get(position-1).getRoomId());
                    startActivity(intent);
                }
            }
        });
        mLvRoomListUnDone.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                if (!(position == 0 || position == (unDoneRooms.size() + 1))) {
                    new AlertDialog.Builder(RoomListActivity.this).setTitle("宿舍评定为：").setItems(Macros.ROOM_STATUS_EXPLAIN, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            NetRequestClient.sharedNetRequestClient().changeroomstatus(AppConfig.sharedAppConfig(RoomListActivity.this).getAccessToken(), unDoneRooms.get(position - 1).getRoomId(), Macros.ROOM_STATUS[which], handler);
                        }
                    }).create().show();
                    return true;
                }
                return false;
            }
        });
        mLvRoomListDone.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!(position == 0 || position == (unDoneRooms.size() + 1))) {
                Intent intent = new Intent();
              //  Person obj = new Person(wg_name.getText().toString(),wg_age.getText().toString());
            //    intent.putParcelableArrayListExtra("detail",doneRooms.get(position-1));
                    intent.putExtra("roomId", position-1);
               // intent.putExtra("detail",);
               // intent.putParcelableArrayListExtra("roomScore",doneRooms.get(position-1).getRoomItemScore());
                intent.setClass(RoomListActivity.this, RoomDetailActivity.class);
                startActivity(intent);
            }}
        });
        NetRequestClient.sharedNetRequestClient().getRoomList(AppConfig.sharedAppConfig(getApplicationContext()).getAccessToken(),"100",handler);
        NetRequestClient.sharedNetRequestClient().getRoomList(AppConfig.sharedAppConfig(getApplicationContext()).getAccessToken(),"200",handler);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().finishActivity(this);
    }


    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_team_member:
                startActivity(new Intent(RoomListActivity.this, TeamMemberActivity.class));
                break;
            case R.id.item_quit:
                AppContext.sharedAppContext().getUnLoginHandler().sendEmptyMessage(Macros.MSG_LOGOUT);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        NetRequestClient.sharedNetRequestClient().getRoomList(AppConfig.sharedAppConfig(getApplicationContext()).getAccessToken(),"100",handler);
        NetRequestClient.sharedNetRequestClient().getRoomList(AppConfig.sharedAppConfig(getApplicationContext()).getAccessToken(),"200",handler);
    }
}
