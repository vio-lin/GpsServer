package com.carPature.daoimpl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.carPature.dao.DeviceLocationInfoDAO;
import com.carPature.dao.DeviceStatusDAO;
import com.carPature.entity1.DeviceLocationInfo;

public class DeviceLocationInfoDAOImpl implements DeviceLocationInfoDAO{
	SessionFactory sessionfactory;
	
	public SessionFactory getSessionfactory() {
		return sessionfactory;
	}

	public void setSessionfactory(SessionFactory sessionfactory) {
		this.sessionfactory = sessionfactory;
	}


	@Override
	public void insertLocation(DeviceLocationInfo locationinfo) {
		// TODO Auto-generated method stub
		Session session = sessionfactory.getCurrentSession();
		session.save(locationinfo);
	}
	
}
