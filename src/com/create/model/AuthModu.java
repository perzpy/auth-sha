package com.create.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 权限模块
 *
 * @author cxb
 * @date Mar 3, 2011
 *
 */
@Entity
@Table(name="BAS_AUTHMODU")
public class AuthModu implements Serializable {
	
	/**
	 * 主键
	 */
	private String id;
	/**
	 * 权限关联ID
	 */
	private String authId;
	/**
	 * 模块关联ID
	 */
	private String moduId;
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@Column(name = "AUTH_ID")
	public String getAuthId() {
		return authId;
	}
	public void setAuthId(String authId) {
		this.authId = authId;
	}
	
	@Column(name = "MODU_ID")
	public String getModuId() {
		return moduId;
	}
	public void setModuId(String moduId) {
		this.moduId = moduId;
	}
	
	public String toString(){
		return "[id=" + this.id + 
				"][authId=" + this.authId + 
				"][moduId=" + this.moduId + "]";
	}

}
