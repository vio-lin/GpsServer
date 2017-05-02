package com.carPature.test;

import org.junit.Test;

import com.carcarPature.utils.GenCRC;

public class TestRCR {
	@Test
	public void getRCR(){
		byte[] pData = new byte[]{
				0x05,0x01,0x11,0x22 
		};
		char c =  GenCRC.getCrc16(pData);
		String a  = Integer.toHexString(c+0);
		System.out.println(a);
	}
}
