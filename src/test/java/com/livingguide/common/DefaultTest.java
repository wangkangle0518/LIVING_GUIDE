package com.livingguide.common;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.livingguide.service.UserService;
import com.livingguide.vo.UserPO;


/**
 * 传统方式单元测试
 *
 */
public class DefaultTest {
	
	private UserService uuserService;
	
	@SuppressWarnings("resource")
	@Before
	public void before(){
	    //使用"spring.xml"和"spring-mybatis.xml"这两个配置文件创建Spring上下文
	    ApplicationContext ac = new ClassPathXmlApplicationContext(new String[]{"ApplicationContext.xml"});
	    //从Spring容器中根据bean的id取出我们要使用的userService对象
	    uuserService = (UserService) ac.getBean("uuserService");
	}
	@Test
	// @Rollback(false)
	public void test() {
		UserPO user = uuserService.getById(1);
		System.err.println(user.toString());
	}
}
