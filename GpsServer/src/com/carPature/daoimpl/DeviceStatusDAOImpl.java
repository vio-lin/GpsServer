package com.carPature.daoimpl;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.carPature.dao.DeviceStatusDAO;
import com.carPature.entity1.DeviceStatus;

public class DeviceStatusDAOImpl implements DeviceStatusDAO{
	SessionFactory sessionfactory;

	public void setSessionfactory(SessionFactory sessionfactory) {
		this.sessionfactory = sessionfactory;
	}

	@Override
	public void insertDevice(DeviceStatus condition) {
		// TODO Auto-generated method stub
		Session session = sessionfactory.getCurrentSession();
		session.save(condition);
	}

	@Override
	public void updateDevice(DeviceStatus condition) {
		// TODO Auto-generated method stub
		Session session = sessionfactory.getCurrentSession();
		DeviceStatus device = searchDecice(condition.getImei());
		if(device==null){
			insertDevice(condition);
		}else{
			session.update(condition);			
		}
	}

	@Override
	public DeviceStatus searchDecice(String IMEI) {
		// TODO Auto-generated method stub
		Session session = sessionfactory.getCurrentSession();
		Criteria c = session.createCriteria(DeviceStatus.class);
		c.add(Restrictions.eq("imei", IMEI));
		return (DeviceStatus) c.list().get(0);
	}
	
}
