package com.neo.filter;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Date;
import java.util.Random;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class SysFilter implements Filter {
	private static final Random RANDOM = new Random();  
	private static final String USEESION = "SESSION_OBJECT";
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		HttpSession session = httpRequest.getSession();
		String url = httpRequest.getRequestURI();
		String method = httpRequest.getParameter("method");
//		System.out.println("url======>" + url);
//		System.out.println("method===>" + method);
		
		String uid = (String)session.getAttribute("USEESION");
		if(null == uid) {
			uid = generateGUID();
			session.setAttribute("USEESION", generateGUID());
			System.out.println(new Date() + "----no session=======>" + uid);
		} else {
			System.out.println(new Date() + "----session==========>" + uid);
		}
		
		filterChain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}
	
	public static String generateGUID()  
    {  
        return new BigInteger(165, RANDOM).toString(36).toUpperCase();  
    } 

}
