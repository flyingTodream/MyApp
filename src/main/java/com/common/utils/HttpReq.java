package com.common.utils;


import com.alibaba.fastjson.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpReq {

	 /**
	  * ����http���󲢻�ȡ���
	  * @param requestUrl �����ַ
	  */
	 public static JSONObject getXpath(String requestUrl){
	  String res="";
	  JSONObject object = null;
	  StringBuffer buffer = new StringBuffer();
	  try{
	   URL url = new URL(requestUrl);
	   HttpURLConnection urlCon= (HttpURLConnection)url.openConnection();
	   if(200==urlCon.getResponseCode()){
	    InputStream is = urlCon.getInputStream();
	    InputStreamReader isr = new InputStreamReader(is,"utf-8");
	    BufferedReader br = new BufferedReader(isr);
	    String str = null;
	    while((str = br.readLine())!=null){
	     buffer.append(str);
	    }
	    br.close();
	    isr.close();
	    is.close();
	    res = buffer.toString();
	    object = JSONObject.parseObject(res);
	   }
	  }catch(IOException e){
	   e.printStackTrace();
	  }
	  return object;
	 }
	 
	 public static void main(String[] args) {
		 String url = "https://restapi.amap.com/v3/geocode/geo?address=%E6%AD%A6%E6%B1%89%E5%A4%A7%E5%AD%A6&key=2ae85cab2657192fb7b16af76fc1b1aa";
		 
		 //getXpath(url);
		 System.out.println(2*0.1==0.2);
	}
	 
	 
	}

