package com.wowoweather.app.model;

public class County {
	private int county_id;
    private String county_name;
    private String county_code;
    private int city_id;
    
    public int getCityId(){
    	return city_id;
    }
    public void setCityId(int id){
    	city_id = id;
    }
    public String getCountyName(){
    	return county_name;
    }
    public void setCountyName(String name){
    	county_name = name;
    }
    public String getCountyCode(){
    	return county_code;
    }
    public void setCountyCode(String code){
    	county_code = code;
    }
    public int getCountyId(){
    	return county_id;
    }
    public void setCountyId(int id){
    	county_id = id;
    }
}
