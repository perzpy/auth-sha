package com.create.service;

import java.util.List;

import com.create.exception.ApplicationException;
import com.create.model.Auth;

/**
 * 权限管理接口
 *
 * @author perzer
 * @date Mar 4, 2011
 */
public interface IAuth {
	/**
	 * 增加权限
	 * @param role
	 * @return
	 * @throws ApplicationException
	 */
	public boolean add(Auth auth) throws ApplicationException;

	/**
	 * 查询所有权限列表
	 * @return
	 * @throws ApplicationException
	 */
	public List queryAll() throws ApplicationException;
	
	/**
	 * 更新角色权限
	 * @param roleId
	 * @param authIds
	 * @throws ApplicationException
	 */
	public void updateRoleAuth(String roleId, String[] authIds) throws ApplicationException;
	
	/**
	 * 添加角色权限信息
	 * @param roleId
	 * @param authId
	 * @throws ApplicationException
	 */
	public void addRoleAuth(String roleId, String authId) throws ApplicationException;
	
	/**
	 * 删除一条角色权限
	 * 
	 * @param roleId 角色ID
	 * @param authId 权限ID
	 * @throws ApplicationException 应用异常
	 */
	public void removeRoleAuth(String roleId, String authId) throws ApplicationException;
	
	/**
	 * 查询角色相关权限
	 * @param roleId
	 * @return
	 * @throws ApplicationException
	 */
	public List queryAuthsByRoleId(String roleId) throws ApplicationException;
	
	/**
	 * 查询模块相关权限
	 * @param moduId
	 * @return
	 * @throws ApplicationException
	 */
	public List queryAuthsByModuId(String moduId) throws ApplicationException;

	/**
	 * 根据用户ID查询相关权限
	 * 
	 * @param userId 用户ID
	 * @return
	 * @throws ApplicationException
	 */
	public List queryAuthsByUserId(String userId) throws ApplicationException;
}
