package com.create.service;

import java.util.List;

import com.create.exception.ApplicationException;
import com.create.model.Modu;
import com.create.util.SplitPage;

/**
 * 模块管理
 *
 * @author cxb
 * @date Mar 3, 2011
 *
 */
public interface IModu {
	
	/**
	 * 增加一个模块
	 * @param modu 模块对象
	 * @return
	 * @throws ApplicationException
	 */
	public boolean add(Modu modu) throws ApplicationException;
	
	/**
	 * 根据ID删除一个模块
	 * @param id 主键
	 * @return
	 * @throws ApplicationException
	 */
	public boolean del(String id) throws ApplicationException;
	
	/**
	 * 修改模块属性
	 * @param modu 模块对象
	 * @return
	 * @throws ApplicationException
	 */
	public boolean update(Modu modu) throws ApplicationException;
	
	/**
	 * 根据ID查找模块
	 * @param id 主键
	 * @return
	 * @throws ApplicationException
	 */
	public Modu queryById(String id) throws ApplicationException;
	
	/**
	 * 根据NAME查找模块
	 * @param name 模块名称
	 * @return
	 * @throws ApplicationException
	 */
	public Modu queryByName(String name) throws ApplicationException;
	
	/**
	 * 分页查询模块列表
	 * @param name 名称
	 * @param startIndex 开始数
	 * @param maxResult 第页数量
	 * @return
	 * @throws ApplicationException
	 */
	public SplitPage queryPageList(String name, int startIndex, int maxResult) throws ApplicationException;
	
	/**
	 * 查询所有模块信息
	 * @return
	 * @throws ApplicationException
	 */
	public List queryAll() throws ApplicationException;
}
