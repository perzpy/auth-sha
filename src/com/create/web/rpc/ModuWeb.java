package com.create.web.rpc;

import java.util.List;

import javax.annotation.Resource;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import com.create.exception.ApplicationException;
import com.create.model.AuthModu;
import com.create.model.Modu;
import com.create.service.IAuthModu;
import com.create.service.IModu;
import com.create.util.SplitPage;

/**
 * 模块管理RPC
 *
 * @author cxb
 * @date Mar 3, 2011
 *
 */
@RemoteProxy
public class ModuWeb {

	private IModu moduService;
	private IAuthModu authModuService;

	@Resource(name = "moduService")
	public void setModuService(IModu moduService) {
		this.moduService = moduService;
	}

	@Resource(name = "authModuService")
	public void setAuthModuService(IAuthModu authModuService) {
		this.authModuService = authModuService;
	}

	/**
	 * 增加一个模块
	 * 
	 * @param modu 模块对象
	 * @return
	 * @throws ApplicationException
	 */
	@RemoteMethod
	public boolean add(Modu modu, String authId) throws ApplicationException {
		moduService.add(modu);
		if(authId != null && !"".equals(authId)){
			AuthModu authModu = new AuthModu();
			authModu.setAuthId(authId);
			authModu.setModuId(modu.getId());
			authModuService.add(authModu);
		}
		return true;
	}

	/**
	 * 根据ID删除一个模块
	 * 
	 * @param id 主键
	 * @return
	 * @throws ApplicationException
	 */
	@RemoteMethod
	public boolean del(String id) throws ApplicationException {
		authModuService.delById(id);
		moduService.del(id);
		return true;
	}

	/**
	 * 修改模块属性
	 * 
	 * @param modu 模块对象
	 * @return
	 * @throws ApplicationException
	 */
	@RemoteMethod
	public boolean update(Modu modu, String authId) throws ApplicationException {
		authModuService.delById(modu.getId());
		moduService.update(modu);
		if(authId != null && !"".equals(authId)){
			AuthModu authModu = new AuthModu();
			authModu.setAuthId(authId);
			authModu.setModuId(modu.getId());
			authModuService.add(authModu);
		}
		return true;
	}

	/**
	 * 根据ID查找模块
	 * 
	 * @param id 主键
	 * @return
	 * @throws ApplicationException
	 */
	@RemoteMethod
	public Modu queryById(String id) throws ApplicationException {
		return moduService.queryById(id);
	}

	/**
	 * 根据NAME查找模块
	 * 
	 * @param name 模块名称
	 * @return
	 * @throws ApplicationException
	 */
	@RemoteMethod
	public Modu queryByName(String name) throws ApplicationException {
		return moduService.queryByName(name);
	}

	/**
	 * 分页查询模块列表
	 * 
	 * @param name 名称
	 * @param startIndex 开始数
	 * @param maxResult 第页数量
	 * @return
	 * @throws ApplicationException
	 */
	@RemoteMethod
	public SplitPage queryPageList(String name, int startIndex, int maxResult) throws ApplicationException {
		return moduService.queryPageList(name, startIndex, maxResult);
	}

	/**
	 * 查询所有模块信息
	 * 
	 * @return
	 * @throws ApplicationException
	 */
	@RemoteMethod
	public List queryAll() throws ApplicationException {
		return moduService.queryAll();
	}
	
	/**
	 * 根据模块ID查询所对应的权限模块对象
	 * 
	 * @param moduId 模块ID
	 * @return
	 * @throws ApplicationException
	 */
	@RemoteMethod
	public List queryAuthModuListByModuId(String moduId) throws ApplicationException {
		List list = null;
		list = authModuService.queryAuthModuMap(moduId);
		return list;
	}
}
