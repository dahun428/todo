package com.simple.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class LoggerFilter implements Filter {
	
	public void init(FilterConfig filterConfig) throws ServletException {}
	public void destroy() {}
	
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		//전처리 작업
		HttpServletRequest httpreq = (HttpServletRequest) req;
		String requestURI = httpreq.getRequestURI();
		String ipAddr = httpreq.getRemoteAddr();
		long startTime = System.currentTimeMillis();
		System.out.println("["+ipAddr+"] 클라이언트의 요청을 받음");
		System.out.println("["+requestURI+"] 요청에 대한 처리 시작");
		
		chain.doFilter(req, resp);

		//후처리 작업
		long endTime = System.currentTimeMillis();
		long workingTime = endTime - startTime;
		System.out.println("소요시간 : [" + workingTime + "] 밀리초");
		System.out.println("["+requestURI+"] 요청에 대한 처리 종료 \n");
			
	}
}
