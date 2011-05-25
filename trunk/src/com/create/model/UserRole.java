package com.create.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 用户角色
 *
 * @author cxb
 * @date Mar 3, 2011
 *
 */
@Entity
@Table(name="BAS_USERROLE")
public class UserRole implements Serializable {
	/**
	 * 主键
	 */
	private String id;
	/**
	 * 用户关联ID
	 */
	private String userId;
	/**
	 * 角色关联ID
	 */
	private String roleId;
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Column(name = "USER_ID")
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	@Column(name = "ROLE_ID")
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	
	public String toString(){
		return "[id=" + this.id + 
				"][userId=" + this.userId + 
				"][roleId=" + this.roleId + "]";
	}

}
