package _02_login.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import _00_init.util.GlobalService;
import _01_register.model.MemberBean;
import _02_login.model.LoginService;
import _02_login.model.LoginServiceImp;

@WebServlet("/LoginServletForPhone")
public class LoginServletForPhone extends HttpServlet {
	private final static String CONTENT_TYPE = "text/html; charset=UTF-8";
	private static final long serialVersionUID = 1L;	
	protected void doPost(HttpServletRequest rq, HttpServletResponse rp) throws ServletException, IOException {
		rq.setCharacterEncoding("UTF-8");
		Gson gson=new Gson();
		BufferedReader br=rq.getReader();
		StringBuffer jsonIn=new StringBuffer();
		String line=null;
		while((line=br.readLine())!=null) {
			jsonIn.append(line);
		}
		System.out.println("input: "+jsonIn);
		JsonObject jsonObject=gson.fromJson(jsonIn.toString(), JsonObject.class);
		String usercellphone=jsonObject.get("id").getAsString();
		String userpassword=jsonObject.get("password").getAsString();
		boolean isUserValid = false;
		String errorMsg="";
		if(usercellphone==null || usercellphone.trim().length()==0) {
			errorMsg= errorMsg+"帳號欄必須填入";
		}
		if(userpassword==null || userpassword.trim().length()==0) {
			errorMsg= errorMsg+ "密碼欄必須填入";
		}
		if(!errorMsg.isEmpty()) {
			jsonObject = new JsonObject();
			jsonObject.addProperty("isUserValid", isUserValid);
			jsonObject.addProperty("errorMsg", errorMsg);
			rp.setContentType(CONTENT_TYPE);
			PrintWriter out = rp.getWriter();
			out.println(jsonObject.toString());
			System.out.println("output: " + jsonObject.toString());
		}
		LoginService login= new LoginServiceImp();
		userpassword=GlobalService.getMD5Endocing(GlobalService.encryptString(userpassword));
		MemberBean mb= login.checkIDPassword(usercellphone,userpassword);
		String useridentity=null;
		String userstatus=null;
		System.out.println(mb);
		if(mb !=null) {
			
			useridentity=mb.getUseridentity();
		userstatus=mb.getUserstatus();
			
			if(useridentity.endsWith("blacklisted")) {
				errorMsg= errorMsg+ "該帳號目前為黑名單,禁止登入";
			}else if(useridentity.endsWith("unregistered")) {
				errorMsg= errorMsg+ "尚未完成驗證,請繼續完成註冊";
			}else if(useridentity.endsWith("warning")) {
				errorMsg= errorMsg+ "請注意第二次警告將會停權";				
			}
		}else {
			errorMsg= errorMsg+"該帳號不存在或密碼錯誤";
		}
		if(errorMsg.isEmpty()) {
			isUserValid = true;
		}
		jsonObject = new JsonObject();
		jsonObject.addProperty("isUserValid", isUserValid);
		rp.setContentType(CONTENT_TYPE);
		PrintWriter out = rp.getWriter();
		out.println(jsonObject.toString());
		System.out.println("output: " + jsonObject.toString());
	}

}
