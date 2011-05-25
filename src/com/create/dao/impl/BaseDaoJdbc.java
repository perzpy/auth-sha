package com.create.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import com.create.dao.BaseDao;
import com.create.dao.JdbcDaoSupportExtend;
import com.create.exception.DatabaseException;

/**
 * DAO基本操作实现（JDBC实现）
 *
 * @author perzer,cxb
 * @date Feb 23, 2011
 */
@Component(value = "baseDaoJdbc")
public class BaseDaoJdbc implements BaseDao {

	private Logger log = Logger.getLogger(this.getClass());

	private JdbcDaoSupportExtend jdbcDaoSupportExtend;

	@Resource(name = "jdbcDaoSupportExtend")
	public void setJdbcDaoSupportExtend(JdbcDaoSupportExtend jdbcDaoSupportExtend) {
		this.jdbcDaoSupportExtend = jdbcDaoSupportExtend;
	}

	public Session getSQLSession() throws DatabaseException {
		return null;
	}

	public Object getTemplate() throws DatabaseException {
		return jdbcDaoSupportExtend.getJdbcTemplate();
	}

	public void addObject(Object object) throws DatabaseException {
	}

	public void delObject(Object object) throws DatabaseException {
	}

	public void updateObject(Object object) throws DatabaseException {
	}

	/*
	 * (non-Javadoc)
	 * @see com.create.dao.BaseDao#delObjectById(java.lang.String, java.lang.String, java.lang.Object)
	 */
	public void delObjectById(String tableName, String columnName, Object id) throws DatabaseException {
		String sql = "DELETE " + tableName + " WHERE " + columnName + " = ?";
		log.info(sql);
		try {
			jdbcDaoSupportExtend.getJdbcTemplate().update(sql, new Object[] { id });
		} catch (Exception e) {
			e.printStackTrace();
			throw new DatabaseException("数据库操作失败", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.create.dao.BaseDao#queryObjectById(java.lang.String, java.lang.String, java.lang.Object)
	 */
	public Object queryObjectById(String tableName, String columnName, Object id) throws DatabaseException {
		List list = null;
		String sql = "SELECT * FROM " + tableName + " WHERE " + columnName + " = ?";
		log.info(sql);
		try {
			list = jdbcDaoSupportExtend.getJdbcTemplate().queryForList(sql, new Object[] { id });
		} catch (Exception e) {
			e.printStackTrace();
			throw new DatabaseException("数据库操作失败", e);
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
		String sql = queryString.toUpperCase();
		if (sql.indexOf("WHERE") == -1) {
			sql = sql + " WHERE 1 = 1";
		}
		for (int i = 0; i < paramNames.length; i++) {
			sql = sql + " AND " + paramNames[i] + " = ? ";
		}
		if (values.length > paramNames.length) {
			for (int i = paramNames.length; i < values.length; i++) {
				sql = sql + values[i - 1];
			}
		}
		log.info(sql);
		try {
			return jdbcDaoSupportExtend.getJdbcTemplate().queryForList(sql, values);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DatabaseException("数据库操作失败", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.create.dao.BaseDao#queryObjectList(java.lang.String, java.lang.Object[])
	 */
	public List queryObjectList(String queryString, Object[] values) throws DatabaseException {
		log.info(queryString);
		try {
			return jdbcDaoSupportExtend.getJdbcTemplate().queryForList(queryString, values);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DatabaseException("数据库操作失败", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.create.dao.BaseDao#queryPageList(java.lang.StringBuilder, java.lang.Object[], int, int)
	 */
	public List queryPageList(StringBuilder queryString, Object[] params, int startIndex, int maxResult)
			throws DatabaseException {
		queryString.insert(0, "SELECT * FROM ( SELECT ROW_.*, ROWNUM ROWNUM_ FROM ( ");
		queryString.append(" ) ROW_ ) WHERE ROWNUM_ <= ? AND ROWNUM_ > ?");
		Object[] newParams = new Object[params.length + 2];
		System.arraycopy(params, 0, newParams, 0, params.length);
		newParams[newParams.length - 2] = startIndex + maxResult;
		newParams[newParams.length - 1] = startIndex;
		log.info(queryString.toString());
		try {
			return jdbcDaoSupportExtend.getJdbcTemplate().queryForList(queryString.toString(), newParams);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DatabaseException("数据库操作失败", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.create.dao.BaseDao#queryListCount(java.lang.StringBuilder, java.lang.Object[])
	 */
	public int queryListCount(StringBuilder queryString, Object[] params) throws DatabaseException {
		log.info(queryString.toString());
		try {
			return jdbcDaoSupportExtend.getJdbcTemplate().queryForInt(queryString.toString(), params);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DatabaseException("数据库操作失败", e);
		}
	}

}
