package _00_init.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebFilter("/*")
public class DisableCacheFilter implements Filter {

    public DisableCacheFilter() {
        // TODO Auto-generated constructor stub
    }

	public void destroy() {
		// TODO Auto-generated method stub
	}

	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		if (req instanceof HttpServletRequest
				&& resp instanceof HttpServletResponse) {
			HttpServletRequest request = (HttpServletRequest) req;
			HttpServletResponse response = (HttpServletResponse) resp;
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Cache-Control", "no-store");
			response.setDateHeader("Expires", 0);
			response.setHeader("Pragma", "no-cache");
		//如果寫在jsp裡面可以如下表示
//		<% 	response.setHeader("Pragma", "no-cache");
//			response.setHeader("Cache-Control", "no-cache");
//			response.setDateHeader("Expires", 0);
//			response.flushBuffer(); %>
		chain.doFilter(request, response);
	}

	}
	public void init(FilterConfig fConfig) throws ServletException {

	}

}
