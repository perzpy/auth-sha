package com.create.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Component;

import com.create.exception.ApplicationException;
import com.create.model.User;
import com.create.service.IBase;
import com.create.service.IUser;
import com.create.util.SplitPage;

/**
 * 用户管理实现类
 *
 * @author cxb
 * @date Mar 3, 2011
 *
 */
@Component(value = "userService")
public class UserImpl extends IBase implements IUser {

	/*
	 * (non-Javadoc)
	 * @see com.create.service.UserService#add(com.create.model.User)
	 */
	public boolean add(User user) throws ApplicationException {
		this.baseDao.addObject(user);
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see com.create.service.UserService#del(java.lang.String)
	 */
	public boolean del(String id) throws ApplicationException {
		this.baseDao.delObjectById("User", "id", id);
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see com.create.service.UserService#queryById(java.lang.String)
	 */
	public User queryById(String id) throws ApplicationException {
		return (User) this.baseDao.queryObjectById("User", "id", id);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.create.service.UserService#queryByName(java.lang.String)
	 */
	public User queryByName(String name) throws ApplicationException {
		User user = new User();
		Object object = this.baseDao.queryObjectById("User", "name", name);
		if(object != null){
			user = (User) object;
		}
		return user;
	}

	/*
	 * (non-Javadoc)
	 * @see com.create.service.UserService#queryPageList(java.lang.String, int, int)
	 */
	public SplitPage queryPageList(String name,
			int startIndex, int maxResult) throws ApplicationException {
		List list = null;
		SplitPage sp = new SplitPage();
		List params = new ArrayList();
		StringBuilder sql = new StringBuilder();
		sql.append("from User");
		if(name != null && !"".equals(name)){
			sql.append(" where name = ?");
			params.add(name);
		}
		StringBuilder sqlTemp = new StringBuilder("select count(*) " + sql.toString());
		int total = this.baseDao.queryListCount(sqlTemp, params.toArray());
		this.log.info("Total = " + total);
		if(total > 0){
			sql.append(" order by name");
			list = this.baseDao.queryPageList(sql, params.toArray(), startIndex, maxResult);
			this.log.info("List Size = " + list.size());
			sp.setTotal(total);
			sp.setSubList(list);
		}
		return sp;
	}

	/*
	 * (non-Javadoc)
	 * @see com.create.service.UserService#update(com.create.model.User)
	 */
	public boolean update(User user) throws ApplicationException {
		if(user.getId() == null || "".equals(user.getId())){
			return false;
		}
		User userTemp = this.queryById(user.getId());
		try {
			BeanUtils.copyProperties(userTemp, user);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		this.baseDao.updateObject(userTemp);
		return true;
	}

}
