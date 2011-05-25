package com.create.service;

import java.util.List;

import com.create.exception.ApplicationException;
import com.create.model.Menu;

/**
 * 菜单管理接口
 *
 * @author perzer
 * @date Mar 8, 2011
 */
public interface IMenu {
	/**
	 * 得到当前角色下的所有菜单
	 * @param roleId
	 * @return
	 * @throws ApplicationException
	 */
	public List queryMenusByRoleId(String roleId) throws ApplicationException;

	/**
	 * 添加菜单
	 * @param menu
	 * @param pId
	 * @param roleId
	 * @throws ApplicationException
	 */
	public void addMenu(Menu menu, String pId, String roleId) throws ApplicationException;

	/**
	 * 根据ID查询菜单信息
	 * @param id
	 * @throws ApplicationException
	 */
	public Object queryById(String id) throws ApplicationException;

	/**
	 * 删除菜单及子菜单
	 * @param id
	 * @param curRoleId
	 * @throws ApplicationException
	 */
	public void removeMenuWithChildsById(String id, String curRoleId) throws ApplicationException;

	/**
	 * 更新菜单
	 * @param menu
	 * @throws ApplicationException
	 */
	public void update(Menu menu) throws ApplicationException;

	/**
	 * 单步调位
	 * @param roleId
	 * @param menuId
	 * @param up
	 * @throws ApplicationException
	 */
	public void stepMoveMenu(String roleId, String menuId, boolean up) throws ApplicationException;

	/**
	 * 根据父级菜单ID加载子菜单
	 * @param pId
	 * @return
	 * @throws ApplicationException
	 */
	public List queryMenusByPId(String pId) throws ApplicationException;
}
