package _02_login.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import _00_init.util.GlobalService;

@WebFilter("/_02_login/login.jsp")
public class FindUserPassword implements Filter {
	String requestURI;
    public FindUserPassword() {
    }
	public void destroy() {
	}
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
if(request instanceof HttpServletRequest && response instanceof HttpServletResponse ) {
	HttpServletRequest req=(HttpServletRequest) request;
	HttpServletResponse resp= (HttpServletResponse) response;
	HttpSession session=req.getSession();
	
	String cookieName="";
	String usercellphone="";
	String password="";
	String rememberMe="false";
	
	Cookie[] cookies=req.getCookies();
	
	if(cookies!=null) {
		for(int i=0;i<cookies.length;i++) {
			cookieName=cookies[i].getName();
			if(cookieName.equals("usercellphone")) {
				usercellphone=cookies[i].getValue();				
			}else if(cookieName.equals("password")) {
				String sp=cookies[i].getValue();
				if(sp!=null) {
					password=GlobalService.decryptString(GlobalService.KEY, sp);
				}
			}else if(cookieName.equals("rm")) {
				rememberMe=cookies[i].getValue();
			}
		}
	}else {
		
	}
	session.setAttribute("rememberMe", rememberMe);
	session.setAttribute("usercellphone", usercellphone);
	session.setAttribute("password", password);
}
		chain.doFilter(request, response);
	}
	public void init(FilterConfig fConfig) throws ServletException {
	}
}
