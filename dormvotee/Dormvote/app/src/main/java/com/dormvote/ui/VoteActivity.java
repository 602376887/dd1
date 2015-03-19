package com.dormvote.ui;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.dormvote.R;
import com.dormvote.adapter.ItemChooseAdapter;
import com.dormvote.adapter.VoteItemAdapter;
import com.dormvote.app.AppConfig;
import com.dormvote.bean.PublicData;
import com.dormvote.bean.PublicDataItem;
import com.dormvote.bean.User;
import com.dormvote.biz.PublicDataMapper;
import com.dormvote.db.UserDao;
import com.dormvote.net.Macros;
import com.dormvote.net.NetRequestClient;
import com.dormvote.utils.ToastUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class VoteActivity extends BaseActivity implements View.OnClickListener {

    @InjectView(R.id.bt_vote_down)
    Button mBtVoteDown;
    @InjectView(R.id.lv_vote)
    ListView mLvVote;
    @InjectView(R.id.bt_up_pic)
    Button mBtUpPic;
    ListView mLvItemSelect;
    EditText mEtOtherReason;

    private int allScore = 0;
    private int mAllScore = 0;

    private VoteItemAdapter voteItemAdapter;
    private List<PublicDataItem> mPublicDataVoteList;
    private List<PublicDataItem> mPublicDataList;

    private String roomId;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Macros.MSG_VOTEROOM:
                    if (msg.arg1 == Macros.SUCCESS) {
                        if(mAllScore<(allScore*0.6)||mAllScore>(allScore*0.9)){
                            ToastUtils.ToastUtil("总分低于60%或高于90%，需要上传图片");
                            Intent intent = new Intent(VoteActivity.this,UpPicActivity.class);
                            intent.putExtra("roomId",roomId);
                            startActivity(intent);

                        }else {
                            ToastUtils.ToastUtil("打分成功");
                        }
                        finish();
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote_details);
        ButterKnife.inject(this);
        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setTitle("打分");
        roomId = getIntent().getStringExtra("roomId");
        ButterKnife.inject(this);
        mPublicDataVoteList = new ArrayList<>();
        mPublicDataList = new ArrayList<>();
        mPublicDataVoteList.addAll(AppConfig.sharedAppConfig(VoteActivity.this).getPublicDataType(AppConfig.PUBLIC_DATA_TYPE_NORMAL));
        mPublicDataList.addAll(AppConfig.sharedAppConfig(VoteActivity.this).getPublicDataType(AppConfig.PUBLIC_DATA_TYPE_ONE_VOTE));
//
//        try {
//            JSONObject json = new JSONObject(AppConfig.sharedAppConfig(getApplicationContext()).getPublicData());
//            Log.e("aaa", json.toString());
//            List<PublicData> publicDataList = PublicDataMapper.parsePublicData(json.getJSONArray("checkitem"));
//            for (int i = 0; i < publicDataList.size(); i++) {
//                if (publicDataList.get(i).getDataType().equals("1")) {
//                    mPublicDataVoteList.addAll(publicDataList.get(i).getItemList());
//                } else if (publicDataList.get(i).getDataType().equals("2")) {
//                    mPublicDataList.addAll(publicDataList.get(i).getItemList());
//                }
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//            Log.e("VoteActivity", e.toString());
//        }
        voteItemAdapter = new VoteItemAdapter(getApplicationContext(), mPublicDataVoteList);
        mLvVote.setAdapter(voteItemAdapter);
        mBtVoteDown.setOnClickListener(this);
        mBtUpPic.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_vote, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_down:
                List<Integer> scores = new ArrayList<>();
                for (int i = 0; i < mLvVote.getCount(); i++) {
                    String score = ((TextView)getViewByPosition(i,mLvVote).findViewById(R.id.tv_progress)).getText().toString();
                    if (score == null || score.equals("")) {
                        ToastUtils.ToastUtil("请将每项的分数打完-。-");
                        return false;
                    } else {
                        scores.add(Integer.parseInt(score));
                    }
                }
                List<PublicDataItem> mPublicDataVoteList2 = new ArrayList<>();
                mPublicDataVoteList2.addAll(mPublicDataVoteList);
                for (int i = 0; i < mPublicDataVoteList2.size(); i++) {
                    allScore += Integer.parseInt(mPublicDataVoteList.get(i).getItemValue());
                    mAllScore+=Integer.parseInt(mPublicDataVoteList2.get(i).getItemValue());
                    mPublicDataVoteList2.get(i).setItemValue(scores.get(i) + "");
                    //先上传图片 然后上传分数
                }

                NetRequestClient.sharedNetRequestClient().voteRoom(AppConfig.sharedAppConfig(getApplicationContext()).getAccessToken(), vote(mPublicDataVoteList2,"c"), roomId, handler);
                break;
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_vote_down:
               List<String> items = new ArrayList<>();
                for (int i = 0; i < mPublicDataList.size(); i++) {
                    items.add(mPublicDataList.get(i).getItemContent());
                }
                final List<String> select_item = new ArrayList<>();
                View view = View.inflate(VoteActivity.this, R.layout.activity_item_select, null);
                mLvItemSelect = (ListView) view.findViewById(R.id.lv_item_select);
              //  mEtOtherReason = (EditText) view.findViewById(R.id.et_other_reason);
                mLvItemSelect.setAdapter(new ItemChooseAdapter(VoteActivity.this,items));
                mLvItemSelect.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            ImageView is_select = (ImageView) view
                                    .findViewById(R.id.iv_is_select);
                            if (is_select.getVisibility() == View.VISIBLE) {
                                is_select.setVisibility(View.INVISIBLE);
                                select_item.remove(mPublicDataList.get(position).getItemId());
                            } else {
                                select_item.add(mPublicDataList.get(position).getItemId());
                                is_select.setVisibility(View.VISIBLE);
                            }
                    }
                });
                new AlertDialog.Builder(VoteActivity.this).setTitle("一票否决原因").setView(view).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        List<PublicDataItem> mPublicDataVoteList = new ArrayList<PublicDataItem>();
                        for (int i = 0; i < select_item.size(); i++) {
                            mPublicDataVoteList.add(new PublicDataItem(select_item.get(i),"","","0"));
                        }
                        NetRequestClient.sharedNetRequestClient().voteRoom(AppConfig.sharedAppConfig(VoteActivity.this).getAccessToken(),vote(mPublicDataVoteList,"o"),roomId,handler);
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).create().show();
//               new AlertDialog.Builder(VoteActivity.this).setTitle("一票否决原因").setItems(items,new DialogInterface.OnClickListener() {
//                   @Override
//                   public void onClick(DialogInterface dialog, int which) {
//                       if(itemIndex.contains((Integer)which)){
//                           itemIndex.remove((Integer)which);
//                       }else{
//                           itemIndex.add((Integer)which);
//                       }
//                   }
//               }).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                   @Override
//                   public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                       ToastUtils.ToastUtil(position+"");
//                   }
//
//                   @Override
//                   public void onNothingSelected(AdapterView<?> parent) {
//ToastUtils.ToastUtil("onNothingSelect");
//                   }
//               }).setPositiveButton("确定",new DialogInterface.OnClickListener() {
//                   @Override
//                   public void onClick(DialogInterface dialog, int which) {
//                   }
//               }).setNegativeButton("取消",new DialogInterface.OnClickListener() {
//                   @Override
//                   public void onClick(DialogInterface dialog, int which) {
//                       ToastUtils.ToastUtil("取消");
//                   }
//               }).create().show();
                break;
            case R.id.bt_up_pic:
                Intent intent = new Intent(VoteActivity.this,UpPicActivity.class);
                intent.putExtra("roomId",roomId);
                startActivity(intent);
                break;

        }
    }

    //    {
//        "score_type":"c",
//            "itemdata":[{
//        "checkitem_id":"10",
//                "item_score":"60"
//    },{
//        "checkitem_id":"11",
//                "item_score":"70"
//    }
//        ]}
    private JSONObject vote(List<PublicDataItem> mPublicDataVoteList,String type) {
        JSONObject json = new JSONObject();
        try {
            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < mPublicDataVoteList.size(); i++) {
                PublicDataItem dataItem = mPublicDataVoteList.get(i);
                JSONObject jsonObjectItem = new JSONObject();
                jsonObjectItem.put("item_score", dataItem.getItemValue());
                jsonObjectItem.put("checkitem_id", dataItem.getItemId());
                jsonArray.put(jsonObjectItem);
            }
            Log.e("VoteActivity", jsonArray.toString());
            json.put("score_type", type);
            json.put("itemdata", jsonArray);
        } catch (JSONException e) {
            Log.e("VoteActivity1", e.toString());
            e.printStackTrace();
        }
        Log.e("VoteActivity", json.toString());
        return json;
    }
    public View getViewByPosition(int pos, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition ) {
            return listView.getAdapter().getView(pos, null, listView);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }
}
