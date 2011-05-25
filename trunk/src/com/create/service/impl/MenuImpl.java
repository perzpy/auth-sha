package com.create.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.create.exception.ApplicationException;
import com.create.exception.NotExsistException;
import com.create.exception.ParamException;
import com.create.model.Menu;
import com.create.model.RoleMenu;
import com.create.service.IBase;
import com.create.service.IMenu;
import com.create.util.Constants;
import com.create.util.LevelCodes;
import com.create.util.Strings;

/**
 * 菜单管理实现类
 *
 * @author perzer
 * @date Mar 8, 2011
 */
@Component(value = "menuService")
public class MenuImpl extends IBase implements IMenu {

	/*
	 * (non-Javadoc)
	 * @see com.create.service.MenuService#queryMenusByRoleId(java.lang.String)
	 */
	public List queryMenusByRoleId(String roleId) throws ApplicationException {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT M.ID,m.NAME,M.LEVELCODE,M.SEQ,M.DESCRIPTION,M.MODU_ID,MD.NAME MODU_NAME,MD.URL ");
		sql.append(" FROM BAS_MENU M LEFT JOIN BAS_MODU MD ON M.MODU_ID = md.ID ");
		sql.append(" WHERE M.ID IN (SELECT R.MENU_ID FROM BAS_ROLEMENU R WHERE R.ROLE_ID = ?) ");
		sql.append(" ORDER BY LENGTH(M.LEVELCODE), M.SEQ DESC, M.LEVELCODE");
		return baseDaoJdbc.queryObjectList(sql.toString(), new String[] { roleId });
	}

	/*
	 * (non-Javadoc)
	 * @see com.create.service.MenuService#addMenu(com.create.model.Menu, java.lang.String, java.lang.String)
	 */
	public void addMenu(Menu menu, String pId, String roleId) throws ApplicationException {
		List list;
		Menu parentMenu;// 父级域
		String pLevelCode = "";

		if (!pId.equals("0")) {
			// 检验父级菜单是否存在
			if ((parentMenu = getMenu(pId)) == null) {
				throw new NotExsistException("父级菜单不存在");
			}
			pLevelCode = parentMenu.getLevelCode();
		}

		list = baseDaoJdbc.queryObjectList(
				"SELECT MAX(M.LEVELCODE) LEVELCODE FROM  BAS_MENU M WHERE M.LEVELCODE LIKE ? ",
				new String[] { pLevelCode + Strings.getRepeatStrs("_", Constants.LEVELCODE_LEN) });
		if (Strings.isBlank((String) ((Map) list.get(0)).get("LEVELCODE"))) {
			menu.setLevelCode(LevelCodes.getFirstChildLevelCode(pLevelCode));
		} else {
			menu.setLevelCode(LevelCodes.getLagerLevelCode((String) ((Map) list.get(0)).get("LEVELCODE")));
		}

		baseDao.addObject(menu);// 添加Menu
		baseDao.addObject(new RoleMenu(roleId, menu.getId()));// 添加RoleMenu
	}

	/*
	 * (non-Javadoc)
	 * @see com.create.service.MenuService#queryById(java.lang.String)
	 */
	public Object queryById(String id) throws ApplicationException {
		List list = baseDaoJdbc.queryObjectList(
				"SELECT T1.*,T2.NAME MODU_NAME FROM BAS_MENU T1,BAS_MODU T2 WHERE T1.MODU_ID=T2.ID AND T1.ID=?",
				new String[] { id });
		return list != null && list.size() > 0 ? list.get(0) : null;
	}

	/*
	 * (non-Javadoc)
	 * @see com.create.service.MenuService#removeMenuWithChildsById(java.lang.String)
	 */
	public void removeMenuWithChildsById(String id, String curRoleId) throws ApplicationException {
		if (!ifExsistById(id)) {
			throw new NotExsistException("指定菜单不存在");
		}
		JdbcTemplate jdbcTemplate = ((JdbcTemplate) baseDaoJdbc.getTemplate());
		//先删除BAS_ROLEMENU表中的关联
		String sql = "DELETE FROM BAS_ROLEMENU T1 WHERE T1.ROLE_ID = ? AND EXISTS "
				+ "(SELECT T2.ID FROM BAS_MENU T2 WHERE T1.MENU_ID = T2.ID "
				+ "AND T2.LEVELCODE like (SELECT LEVELCODE FROM BAS_MENU WHERE ID='" + id + "' )||'%')";
		log.info(sql);
		jdbcTemplate.update(sql, new Object[] { curRoleId });

		//删除菜单
		sql = "DELETE FROM BAS_MENU WHERE LEVELCODE like (SELECT LEVELCODE FROM BAS_MENU WHERE ID='" + id + "' )||'%'";
		log.info(sql);
		jdbcTemplate.update(sql);
	}

