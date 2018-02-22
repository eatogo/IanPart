package _01_register.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/_01_register/verifymail.do")
public class VerifymailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		HttpSession session = req.getSession();
		Cookie cookieVerifymail = null;
		String cookieName = "";
		String verifynumber = "";
		String verifymail="false";
		String verifynumberinput=request.getParameter("verifynumber");;
		Cookie[] cookies = req.getCookies();
		Map<String, String> errorMsg = new HashMap<String, String>();

		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				cookieName = cookies[i].getName();
				if (cookieName.equals("verifynumber")) {
					verifynumber = cookies[i].getValue();
					System.out.println(verifynumber);
				}
			}
		}
		if ( verifynumberinput == null ||  verifynumberinput.trim().length() == 0) {
			errorMsg.put("verifynumberEmptyMsg", "帳號欄必須填入");
			RequestDispatcher rd= request.getRequestDispatcher("verifyemail.jsp");
			rd.forward(request, response);
			return;
		}
		//兩字串相等不要用==,要用.equals
			if(verifynumberinput.equals(verifynumber)) {
				System.out.println("認證成功");
				verifymail="true";
				cookieVerifymail = new Cookie("cookieVerifymail", verifymail);
				cookieVerifymail.setMaxAge(30 * 60 * 60);
				cookieVerifymail.setPath(request.getContextPath());
				response.addCookie(cookieVerifymail);
			RequestDispatcher rd = request.getRequestDispatcher("register.jsp");
			rd.forward(request, response);
			return;
			}
	}

}
