package com.create.test;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.create.exception.ApplicationException;
import com.create.service.impl.UserImpl;

/**
 * 
 * 用户测试类
 * @author perzpy(perzpy@gmail.com)
 * @date Jun 17, 2011
 *
 */
@RunWith(SpringJUnit4ClassRunner.class) //指定测试用例的运行器，这里指定了JUnit4
//@ContextConfiguration 注解有以下两个常用的属性：  
//locations：可以通过该属性手工指定 Spring 配置文件所在的位置,可以指定一个或多个 Spring 配置文件  
//inheritLocations：是否要继承父测试类的 Spring 配置文件，默认为 true  
@ContextConfiguration({"classpath:/applicationContext.xml"})
@Transactional //对所有的测试方法都使用事务，并在测试完成后回滚事务
public class UserServiceImplTest {
	@Autowired //自动注入applicationContext,这样就可以使用applicationContext.getBean("beanName")
	//private ApplicationContext applicationContext;
	
	@Resource //会自动注入 default by type
	private UserImpl userImpl;
	
	@Before //在每个测试用例方法之前都会执行
	public void init() {
		System.out.println("test begin……");
	}
	
	@After
	public void destory() {
		System.out.println("test end……");
	}

	@Test
	@Transactional
	//@Rollback(false) //这里设置为false，就让事务不回滚
	public void testDel() throws ApplicationException {
		Assert.assertNotNull(userImpl);
		Assert.assertTrue(userImpl.del("1"));
	}
}
