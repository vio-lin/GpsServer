package com.carPature.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
//取出定位点中数值最大的rssi信号点对应的基站位置
import java.io.FileReader;
import java.io.IOException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class ExportData {
	public static void main(String[] args) {
		//D://data/1.txt 东区南可以获得的数据
		File file = new File("D://data/1.txt");
		try {
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String line = br.readLine();
			int count =1;
			while(line!=null&&!"".equals(line)){
				//获取每一行的最大的RSSI 和对应的数据
				//获取行号和每一行所需要的最大的数据和总的平均值
				JSONArray array = (JSONArray) JSON.parse(line);
				int maxrssi = 0;
				String loc = "";
				int sum =0;
				System.out.println("第"+count+"组数据");
				for(Object obj : array){
					JSONObject objtemp = (JSONObject)obj;
					int ci = objtemp.getInteger("ci");
					int lac = objtemp.getInteger("lac");
					int rssi = objtemp.getInteger("rssi");
					System.out.println();
					sum+=rssi;
					if(maxrssi<=rssi){
						loc = "lac:"+lac+";loc"+ci;
						maxrssi = rssi;
					}
					System.out.println();
				}
				
//				System.out.println(count+"|"+loc+"|"+maxrssi+"|"+sum*1.0/array.size());
//				System.out.println(count+":"+"最大的信号基站"+loc+"信号量"+maxrssi+"各个基站的平均信号"+sum*1.0/array.size());
				count++;
				line =br.readLine();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
