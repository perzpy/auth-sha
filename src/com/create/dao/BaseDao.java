package com.create.dao;

import java.util.List;

import org.hibernate.Session;

import com.create.exception.DatabaseException;

/**
 * DAO基本操作接口
 *
 * @author cxb
 * @date Feb 23, 2011
 */
public interface BaseDao {
	public Session getSQLSession() throws DatabaseException;

	public Object getTemplate() throws DatabaseException;

	public void addObject(Object object) throws DatabaseException;

	public void delObject(Object object) throws DatabaseException;

	public void updateObject(Object object) throws DatabaseException;

	/**
	 * 根据条件删除对象
	 * 
	 * @param entityName 实体类名或数据库表名（实体类名对应Hibernate实现，数据库表名对应jdbc实现）
	 * @param columnName 字段名
	 * @param id 主键
	 * @throws DatabaseException
	 */
	public void delObjectById(String entityName, String columnName, Object id) throws DatabaseException;

	/**
	 * 根据条件查询LIST
	 * 若字段值比字段名的数量多，则SQL会追加多余的字段值
	 * 
	 * @param sql 查询SQL
	 * @param paramNames 字段名
	 * @param values 对应字段的值
	 * @return
	 * @throws DatabaseException
	 */
	public List queryObjectList(String sql, String[] paramNames, Object[] values) throws DatabaseException;

	/**
	 * 根据条件查询LIST
	 * 
	 * @param sql 查询SQL
	 * @param values 对应字段的值
	 * @return
	 * @throws DatabaseException
	 */
	public List queryObjectList(String sql, Object[] values) throws DatabaseException;
	
	/**
	 * 根据条件分页查询
	 * 
	 * @param sql 查询SQL
	 * @param params 查询参数
	 * @param startIndex 开始记录数
	 * @param maxResult 单行显示数
	 * @return
	 * @throws DatabaseException
	 */
	public List queryPageList(StringBuilder sql, Object[] params, int startIndex, int maxResult)
			throws DatabaseException;

	/**
	 * 查询记录数
	 * @param sql 查询SQL
	 * @param params 查询参数
	 * @return
	 * @throws DatabaseException
	 */
	public int queryListCount(StringBuilder sql, Object[] params) throws DatabaseException;

	/**
	 * 根据ID查询对象
	 * @param entityName 实体类名
	 * @param columnName 字段名
	 * @param id 主键值
	 * @return
	 * @throws DatabaseException
	 */
	public Object queryObjectById(String entityName, String columnName, Object id) throws DatabaseException;
}
