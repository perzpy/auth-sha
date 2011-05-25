package com.create.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 角色菜单
 *
 * @author cxb
 * @date Mar 3, 2011
 *
 */
@Entity
@Table(name="BAS_ROLEMENU")
public class RoleMenu implements Serializable {
	/**
	 * 主键
	 */
	private String id;
	/**
	 * 角色关联ID
	 */
	private String roleId;
	/**
	 * 菜单关联ID
	 */
	private String menuId;
	
	public RoleMenu(String roleId, String menuId) {
		super();
		this.roleId = roleId;
		this.menuId = menuId;
	}
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Column(name = "ROLE_ID")
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	@Column(name = "MENU_ID")
	public String getMenuId() {
		return menuId;
	}
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	
	public String toString(){
		return "[id=" + this.id + 
				"][roleId=" + this.roleId + 
				"][menuId=" + this.menuId + "]";
	}

}
