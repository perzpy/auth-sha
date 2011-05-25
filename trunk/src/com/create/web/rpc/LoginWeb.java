package com.create.web.rpc;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import com.create.exception.ApplicationException;
import com.create.model.Auth;
import com.create.model.LoginUser;
import com.create.model.Role;
import com.create.model.User;
import com.create.model.UserRole;
import com.create.service.IAuth;
import com.create.service.IRole;
import com.create.service.IUser;
import com.create.service.IUserRole;
import com.create.util.Lang;

/**
 * 用户登陆
 *
 * @author cxb
 * @date Mar 7, 2011
 *
 */
@RemoteProxy
public class LoginWeb {
	
	private IUser userService;
	private IRole roleService;
	private IUserRole userRoleService;
	private IAuth authService;
	
	@Resource(name = "userService")
	public void setUserService(IUser userService) {
		this.userService = userService;
	}
	
	@Resource(name = "roleService")
	public void setRoleService(IRole roleService) {
		this.roleService = roleService;
	}
	
	@Resource(name = "userRoleService")
	public void setUserRoleService(IUserRole userRoleService) {
		this.userRoleService = userRoleService;
	}

	@Resource(name = "authService")
	public void setAuthService(IAuth authService) {
		this.authService = authService;
	}

	/**
	 * 用户登陆
	 * @param name 用户名
	 * @param password 密码
	 * @param checkCode 验证码
	 * @return
	 * @throws ApplicationException
	 */
	@RemoteMethod
	public boolean login(String name, String password, String checkCode) throws ApplicationException{
		if(name == null || "".equals(name)){
			throw new ApplicationException("用户名不能为空！");
		}
		if(password == null || "".equals(password)){
			throw new ApplicationException("密码不能为空！");
		}
		if(checkCode == null || "".equals(checkCode)){
			throw new ApplicationException("验证码不能为空！");
		}
		WebContext webContext = WebContextFactory.get();
		HttpSession session = webContext.getSession();
		//HttpServletRequest request = webContext.getHttpServletRequest();
		String checkCodeTemp = (String) session.getAttribute("checkCode");
		if(!checkCode.equals(checkCodeTemp)){
			throw new ApplicationException("验证码错误！");
		}
		User user = userService.queryByName(name);
		boolean flag = false;
		if(user.getName() == null || !name.equals(user.getName())){
			flag = true;
		}
		if(user.getPasswd() == null || !password.equals(user.getPasswd())){
			flag = true;
		}
		if(flag){
			throw new ApplicationException("用户名或密码错误！");
		}
		
		session.setAttribute("user", user);
		
		LoginUser loginUser = new LoginUser();
		if(user != null){
			loginUser.setPassWord(user.getPasswd());
			loginUser.setUserName(user.getName());
			loginUser.setIp(Lang.getRemoteClientIp());
			List authList = authService.queryAuthsByUserId(user.getId());
			if(authList != null && authList.size() > 0){
				String[] authors = new String[authList.size()];
				for(int i = 0; i < authList.size(); i ++){
					Auth auth = (Auth) authList.get(i);
					authors[i] = auth.getName();
				}
				loginUser.setAuthors(authors);
			}
			System.out.println(loginUser.toString());
			session.setAttribute("security_user", loginUser);
			session.setAttribute("sessionId", session.getId());
		}
		
		
		
		return true;
	}
	
	/**
	 * 取得当前登陆用户的角色
	 * （此用户角色对象的角色ID就是此用户角色的菜单名）
	 * 
	 * @return
	 * @throws ApplicationException
	 */
	@RemoteMethod
	public List loginUserRole() throws ApplicationException {
		User user = this.getLoginUser();
		List list = userRoleService.queryUserRoleMap(user.getId());
		if(list.size() == 0){
			throw new ApplicationException("请先联系管理员分配角色！");
		}
		return list;
	}
	
	/**
	 * 当前登陆用户是否有权限
	 * 
	 * @return
	 * @throws ApplicationException
	 */
	@RemoteMethod
	public boolean loginUserIsAuth() throws ApplicationException {
		User user = this.getLoginUser();
		List<UserRole> list = userRoleService.queryListByUserId(user.getId());
		if(list.size() == 0){
			throw new ApplicationException("请先联系管理员分配角色！");
		}
		for(UserRole userRole : list){
			Role role = roleService.queryByPK(userRole.getRoleId());
			
			
		}
		return false;
	}
	
	/**
	 * 从session中取得当前登陆用户信息
	 * @return
	 * @throws ApplicationException
	 */
	@RemoteMethod
	public User getLoginUser() throws ApplicationException {
		User user = null;
		WebContext webContext = WebContextFactory.get();
		HttpSession session = webContext.getSession();
		Object object = session.getAttribute("user");
		if(object != null){
			user = (User) object;
		}else{
			throw new ApplicationException("请先登陆系统！");
		}
		return user;
	}
}
