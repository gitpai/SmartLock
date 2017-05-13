package com.global.controller;

import org.json.JSONArray;
import org.json.JSONObject;

public class Ceshi {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String hh = "{\"lat\":\"31.318665\",\"lon\":\"121.394760\",\"asl\":\"37.800\",\"locksta\":\"false\"}";
         JSONObject  dataJson=new JSONObject(hh);
        // String 
         try {
        	 System.out.println(dataJson.getDouble("lat")); 
             System.out.println(dataJson.getDouble("lon")); 
             System.out.println(dataJson.getDouble("asl")); 
             System.out.println(dataJson.getBoolean("locksta")); 
		} catch (Exception e) {
			System.out.println("fashngcuowu");
		}
        
         
        /* 
 		JSONObject  response=dataJson.getJSONObject("response");
 		JSONArray data=response.getJSONArray("data");
 		JSONObject info=data.getJSONObject(0);
 		
 		
 		String province=info.getString("province");
 		String city=info.getString("city");
 		String district=info.getString("district");
 		String address=info.getString("address");*/
	}

}
