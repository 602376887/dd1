package com.dormvote.utils;

import android.widget.Toast;

import com.dormvote.app.AppContext;


public class ToastUtils {
	public static void ToastUtil(String content){
		Toast.makeText(AppContext.sharedAppContext().getApplicationContext(), content, Toast.LENGTH_SHORT).show();
	}

}
