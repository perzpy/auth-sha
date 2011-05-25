package com.create.web.rpc;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import com.create.model.ExampleTable;
import com.create.service.IExampleTable;
import com.create.util.SplitPage;

@RemoteProxy
public class ExampleWeb {
	private static Logger log = Logger.getLogger(ExampleWeb.class);
	
	private IExampleTable exampleTableService;

	@Resource(name = "exampleTableService")
	public void setExampleTableService(IExampleTable exampleTableService) {
		this.exampleTableService = exampleTableService;
	}
	
	@RemoteMethod
	public SplitPage queryPageList(String name, int startIndex, int maxResult) throws Exception{
		return exampleTableService.queryPageList(name, startIndex, maxResult);
	}

	@RemoteMethod
	public boolean del(String id) throws Exception{
		return exampleTableService.del(id);
	}
	
	@RemoteMethod
	public boolean add(ExampleTable exampleTable) throws Exception{
		exampleTableService.add(exampleTable);
		return true;
	}
}
