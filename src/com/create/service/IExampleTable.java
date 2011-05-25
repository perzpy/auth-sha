package com.create.service;

import java.util.List;

import com.create.model.ExampleTable;
import com.create.util.SplitPage;

public interface IExampleTable {
	public boolean add(ExampleTable exampleTable) throws Exception;
	public boolean del(String id) throws Exception;
	public boolean modify(ExampleTable exampleTable);
	public ExampleTable query(String id);
	public List queryList() throws Exception;
	public int queryListCount(String name) throws Exception;
	public SplitPage queryPageList(String name, int startIndex, int maxResult) throws Exception;
}
