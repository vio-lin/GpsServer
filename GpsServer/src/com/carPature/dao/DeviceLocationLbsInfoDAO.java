package com.carPature.dao;

import com.carPature.entity1.DeviceLocationLbsInfo;

public interface DeviceLocationLbsInfoDAO {
	//插入多基站定位到数据库
	public void insertLbsInfo(DeviceLocationLbsInfo condition);
	//获取指定Id的数据库信息
	public DeviceLocationLbsInfo getSpacificLocation(int id);
}