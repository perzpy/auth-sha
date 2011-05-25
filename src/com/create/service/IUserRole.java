package com.create.service;

import java.util.List;

import com.create.exception.ApplicationException;
import com.create.model.UserRole;

/**
 * 用户角色管理接口
 *
 * @author cxb
 * @date Mar 9, 2011
 *
 */
public interface IUserRole {
	
	/**
	 * 增加一个用户角色对象
	 * @param userRole 用户对象
	 * @return
	 * @throws ApplicationException
	 */
	public boolean add(UserRole userRole) throws ApplicationException;
	
	/**
	 * 删除一个用户对应的所有用户角色对象
	 * @param userId 用户主键
	 * @return
	 * @throws ApplicationException
	 */
	public boolean delById(String userId) throws ApplicationException;
	
	/**
	 * 根据ID查询用户角色对象
	 * 
	 * @param id 主键
	 * @return
	 * @throws ApplicationException
	 */
	public UserRole queryById(String id) throws ApplicationException ;
	
	/**
	 * 根据用户ID查询用户角色列表
	 * 
	 * @param userId 用户ID
	 * @return
	 * @throws ApplicationException
	 */
	public List<UserRole> queryListByUserId(String userId) throws ApplicationException ;
	
	/**
	 * 根据用户ID查询用户角色对象及角色对象列表
	 * 
	 * @param userId 用户ID
	 * @return
	 * @throws ApplicationException
	 */
	public List queryUserRoleMap(String userId) throws ApplicationException ;
	
	/**
	 * 根据角色ID查询用户角色对象
	 * 
	 * @param roleId 角色ID
	 * @return
	 * @throws ApplicationException
	 */
	public UserRole queryByRoleId(String roleId) throws ApplicationException ;
	
}
