package _01_register.controller;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import _01_register.model.MemberBean;
import _01_register.model.MemberDao;
import _01_register.model.MemberDao_Jdbc;

@MultipartConfig(location = "", fileSizeThreshold = 5 * 1024 * 1024, maxFileSize = 1024 * 1024
		* 500, maxRequestSize = 1024 * 1024 * 500 * 5)
@WebServlet("/_01_register/register.do")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		Map<String, String> errorMsg = new HashMap<String, String>();
		Map<String, String> msgOK = new HashMap<String, String>();
		HttpSession session = request.getSession();
		request.setAttribute("MsgMap", errorMsg);
		session.setAttribute("MsgOK", msgOK);
		
		String userpassword = "";
		String usercellphone = "";
		String username = "";
		String useremail1 = "";
		String useremail2 = "";
		String useravater = "";
		String filename="";
		InputStream is = null;
		Collection<Part> parts = request.getParts();
		if (parts != null) {
			for (Part p : parts) {
				String fldName = p.getName();
				String value = request.getParameter(fldName);
				// 1. 讀取使用者輸入資料
				if (p.getContentType() == null) {
					if (fldName.equals("usercellphone")) {
						usercellphone = value;
					}
					 else if (fldName.equals("userpassword")) {
						userpassword = value;
					} else if (fldName.equals("username")) {
						username = value;
					} else if (fldName.equals("useremail1")) {
						useremail1 = value;
					} else if (fldName.equals("useremail2")) {
						useremail2 = value;
					} else {
						Part photo = request.getPart("photo");
						filename = photo.getSubmittedFileName();
					}

				}
			}
			// 2. 進行必要的資料轉換
			// (無)
			// 3. 檢查使用者輸入資料
			if(usercellphone==null||usercellphone.trim().length()==0) {
				errorMsg.put("errorphone","電話號碼欄必須輸入");
			}if (userpassword == null || userpassword.trim().length() == 0) {
				errorMsg.put("errorPasswordEmpty","密碼欄必須輸入");
			}if (username == null || username.trim().length() == 0) {
					errorMsg.put("errorName","姓名欄必須輸入");	
			}if (useremail1 == null || useremail1.trim().length() == 0) {
				errorMsg.put("errorEmail1","電子郵件欄必須輸入");
			}if (useremail2 == null || useremail2.trim().length() == 0) {
				errorMsg.put("errorEmail2","驗證碼欄必須輸入");
			}	if (!errorMsg.isEmpty()) {
				RequestDispatcher rd = request.getRequestDispatcher("register.jsp");
			rd.forward(request, response);
			return;}

		}try {
			// 4. 產生MemberDao物件，以便進行Business Logic運算
			// MemberDaoImpl_Jdbc類別的功能：
			// 1.檢查帳號是否已經存在，已存在的帳號不能使用，回傳相關訊息通知使用者修改
			// 2.若無問題，儲存會員的資料
			MemberDao dao= new MemberDao_Jdbc();
			if(dao.checkphone(usercellphone)) {
				errorMsg.put("errorIDDup", "此帳號已存在，請換新帳號");
			}else{
				Timestamp ts=new java.sql.Timestamp(System.currentTimeMillis());
				MemberBean mem=new MemberBean(null, usercellphone, userpassword, username, useremail1, null, ts, "registered", "consumer",1);
			int n=dao.saveUser(mem);
			if(n==1) {
				msgOK.put("InsertOK","<Font color='red'>新增成功，請開始使用本系統</Font>");
				response.sendRedirect("../index.jsp");
				return;
			}else {
				errorMsg.put("errorIDDup","新增此筆資料有誤(RegisterServlet)");
			}
			}
			// 5.依照 Business Logic 運算結果來挑選適當的畫面
			if (!errorMsg.isEmpty()) {
			// 導向原來輸入資料的畫面，這次會顯示錯誤訊息
			RequestDispatcher rd = request.getRequestDispatcher("register.jsp");
			rd.forward(request, response);
			return;
			}		
		}catch(Exception e) {
			e.printStackTrace();
			errorMsg.put("errorIDDup", e.getMessage());
			RequestDispatcher rd = request.getRequestDispatcher("register.jsp");
			rd.forward(request, response);
		}
	}
	}
	
