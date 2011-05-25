package com.create.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Component;

import com.create.exception.DatabaseException;
import com.create.model.ExampleTable;
import com.create.service.IBase;
import com.create.service.IExampleTable;
import com.create.util.SplitPage;
import com.create.util.Strings;

@Component(value = "exampleTableService")
public class ExampleTableImpl extends IBase implements IExampleTable {

	private HibernateTemplate hibernateTemplate;

	@Resource(name = "hibernateTemplate")
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	public boolean add(ExampleTable exampleTable) throws Exception {
		this.baseDao.addObject(exampleTable);
		return true;
	}

	public boolean addTransactionTest() throws Exception {
		ExampleTable exampleTable;
		for (int i = 0; i < 10; i++) {
			exampleTable = new ExampleTable();
			exampleTable.setId(String.valueOf(i));
			this.baseDao.addObject(exampleTable);
			if (i == 6)
				throw new DatabaseException("TEST");
		}
		return true;
	}

	public boolean del(String id) throws Exception {
		this.baseDaoJdbc.delObjectById("TEST_EXAMPLE", "ID", id);
		return true;
	}

	public boolean modify(ExampleTable exampleTable) {
		// TODO Auto-generated method stub
		return true;
	}

	public ExampleTable query(String id) {
		// TODO Auto-generated method stub
		ExampleTable exampleTable = null;
		String hql = "from ExampleTable where id = ?";
		List list = hibernateTemplate.find(hql, id);
		if (list != null && list.size() == 1) {
			exampleTable = (ExampleTable) list.get(0);
		}
		return exampleTable;
	}

	public List queryList() {
		// TODO Auto-generated method stub
		List list = null;
		String hql = "from ExampleTable";
		list = hibernateTemplate.find(hql);
		System.out.println(list.size());
		return list;
	}

	public int queryListCount(String name) throws Exception {
		List params = new ArrayList();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT COUNT(1) FROM TEST_EXAMPLE WHERE 1 = 1 ");
		if (name != null && !"".equals(name)) {
			sql.append(" AND NAME LIKE ? ");
			params.add(Strings.addBeginEnd(name, "%"));
		}
		return baseDaoJdbc.queryListCount(sql, params.toArray());
	}

	public SplitPage queryPageList(String name, int startIndex, int maxResult) throws Exception {
		SplitPage sp = new SplitPage();
		List params = new ArrayList();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM TEST_EXAMPLE WHERE 1 = 1 ");
		if (name != null && !"".equals(name)) {
			sql.append(" AND NAME LIKE ? ");
			params.add(Strings.addBeginEnd(name, "%"));
		}
		sql.append(" ORDER BY ID ");
		int count = queryListCount(name);
		if (count > 0) {
			List list = baseDaoJdbc.queryPageList(sql, params.toArray(), startIndex, maxResult);
			sp.setTotal(count);
			sp.setSubList(list);
		}
		return sp;
	}

}
