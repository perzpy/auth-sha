package com.create.service;

import com.create.exception.ApplicationException;
import com.create.model.User;
import com.create.util.SplitPage;

/**
 * 用户管理接口
 *
 * @author cxb
 * @date Mar 3, 2011
 *
 */
public interface IUser {
	
	/**
	 * 增加一个用户
	 * @param user 用户对象
	 * @return
	 * @throws ApplicationException
	 */
	public boolean add(User user) throws ApplicationException;
	
	/**
	 * 根据ID删除一个用户
	 * @param id 主键
	 * @return
	 * @throws ApplicationException
	 */
	public boolean del(String id) throws ApplicationException;
	
	/**
	 * 修改用户属性
	 * @param user 用户对象
	 * @return
	 * @throws ApplicationException
	 */
	public boolean update(User user) throws ApplicationException;
	
	/**
	 * 根据ID查找用户
	 * @param id 主键
	 * @return
	 * @throws ApplicationException
	 */
	public User queryById(String id) throws ApplicationException;
	
	/**
	 * 根据NAME查找用户
	 * @param name 用户名称
	 * @return
	 * @throws ApplicationException
	 */
	public User queryByName(String name) throws ApplicationException;
	
	/**
	 * 分页查询用户列表
	 * @param name 名称
	 * @param startIndex 开始数
	 * @param maxResult 第页数量
	 * @return
	 * @throws ApplicationException
	 */
	public SplitPage queryPageList(String name, int startIndex, int maxResult) throws ApplicationException;

}
