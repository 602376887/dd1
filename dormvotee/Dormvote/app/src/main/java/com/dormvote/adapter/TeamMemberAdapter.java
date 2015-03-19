package com.dormvote.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dormvote.R;
import com.dormvote.bean.User;
import com.dormvote.net.Macros;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Administrator on 2015/1/9.
 */
public class TeamMemberAdapter extends BaseAdapter {
    private Context context;
    private List<User> users;

    public TeamMemberAdapter(Context context, List<User> users) {
        this.context = context;
        this.users = users;
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder= null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.activity_team_member_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        User user=users.get(position);

        holder.mTvName.setText(user.getUserRealname());
        Log.e("adapter",user.getUserRoleInTeam());
        if(user.getUserRoleInTeam().equals(Macros.NET_STATUS_0.getStatus()+"")){
            holder.mTvRoleInTeam.setText(Macros.NET_STATUS_0.getExplain());
        }else if(user.getUserRoleInTeam().equals(Macros.NET_STATUS_1.getStatus()+"")){
            holder.mTvRoleInTeam.setText(Macros.NET_STATUS_1.getExplain());
        }else {
            holder.mTvRoleInTeam.setText("no nanme");
        }

        holder.mTvTel.setText(user.getUserTel());

        return convertView;
    }

    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'activity_team_member_item.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Inmite Developers (http://inmite.github.io)
     */
    static class ViewHolder {
        @InjectView(R.id.tv_name)
        TextView mTvName;
        @InjectView(R.id.tv_role_in_team)
        TextView mTvRoleInTeam;
        @InjectView(R.id.tv_tel)
        TextView mTvTel;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
