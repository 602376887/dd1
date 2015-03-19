package com.dormvote.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
	private static String name = "playtogether.db";

	public DBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, null, version);
	}

	public DBHelper(Context context) {
		super(context, name, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.beginTransaction();
		try {
			String CREATE_USER_TABLE = "create table user (id integer primary key ,user_id varchar(10), "
					+ "realname varchar(50),tel varchar(10),role_in_team varchar(10),"
					+ "access_token varchar(30))";
			//String im_message_sql = "CREATE TABLE im_message(id integer primary key,msgid INTEGER,fromid INTEGER,fromnickname varchar(20), toid INTEGER,content text DEFAULT (''),messagetype text DEFAULT (''),createtime INTEGER)";
			//String chat_contacts_sql = "CREATE TABLE chat_contacts(id integer primary key,targetid integer ,targetnickname varchar(20),targetavatar varchar(60), ownerid integer ,msgid INTEGER,type int,msgcontent text DEFAULT (''),createtime INTEGER,noreadcount integer DEFAULT(0),lastreadmsgid integer)";
			//String CHAT_MESSAGE_TABLE_UNIQUE_INDEX_sql = "CREATE UNIQUE INDEX unique_index_msgid ON im_message(msgid)";
			//String CHAT_CONTACTS_TABLE_UNIQUE_INDEX_sql = "CREATE UNIQUE INDEX unique_index_userid ON chat_contacts(targetid)";
			
			
			db.execSQL(CREATE_USER_TABLE);
			//db.execSQL(im_message_sql);
			//db.execSQL(chat_contacts_sql);
			//db.execSQL(CHAT_MESSAGE_TABLE_UNIQUE_INDEX_sql);//创建消息的服务器端消息id为索引
			//db.execSQL(CHAT_CONTACTS_TABLE_UNIQUE_INDEX_sql);// 创建联系人表的索引

			db.setTransactionSuccessful();

		} finally {
			db.endTransaction();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
