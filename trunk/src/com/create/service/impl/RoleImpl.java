package com.create.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.create.exception.ApplicationException;
import com.create.model.Role;
import com.create.service.IBase;
import com.create.service.IRole;
import com.create.util.Constants;
import com.create.util.SplitPage;
import com.create.util.Strings;

/**
 * 角色管理实现类
 *
 * @author perzer
 * @date Mar 4, 2011
 */
@Component(value = "roleService")
public class RoleImpl extends IBase implements IRole {

	/*
	 * (non-Javadoc)
	 * @see com.create.service.RoleService#add(com.create.model.Role)
	 */
	public String add(Role role) throws ApplicationException {
		baseDao.addObject(role);
		return role.getId();
	}

	/*
	 * (non-Javadoc)
	 * @see com.create.service.RoleService#queryPageList(java.lang.String, int, int)
	 */
	public SplitPage queryPageList(String name, int startIndex, int maxResult) throws ApplicationException {
		List list = null;
		SplitPage sp = new SplitPage();
		List params = new ArrayList();
		StringBuilder sql = new StringBuilder();
		sql.append("from Role");
		if (name != null && !"".equals(name)) {
			sql.append(" where name = ?");
			params.add(name);
		}
		StringBuilder sqlTemp = new StringBuilder("select count(*) ").append(sql);
		int total = this.baseDao.queryListCount(sqlTemp, params.toArray());
		if (total > 0) {
			list = this.baseDao.queryPageList(sql, params.toArray(), startIndex, maxResult);
			sp.setTotal(total);
			sp.setSubList(list);
		}
		return sp;
	}

