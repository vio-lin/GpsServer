package com.carPature.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class RelationRssiDist {
	public static void main(String[] args) {
		File file = new File("D://data//data3.txt");
		int anti=0;
		try {
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String line = br.readLine();
			while (line != null && !line.equals("")) {
				ArrayList<Position> PosList = new ArrayList<>();
				JSONArray arr = JSON.parseArray(line);
				for (Object obj : arr) {
					JSONObject objtemp = (JSONObject) obj;
					// System.out.println(objtemp.getInteger("rssi:")+" "+objtemp.getDouble("dis"));
					PosList.add(new Position(objtemp.getInteger("rssi:"),
							objtemp.getDouble("dis")));
				}
				Collections.sort(PosList);
				ArrayList<Double> list = new ArrayList<>();
				for (int i = 0; i < PosList.size(); i++) {
					System.out.println(PosList.get(i).rssi + "|" + PosList.get(i).dis
							+ "|" );
					list.add(PosList.get(i).dis);
				}
				// 求证逆序数
				AntiOrder ati = new AntiOrder();
				double[] temp = new double[list.size()];
				for (int i = 0; i < list.size(); i++) {
					temp[i] = list.get(i);
				}
				ati.count(temp, temp.length);
				System.out.println(ati.num);
				anti += ati.num;
				line = br.readLine();
			}
			System.out.println(anti);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
