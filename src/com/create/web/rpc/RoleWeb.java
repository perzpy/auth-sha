package com.create.web.rpc;

import java.util.List;

import javax.annotation.Resource;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import com.create.exception.ApplicationException;
import com.create.exception.ParamException;
import com.create.model.Role;
import com.create.service.IRole;
import com.create.util.SplitPage;
import com.create.util.Strings;

/**
 * 角色管理RPC
 *
 * @author perzer
 * @date Mar 4, 2011
 */
@RemoteProxy
public class RoleWeb {

	private IRole roleService;

	@Resource(name = "roleService")
	public void setRoleService(IRole roleService) {
		this.roleService = roleService;
	}

	/**
	 * 增加一个角色
	 * @param
	 * @return
	 * @throws ApplicationException
	 */
	@RemoteMethod
	public String add(Role role) throws ApplicationException {
		return roleService.add(role);
	}

	/**
	 * 删除角色
	 * @param id
	 * @return
	 * @throws ApplicationException
	 */
	@RemoteMethod
	public boolean del(String id) throws ApplicationException {
		if (Strings.isDirtyOrBlank(id)) {
			throw new ParamException("参数错误");
		}
		return roleService.del(id);
	}

	/**
	 * 分页查询角色列表
	 * @param name 名称
	 * @param startIndex 开始数
	 * @param maxResult 第页数量
	 * @return
	 * @throws ApplicationException
	 */
	@RemoteMethod
	public SplitPage queryPageList(String name, int startIndex, int maxResult) throws ApplicationException {
		return roleService.queryPageList(name, startIndex, maxResult);
	}

	/**
	 * 根据主键查询角色信息
	 * @param id
	 * @return
	 * @throws ApplicationException
	 */
	@RemoteMethod
	public Role queryByPK(String id) throws ApplicationException {
		if (Strings.isDirtyOrBlank(id)) {
			throw new ParamException("参数错误");
		}
		return roleService.queryByPK(id);
	}

	/**
	 * 更新角色信息
	 * @param role
	 * @return
	 * @throws ApplicationException
	 */
	@RemoteMethod
	public String update(Role role) throws ApplicationException {
		return roleService.update(role);
	}

	/**
	 * 生成前台静态菜单
	 * @param roleId
	 * @return
	 * @throws ApplicationException
	 */
	@RemoteMethod
	public boolean createMenu(String roleId) throws ApplicationException {
		if (Strings.isDirtyOrBlank(roleId)) {
			throw new ParamException("参数错误");
		}
		return roleService.createMenu(roleId);
	}
	
	/**
	 * 查询全部的角色对象列表
	 * 
	 * @return
	 * @throws ApplicationException
	 */
	@RemoteMethod
	public List<Role> queryListAll() throws ApplicationException {
		return roleService.queryListAll();
	}
}
