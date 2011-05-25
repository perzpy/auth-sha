package com.create.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 菜单
 *
 * @author cxb
 * @date Mar 3, 2011
 *
 */
@Entity
@Table(name="BAS_MENU")
public class Menu implements Serializable {
	
	/**
	 * 主键
	 */
	private String id;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 层级
	 */
	private String levelCode;
	/**
	 * 顺序
	 */
	private int seq;
	/**
	 * 描述
	 */
	private String description;
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
	@Column(name = "NAME")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(name = "LEVELCODE")
	public String getLevelCode() {
		return levelCode;
	}
	public void setLevelCode(String levelCode) {
		this.levelCode = levelCode;
	}
	@Column(name = "SEQ")
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	@Column(name = "DESCRIPTION")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
				"][name=" + this.name + 
				"][levelCode=" + this.levelCode + 
				"][seq=" + this.seq + 
				"][description=" + this.description + 
				"][moduId=" + this.moduId + "]";
	}

}
