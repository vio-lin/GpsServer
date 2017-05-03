package com.carPature.daoimpl;

import java.lang.ProcessBuilder.Redirect;
import java.util.List;

import org.hibernate.CacheMode;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Restrictions;
import org.hibernate.hql.internal.ast.HqlSqlWalker;
import org.hibernate.hql.internal.ast.tree.FromClause;
import org.hibernate.hql.internal.ast.tree.RestrictableStatement;
import org.hibernate.sql.JoinType;
import org.hibernate.transform.ResultTransformer;

import com.carPature.dao.UsersDAO;
import com.carPature.entity1.Users;

import antlr.collections.AST;

public class UsersDAOImp implements UsersDAO{
	SessionFactory  sessionfactory;
	
	public void setSessionfactory(SessionFactory sessionfactory) {
		this.sessionfactory = sessionfactory;
	}

	@Override
	public List search(Users condition) {
		// TODO Auto-generated method stub
		Session session = sessionfactory.getCurrentSession();
		Criteria c = session.createCriteria(Users.class);
		Example example = Example.create(condition);
		c.add(example);
		return c.list();
	}

	@Override
	public void addUser(Users condition) {
		// TODO Auto-generated method stub
		Session session = sessionfactory.getCurrentSession();
		session.save(condition);
	}

	@Override
	public void updateUser(Users dondition) {
		// TODO Auto-generated method stub
		Session session = sessionfactory.getCurrentSession();
		session.update(sessionfactory);
	}

	@Override
	public Users findUserBy(Users condition) {
		// TODO Auto-generated method stub
		Session session = sessionfactory.getCurrentSession();
		Criteria c = session.createCriteria(Users.class);
		if(condition.getId()!=null){
			c.add(Restrictions.eq("id",condition.getId()));
			return (Users) c.list().get(0);
		}
		Example example = Example.create(condition);
		c.add(example);
		return (Users) c.list().get(0);
	}

	@Override
	public void delUser(Users user) {
		// TODO Auto-generated method stub
		Session session = sessionfactory.getCurrentSession();
		Users users = findUserBy(user);
		session.delete(users);
	}
	
}
