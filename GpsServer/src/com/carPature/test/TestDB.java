package com.carPature.test;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.carPature.dao.DeviceLocationInfoDAO;
import com.carPature.dao.DeviceStatusDAO;
import com.carPature.dao.UsersDAO;
import com.carPature.entity1.DeviceLocationInfo;
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
		location.setDate(new Timestamp(System.currentTimeMillis()));
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
}
