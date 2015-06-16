package com.wowoweather.app.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.json.JSONObject;

import com.wowoweather.app.model.City;
import com.wowoweather.app.model.County;
import com.wowoweather.app.model.Province;
import com.wowoweather.app.model.WoWoWeatherDB;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;

public class Utility {
    public synchronized static boolean handleProvincesResponse(WoWoWeatherDB wowoWeatherDB, String response){
    	  if(!TextUtils.isEmpty(response)){
    		  String[] allprovinces = response.split(",");
    		  if(allprovinces != null && allprovinces.length>0){
    			    for(String s : allprovinces){
    			    	String[] tempdata = s.split("\\|");
    			    	Province province = new Province();
    			    	province.setProvinceCode(tempdata[0]);
    			    	province.setProvinceName(tempdata[1]);
    			    	wowoWeatherDB.saveProvince(province);
    			    }
    			    return true;
    		  }
    	  }
    	  return false;
    }
    public synchronized static boolean handleCityResponse(WoWoWeatherDB wowoWeatherDB, String response,int provinceId){
  	  if(!TextUtils.isEmpty(response)){
  		  String[] allcities = response.split(",");
  		  if(allcities != null && allcities.length>0){
  			    for(String s : allcities){
  			    	String[] tempdata = s.split("\\|");
  			    	City city = new City();
  			    	city.setCityCode(tempdata[0]);
  			    	city.setCityName(tempdata[1]);
  			    	city.setProvinceId(provinceId);
  			    	wowoWeatherDB.saveCity(city);
  			    }
  			    return true;
  		  }
  	  }
  	  return false;
  } 
    public synchronized static boolean handleCountyResponse(WoWoWeatherDB wowoWeatherDB, String response,int cityId){
    	  if(!TextUtils.isEmpty(response)){
    		  String[] allcounties = response.split(",");
    		  if(allcounties != null && allcounties.length>0){
    			    for(String s : allcounties){
    			    	String[] tempdata = s.split("\\|");
    			    	County county = new County();
    			    	county.setCountyCode(tempdata[0]);
    			    	county.setCountyName(tempdata[1]);
    			    	county.setCityId(cityId);
    			    	wowoWeatherDB.saveCounty(county);
    			    }
    			    return true;
    		  }
    	  }
    	  return false;
    } 
    
    public static void handleWeatherResponse(Context context,String response){
    	try{
    		JSONObject jsonObeject = new JSONObject(response);
    		JSONObject weatherInfo = jsonObeject.getJSONObject("weatherinfo");
    		String cityName = weatherInfo.getString("city");
    		String weatherCode = weatherInfo.getString("cityid");
    		String temp1 = weatherInfo.getString("temp1");
    		String temp2 = weatherInfo.getString("temp2");
    		String weatherDesp = weatherInfo.getString("weather");
    		String pubishTime = weatherInfo.getString("ptime");
    		//Log.d("HEHEHEHEHEHEH",temp1);
    		saveWeatherInfo(context,cityName,weatherCode,temp1,temp2,weatherDesp,pubishTime);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
	public static void saveWeatherInfo(Context context, String cityName,
			String weatherCode, String temp1, String temp2, String weatherDesp,
			String pubishTime) {
		// TODO Auto-generated method stub
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyƒÍM‘¬d»’", Locale.CHINA);
		SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
		editor.putBoolean("city_selected", true);
		editor.putString("city_name", cityName);
		editor.putString("weather_code", weatherCode);
		editor.putString("temp1", temp1);
		editor.putString("temp2", temp2);
		editor.putString("weather_desp", weatherDesp);
		editor.putString("publish_time", pubishTime);
		editor.putString("current_date",sdf.format(new Date()));
		editor.commit();
	}
}
