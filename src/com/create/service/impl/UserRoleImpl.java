package com.create.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.create.exception.ApplicationException;
import com.create.model.UserRole;
import com.create.service.IBase;
import com.create.service.IUserRole;

/**
 * 用户角色管理实现类
 *
 * @author cxb
 * @date Mar 9, 2011
 *
 */
@Component(value = "userRoleService")
public class UserRoleImpl extends IBase implements IUserRole {

	/*
	 * (non-Javadoc)
	 * @see com.create.service.UserRoleService#add(com.create.model.UserRole)
	 */
	public boolean add(UserRole userRole) throws ApplicationException {
		this.baseDao.addObject(userRole);
		return true;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.create.service.UserRoleService#delById(java.lang.String)
	 */
	public boolean delById(String userId) throws ApplicationException {
		this.baseDaoJdbc.delObjectById("BAS_USERROLE", "USER_ID", userId);
		return true;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.create.service.UserRoleService#queryById(java.lang.String)
	 */
	public UserRole queryById(String id) throws ApplicationException {
		UserRole userRole = null;
		Object object = this.baseDao.queryObjectById("UserRole", "id", id);
		if(object != null){
			userRole = (UserRole) object;
		}
		return userRole;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.create.service.UserRoleService#queryByUserId(java.lang.String)
	 */
	public List<UserRole> queryListByUserId(String userId) throws ApplicationException {
		List list = null;
		String sql = "from UserRole where userId = ?";
		Object[] values = {userId};
		list = this.baseDao.queryObjectList(sql, values);
		return list;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.create.service.UserRoleService#queryUserRoleMap(java.lang.String)
	 */
	public List queryUserRoleMap(String userId) throws ApplicationException {
		List list = null;
		String sql = "SELECT R.*, UR.USER_ID FROM BAS_ROLE R, BAS_USERROLE UR WHERE R.ID = UR.ROLE_ID AND UR.USER_ID = ?";
		Object[] values = {userId};
		list = this.baseDaoJdbc.queryObjectList(sql, values);
		return list;
	}

	/*
	 * (non-Javadoc)
	 * @see com.create.service.UserRoleService#queryByRoleId(java.lang.String)
	 */
	public UserRole queryByRoleId(String roleId) throws ApplicationException {
		UserRole userRole = null;
		Object object = this.baseDao.queryObjectById("UserRole", "roleId", roleId);
		if(object != null){
			userRole = (UserRole) object;
		}
		return userRole;
	}

}
