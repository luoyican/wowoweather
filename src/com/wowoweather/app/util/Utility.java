package com.wowoweather.app.util;

import com.wowoweather.app.model.City;
import com.wowoweather.app.model.County;
import com.wowoweather.app.model.Province;
import com.wowoweather.app.model.WoWoWeatherDB;

import android.text.TextUtils;

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
}