	/*
	 * (non-Javadoc)
	 * @see com.create.service.MenuService#update(com.create.model.Menu)
	 */
	public void update(Menu menu) throws ApplicationException {
		baseDao.updateObject(menu);
	}

	/*
	 * (non-Javadoc)
	 * @see com.create.service.MenuService#stepMoveMenu(java.lang.String, java.lang.String, boolean)
	 */
	public void stepMoveMenu(String roleId, String menuId, boolean up) throws ApplicationException {
		Menu curMenu = this.getMenu(menuId);
		List menus = this.queryMenusInSameLevel(roleId, curMenu.getLevelCode(), true);
		if (menus == null || menus.size() == 0) {
			return;
		}

		int index = -1;

		// 1.找位
		for (int i = 0; i < menus.size(); i++) {
			if (((Menu) menus.get(i)).getId().equals(menuId)) {
				index = i;
				break;
			}
		}

		if (index == -1) {
			return;
		}

		// 2.交换
		Menu tmpMenu = null;
		if (up) {
			if (index - 1 >= 0) {
				tmpMenu = (Menu) menus.get(index - 1);
				menus.set(index - 1, curMenu);
				menus.set(index, tmpMenu);
			}
		} else {
			if (index + 1 <= menus.size() - 1) {
				tmpMenu = (Menu) menus.get(index + 1);
				menus.set(index + 1, curMenu);
				menus.set(index, tmpMenu);
			}
		}

		// 3.更新
		if (tmpMenu != null) {
			for (int i = 0; i < menus.size(); i++) {
				Menu menu = (Menu) menus.get(i);
				menu.setSeq(menus.size() - i);
				baseDao.updateObject(menu);
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * @see com.create.service.MenuService#queryMenusByPId(java.lang.String)
	 */
	public List queryMenusByPId(String pId) throws ApplicationException {
		return baseDaoJdbc.queryObjectList(
				"SELECT * FROM BAS_MENU M WHERE M.LEVELCODE LIKE (SELECT T2.LEVELCODE FROM BAS_MENU T2 WHERE T2.ID = '"
						+ pId + "') || '_%' ORDER BY LENGTH(m.LEVELCODE),m.SEQ DESC,m.LEVELCODE", null);
	}

	/**
	 * 得到菜单
	 * @param id
	 * @return
	 * @throws ApplicationException
	 */
	private Menu getMenu(String id) throws ApplicationException {
		return (Menu) baseDao.queryObjectById("Menu", "id", id);
	}

	/**
	 * 菜单是否存在
	 * @param id
	 * @return
	 * @throws ApplicationException
	 */
	private boolean ifExsistById(String id) throws ApplicationException {
		if (Strings.isDirtyOrBlank(id)) {
			throw new ParamException("参数错误");
		}

		List list = baseDaoJdbc.queryObjectList("SELECT 1 FROM BAS_MENU WHERE ID= ?", new String[] { id });
		return list != null && list.size() > 0;
	}

	/**
	 * queryMenusInSameLevel
	 * 
	 * @param levelCode
	 * @return
	 * @throws ApplicationException
	 */
	private List queryMenusInSameLevel(String roleId, String levelCode, boolean ifIn) throws ApplicationException {
		return baseDao.queryObjectList(
				"select m from Menu m,RoleMenu rm where rm.menuId=m.id  and rm.roleId='" + roleId
						+ "' and m.levelCode like '"
						+ levelCode.substring(0, levelCode.length() - Constants.LEVELCODE_LEN)
						+ Strings.getRepeatStrs("_", Constants.LEVELCODE_LEN) + "' "
						+ ((!ifIn) ? " and  m.levelCode!='" + levelCode + "'" : "")
						+ " order by seq desc,levelCode asc", null);
	}
}
