package com.create.web.rpc;

import java.util.List;

import javax.annotation.Resource;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import com.create.exception.ApplicationException;
import com.create.exception.ParamException;
import com.create.model.Auth;
import com.create.service.IAuth;
import com.create.util.Strings;

/**
 * 权限管理RPC
 *
 * @author perzer
 * @date Mar 4, 2011
 */
@RemoteProxy
public class AuthWeb {

	private IAuth authService;

	@Resource(name = "authService")
	public void setAuthService(IAuth authService) {
		this.authService = authService;
	}

	/**
	 * 增加权限
	 * @param role
	 * @return
	 * @throws ApplicationException
	 */
	@RemoteMethod
	public boolean add(Auth auth) throws ApplicationException {
		return authService.add(auth);
	}

	/**
	 * 查询所有权限列表
	 * @return
	 * @throws ApplicationException
	 */
	@RemoteMethod
	public List queryAll() throws ApplicationException {
		return authService.queryAll();
	}

	/**
	 * 更新角色权限
	 * @param roleId
	 * @param authIds
	 * @throws ApplicationException
	 */
	@RemoteMethod
	public void updateRoleAuth(String roleId, String[] authIds) throws ApplicationException {
		if (Strings.isDirtyOrBlank(roleId) || Strings.isDirtyOrBlank(authIds)) {
			throw new ParamException("参数错误");
		}
		authService.updateRoleAuth(roleId, authIds);
	}

	/**
	 * 查询角色相关权限
	 * @param roleId
	 * @return
	 * @throws ApplicationException
	 */
	@RemoteMethod
	public List queryAuthsByRoleId(String roleId) throws ApplicationException {
		if (Strings.isDirtyOrBlank(roleId)) {
			throw new ParamException("参数错误");
		}
		return authService.queryAuthsByRoleId(roleId);
	}
	
	/**
	 * 查询模块相关权限
	 * @param moduId
	 * @return
	 * @throws ApplicationException
	 */
	@RemoteMethod
	public List queryAuthsByModuId(String moduId) throws ApplicationException {
		if (Strings.isDirtyOrBlank(moduId)) {
			throw new ParamException("参数错误");
		}
		return authService.queryAuthsByModuId(moduId);
	}
	
	/**
	 * 添加角色权限信息
	 * @param roleId
	 * @param authId
	 * @throws ApplicationException
	 */
	@RemoteMethod
	public void addRoleAuth(String roleId, String authId) throws ApplicationException {
		if (Strings.isDirtyOrBlank(roleId, authId)) {
			throw new ParamException("参数错误");
		}
		authService.addRoleAuth(roleId, authId);
	}
	
	/**
	 * 删除一条角色权限
	 * 
	 * @param roleId 角色ID
	 * @param authId 权限ID
	 * @throws ApplicationException 应用异常
	 */
	@RemoteMethod
	public void removeRoleAuth(String roleId, String authId) throws ApplicationException {
		if (Strings.isDirtyOrBlank(roleId, authId)) {
			throw new ParamException("参数错误");
		}
		authService.removeRoleAuth(roleId, authId);
	}
}
