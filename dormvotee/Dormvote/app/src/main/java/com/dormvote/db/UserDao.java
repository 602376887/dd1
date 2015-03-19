package com.dormvote.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.dormvote.bean.User;


public class UserDao {
	private static final String String = null;
	private DBHelper helper;

	public UserDao(Context context) {
		helper = new DBHelper(context);
	}

	public boolean add(User user) {
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("user_id", user.getUserId());
		values.put("realname", user.getUserRealname());
		values.put("tel", user.getUserTel());
		values.put("role_in_team", user.getUserRoleInTeam());
		values.put("access_token", user.getAccess_token());

		db.delete("user", null, null);
		long rawid = db.insert("user", null, values);
		db.close();
		if (rawid > 0) {
			return true;
		} else {
			return false;
		}
	}

	public int update(User user) {
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		String uid = java.lang.String.valueOf(user.getUserId());
        values.put("realname", user.getUserRealname());
        values.put("tel", user.getUserTel());
        values.put("role_in_team", user.getUserRoleInTeam());
        values.put("access_token", user.getAccess_token());
		return db.update("user", values, "id=?", new String[] { uid });

	}
	public void delete() {
		SQLiteDatabase db = helper.getWritableDatabase();
		db.delete("user", null, null);
		db.close();
	}

	public User query() {
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db
				.query("user", new String[] { "user_id", "realname", "tel", "role_in_team",
						"access_token"}, null, null, null,
						null, null);
		User user = null;
		while (cursor.moveToNext()) {
			if (cursor.moveToFirst()) {
				String user_id = cursor.getString(cursor.getColumnIndex("user_id"));
				String realname = cursor.getString(cursor.getColumnIndex("realname"));
				String tel = cursor.getString(cursor
						.getColumnIndex("tel"));
				String role_in_team = cursor.getString(cursor
						.getColumnIndex("role_in_team"));
				String access_token = cursor.getString(cursor
						.getColumnIndex("access_token"));

				user = new User(access_token,user_id,realname,tel,role_in_team);
			}

		}

		cursor.close();
		db.close();
		return user;
	}

}
