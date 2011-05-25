package com.create.web.rpc;

import java.util.List;

import javax.annotation.Resource;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import com.create.exception.ApplicationException;
import com.create.exception.ParamException;
import com.create.model.Menu;
import com.create.service.IMenu;
import com.create.util.Strings;

/**
 * 菜单管理RPC
 *
 * @author perzer
 * @date Mar 8, 2011
 */
@RemoteProxy
public class MenuWeb {

	private IMenu menuService;

	@Resource(name = "menuService")
	public void setMenuService(IMenu menuService) {
		this.menuService = menuService;
	}

	/**
	 * 得到当前角色下的所有菜单
	 * @param roleId
	 * @return
	 * @throws ApplicationException
	 */
	@RemoteMethod
	public List queryMenusByRoleId(String roleId) throws ApplicationException {
		if (Strings.isDirtyOrBlank(roleId)) {
			throw new ParamException("参数错误");
		}
		return menuService.queryMenusByRoleId(roleId);
	}

	/**
	 * 添加菜单
	 * @param menu
	 * @param pId
	 * @param roleId
	 * @throws ApplicationException
	 */
	@RemoteMethod
	public String addMenu(Menu menu, String pId, String roleId) throws ApplicationException {
		if (menu == null || Strings.isDirtyOrBlank(menu.getName(), menu.getModuId(), pId)) {
			throw new ParamException("参数错误");
		}
		menuService.addMenu(menu, pId, roleId);
		return menu.getId();
	}

	/**
	 * 根据ID查询菜单信息
	 * @param id
	 * @throws ApplicationException
	 */
	@RemoteMethod
	public Object queryById(String id) throws ApplicationException {
		if (Strings.isDirtyOrBlank(id)) {
			throw new ParamException("参数错误");
		}
		return menuService.queryById(id);
	}

	/**
	 * 删除菜单及子菜单
	 * @param id
	 * @throws ApplicationException
	 */
	@RemoteMethod
	public void removeMenuWithChildsById(String id, String curRoleId) throws ApplicationException {
		if (Strings.isDirtyOrBlank(id)) {
			throw new ParamException("参数错误");
		}
		menuService.removeMenuWithChildsById(id, curRoleId);
	}

	/**
	 * 更新菜单
	 * @param menu
	 * @throws ApplicationException
	 */
	@RemoteMethod
	public void update(Menu menu) throws ApplicationException {
		menuService.update(menu);
	}

	/**
	 * 单步调位
	 * @param roleId
	 * @param menuId
	 * @param up
	 * @throws ApplicationException
	 */
	@RemoteMethod
	public void stepMoveMenu(String roleId, String menuId, boolean up) throws ApplicationException {
		if (Strings.isDirtyOrBlank(menuId)) {
			throw new ParamException("参数错误");
		}
		menuService.stepMoveMenu(roleId, menuId, up);
	}

	/**
	 * 根据父级菜单ID加载子菜单
	 * @param pId
	 * @return
	 * @throws ApplicationException
	 */
	@RemoteMethod
	public List queryMenusByPId(String pId) throws ApplicationException {
		if (Strings.isDirtyOrBlank(pId)) {
			throw new ParamException("参数错误");
		}
		return menuService.queryMenusByPId(pId);
	}
}
