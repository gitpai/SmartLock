package com.global.daoimpl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.global.dao.CycLockDAO;
import com.global.model.CycLock;

public class CycLockDAOImpl implements CycLockDAO {
	private MySessionFactory sessionFactory = MySessionFactory.getInstance();
	
	@Override
	public void save(CycLock cyc) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			session.saveOrUpdate(cyc);
			tx.commit();
		} catch (RuntimeException e) {
			tx.rollback();
			throw e;
		} finally {
			session.close();
		}
	}

	@Override
	public List<CycLock> findAll() {
		Session session = sessionFactory.openSession();
		try {
			String hql = "from CycLock order by time desc";
			Query query = session.createQuery(hql);
			@SuppressWarnings("unchecked")
			List<CycLock> cycs = query.list();
			if (cycs.size() == 0) {
				return null;
			}
			return cycs;
		} catch (RuntimeException e) {
			throw e;
		} finally {
			session.close();
		}
	}

	@Override
	public CycLock findLastet() {
		Session session = sessionFactory.openSession();
		try {
			String hql = "from CycLock order by time desc";
			Query query = session.createQuery(hql);
			query.setFirstResult(0);
			query.setMaxResults(1);
			@SuppressWarnings("unchecked")
			List<CycLock> cycs = query.list();
			if (cycs.size() == 0) {
				return null;
			}
			return cycs.get(0);
		} catch (RuntimeException e) {
			throw e;
		} finally {
			session.close();
		}
	}

	@Override
	public void delete(long id) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			String hql = "delete from CycLock where id=:id";
			Query query = session.createQuery(hql);
			query.setLong("id", id);
			query.executeUpdate();
			tx.commit();
		} finally {
			session.close();
		}
	}

}
