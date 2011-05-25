package com.create.web.rpc;

import java.util.List;

import javax.annotation.Resource;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import com.create.exception.ApplicationException;
import com.create.model.User;
import com.create.model.UserRole;
import com.create.security.Authenticate;
import com.create.service.IUser;
import com.create.service.IUserRole;
import com.create.util.SplitPage;

/**
 * 用户管理RPC
 *
 * @author cxb
 * @date Mar 3, 2011
 *
 */
@RemoteProxy
public class UserWeb {
	
	private IUser userService;
	private IUserRole userRoleService;
	
	@Resource(name = "userService")
	public void setUserService(IUser userService) {
		this.userService = userService;
	}
	
	@Resource(name = "userRoleService")
	public void setUserRoleService(IUserRole userRoleService) {
		this.userRoleService = userRoleService;
	}

	/**
	 * 增加一个用户
	 * @param user 用户对象
	 * @return
	 * @throws ApplicationException
	 */
	@RemoteMethod
	public boolean add(User user, String roleIds) throws ApplicationException {
		userService.add(user);
		if(roleIds != null && !"".equals(roleIds)){
			String[] roleId = roleIds.split(",");
			for(int i = 0; i < roleId.length; i ++){
				UserRole userRole = new UserRole();
				userRole.setUserId(user.getId());
				userRole.setRoleId(roleId[i]);
				userRoleService.add(userRole);
			}
		}
		return true;
	}
	
	/**
	 * 根据ID删除一个用户
	 * @param id 主键
	 * @return
	 * @throws ApplicationException
	 */
	@RemoteMethod
	public boolean del(String id) throws ApplicationException {
		userRoleService.delById(id);
		userService.del(id);
		return true;
	}
	
	/**
	 * 修改用户属性
	 * @param user 用户对象
	 * @return
	 * @throws ApplicationException
	 */
	@RemoteMethod
	public boolean update(User user, String roleIds) throws ApplicationException {
		userRoleService.delById(user.getId());
		userService.update(user);
		if(roleIds != null && !"".equals(roleIds)){
			String[] roleId = roleIds.split(",");
			for(int i = 0; i < roleId.length; i ++){
				UserRole userRole = new UserRole();
				userRole.setUserId(user.getId());
				userRole.setRoleId(roleId[i]);
				System.out.println(user.getId() + "," + roleId[i]);
				userRoleService.add(userRole);
			}
		}
		return true;
	}
	
	/**
	 * 根据ID查找用户
	 * @param id 主键
	 * @return
	 * @throws ApplicationException
	 */
	@RemoteMethod
	public User queryById(String id) throws ApplicationException {
		return userService.queryById(id);
	}
	
	/**
	 * 根据NAME查找用户
	 * @param name 用户名称
	 * @return
	 * @throws ApplicationException
	 */
	@RemoteMethod
	public User queryByName(String name) throws ApplicationException {
		return userService.queryByName(name);
	}
	
	/**
	 * 分页查询用户列表
	 * @param name 名称
	 * @param startIndex 开始数
	 * @param maxResult 第页数量
	 * @return
	 * @throws ApplicationException
	 */
	@RemoteMethod
	@Authenticate("USER_LIST")
	public SplitPage queryPageList(String name, int startIndex, int maxResult) throws ApplicationException {
		return userService.queryPageList(name, startIndex, maxResult);
	}
	
	/**
	 * 根据用户ID查询全部的用户角色对象列表
	 * 
	 * @param userId 用户ID
	 * @return
	 * @throws ApplicationException
	 */
	@RemoteMethod
	public List queryUserRoleListByUserId(String userId) throws ApplicationException {
		List list = null;
		list = userRoleService.queryUserRoleMap(userId);
		return list;
	}

}
