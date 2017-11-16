package com.livingguide.commom;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.livingguide.service.UuserService;
import com.livingguide.vo.UuserPO;

@RunWith(SpringJUnit4ClassRunner.class) // 使用Junit4测试
@ContextConfiguration(locations = { "classpath*:ApplicationContext.xml" }) // 加载配置文件
// ------------如果加入以下代码，所有继承该类的测试类都会遵循该配置，也可以不加，在测试类的方法上///控制事务，参见下一个实例
// 这个非常关键，如果不加入这个注解配置，事务控制就会完全失效！
// @Transactional
@Transactional(rollbackFor = Exception.class)
public class UuserDaoTest {

	// 使用DAO时放开注释即可
	@Autowired
	private UuserService uuserService;

	@Test
	// @Rollback(false)
	public void testGetById() {
		UuserPO user = uuserService.getById(1);
		System.err.println(user.toString());
	}

	@Test
	@Rollback(false)
	public void testUpdate() {
		UuserPO user = uuserService.getById(11);
		user.setNickname("乐乐");
		boolean ok = uuserService.update(user);
		System.err.println(ok);
	}

	@Test
	@Rollback(true)
	public void testDelete() {
		boolean user = uuserService.delete(12);
		System.err.println(user);
	}
}
