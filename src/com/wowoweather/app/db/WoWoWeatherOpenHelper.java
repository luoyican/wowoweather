package com.wowoweather.app.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class WoWoWeatherOpenHelper extends SQLiteOpenHelper{
    
	private static final String CREATE_PROVINCE="create table Province("
			                                                     +"province_id integer primary key autoincrement,"
	                                                             +"province_name text,"
			                                                     +"province_code text)";
	
	private static final String CREATE_CITY="create table City("
			                                                +"city_id integer primary key autoincrement,"
			                                                +"city_name text,"
			                                                +"city_code text,"
			                                                +"province_id integer)";
	
	private static final String CREATE_COUNTY="create table County("
			                                                +"county_id integer primary key autoincrement,"
			                                                +"county_name text,"
			                                                +"county_code text,"
			                                                +"city_id integer)";
    public WoWoWeatherOpenHelper(Context context,String name,CursorFactory factory,int version) {
		// TODO Auto-generated constructor stub
    	super(context, name, factory, version);
	}
	@Override
    public void onCreate(SQLiteDatabase db) {
    	// TODO Auto-generated method stub
    	db.execSQL(CREATE_PROVINCE);
    	db.execSQL(CREATE_CITY);
    	db.execSQL(CREATE_COUNTY);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    	// TODO Auto-generated method stub
    }
}
