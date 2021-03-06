package com.jh.filter;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jh.member.memberDTO;

/**
 * Servlet Filter implementation class FilterTest1
 */
@WebFilter("/FilterTest1")
public class FilterTest1 implements Filter {
	private HashMap<String, Boolean> map;
    /**
     * Default constructor. 
     */
    public FilterTest1() {
    	map = new HashMap<String, Boolean>();
    	map.put("/noticeList", false);
    	map.put("/noticeSelect", false);
    	map.put("/noticeWrite", true);
    	map.put("/noticeUpdate", true);
    	map.put("/noticeDelete", true);
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		
		
		HttpSession session = ((HttpServletRequest)request).getSession();
		memberDTO memberdto = (memberDTO)session.getAttribute("member");
		String command =((HttpServletRequest)request).getPathInfo();
		boolean check = map.get(command);
		
		if(check) {
			//관리자만 통과
			if(memberdto != null && memberdto.getId().equals("admin")) {
				chain.doFilter(request, response);
				
			}else {
				((HttpServletResponse)response).sendRedirect("../index.do");
			}
			
		}else {
			chain.doFilter(request, response);
		}
		
		
		//response
		
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
