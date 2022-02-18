package com.example.demo;

import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class BoardApplicationTests {
	
	@Autowired
	private ApplicationContext context;
	@Autowired
	private SqlSessionFactory sessionFactory;
	
	
	@Test
	void contextLoads() {
	}
	
	@Test
	public void ApplicationContextTest() {
		try {
			System.out.println(context.getBean("sqlSessionFactory"));
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void SqlSessionFactoryTest() {
		try {
			System.out.println(sessionFactory.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	

}
