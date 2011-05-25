package com.create.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Component;

import com.create.dao.BaseDao;
import com.create.dao.HibernateDaoSupportExtend;
import com.create.exception.DatabaseException;
import com.create.util.Strings;

/**
 * DAO基本操作实现（Hibernate实现）
 *
 * @author perzer,cxb
 * @date Feb 23, 2011
 */
@Component(value = "baseDao")
public class BaseDaoHibernate implements BaseDao {

	private HibernateDaoSupportExtend hibernateDaoSupportExtend;

	@Resource(name = "hibernateDaoSupportExtend")
	public void setHibernateDaoSupportExtend(HibernateDaoSupportExtend hibernateDaoSupportExtend) {
		this.hibernateDaoSupportExtend = hibernateDaoSupportExtend;
	}

	public Session getSQLSession() throws DatabaseException {
		try {
			return hibernateDaoSupportExtend.getHibernateTemplate().getSessionFactory().openSession();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DatabaseException("数据库操作失败！", e);
		}
	}

	public Object getTemplate() throws DatabaseException {
		try {
			return hibernateDaoSupportExtend.getHibernateTemplate();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DatabaseException("数据库操作失败！", e);
		}
	}

	public void addObject(Object object) throws DatabaseException {
		try {
			hibernateDaoSupportExtend.getHibernateTemplate().save(object);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DatabaseException("数据库操作失败！", e);
		}
	}

	public void delObject(Object object) throws DatabaseException {
		try {
			hibernateDaoSupportExtend.getHibernateTemplate().delete(object);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DatabaseException("数据库操作失败！", e);
		}
	}

	public void updateObject(Object object) throws DatabaseException {
		try {
			hibernateDaoSupportExtend.getHibernateTemplate().update(object);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DatabaseException("数据库操作失败！", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.create.dao.BaseDao#delObjectById(java.lang.String, java.lang.String, java.lang.Object)
	 */
	public void delObjectById(String entityName, String columnName, Object id) throws DatabaseException {
		Object object = null;
		try {
			object = this.queryObjectById(entityName, columnName, id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DatabaseException("数据库操作失败！", e);
		}
		if (object != null) {
			this.delObject(object);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.create.dao.BaseDao#queryObjectById(java.lang.String, java.lang.String, java.lang.Object)
	 */
	public Object queryObjectById(String entityName, String columnName, Object id) throws DatabaseException {
		List list = null;
		String hql = "from " + Strings.upperFirst(entityName) + " where " + columnName + " = ?";
		try {
			list = hibernateDaoSupportExtend.getHibernateTemplate().find(hql, id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DatabaseException("数据库操作失败！", e);
		}
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see com.create.dao.BaseDao#queryObjectList(java.lang.String, java.lang.String[], java.lang.Object[])
	 */
	public List queryObjectList(String queryString, String[] paramNames, Object[] values) throws DatabaseException {
		try {
			return hibernateDaoSupportExtend.getHibernateTemplate().findByNamedParam(queryString, paramNames, values);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DatabaseException("数据库操作失败！", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.create.dao.BaseDao#queryObjectList(java.lang.String, java.lang.Object[])
	 */
	public List queryObjectList(String queryString, Object[] values) throws DatabaseException {
		try {
			return hibernateDaoSupportExtend.getHibernateTemplate().find(queryString, values);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DatabaseException("数据库操作失败！", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.create.dao.BaseDao#queryPageList(java.lang.StringBuilder, java.lang.Object[], int, int)
	 */
	public List queryPageList(final StringBuilder queryString, final Object[] params, final int startIndex,
			final int maxResult) throws DatabaseException {
		try {
			return hibernateDaoSupportExtend.getHibernateTemplate().executeFind(new HibernateCallback() {
				public Object doInHibernate(Session session) throws HibernateException, SQLException {
					Query query = session.createQuery(queryString.toString());
					for (int i = 0; i < params.length; i++) {
						query.setParameter(i, params[i]);
					}
					query.setFirstResult(startIndex);
					query.setMaxResults(maxResult);
					return query.list();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			throw new DatabaseException("数据库操作失败！", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.create.dao.BaseDao#queryListCount(java.lang.StringBuilder, java.lang.Object[])
	 */
	public int queryListCount(StringBuilder queryString, Object[] params) throws DatabaseException {
		List list = null;
		try {
			list = hibernateDaoSupportExtend.getHibernateTemplate().find(queryString.toString(), params);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DatabaseException("数据库操作失败！", e);
		}
		if (list != null && list.size() == 1) {
			String total = list.iterator().next().toString();
			return Integer.parseInt(total);
		}
		return 0;
	}

}
