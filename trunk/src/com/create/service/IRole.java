package com.create.service;

import java.util.List;

import com.create.exception.ApplicationException;
import com.create.model.Role;
import com.create.util.SplitPage;

/**
 * 角色管理接口
 *
 * @author perzer
 * @date Mar 4, 2011
 */
public interface IRole {
	/**
	 * 增加角色
	 * @param role
	 * @return
	 * @throws ApplicationException
	 */
	public String add(Role role) throws ApplicationException;

	/**
	 * 更新角色信息
	 * @param role
	 * @return
	 * @throws ApplicationException
	 */
	public String update(Role role) throws ApplicationException;

	/**
	 * 分页查询角色列表
	 * @param name 名称
	 * @param startIndex 开始数
	 * @param maxResult 第页数量
	 * @return
	 * @throws ApplicationException
	 */
	public SplitPage queryPageList(String name, int startIndex, int maxResult) throws ApplicationException;

	/**
	 * 删除角色
	 * @param id
	 * @return
	 * @throws ApplicationException
	 */
	public boolean del(String id) throws ApplicationException;

	/**
	 * 根据主键查询角色信息
	 * @param id
	 * @return
	 * @throws ApplicationException
	 */
	public Role queryByPK(String id) throws ApplicationException;

	/**
	 * 生成前台静态菜单
	 * @param roleId
	 * @return
	 * @throws ApplicationException
	 */
	public boolean createMenu(String roleId) throws ApplicationException;
	
	/**
	 * 查询全部的角色对象列表
	 * 
	 * @return
	 * @throws ApplicationException
	 */
	public List<Role> queryListAll() throws ApplicationException;
}
