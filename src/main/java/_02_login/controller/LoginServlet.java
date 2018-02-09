package _02_login.controller;

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

import _00_init.util.GlobalService;
import _01_register.model.MemberBean;
import _02_login.model.LoginService;
import _02_login.model.LoginServiceImp;

@WebServlet("/_02_login/login.do")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session= request.getSession();
		// 1. 讀取使用者輸入資料
		String usercellphone=request.getParameter("userCellphone");
		String userpassword=request.getParameter("pswd");
		String rm=request.getParameter("rememberMe");
		String requestURI = (String) session.getAttribute("requestURI");
		System.out.println(usercellphone+userpassword+rm+ requestURI);
		// 2. 進行必要的資料轉換
		// 3. 檢查使用者輸入資料
		Map<String,String> errorMsg=new	HashMap<String,String>();
		request.setAttribute("ErrorMsg", errorMsg);
		//帳號密碼是否為空
		if(usercellphone==null || usercellphone.trim().length()==0) {
			errorMsg.put("CellphoneEmptyMsg", "帳號欄必須填入");
		}
		if(userpassword==null || userpassword.trim().length()==0) {
			errorMsg.put("PasswordEmptyMsg", "密碼欄必須填入");
		}
		//記住帳密
		Cookie cookieUser=null;
		Cookie cookiePassword=null;
		Cookie cookieRememberme=null;
		if(rm!=null) {
			cookieUser=new Cookie("usercellphone",usercellphone);
			cookieUser.setMaxAge(30*60*60);
			cookieUser.setPath(request.getContextPath());
			String encodePassword=GlobalService.encryptString(userpassword);
			//public Cookie(java.lang.String name,java.lang.String value)
			cookiePassword=new Cookie("password",encodePassword);
			cookiePassword.setMaxAge(30*60*60);
			cookiePassword.setPath(request.getContextPath());
			cookieRememberme=new Cookie("rm","true");
			cookieRememberme.setMaxAge(30*60*60);
			cookieRememberme.setPath(request.getContextPath());
		}else {
			cookieUser=new Cookie("usercellphone",usercellphone);
			cookieUser.setMaxAge(0);
			cookieUser.setPath(request.getContextPath());
			String encodePassword=GlobalService.encryptString(userpassword);
			cookiePassword=new Cookie("password",encodePassword);
			cookiePassword.setMaxAge(0);
			cookiePassword.setPath(request.getContextPath());
			cookieRememberme = new Cookie("rm", "false");
			cookieRememberme.setMaxAge(30 * 60 * 60);
			cookieRememberme.setPath(request.getContextPath());
		}
		response.addCookie(cookieUser);
		response.addCookie(cookiePassword);
		response.addCookie(cookieRememberme);		
		if(!errorMsg.isEmpty()) {//如果不是空的請重新登入
		RequestDispatcher rd= request.getRequestDispatcher("login.jsp");
		rd.forward(request, response);
		return;
		}
		// 4. 進行 Business Logic 運算
		LoginService login= new LoginServiceImp();
		userpassword=GlobalService.getMD5Endocing(GlobalService.encryptString(userpassword));
		MemberBean mb= login.checkIDPassword(usercellphone,userpassword);
		String useridentity=null;
		String userstatus=null;
		System.out.println(mb);
		if(mb !=null) {
			session.setAttribute("LoginOK", mb);
			useridentity=mb.getUseridentity();
		userstatus=mb.getUserstatus();
			session.setAttribute("UserIdentity", useridentity);
			session.setAttribute("UserStatus", userstatus);
			if(useridentity.endsWith("blacklisted")) {
				errorMsg.put("blacklistedLogin", "該帳號目前為黑名單,禁止登入");
			}else if(useridentity.endsWith("unregistered")) {
				errorMsg.put("unregisteredLogin", "尚未完成驗證,請繼續完成註冊");
			}else if(useridentity.endsWith("warning")) {
				errorMsg.put("warningLogin", "請注意第二次警告將會停權");				
			}
		}else {
			errorMsg.put("LoginError", "該帳號不存在或密碼錯誤");
		}
		
		// 5.依照 Business Logic 運算結果來挑選適當的畫面
	if(errorMsg.isEmpty()) {
		if(requestURI!=null) {
			requestURI=(requestURI.length()==0 ? request.getContextPath():requestURI);
			response.sendRedirect(response.encodeRedirectURL(requestURI));
			return;
		}else {
			response.sendRedirect(response.encodeRedirectURL(request.getContextPath()));
		return;
		}
	}else {
		RequestDispatcher rd=request.getRequestDispatcher("login.jsp");
		rd.forward(request, response);
		return;
	}
	}

}
