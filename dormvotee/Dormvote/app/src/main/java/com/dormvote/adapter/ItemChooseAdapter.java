package com.dormvote.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dormvote.R;

/**
 * 
 * @author 邓盾
 * @date 2014-7-22
 */
public class ItemChooseAdapter extends BaseAdapter {

	private Context context;
	private List<String> items;
	
	public ItemChooseAdapter(Context context, List<String> items) {
		super();
		this.context = context;
		this.items = items;
	}

	
	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int arg0) {
		return items.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		ViewHolder holder;
		if(arg1 == null){
			arg1 = View.inflate(context, R.layout.dish_template, null);
			holder = new ViewHolder();
			holder.tv_dish_name = (TextView) arg1.findViewById(R.id.tv_item_name);
			arg1.setTag(holder);
		}else{
			holder = (ViewHolder) arg1.getTag();
		}
		holder.tv_dish_name.setText(items.get(arg0));
		return arg1;
	}
	public class ViewHolder{
		TextView tv_dish_name;
	}

}