	/*
	 * (non-Javadoc)
	 * @see com.create.service.RoleService#del(java.lang.String)
	 */
	public boolean del(String id) throws ApplicationException {
		try {
			this.baseDaoJdbc.delObjectById("BAS_ROLE", "ID", id);
		} catch (Exception ex) {
			throw new ApplicationException("请先删除跟此角色有关的信息");
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see com.create.service.RoleService#queryByPK(java.lang.String)
	 */
	public Role queryByPK(String id) throws ApplicationException {
		return (Role) baseDao.queryObjectById("Role", "id", id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.create.service.RoleService#update(com.create.model.Role)
	 */
	public String update(Role role) throws ApplicationException {
		baseDao.updateObject(role);
		return role.getId();
	}

	/*
	 * (non-Javadoc)
	 * @see com.create.service.RoleService#createMenu(java.lang.String)
	 */
	public boolean createMenu(String roleId) throws ApplicationException {
		boolean flag = false;
		String path = null;
		String webRootPath = null;
		File file = null;
		File file1 = null;
		FileOutputStream fos = null;
		OutputStreamWriter osw = null;
		PrintWriter outFile = null;
		StringBuilder sb = new StringBuilder();
		try {
			path = String.valueOf(new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath()));
			webRootPath = path.substring(0, path.indexOf(File.separator + "WEB-INF"));
			file1 = new File(webRootPath + File.separator + "template");
			if (!file1.exists()) {
				file1.mkdir();
			}
			file = new File(webRootPath + File.separator + "template" + File.separator + roleId + ".html");
			file.createNewFile();
			sb.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
			sb.append("<head>");
			sb.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />");
			sb.append("<link id=\"style\" href=\"../styles/all.css\" rel=\"stylesheet\" type=\"text/css\" />");
			sb.append("<link id=\"style\" href=\"../styles/jquery.treeview.css\" rel=\"stylesheet\" type=\"text/css\" />");
			sb.append("<script type=\"text/javascript\" src=\"../scripts/jquery-1.5.min.js\"></script>");
			sb.append("<script type=\"text/javascript\" src=\"../scripts/jquery.treeview.js\"></script>");
			sb.append("</head>");
			sb.append("<script language=\"javascript\">");
			sb.append("jQuery(function() {");
			sb.append("$('#browser').treeview({");
			sb.append("collapsed : true,");
			sb.append("unique : true");
			sb.append("});");
			sb.append("});");
			sb.append("</script>");
			sb.append("<body>");
			sb.append("<table width='100%' height='100%' border='0' cellpadding='0' cellspacing='0' style='table-layout: fixed;'><tr>");
			sb.append("<td style='width: 4px; background-image: url(../images/main_16.gif)'>&nbsp;</td>");
			sb.append("<td valign='top'>");
			sb.append("<table width='100%' border='0' cellspacing='0' cellpadding='0'>");
			sb.append("<tr>");
			sb.append("<td style='background: url(../images/main_07.gif) repeat; width: 310px; height: 25px;'>&nbsp;身份</td>");
			sb.append("</tr>");
			sb.append("<tr>");
			sb.append("<td>");
			sb.append("<ul id='browser' class='filetree'>");
			//解析树形菜单
			sb.append(parseNode(roleId));
			sb.append("</ul></td></tr></table></td></tr></table>");
			sb.append("</body>");
			sb.append("</html>");
			fos = new FileOutputStream(file);
			osw = new OutputStreamWriter(fos, "utf-8");//这里可以生成编码是utf-8的html的文件
			outFile = new PrintWriter(osw);
			outFile.write(sb.toString());
			flag = true;
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		} finally {
			try {
				osw.close();
				fos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return flag;
	}

	/*
	 * (non-Javadoc)
	 * @see com.create.service.RoleService#queryListAll()
	 */
	public List<Role> queryListAll() throws ApplicationException {
		return this.baseDao.queryObjectList("from Role", new Object[0]);
	}

	/**
	 * 根据角色ID、层级编码、模块ID查询菜单信息
	 * @param roleId
	 * @param levelCode
	 * @param moduId
	 * @return
	 * @throws ApplicationException
	 */
	private List queryModuByCondition(String roleId, String levelCode, String moduId) throws ApplicationException {
		List params = new ArrayList();
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT M.ID, M.NAME, M.LEVELCODE, M.SEQ, M.DESCRIPTION, M.MODU_ID,MD.URL "
				+ "FROM BAS_MENU M LEFT JOIN BAS_MODU MD ON M.MODU_ID=MD.ID WHERE M.ID "
				+ "IN(SELECT R.MENU_ID FROM BAS_ROLEMENU R WHERE R.ROLE_ID=?) ");
		params.add(roleId);

		if (!Strings.isBlank(levelCode)) {
			sql.append(" AND M.LEVELCODE LIKE ");
			sql.append(" '" + levelCode + Strings.getRepeatStrs("_", Constants.LEVELCODE_LEN) + "' ");
		}

		if (!Strings.isBlank(moduId)) {
			if ("ROOT".equals(moduId)) {
				sql.append(" AND LENGTH(M.LEVELCODE)=10 ");
			} else {
				sql.append(" AND M.MODU_ID = ?");
				params.add(moduId);
			}
		}
		sql.append(" ORDER BY LENGTH(M.LEVELCODE), M.SEQ DESC, M.LEVELCODE ");
		return baseDaoJdbc.queryObjectList(sql.toString(), params.toArray());
	}

	/**
	 * 解析根菜单
	 * @param roleId
	 * @return
	 * @throws ApplicationException
	 */
	private String parseNode(String roleId) throws ApplicationException {
		List list = queryModuByCondition(roleId, null, "ROOT");
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			Map map = (Map) list.get(i);
			sb.append("<li><span class='folder'>" + map.get("NAME") + "</span>");
			List childList = queryModuByCondition(roleId, map.get("LEVELCODE").toString(), "");
			sb.append(parseChildNode(roleId, childList));
			sb.append("</li>");
		}
		return sb.toString();
	}

	/**
	 * 解析子菜单
	 * @param roleId
	 * @param childList
	 * @return
	 */
	private String parseChildNode(String roleId, List childList) throws ApplicationException {
		StringBuilder sb = new StringBuilder();
		if (childList != null && childList.size() > 0) {
			sb.append("<ul>");
			for (int i = 0; i < childList.size(); i++) {
				Map map = (Map) childList.get(i);
				List tempList = queryModuByCondition(roleId, map.get("LEVELCODE").toString(), "");
				if (tempList != null && tempList.size() > 0) {
					sb.append("<li><span class='folder'>" + map.get("NAME") + "</span>");
					sb.append(parseChildNode(roleId, tempList));
				} else {
					sb.append("<li><span class='file'><a target='mainFrame' href='../" + map.get("URL") + "'>"
							+ map.get("NAME") + "</a></span></li>");
				}
			}
			sb.append("</ul>");
		}
		return sb.toString();
	}

}
