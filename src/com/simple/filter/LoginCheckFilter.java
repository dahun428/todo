package com.simple.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.simple.vo.User;

public class LoginCheckFilter implements Filter {
	
	private String loginCheckURL;
	private String loginUnCheckURL;
	
	public void init(FilterConfig filterConfig) throws ServletException {
		loginCheckURL = filterConfig.getInitParameter("checkURL");
		loginUnCheckURL = filterConfig.getInitParameter("uncheckURL");
	}
	public void destroy() {}
	
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
	
		
		HttpServletRequest httpReq = (HttpServletRequest) req;
		String requestURL = httpReq.getRequestURI();
		
		//요청 url 이 로그인 체크 url 과 일치하는 경우
		if(requestURL.equals(loginCheckURL)) {
		//로그인 여부 확인
			HttpSession session = httpReq.getSession();
			User user = (User) session.getAttribute("loginedUser");
			//로그인 되어 있지 않으면 에러페이지로 보낸다.
			if(user == null) {
				httpReq.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);;
				return;
			}
			//로그인되어 있는 경우 다음 필터를 실행한다.
			chain.doFilter(req, resp);
		} else {
			
			//요청 url 과 로그인 체크 url 이 일치하지 않는 경우
			//로그인 여부를 체크할 필요가 없으므로 다음번 필터나 서블릿을 실행한다.
			chain.doFilter(req, resp);
		}
		
	}
}
	