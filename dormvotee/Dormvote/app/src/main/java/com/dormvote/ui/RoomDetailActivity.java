package com.dormvote.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.dormvote.R;
import com.dormvote.adapter.RoomDetailPicAdapter;
import com.dormvote.adapter.RoomDetailScoreDetailAdapter;
import com.dormvote.adapter.ViewPagerAdapter;
import com.dormvote.app.AppConfig;
import com.dormvote.app.AppManager;
import com.dormvote.bean.Room;
import com.dormvote.net.Macros;
import com.dormvote.net.NetRequestClient;
import com.dormvote.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Administrator on 2015/1/17.
 */
public class RoomDetailActivity extends ActionBarActivity implements ActionBar.TabListener {

    @InjectView(R.id.tv_room_locale)
    TextView mTvRoomLocale;
    @InjectView(R.id.tv_room_status)
    TextView mTvRoomStatus;
    @InjectView(R.id.pager_room_info)
    ViewPager mPagerRoomInfo;
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private ViewPagerAdapter mAdapter;

    /**
     * The {@link android.support.v4.view.ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    private TextView mTvRoom;
    private ListView mLvRoomList1;
    private ListView mLvRoomList2;
    private Room room;

    // private RoomListDoneAdapter roomDoneAdapter;
    private RoomDetailScoreDetailAdapter roomDetailScoreDetailAdapter;
    private RoomDetailPicAdapter roomDetailPicAdapter;
    private List<Room> doneRooms;
    private List<Room> unDoneRooms;
    List<View> viewLists;
    private String roomTitle = "";

    private String[] title = {"评分详情", "照片"};

    private Handler handler=new Handler( ){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case Macros.MSG_CHANGE_ROOM_STATUS:
                    if(msg.arg1==Macros.SUCCESS){
                        ToastUtils.ToastUtil("重置成功");
                        finish();
                    }
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        setContentView(R.layout.activity_room_detail_list);
        ButterKnife.inject(this);
        // Set up the action bar.
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("详情");
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        viewLists = new ArrayList<>();
        mLvRoomList1 = new ListView(RoomDetailActivity.this);

        mLvRoomList2 = new ListView(RoomDetailActivity.this);
        viewLists.add(mLvRoomList1);
        viewLists.add(mLvRoomList2);
        mAdapter = new ViewPagerAdapter(viewLists, RoomDetailActivity.this);
        int roomId = getIntent().getIntExtra("roomId", 0);
        room = RoomListActivity.doneRooms.get(roomId);

        mTvRoomStatus.setText(AppConfig.sharedAppConfig(RoomDetailActivity.this).getExplainFormStatus(room.getRoomStatus()));
        mTvRoomLocale.setText(room.getRoomTitle());
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager_room_info);
        mTvRoom = (TextView) findViewById(R.id.tv_room_locale);
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
        roomDetailScoreDetailAdapter = new RoomDetailScoreDetailAdapter(RoomDetailActivity.this, room.getRoomItemScore(),room.getRoomStatus());
        roomDetailPicAdapter = new RoomDetailPicAdapter(RoomDetailActivity.this,room.getRoomPic());
        // roomDoneAdapter = new RoomListDoneAdapter(getApplicationContext(),doneRooms);
        //roomUndoneAdapter = new RoomListUndoneAdapter(getApplicationContext(),unDoneRooms);
        mLvRoomList1.setAdapter(roomDetailScoreDetailAdapter);
        mLvRoomList2.setAdapter(roomDetailPicAdapter);

        //   mLvRoomListDone.setAdapter(roomDoneAdapter);
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
        getMenuInflater().inflate(R.menu.menu_room_detail,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            case R.id.item_up_pic:
                Intent intent = new Intent(RoomDetailActivity.this,UpPicActivity.class);
                intent.putExtra("roomId",room.getRoomId());
                startActivity(intent);
                break;
            case R.id.item_reset_status:
                NetRequestClient.sharedNetRequestClient().changeroomstatus(AppConfig.sharedAppConfig(RoomDetailActivity.this).getAccessToken(),room.getRoomId(), Macros.ROOM_STATUS_UNCHECKED.getStatus()+"",handler);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
