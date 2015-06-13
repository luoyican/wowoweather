package com.wowoweather.app.model;

public class City {
    private int city_id;
    private String city_name;
    private String city_code;
    private int province_id;
    
    public int getCityId(){
    	return city_id;
    }
    public void setCityId(int id){
    	city_id = id;
    }
    public String getCityName(){
    	return city_name;
    }
    public void setCityName(String name){
    	city_name = name;
    }
    public String getCityCode(){
    	return city_code;
    }
    public void setCityCode(String code){
    	city_code = code;
    }
    public int getProvinceId(){
    	return province_id;
    }
    public void setProvinceId(int id){
    	province_id = id;
    }
    
}
