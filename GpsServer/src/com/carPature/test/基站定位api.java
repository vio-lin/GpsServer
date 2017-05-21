package com.carPature.test;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.spi.LocationInfo;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.carPature.dao.DeviceLocationLbsInfoDAO;
import com.carPature.entity1.DeviceLocationLbsInfo;

public class 基站定位api {
	public static void main(String[] args) {
//		List<Integer> ids = new ArrayList<Integer>(Arrays.asList(new Integer[]{21,23,25,27,29,30,32,33,35,38,39,40,41,43,44,47,48,49,50}));
		for(int i = 77;i<=88;i++){
			System.out.println("编号"+i+":");
			System.out.println(getRequest(i));
		}
	}
    public static String getRequest(int id) {
    	String resultString = "http://api.gpsspg.com/bs/?oid=4853&key=3B952D4AF0E59DA44F5EC8A5324AE66A&";
    	ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        DeviceLocationLbsInfoDAO locationdao = (DeviceLocationLbsInfoDAO) context.getBean("DeviceLocationLbsInfoDAO");
        DeviceLocationLbsInfo info = locationdao.getSpacificLocation(id);
    	/** 这边的version 是从文件中读取相应的数据 
        File file = new File("D://data//save.txt");
        StringBuilder sb = new StringBuilder();
        try {
            FileReader reader = new FileReader(file);
            BufferedReader rd = new BufferedReader(reader);
            String temp =rd.readLine();
            int i =1;
            while(temp!="end"&&temp!=null){
                 String[] arg = temp.split(",");
//                 460,0,6347,54147,48
//                 resultString += "bs=460,0,6347,54147,-70%7C460,0,6347,54050,-63%7C460,0,6347,54146,-69";
                 sb.append("460,");
                 sb.append(arg[1]+",");
                 sb.append(arg[2]+",");
                 sb.append(arg[3]+",");
                 sb.append((Integer.valueOf(arg[4])-110)+"");
                 temp =rd.readLine();
                 if(temp!="end"&&temp!=null){
                     sb.append("%7C");
                 }
            }
        } catch (FileNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }*/
    	
        //重复拿到多组数据
        StringBuilder sb = new StringBuilder();
        if(info.getCi1()!=0){
        	sb.append("460,"+info.getMnc()+","+info.getLac1()+","+info.getCi1()+","+getRssi(info.getRssi()));
        }
        if(info.getCi2()!=0){
        	sb.append("%7C");
        	sb.append("460,"+info.getMnc()+","+info.getCi2()+","+info.getLac2()+","+getRssi(info.getRssi2()));
        }
        if(info.getCi3()!=0){
        	sb.append("%7C");
        	sb.append("460,"+info.getMnc()+","+info.getCi3()+","+info.getLac3()+","+getRssi(info.getRssi3()));
        }
        if(info.getCi4()!=0){
        	sb.append("%7C");
        	sb.append("460,"+info.getMnc()+","+info.getCi4()+","+info.getLac4()+","+getRssi(info.getRssi4()));
        }
        if(info.getCi5()!=0){
        	sb.append("%7C");
        	sb.append("460,"+info.getMnc()+","+info.getCi5()+","+info.getLac5()+","+getRssi(info.getRssi5()));
        }
        if(info.getCi6()!=0){
        	sb.append("%7C");
        	sb.append("460,"+info.getMnc()+","+info.getCi6()+","+info.getLac6()+","+getRssi(info.getRssi6()));
        }
        if(info.getCi7()!=0){
        	sb.append("%7C");
        	sb.append("460,"+info.getMnc()+","+info.getCi7()+","+info.getLac7()+","+getRssi(info.getRssi7()));
        }
        resultString += "bs="+sb.toString();
        //        resultString += "bs=460,0,6347,54147,-70%7C460,0,6347,54050,-63%7C460,0,6347,54146,-69";
        resultString += "&output=json&to=1";
//        System.out.println(resultString);
        /** 这里采用get方法，直接将参数加到URL上 */
        /** 新建HttpClient */
        HttpClient client = new DefaultHttpClient();
        /** 采用GET方法 */
//        System.out.println(resultString);
        HttpGet get = new HttpGet(resultString);
        try {
            /** 发起GET请求并获得返回数据 */
            HttpResponse response = client.execute(get);
            HttpEntity entity = response.getEntity();
            BufferedReader buffReader = new BufferedReader(
                    new InputStreamReader(entity.getContent()));
            StringBuffer strBuff = new StringBuffer();
            String result = null;
            while ((result = buffReader.readLine()) != null) {
                strBuff.append(result);
            }
            resultString = strBuff.toString();
            System.out.println();
//            System.out.println(resultString);
            return resultString;
          /*   下面添加的方法 用开返回一个直接可以定位的 url信息
           *  System.out.println();
            *//** 解析JSON数据，获得物理地址 *//*
            if (resultString != null && resultString.length() > 0) {
                JSONObject jsonobject = new JSONObject(resultString);
                System.out.println("lat" + jsonobject.getString("latitude"));
                System.out.println("lon" + jsonobject.getString("longitude"));
                System.out.println("https://www.google.com/maps/place/"+jsonobject.getString("latitude")+","+jsonobject.getString("longitude"));
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            get.abort();
            client = null;
        }
		return "Faild";
    }
    
    public static int getRssi(int Rexle){
    	if(Rexle>63){
    		return -48;
    	}else if(Rexle<0){
    		return -110;
    	}else{
    		return Rexle -110;
    	}
    }
}