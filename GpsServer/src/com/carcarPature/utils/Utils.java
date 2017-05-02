/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.carcarPature.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * This class includes a small subset of standard GATT attributes for
 * demonstration purposes.
 */
public class Utils {

	private static HashMap<Integer, String> serviceTypes = new HashMap();

	public static String getServiceType(int type) {
		return serviceTypes.get(type);
	}



	// --------------------------------------------------------------------------


	/**
	 * 位运算结果的反推函数10 -> 2 | 8;
	 */
	static private List<Integer> getElement(int number) {
		List<Integer> result = new ArrayList<Integer>();
		for (int i = 0; i < 32; i++) {
			int b = 1 << i;
			if ((number & b) > 0)
				result.add(b);
		}

		return result;
	}

	public static String bytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder("");
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}

	public static String bytesToString(byte[] src) {
		// byte[] ת string
		String res = new String(src);
		return res;
	}

	// HexString����>byte
	public static byte[] hexStringToBytes(String hexString) {
		if (hexString == null || hexString.equals("")) {
			return null;
		}
		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));

		}
		return d;
	}

	private static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}

	// 判断是否16进制字符
	public static boolean isHexChar(String str) {
		for (int i = 0; i < str.length(); i++) {
			if ((str.charAt(i) >= '0' && str.charAt(i) <= '9')
					|| (str.charAt(i) >= 'a' && str.charAt(i) <= 'f')
					|| (str.charAt(i) >= 'A' && str.charAt(i) <= 'F')) {
			} else {
				return false;
			}
		}
		return true;
	}
	
	public static int byteArrayToInt(byte[] b, int offset) {
	       int value= 0;
	       for (int i = 0; i < 4; i++) {
	           //int shift= (4 - 1 - i) * 8;
	    	   int shift= (i) * 8;
	           value +=(b[i + offset] & 0x000000FF) << shift;
	       }
	       return value;
	 }	
	
	public static short byteArrayToShort(byte[] b, int offset) {
	       short value= 0;
	       for (int i = 0; i < 2; i++) {
//	           int shift= (2 - 1 - i) * 8;
	    	   int shift= (i) * 8;
	           value +=(b[i + offset] & 0x00FF) << shift;
	       }
	       return value;
	 }		
}
