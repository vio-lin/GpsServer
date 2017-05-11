package com.carPature.daoimpl;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaQuery;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.hibernate.engine.spi.TypedValue;

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

	@Override
	public DeviceLocationLbsInfo getSpacificLocation(int id) {
		// TODO Auto-generated method stub
		Session session = sessionfactoty.getCurrentSession();
		Criteria c = session.createCriteria(DeviceLocationLbsInfo.class);
		c.add(Restrictions.eq("id", id));
		return (DeviceLocationLbsInfo) c.list().get(0);
	}

}
