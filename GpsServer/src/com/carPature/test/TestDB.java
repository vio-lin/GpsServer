package com.carPature.test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;




import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.carPature.dao.DeviceLocationInfoDAO;
import com.carPature.dao.DeviceLocationLbsInfoDAO;
import com.carPature.dao.DeviceStatusDAO;
import com.carPature.dao.UsersDAO;
import com.carPature.entity1.DeviceLocationInfo;
import com.carPature.entity1.DeviceLocationLbsInfo;
import com.carPature.entity1.DeviceStatus;
import com.carPature.entity1.Users;

public class TestDB {
	@Test
	public void TestUser(){
		//
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		context.start();
		UsersDAO dao = (UsersDAO) context.getBean("UserDao"); 
		Users user = new Users();
		user.setImei("lux");
		user.setName("sjhdjd");
		user.setPlatenumber("浙J888");
		dao.addUser(user);
	}
	@Test
	public void TestDeviceStat(){
		ClassPathXmlApplicationContext context =  new ClassPathXmlApplicationContext("applicationContext.xml");
		context.start();
		DeviceStatusDAO dao = (DeviceStatusDAO) context.getBean("DeviceStatusDAO");
		DeviceStatus device = new DeviceStatus();
		//增加的代码
		device.setImei("481215842551");
		device.setLanguage(true);
		device.setBattery(2.9f);
		device.setCharging(true);
		device.setGpsstate(false);
		device.setAcc(true);
		device.setGsm(5);
		device.setGuard(true);
		device.setOilelectirc(true);
		dao.insertDevice(device);
		//删除的代码
//		dao.updateDevice(device);
		DeviceStatus condition = new DeviceStatus();
		DeviceStatus device2 = dao.searchDecice("481215842551");
		System.out.println(device2);
		
	}
	@Test
	public void TestDeviceLocationInfo(){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		context.start();
		DeviceLocationInfoDAO locationdao = (DeviceLocationInfoDAO) context.getBean("DeviceLocationInfoDAO");
		DeviceLocationInfo location = new DeviceLocationInfo();
		location.setAcc(true);
		location.setCellId(558);
		location.setDate(new Timestamp(2017-1900,4,3,22,10,0,1));
		location.setGpssstat("asdasd".getBytes());
		location.setLac(418);
		location.setLatitude(31.21645648);
		location.setLongtitude(121.123546132);
		location.setMcc(4545);
		location.setMnc(4878);
		location.setOploadMode("asda");
		location.setOrienStat("orie");
		location.setRealNot(false);
		location.setSpeed(45141.5f);
		locationdao.insertLocation(location);
	}
	
	@Test
	public void TestDeviceLocationLBSInfo(){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		context.start();
		DeviceLocationLbsInfoDAO locationdao = (DeviceLocationLbsInfoDAO) context.getBean("DeviceLocationLbsInfoDAO");
		DeviceLocationLbsInfo info = new DeviceLocationLbsInfo();
		info.setCi1(1545);
		info.setCi2(1546);
		info.setCi3(1547);
		info.setCi4(1548);
		info.setCi5(1549);
		info.setCi6(1550);
		info.setCi7(1551);
		info.setLac1(456);
		info.setLac2(457);
		info.setLac3(458);
		info.setLac4(459);
		info.setLac5(460);
		info.setLac6(461);
		info.setRssi(125);
		info.setRssi2(126);
		info.setRssi3(127);
		info.setRssi4(128);
		info.setRssi5(129);
		info.setRssi6(130);
		info.setRssi7(131);
		info.setMcc(57);
		info.setMnc(48);
		locationdao.insertLbsInfo(info);
	}
	@Test
	public void getChange(){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		context.start();
		DeviceLocationLbsInfoDAO locationdao = (DeviceLocationLbsInfoDAO) context.getBean("DeviceLocationLbsInfoDAO");
		List<Integer> ids = new ArrayList<Integer>(Arrays.asList(new Integer[]{21,23,25,27,29,30,32,33,35,38,39,40,41,43,44,47,48,49,50}));
//		List<Integer> ids = new ArrayList<Integer>(Arrays.asList(new Integer[]{32}));
		
		for(int i =77;i<=88;i++){
			DeviceLocationLbsInfo info = locationdao.getSpacificLocation(i);
			List<Object> list = new ArrayList<Object>();
			list.add(new BaseStation(info.getLac1(), info.getCi1(), info.getRssi()));
			if(info.getCi2()!=0)
			list.add(new BaseStation(info.getCi2(),info.getLac2(), info.getRssi2()));
			if(info.getCi3()!=0)
			list.add(new BaseStation(info.getCi3(),info.getLac3(), info.getRssi3()));
			if(info.getCi4()!=0)
			list.add(new BaseStation(info.getCi4(),info.getLac4(), info.getRssi4()));
			if(info.getCi5()!=0)
			list.add(new BaseStation(info.getCi5(),info.getLac5(), info.getRssi5()));
			if(info.getCi6()!=0)
			list.add(new BaseStation(info.getCi6(),info.getLac6(), info.getRssi6()));
			if(info.getCi7()!=0)
			list.add(new BaseStation(info.getCi7(),info.getLac7(), info.getRssi7()));
			JSONArray array = new JSONArray(list);
			System.out.println("编号："+i+":");
			System.out.println(array.toJSONString());
		}
		
	}
	
	class BaseStation{
		public int lac;
		public int ci;
		public int rssi;
		public int getLac() {
			return lac;
		}
		public void setLac(int lac) {
			this.lac = lac;
		}
		public int getCi() {
			return ci;
		}
		public void setCi(int ci) {
			this.ci = ci;
		}
		public int getRssi() {
			return rssi;
		}
		public void setRssi(int rssi) {
			this.rssi = rssi;
		}
		public BaseStation(int lac, int ci, int rssi) {
			this.lac = lac;
			this.ci = ci;
			this.rssi = rssi;
		}
		
	}
}
