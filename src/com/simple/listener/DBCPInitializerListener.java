package com.simple.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.dbcp2.BasicDataSource;

import com.simple.util.ConnectionUtil;


public class DBCPInitializerListener implements ServletContextListener {

	public void contextInitialized(ServletContextEvent sce) {


		//<context-param> 의 설정 값을 담고 있는 ServletContext객체 횔득
		ServletContext sc = sce.getServletContext();
		//<context-param> 값 조회하기
		String url = sc.getInitParameter("db.url");
		String driverClassName = sc.getInitParameter("db.driverClassName");
		String user = sc.getInitParameter("db.username");
		String password = sc.getInitParameter("db.password");
		
		System.out.println("url : " + url);
		System.out.println("driverClassName  : " + driverClassName );
		System.out.println("user : " + user );
		System.out.println("password : " + password );
		
		//Database Connection Pool 생성하기
		BasicDataSource ds = new BasicDataSource();
		ds.setUrl(url);
		ds.setDriverClassName(driverClassName);
		ds.setUsername(user);
		ds.setPassword(password);
		
		ConnectionUtil.setDataSource(ds);
		
		System.out.println("리스너가 실행됨 ...");
	}
	
}
