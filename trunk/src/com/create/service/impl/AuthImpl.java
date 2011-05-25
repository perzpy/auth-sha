package com.create.service.impl;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.create.exception.ApplicationException;
import com.create.model.Auth;
import com.create.model.RoleAuth;
import com.create.service.IAuth;
import com.create.service.IBase;

/**
 * 权限管理实现类
 *
 * @author perzer
 * @date Mar 4, 2011
 */
@Component(value = "authService")
public class AuthImpl extends IBase implements IAuth {

	/*
	 * (non-Javadoc)
	 * @see com.create.service.AuthService#add(com.create.model.Auth)
	 */
	public boolean add(Auth auth) throws ApplicationException {
		baseDao.addObject(auth);
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see com.create.service.AuthService#queryAll()
	 */
	public List queryAll() throws ApplicationException {
		return baseDaoJdbc.queryObjectList("SELECT T.ID,T.NAME,T.DESCRIPTION FROM BAS_AUTH T", null);
	}

	/*
	 * (non-Javadoc)
	 * @see com.create.service.AuthService#addRoleAuth(java.lang.String, java.lang.String)
	 */
	public void addRoleAuth(String roleId, String authId) throws ApplicationException {
		RoleAuth obj = new RoleAuth();
		obj.setAuthId(authId);
		obj.setRoleId(roleId);

		baseDao.addObject(obj);
	}

	/*
	 * (non-Javadoc)
	 * @see com.create.service.IAuth#removeRoleAuth(java.lang.String, java.lang.String)
	 */
	public void removeRoleAuth(String roleId, String authId) throws ApplicationException {
		((JdbcTemplate) baseDaoJdbc.getTemplate()).update(
				"DELETE FROM BAS_ROLEAUTH R WHERE R.ROLE_ID=? AND R.AUTH_ID=?", new String[] { roleId, authId });
	}

	/*
	 * (non-Javadoc)
	 * @see com.create.service.AuthService#updateRoleAuth(java.lang.String, java.lang.String[])
	 */
	public void updateRoleAuth(String roleId, String[] authIds) throws ApplicationException {
		((JdbcTemplate) baseDaoJdbc.getTemplate())
				.execute(("DELETE FROM BAS_ROLEAUTH R WHERE R.ROLE_ID='" + roleId + "'"));
		for (String authId : authIds) {
			addRoleAuth(roleId, authId);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.create.service.AuthService#queryAuthsByRoleId(java.lang.String)
	 */
	public List queryAuthsByRoleId(String roleId) throws ApplicationException {
		return this.baseDao.queryObjectList(
				"from Auth a where a.id in(select r.authId from RoleAuth r where r.roleId=?)", new String[] { roleId });
	}

	/*
	 * (non-Javadoc)
	 * @see com.create.service.AuthService#queryAuthsByModuId(java.lang.String)
	 */
	public List queryAuthsByModuId(String moduId) throws ApplicationException {
		return baseDao.queryObjectList(
				"from Auth a where a.id in (select m.authId from AuthModu m where m.moduId= ? )",
				new String[] { moduId });
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.create.service.IAuth#queryAuthsByUserId(java.lang.String)
	 */
	public List queryAuthsByUserId(String userId) throws ApplicationException {
		List list = null;
		String hql = "FROM Auth a WHERE a.id IN (SELECT r.authId FROM RoleAuth r WHERE r.roleId  IN (SELECT u.roleId FROM UserRole u WHERE u.userId = ?))";
		list = this.baseDao.queryObjectList(hql, new String[] {userId});
		return list;
	}
}
