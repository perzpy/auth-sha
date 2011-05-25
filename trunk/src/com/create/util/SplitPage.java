package com.create.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页类 
 *  
 * @author 黄红滔
 * @date Feb 2, 2009
 */
@SuppressWarnings("serial")
public class SplitPage implements java.io.Serializable {

	private int startIndex; //开始行数
	private int count; //每页显示行数
	private List list; //数据列表

	public int total; //数据总行数
	public List subList; //当前页面显示数据

	public List totalCount; //合计
	public List subCount; //每页小计

	public SplitPage() {
		this.total = 0;
		this.subList = new ArrayList();
	}

	public SplitPage(int startIndex, int count, List list) {
		this.startIndex = startIndex;
		this.count = count;
		this.list = list;

		int size = (list == null || list.isEmpty()) ? 0 : list.size();

		this.total = size;
		int toCount = 0;
		int curPage = startIndex;
		int lessCount = size - curPage;

		if (lessCount > count)
			toCount = curPage + count;
		else
			toCount = size;
		this.subList = list.subList(startIndex, toCount);
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List getSubList() {
		return subList;
	}

	public void setSubList(List subList) {
		this.subList = subList;
	}

	public List getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(List totalCount) {
		this.totalCount = totalCount;
	}

	public List getSubCount() {
		return subCount;
	}

	public void setSubCount(List subCount) {
		this.subCount = subCount;
	}

}
