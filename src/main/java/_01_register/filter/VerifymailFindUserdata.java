package _01_register.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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

@WebFilter("/_01_register/register.jsp")
public class VerifymailFindUserdata implements Filter {
	public VerifymailFindUserdata() {
	}

	public void destroy() {
		// TODO Auto-generated method stub
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		if (request instanceof HttpServletRequest && response instanceof HttpServletResponse) {
			HttpServletRequest req = (HttpServletRequest) request;
			HttpServletResponse resp = (HttpServletResponse) response;
			HttpSession session = req.getSession();

			String cookieName = "";
			String usercellphone = "";
			String userpassword = "";
			String username = "";
			String useremail = "";
			String useravater = "";

			Cookie[] cookies = req.getCookies();
			
			if (cookies != null) {
				for (int i = 0; i < cookies.length; i++) {
					cookieName = cookies[i].getName();
					if (cookieName.equals("usercellphone")) {
						usercellphone = cookies[i].getValue();
					} else if (cookieName.equals("userpassword")) {
						String sp = cookies[i].getValue();
						if (sp != null) {
							userpassword = GlobalService.decryptString(GlobalService.KEY, sp);
						}
					} else if (cookieName.equals("username")) {
						username = cookies[i].getValue();
					} else if (cookieName.equals("useremail")) {
						useremail = cookies[i].getValue();
					} else if (cookieName.equals("useravater")) {
						useravater = cookies[i].getValue();
					}
				}
			}
			
			session.setAttribute("usercellphone", usercellphone);
			session.setAttribute("userpassword ", userpassword );
			session.setAttribute("username", username);
			session.setAttribute("useremail", useremail);
			session.setAttribute("useravater",useravater);
	
		}

		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
