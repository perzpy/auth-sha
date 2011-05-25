package com.create.model;

public class LoginUser {
	/**
	 * 权限集合
	 */
	private String[] authors = new String[0];
	/**
	 * 密码
	 */
	private String passWord = null;
	/**
	 * 登录名
	 */
	private String userName = null;
	/**
	 * 域ID
	 */
	private String regionId = null;
	/**
	 * 用户类型
	 */
	private String type = null;
	/**
	 * 用户IP
	 */
	private String ip = null;
	
	public String[] getAuthors() {
		return authors;
	}
	public void setAuthors(String[] authors) {
		this.authors = authors;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getRegionId() {
		return regionId;
	}
	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public String authorsToString(){
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < this.authors.length; i ++){
			sb.append(this.authors[i]);
			if(i != this.authors.length - 1){
				sb.append(",");
			}
		}
		return sb.toString();
	}

	public String toString(){
		return "[authors=" + this.authorsToString() + 
			"][passWord=" + this.passWord + 
			"][userName=" + this.userName + 
			"][regionId=" + this.regionId + 
			"][type=" + this.type + 
			"][ip=" + this.ip + "]";
	}
	
}
