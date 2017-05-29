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

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class RelationRssiDistAll {
	public static void main(String[] args) {
		File file = new File("D://data//data3.txt");
		ArrayList<Position> PosList = new ArrayList<>();
		try {
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String line = br.readLine();
			while (line != null && !line.equals("")) {
				JSONArray arr = JSON.parseArray(line);
				for (Object obj : arr) {
					JSONObject objtemp = (JSONObject) obj;
					// System.out.println(objtemp.getInteger("rssi:")+" "+objtemp.getDouble("dis"));
					PosList.add(new Position(objtemp.getInteger("rssi:"),
							objtemp.getDouble("dis")));
				}
				line = br.readLine();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Collections.sort(PosList);
		ArrayList<Double> list = new ArrayList<>();
		for (int i = 0; i < PosList.size(); i += 2) {
//			System.out.println(PosList.get(i).rssi + "|" + PosList.get(i).dis
//					+ "|" + PosList.get(i + 1).rssi + "|"
//					+ PosList.get(i + 1).dis);
			System.out.printf("%d|%.2f|%d|%.2f\n",PosList.get(i).rssi,PosList.get(i).dis
					,PosList.get(i + 1).rssi ,PosList.get(i + 1).dis);
			list.add(PosList.get(i).dis);
			list.add(PosList.get(i+1).dis);
		}
		// 求证逆序数
		AntiOrder ati = new AntiOrder();
		double[] temp = new double[list.size()];
		for(int i =0;i<list.size();i++){
			temp[i] = list.get(i);
		}
		ati.count(temp, temp.length);
		System.out.println(ati.num);
	}
}

class Position implements Comparable<Position> {
	public int rssi;
	public double dis;

	public Position(int rssi, double dis) {
		this.rssi = rssi;
		this.dis = dis;
	}

	@Override
	public int compareTo(Position o) {
		// TODO Auto-generated method stub
		return this.rssi - o.rssi;
	}
}

class AntiOrder {
	int num = 0;
	public AntiOrder(){
		super();
	}
	public int count(double[] A, int n) {
		merge(A, 0, n - 1);
		return num;
	}

	private void merge(double[] A, int l, int r) {
		if (l >= r)
			return;
		double[] tmp = new double[r - l + 1];
		int mid = (l + r) / 2;
		merge(A, l, mid);
		merge(A, mid + 1, r);
		int i = 0, j = l, k = mid + 1;
		while (j <= mid && k <= r) {
			if (A[j] >= A[k])
				tmp[i++] = A[j++];
			else {
				tmp[i++] = A[k++];
				num += mid - j + 1;
			}
		}
		while (j <= mid)
			tmp[i++] = A[j++];
		while (k <= r)
			tmp[i++] = A[k++];
		i = 0;
		for (int m = l; m <= r; m++) {
			A[m] = tmp[i++];
		}
	}

	@Test
	public void testAnti() {
		double[] num = new double[] { 1.0, 2.0, 3.0 };
		AntiOrder anti = new AntiOrder();
		anti.count(num, num.length);
		System.out.println(anti.num);
	}
}

