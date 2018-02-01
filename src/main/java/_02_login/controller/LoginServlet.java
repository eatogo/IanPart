package _02_login.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/_02_login/login.do")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session= request.getSession();
		String userid=request.getParameter("userId");
		String userpassword=request.getParameter("pswd");
		String rm=request.getParameter("rememberMe");
		String requestURI = (String) session.getAttribute("requestURI");
		System.out.println(userid+userpassword+rm+ requestURI);
		// 1. 讀取使用者輸入資料
		// 2. 進行必要的資料轉換
		// 3. 檢查使用者輸入資料
		// 4. 進行 Business Logic 運算
		// 5.依照 Business Logic 運算結果來挑選適當的畫面
	
	}

}
