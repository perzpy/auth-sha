package com.create.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Component;

import com.create.exception.ApplicationException;
import com.create.model.Modu;
import com.create.service.IBase;
import com.create.service.IModu;
import com.create.util.SplitPage;

/**
 * 模块管理
 *
 * @author cxb
 * @date Mar 3, 2011
 *
 */
@Component(value = "moduService")
public class ModuImpl extends IBase implements IModu {

	/*
	 * (non-Javadoc)
	 * @see com.create.service.ModuService#add(com.create.model.Modu)
	 */
	public boolean add(Modu modu) throws ApplicationException {
		this.baseDao.addObject(modu);
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see com.create.service.ModuService#del(java.lang.String)
	 */
	public boolean del(String id) throws ApplicationException {
		this.baseDaoJdbc.delObjectById("BAS_MODU", "ID", id);
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see com.create.service.ModuService#queryById(java.lang.String)
	 */
	public Modu queryById(String id) throws ApplicationException {
		Modu modu = new Modu();
		Object object = this.baseDaoJdbc.queryObjectById("BAS_MODU", "ID", id);
		if (object != null) {
			System.out.println(object.getClass());
			Map map = (Map) object;
			modu.setId((String) map.get("ID"));
			modu.setName((String) map.get("NAME"));
			modu.setUrl((String) map.get("URL"));
			modu.setDescription((String) map.get("DESCRIPTION"));
		}
		return modu;
	}

	/*
	 * (non-Javadoc)
	 * @see com.create.service.ModuService#queryByName(java.lang.String)
	 */
	public Modu queryByName(String name) throws ApplicationException {
		Modu modu = new Modu();
		Object object = this.baseDaoJdbc.queryObjectById("BAS_MODU", "NAME", name);
		if (object != null) {
			System.out.println(object.getClass());
			Map map = (Map) object;
			modu.setId((String) map.get("ID"));
			modu.setName((String) map.get("NAME"));
			modu.setUrl((String) map.get("URL"));
			modu.setDescription((String) map.get("DESCRIPTION"));
		}
		return modu;
	}

	/*
	 * (non-Javadoc)
	 * @see com.create.service.ModuService#queryPageList(java.lang.String, int, int)
	 */
	public SplitPage queryPageList(String name, int startIndex, int maxResult) throws ApplicationException {
		List list = null;
		SplitPage sp = new SplitPage();
		List params = new ArrayList();
		StringBuilder sql = new StringBuilder();
		sql.append("FROM BAS_MODU");
		if (name != null && !"".equals(name)) {
			sql.append(" WHERE NAME = ?");
			params.add(name);
		}
		StringBuilder sqlTemp = new StringBuilder("SELECT COUNT(1) " + sql.toString());
		int total = this.baseDaoJdbc.queryListCount(sqlTemp, params.toArray());
		this.log.info("Total = " + total);
		if (total > 0) {
			sql.insert(0, "SELECT * ");
			sql.append(" ORDER BY NAME");
			list = this.baseDaoJdbc.queryPageList(sql, params.toArray(), startIndex, maxResult);
			this.log.info("List Size = " + list.size());
			sp.setTotal(total);
			sp.setSubList(list);
		}
		return sp;
	}

	/*
	 * (non-Javadoc)
	 * @see com.create.service.ModuService#update(com.create.model.Modu)
	 */
	public boolean update(Modu modu) throws ApplicationException {
		if (modu.getId() == null || "".equals(modu.getId())) {
			return false;
		}
		Modu moduTemp = this.queryById(modu.getId());
		try {
			BeanUtils.copyProperties(moduTemp, modu);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		this.baseDao.updateObject(moduTemp);
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see com.create.service.ModuService#queryAll()
	 */
	public List queryAll() throws ApplicationException {
		return baseDaoJdbc.queryObjectList("SELECT * FROM BAS_MODU", null);
	}

}
