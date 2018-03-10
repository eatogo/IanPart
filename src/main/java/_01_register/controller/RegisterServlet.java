package _01_register.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import _00_init.util.GlobalService;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import _01_register.model.MemberBean;
import _01_register.model.MemberDao;
import _01_register.model.MemberDao_Jdbc;

@MultipartConfig(location = "", fileSizeThreshold = 5 * 1024
		* 1024, maxFileSize = 1024 * 1024 * 500, maxRequestSize = 1024 * 1024 * 500 * 5)
@WebServlet("/_01_register/register.do")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Collection<Part> parts;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		Map<String, String> errorMsg = new HashMap<String, String>();
		Map<String, String> msgOK = new HashMap<String, String>();
		HttpSession session = request.getSession();
		String verifyorsubmit = request.getParameter("submit");
		System.out.println(verifyorsubmit);
		request.setAttribute("MsgMap", errorMsg);
		session.setAttribute("MsgOK", msgOK);
		String path = this.getServletContext().getRealPath("/");
		System.out.println(path);
		String userpassword = "";
		String usercellphone = "";
		String username = "";
		String useremail = "";
		String useravater = "";

		if (verifyorsubmit.equals("verify")) {
			System.out.println("This is verified test");
			// 1. 讀取使用者輸入資料(=驗證=)
			Collection<Part> parts1 = request.getParts();
			parts1 = request.getParts();
			if (parts1 != null) {
				for (Part p1 : parts1) {
					String fldName1 = p1.getName();
					String value1 = request.getParameter(fldName1);
					if (p1.getContentType() == null) {
						if (fldName1.equals("usercellphone")) {
							usercellphone = value1;
						} else if (fldName1.equals("userpassword")) {
							userpassword = value1;
						} else if (fldName1.equals("username")) {
							username = value1;
						} else if (fldName1.equals("useremail")) {
							useremail = value1;
						} else {
						}
					}
				}
				// 2. 進行必要的資料轉換(=驗證=)
				// 3. 檢查使用者輸入資料(=驗證=)
				if (usercellphone == null || usercellphone.trim().length() == 0) {
					errorMsg.put("errorphone", "電話號碼欄必須輸入");
				}
				if (userpassword == null || userpassword.trim().length() == 0) {
					errorMsg.put("errorPasswordEmpty", "密碼欄必須輸入");
				}
				if (username == null || username.trim().length() == 0) {
					errorMsg.put("errorName", "姓名欄必須輸入");
				}
				if (useremail == null || useremail.trim().length() == 0) {
					errorMsg.put("errorEmail1", "電子郵件欄必須輸入");
				}
				if (!errorMsg.isEmpty()) {
					RequestDispatcher rd = request.getRequestDispatcher("register.jsp");
					rd.forward(request, response);
					return;
				}
				// 3.2記住使用者輸入資料
				Cookie cookieUsercellphone = null;
				Cookie cookiePassword = null;
				Cookie cookieUsername = null;
				Cookie cookieUseremail = null;
				Cookie cookieVerifynumber=null;
				
				cookieUsercellphone = new Cookie("usercellphone", usercellphone);
				cookieUsercellphone.setMaxAge(30 * 60 * 60);
				cookieUsercellphone.setPath(request.getContextPath());
				String encodePassword = GlobalService.encryptString(userpassword);
				// public Cookie(java.lang.String name,java.lang.String value)
				cookiePassword = new Cookie("userpassword", encodePassword);
				cookiePassword.setMaxAge(30 * 60 * 60);
				cookiePassword.setPath(request.getContextPath());
				
				cookieUsername = new Cookie("username", username);
				cookieUsername.setMaxAge(30 * 60 * 60);
				cookieUsername.setPath(request.getContextPath());

				cookieUseremail = new Cookie("useremail", useremail);
				cookieUseremail.setMaxAge(30 * 60 * 60);
				cookieUseremail.setPath(request.getContextPath());

				// 4. 進行Business Logic運算(=驗證=)
				// 4.1產生驗證碼
				int z;
				StringBuilder sb = new StringBuilder();
				int i;
				for (i = 0; i < 8; i++) {
					z = (int) ((Math.random() * 7) % 3);
					if (z == 1) { // 放數字
						sb.append((int) ((Math.random() * 9) + 1));
					} else if (z == 2) { // 放大寫英文
						sb.append((char) (((Math.random() * 26) + 65)));
					} else {// 放小寫英文
						sb.append((char) ((Math.random() * 26) + 97));
					}
				}
				String sbs = sb.toString();
				System.out.println(sbs);
				cookieVerifynumber = new Cookie("verifynumber", sbs);
				cookieVerifynumber.setMaxAge(30 * 60 * 60);
				cookieVerifynumber.setPath(request.getContextPath());
				response.addCookie(cookieUsercellphone);
				response.addCookie(cookiePassword);
				response.addCookie(cookieUsername);
				response.addCookie(cookieUseremail);	
				response.addCookie(cookieVerifynumber);
				
				String host = "smtp.gmail.com";
				int port = 587;
				final String user = "iw5420@gmail.com";
				final String password = "qaz124321231";// your password

				Properties props = new Properties();
				props.put("mail.smtp.host", host);
				props.put("mail.smtp.auth", "true");
				props.put("mail.smtp.starttls.enable", "true");
				props.put("mail.smtp.port", port);
				Session session2 = Session.getInstance(props, new Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(user, password);
					}
				});
				try {
					Message message = new MimeMessage(session2);
					message.setFrom(new InternetAddress("iw5420@gmail.com"));
					message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(useremail));
					message.setSubject("測試寄信.");
					message.setText("Dear " + user + ", 您的驗證碼為" + sbs);

					Transport transport = session2.getTransport("smtp");
					transport.connect(host, port, username, password);
					Transport.send(message);
					System.out.println("寄送email結束.");		
				} catch (MessagingException e) {
					throw new RuntimeException(e);
				}
			}	
			
			
			RequestDispatcher rd = request.getRequestDispatcher("verifyemail.jsp");
			rd.forward(request, response);
			return;	
			// 5.依照 Business Logic 運算結果來挑選適當的畫面(=驗證=)
		} else {

			// 1. 讀取使用者輸入資料
			Collection<Part> parts2 = request.getParts();
			if (parts2 != null) {
				for (Part p2 : parts2) {
					String fldName2 = p2.getName();
					String value2 = request.getParameter(fldName2);
					if (p2.getContentType() == null) {
						if (fldName2.equals("usercellphone")) {
							usercellphone = value2;
						} else if (fldName2.equals("userpassword")) {
							userpassword = value2;
						} else if (fldName2.equals("username")) {
							username = value2;
						} else if (fldName2.equals("useremail")) {
							useremail = value2;
						} else {
							Part photo = request.getPart("photo");
							useravater= photo.getSubmittedFileName();
							//String cd = photo.getHeader("Content-Disposition");
							//System.out.println(cd2);
							//System.out.println(cd);
							// 得到上傳文件名稱
							// 因為\是跳脫字元,所以\要重複一次
							//useravater = cd.substring(cd.lastIndexOf("\\") + 1, cd.length() - 1);
							System.out.println(useravater);

							try (InputStream in = photo.getInputStream();
									OutputStream out = new FileOutputStream(
											path+"images/"
													+ useravater)) {
								byte[] buffer = new byte[1024];
								int length = -1;
								while ((length = in.read(buffer)) != -1) {
									out.write(buffer, 0, length);
								}
							}
						}
					}
				}
				// 2. 進行必要的資料轉換
				// (無)
				// 3. 檢查使用者輸入資料
				if (usercellphone == null || usercellphone.trim().length() == 0) {
					errorMsg.put("errorphone", "電話號碼欄必須輸入");
				}
				if (userpassword == null || userpassword.trim().length() == 0) {
					errorMsg.put("errorPasswordEmpty", "密碼欄必須輸入");
				}
				if (username == null || username.trim().length() == 0) {
					errorMsg.put("errorName", "姓名欄必須輸入");
				}
				if (useremail == null || useremail.trim().length() == 0) {
					errorMsg.put("errorEmail1", "電子郵件欄必須輸入");
				}
				//檢查是否有驗證信箱
				Cookie[] cookies = request.getCookies();
				String cookieName = "";
				String verifymail="false";
				if (cookies != null) {
					for (int i = 0; i < cookies.length; i++) {
						cookieName = cookies[i].getName();
						if (cookieName.equals("verifynumber")) {
							verifymail = cookies[i].getValue();
							System.out.println(verifymail);
						}
					}
				}
				if (verifymail.equals("false")) {
					errorMsg.put("verifymail", "請先驗證信箱");
				}
				if (!errorMsg.isEmpty()) {
					RequestDispatcher rd = request.getRequestDispatcher("register.jsp");
					rd.forward(request, response);
					return;
				}
				

				
				
				
				try {
					// 4. 產生MemberDao物件，以便進行Business Logic運算
					// MemberDaoImpl_Jdbc類別的功能：
					// 1.檢查帳號是否已經存在，已存在的帳號不能使用，回傳相關訊息通知使用者修改
					// 2.若無問題，儲存會員的資料
					MemberDao dao = new MemberDao_Jdbc();
					if (dao.checkphone(usercellphone)) {
						errorMsg.put("errorIDDup", "此帳號已存在，請換新帳號");
					} else {
						Timestamp ts = new java.sql.Timestamp(System.currentTimeMillis());
						MemberBean mem = new MemberBean(null, usercellphone, userpassword, username, useremail,
								useravater, ts, "normal", "consumer", 1);
						int n = dao.saveUser(mem);
						if (n == 1) {
							msgOK.put("InsertOK", "<Font color='red'>新增成功，請開始使用本系統</Font>");
							response.sendRedirect("../index.jsp");
							return;
						} else {
							errorMsg.put("errorIDDup", "新增此筆資料有誤(RegisterServlet)");
						}
					}
					// 5.依照 Business Logic 運算結果來挑選適當的畫面
					if (!errorMsg.isEmpty()) {
						// 導向原來輸入資料的畫面，這次會顯示錯誤訊息
						RequestDispatcher rd = request.getRequestDispatcher("register.jsp");
						rd.forward(request, response);
						return;
					}
				} catch (Exception e) {
					e.printStackTrace();
					errorMsg.put("errorIDDup", e.getMessage());
					RequestDispatcher rd = request.getRequestDispatcher("register.jsp");
					rd.forward(request, response);
				}
			}
		}
	}

}
