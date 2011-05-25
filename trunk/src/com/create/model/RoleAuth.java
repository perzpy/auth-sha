package com.create.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 角色权限
 *
 * @author cxb
 * @date Mar 3, 2011
 *
 */
@Entity
@Table(name="BAS_ROLEAUTH")
public class RoleAuth implements Serializable {
	/**
	 * 主键
	 */
	private String id;
	/**
	 * 角色关联ID
	 */
	private String roleId;
	/**
	 * 权限关联ID
	 */
	private String authId;
	
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
	@Column(name = "AUTH_ID")
	public String getAuthId() {
		return authId;
	}
	public void setAuthId(String authId) {
		this.authId = authId;
	}
	
	public String toString(){
		return "[id=" + this.id + 
				"][roleId=" + this.roleId + 
				"][authId=" + this.authId + "]";
	}

}
