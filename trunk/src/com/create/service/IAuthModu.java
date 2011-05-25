package com.create.service;

import java.util.List;

import com.create.exception.ApplicationException;
import com.create.model.AuthModu;

/**
 * 权限模块管理接口
 *
 * @author cxb
 * @date Mar 9, 2011
 *
 */
public interface IAuthModu {
	
	/**
	 * 增加一个权限模块对象
	 * 
	 * @param authModu 权限模块
	 * @return
	 * @throws ApplicationException
	 */
	public boolean add(AuthModu authModu) throws ApplicationException;
	
	/**
	 * 删除一个模块对应的所有权限模块对象
	 * 
	 * @param moduId 模块主键
	 * @return
	 * @throws ApplicationException
	 */
	public boolean delById(String moduId) throws ApplicationException;
	
	/**
	 * 根据模块主键查询权限对象及权限模块列表
	 * 
	 * @param moduId 模块主键
	 * @return
	 * @throws ApplicationException
	 */
	public List queryAuthModuMap(String moduId) throws ApplicationException ;
	
}
