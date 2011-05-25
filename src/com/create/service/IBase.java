package com.create.service;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.create.dao.BaseDao;

public class IBase {
	protected BaseDao baseDao;
	protected BaseDao baseDaoJdbc;
	protected Logger log = Logger.getLogger(this.getClass());

	@Resource(name = "baseDao")
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@Resource(name = "baseDaoJdbc")
	public void setBaseDaoJdbc(BaseDao baseDaoJdbc) {
		this.baseDaoJdbc = baseDaoJdbc;
	}
	
}
