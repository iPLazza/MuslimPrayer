package com.example.fazazi.muslimprayer.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class RecitateurDao extends SQLiteOpenHelper{

	public static final String KEY_ID="id";
	public static final String KEY_NAME="nom";
	public static final String KEY_RECITATEURID="recitationId";
	public static final String KEY_URL="url";



	private static final String DATABASE_NAME="muslim.db";

	private static final String DATABASE_TABLE="recitateur";

	private static final String DATABASE_CREATE=
			"create table if not exists recitateur (id integer primary key autoincrement,"
					+ " nom VARCHAR not null,"
					+ " url VARCHAR not null, "
					+ " recitationId integer not null);";

	public RecitateurDao(Context context) {
		super(context, DATABASE_NAME, null, 1);
	}


	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	public boolean insertRecord(String nom,String url,int recitationId){
		SQLiteDatabase db=this.getWritableDatabase();
		db.execSQL(DATABASE_CREATE);
		ContentValues contentValues=new ContentValues();
		contentValues.put(KEY_NAME, nom);
		contentValues.put(KEY_URL, url);
		contentValues.put(KEY_RECITATEURID, recitationId);


		long result= db.insert(DATABASE_TABLE, null, contentValues);
		if(result==-1)
			return false;
		else
			return true;
	}


	public Cursor getAllData()
	{
		SQLiteDatabase db=this.getWritableDatabase();
		Cursor res=db.rawQuery("select * from "+ DATABASE_TABLE, null);
		return res;
	}

	public Cursor getUser(String userName,String code)
	{
		SQLiteDatabase db=this.getWritableDatabase();
		Cursor res=db.rawQuery("select * from "+ DATABASE_TABLE, null);

		return res;
	}




}
