package com.carPature.daoimpl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.carPature.dao.DeviceLocationLbsInfoDAO;
import com.carPature.entity1.DeviceLocationLbsInfo;

public class DeviceLocationLbsInfoDAOImpl implements DeviceLocationLbsInfoDAO{
	SessionFactory sessionfactoty;
	
	public void setSessionfactoty(SessionFactory sessionfactoty) {
		this.sessionfactoty = sessionfactoty;
	}

	@Override
	public void insertLbsInfo(DeviceLocationLbsInfo condition) {
		// TODO Auto-generated method stub
		Session session = sessionfactoty.getCurrentSession();
		session.save(condition);
	}

}
