package net.koreate.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class PrintEncodingFilter implements Filter {
	
	String filterParam;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("PrintEncoding Filter init ㄱㄱ");
		filterParam = filterConfig.getInitParameter("filterParam");
		System.out.println("filterParam : "+filterParam);
		System.out.println("PrintEncoding Filter init ㅂㅂ");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		System.out.println("PrintEncodingFilter doFilter ㄱㄱ");
		HttpServletRequest req = (HttpServletRequest)request;
		System.out.println("요청 URI : "+req.getRequestURI());
		String method = req.getMethod();
		System.out.println("전송방식 : "+method);
		if(method.equalsIgnoreCase("POST")) {
			req.setCharacterEncoding(filterParam);
		}
		chain.doFilter(request, response);
		System.out.println("PrintEncodingFilter doFilter ㅂㅂ");
	}

	@Override
	public void destroy() {
		System.out.println("PrintEncodingFilter destroy ㄱㄱ");
		System.out.println("PrintEncodingFilter destroy ㅂㅂ");
	}

}
