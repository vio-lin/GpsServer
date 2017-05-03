package com.carPature.dao;

import com.carPature.entity1.DeviceStatus;

public interface DeviceStatusDAO {
	//插入一条设备信息
	public void insertDevice(DeviceStatus condition);
	//更新一条设备信息
	public void updateDevice(DeviceStatus condition);
	//查询一条设备的IMEI码
	public DeviceStatus searchDecice(String IMEI);
}
