package com.wowoweather.app.model;

import java.util.ArrayList;
import java.util.List;

import com.wowoweather.app.db.WoWoWeatherOpenHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class WoWoWeatherDB {
     public static final String DB_NAME="wowo_weather";
     public static final int VERSION = 1;
     private static WoWoWeatherDB wowoWeatherDB;
     private SQLiteDatabase db;
     
     private WoWoWeatherDB(Context context){
    	 WoWoWeatherOpenHelper dbHelper = new WoWoWeatherOpenHelper(context, DB_NAME, null, VERSION);
    	 db = dbHelper.getWritableDatabase();
     }
     
     public synchronized static WoWoWeatherDB getInstance(Context context){
    	 if(wowoWeatherDB == null){
    		 wowoWeatherDB = new WoWoWeatherDB(context);
    	 }
    	 return wowoWeatherDB;
     }
     public void saveProvince(Province province){
    	 if(province != null){
    		 ContentValues values = new ContentValues();
    		 values.put("province_name", province.getProvinceName());
    		 values.put("province_code", province.getProvinceCode());
    		 db.insert("Province", null, values);
    	 }
     }
     public List<Province> loadProvinces(){
    	 List<Province> list = new ArrayList<Province>();
    	 Cursor cursor = db.query("Province", null, null, null, null, null, null);
    	 if(cursor.moveToFirst()){
    		 do{
    			 Province province = new Province();
    			 province.setProvinceId(cursor.getInt(cursor.getColumnIndex("province_id")));
    			 province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
    			 province.setProvinceCode(cursor.getString(cursor.getColumnIndex("province_code")));
    			 list.add(province);
    		 }while(cursor.moveToNext());
    	 }
    	 return list;
     }
     public void saveCity(City city){
    	 if(city != null){
    		 ContentValues values = new ContentValues();
    	     values.put("city_name", city.getCityName());
    	     values.put("city_code", city.getCityCode());
    	     values.put("province_id", city.getProvinceId());
    	     db.insert("City", null, values);
    	 }
     }
     public List<City> loadCity(int provinceId){
    	 List<City> list = new ArrayList<City>();
    	 Cursor cursor = db.query("City", null,"province_id=?", new String[]{String.valueOf(provinceId)}, null, null, null);
    	 if(cursor.moveToFirst()){
    		 do{
    			 City city = new City();
    			 city.setCityId(cursor.getInt(cursor.getColumnIndex("city_id")));
    			 city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
    			 city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
    			 city.setProvinceId(provinceId);
    			 list.add(city);
    		 }while(cursor.moveToNext());
    	 }
    	 return list;
     }
     public void saveCounty(County county){
    	 if(county != null){
    		 ContentValues values = new ContentValues();
    	     values.put("county_name", county.getCountyName());
    	     values.put("county_code", county.getCountyCode());
    	     values.put("city_id", county.getCityId());
    	     db.insert("County", null, values);
    	 }
     }
     public List<County> loadCounty(int cityId){
    	 List<County> list = new ArrayList<County>();
    	 Cursor cursor = db.query("County", null,"city_id=?",new String[]{String.valueOf(cityId)}, null, null, null);
    	 if(cursor.moveToFirst()){
    		 do{
    			 County county = new County();
    			 county.setCityId(cityId);
    			 county.setCountyName(cursor.getString(cursor.getColumnIndex("county_name")));
    			 county.setCountyCode(cursor.getString(cursor.getColumnIndex("county_code")));
    			 county.setCountyId(cursor.getInt(cursor.getColumnIndex("county_id")));
    			 list.add(county);
    		 }while(cursor.moveToNext());
    	 }
    	 return list;
     }
}
