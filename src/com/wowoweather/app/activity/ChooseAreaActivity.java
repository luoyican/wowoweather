package com.wowoweather.app.activity;

import java.util.ArrayList;
import java.util.List;

import com.wowoweather.app.R;
import com.wowoweather.app.model.City;
import com.wowoweather.app.model.County;
import com.wowoweather.app.model.Province;
import com.wowoweather.app.model.WoWoWeatherDB;
import com.wowoweather.app.util.HttpCallbackListener;
import com.wowoweather.app.util.HttpUtil;
import com.wowoweather.app.util.Utility;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ChooseAreaActivity extends Activity{
   public static final int PROVINCE_LEVEL = 0;
   public static final int CITY_LEVEL = 1;
   public static final int COUNTY_LEVEL = 2;
   
   private ProgressDialog progressDialog;
   private ListView listView;
   private TextView titleText;
   private ArrayAdapter<String> adapter;
   private WoWoWeatherDB wowoWeatherDB;
   private List<String> dataList = new ArrayList<String>();
   private List<Province> provinceList;
   private List<City> cityList;
   private List<County> countyList;
   private Province selectProvince;
   private City selectCity;
   private int currentLevel;
   private boolean isFromWeatherActivity;
   
   @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		isFromWeatherActivity = getIntent().getBooleanExtra("from_weather_activity", false);
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		if(prefs.getBoolean("city_selected", false)&& !isFromWeatherActivity){
			Intent intent = new Intent(this,WeatherActivity.class);
		    startActivity(intent);
		    finish();
		    return;
		}
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
	    setContentView(R.layout.choose_area);
	    listView = (ListView) findViewById(R.id.list_view);
	    titleText = (TextView) findViewById(R.id.title_text);
	    adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,dataList);
	    listView.setAdapter(adapter);
	    wowoWeatherDB = WoWoWeatherDB.getInstance(this);
	    listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if(currentLevel == PROVINCE_LEVEL){
					selectProvince = provinceList.get(position);
					queryCities();
				}else if(currentLevel == CITY_LEVEL){
					selectCity = cityList.get(position);
					queryCounties();
				}else if(currentLevel == COUNTY_LEVEL){
					String countyCode = countyList.get(position).getCountyCode();
					Intent intent = new Intent(ChooseAreaActivity.this,WeatherActivity.class);
					intent.putExtra("county_code", countyCode);
					startActivity(intent);
					finish();
				}
			}
		});
	    queryProvinces();
   }
   private void queryProvinces(){
	   provinceList = wowoWeatherDB.loadProvinces();
	   if(provinceList.size() > 0){
		   dataList.clear();
		   for(Province province:provinceList){
			   dataList.add(province.getProvinceName());
		   }
		   adapter.notifyDataSetChanged();
		   listView.setSelection(0);
		   titleText.setText("中国");
		   currentLevel = PROVINCE_LEVEL;
	   }else{
		   queryFromServer(null,"province");
	   }
   }
   private void queryCities(){
	   cityList = wowoWeatherDB.loadCity(selectProvince.getProvinceId());
	   if(cityList.size() > 0){
		   dataList.clear();
		   for(City city : cityList){
			   dataList.add(city.getCityName());
		   }
		   adapter.notifyDataSetChanged();
		   listView.setSelection(0);
		   titleText.setText(selectProvince.getProvinceName());
		   currentLevel = CITY_LEVEL;
	   }else{
		   queryFromServer(selectProvince.getProvinceCode(),"city");
	   }
   }
   private void queryCounties(){
	   countyList = wowoWeatherDB.loadCounty(selectCity.getCityId());
	   if(countyList.size() > 0){
		   dataList.clear();
		   for(County county : countyList){
			   dataList.add(county.getCountyName());
		   }
		   adapter.notifyDataSetChanged();
		   listView.setSelection(0);
		   titleText.setText(selectCity.getCityName());
		   currentLevel = COUNTY_LEVEL;
	   }else{
		   queryFromServer(selectCity.getCityCode(),"county");
	   }
   }
   private void queryFromServer(final String code, final String type){
	   String address;
	   if(!TextUtils.isEmpty(code)){
		   address = "http://www.weather.com.cn/data/list3/city"+code+".xml";
	   }else{
		   address = "http://www.weather.com.cn/data/list3/city.xml";
	   }
	   showProgressDialog();
	   HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
		
		@Override
		public void onFinish(String response) {
			// TODO Auto-generated method stub
			boolean result = false;
			//Log.d("NININI","SMKSMIGONSNIDGNVI");
			if("province".equals(type)){
				result = Utility.handleProvincesResponse(wowoWeatherDB,response);
			}else if("city".equals(type)){
				result = Utility.handleCityResponse(wowoWeatherDB, response, selectProvince.getProvinceId());
			}else if("county".equals(type)){
				result = Utility.handleCountyResponse(wowoWeatherDB, response, selectCity.getCityId());
			}
			if(result){
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						closeProgressDialog();
						if("province".equals(type)){
						     queryProvinces();	
						}else if("city".equals(type)){
							 queryCities();
						}else if("county".equals(type)){
							 queryCounties();
						}
					}
				});
			}
		}
		
		@Override
		public void onError(Exception e) {
			// TODO Auto-generated method stub
			runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
				   closeProgressDialog();
				   Toast.makeText(ChooseAreaActivity.this, "加载失败，稍后再试", Toast.LENGTH_LONG).show();
				}
			});
		}
	});
   }
   private void showProgressDialog(){
	   if(progressDialog == null){
		   progressDialog = new ProgressDialog(this);
		   progressDialog.setMessage("正在玩命的加载....");
		   progressDialog.setCanceledOnTouchOutside(false);
	   }
	   progressDialog.show();
   }
   private void closeProgressDialog(){
	   if(progressDialog != null){
		   progressDialog.dismiss();
	   }
   }
   public void onBackPressed(){
	   if(currentLevel ==COUNTY_LEVEL){
		   queryCities();
	   }else if(currentLevel == CITY_LEVEL){
		   queryProvinces();
	   }else{
		   if(isFromWeatherActivity){
		   Intent intent = new Intent(this,WeatherActivity.class);
		   startActivity(intent);
		   }
			   finish();
	   }
   }
}
