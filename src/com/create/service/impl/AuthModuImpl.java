package com.create.service.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.create.exception.ApplicationException;
import com.create.model.AuthModu;
import com.create.service.IAuthModu;
import com.create.service.IBase;

/**
 * 权限模块管理实现
 *
 * @author cxb
 * @date Mar 11, 2011
 *
 */
@Component(value = "authModuService")
public class AuthModuImpl extends IBase implements IAuthModu {

	/*
	 * (non-Javadoc)
	 * @see com.create.service.AuthModuService#add(com.create.model.AuthModu)
	 */
	public boolean add(AuthModu authModu) throws ApplicationException {
		this.baseDao.addObject(authModu);
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see com.create.service.AuthModuService#delById(java.lang.String)
	 */
	public boolean delById(String moduId) throws ApplicationException {
		this.baseDaoJdbc.delObjectById("BAS_AUTHMODU", "MODU_ID", moduId);
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see com.create.service.AuthModuService#queryAuthModuMap(java.lang.String)
	 */
	public List queryAuthModuMap(String moduId) throws ApplicationException {
		List list = null;
		String sql = "SELECT A.*, AM.MODU_ID FROM BAS_AUTH A, BAS_AUTHMODU AM WHERE A.ID = AM.AUTH_ID AND AM.MODU_ID = ?";
		Object[] values = {moduId};
		list = this.baseDaoJdbc.queryObjectList(sql, values);
		return list;
	}

}
