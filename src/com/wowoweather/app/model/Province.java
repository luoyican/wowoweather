package com.wowoweather.app.model;

public class Province {
   private int province_id;
   private String province_name;
   private String province_code;
   
   public int getProvinceId(){
	   return province_id;
   }
   public void setProvinceId(int id){
	   province_id = id;
   }
   public String getProvinceName(){
	   return province_name;
   }
   public void setProvinceName(String name){
	   province_name = name;
   }
   public String getProvinceCode(){
	   return province_code;
   }
   public void setProvinceCode(String code){
	   province_code = code;
   }
}
