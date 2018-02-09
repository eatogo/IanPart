package _01_register.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import _01_register.model.MemberBean;
import _01_register.model.MemberDao;
import _01_register.model.MemberDao_Jdbc;

@WebServlet("/RegisterServletForPhone")
public class RegisterServletForPhone extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final static String CONTENT_TYPE = "text/html; charset=UTF-8";
   @Override
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
	String errorMsg = new String();
	String userpassword=jsonObject.get("userpassword").getAsString();
	String usercellphone =jsonObject.get("usercellphone").getAsString();
	String username=jsonObject.get("username").getAsString();
	String useremail1=jsonObject.get("useremail").getAsString();
	String useravater = "";
	boolean isUserSaved = false;
	if(usercellphone==null||usercellphone.trim().length()==0) {
		errorMsg=errorMsg+"電話號碼欄";
	}if (userpassword == null || userpassword.trim().length() == 0) {
		errorMsg=errorMsg+"密碼欄";
	}if (username == null || username.trim().length() == 0) {
			errorMsg=errorMsg+"姓名欄";	
	}if (useremail1 == null || useremail1.trim().length() == 0) {
		errorMsg=errorMsg+"電子郵件欄";
	}if (!errorMsg.isEmpty()) {
		isUserSaved=false;
		errorMsg=errorMsg+"必須輸入";
		jsonObject = new JsonObject();
		jsonObject.addProperty("isUserSaved", isUserSaved);
		jsonObject.addProperty("errorMsg",errorMsg);
		rp.setContentType(CONTENT_TYPE);
		PrintWriter out = rp.getWriter();
		out.println(jsonObject.toString());
		System.out.println("output: " + jsonObject.toString());
	}
	
	
   try {
		// 4. 產生MemberDao物件，以便進行Business Logic運算
		// MemberDaoImpl_Jdbc類別的功能：
		// 1.檢查帳號是否已經存在，已存在的帳號不能使用，回傳相關訊息通知使用者修改
		// 2.若無問題，儲存會員的資料
		MemberDao dao= new MemberDao_Jdbc();
		if(dao.checkphone(usercellphone)) {
			errorMsg=errorMsg+"此帳號已存在，請換新帳號";
		}else{
			Timestamp ts=new java.sql.Timestamp(System.currentTimeMillis());
			MemberBean mem=new MemberBean(null, usercellphone, userpassword, username, useremail1, useravater, ts, "normal", "consumer",1);
		int n=dao.saveUser(mem);
		if(n==1) {
			isUserSaved=true;
			jsonObject = new JsonObject();
			jsonObject.addProperty("isUserSaved", isUserSaved);
			jsonObject.addProperty("errorMsg",errorMsg);
			rp.setContentType(CONTENT_TYPE);
			PrintWriter out = rp.getWriter();
			out.println(jsonObject.toString());
			System.out.println("output: " + jsonObject.toString());
		}else {
			errorMsg=errorMsg+"新增此筆資料有誤(RegisterServlet)";
		}
		}
		// 5.依照 Business Logic 運算結果來挑選適當的畫面
		if (!errorMsg.isEmpty()) {
		// 導向原來輸入資料的畫面，這次會顯示錯誤訊息
			isUserSaved=false;
			jsonObject = new JsonObject();
			jsonObject.addProperty("isUserSaved", isUserSaved);
			jsonObject.addProperty("errorMsg",errorMsg);
			rp.setContentType(CONTENT_TYPE);
			PrintWriter out = rp.getWriter();
			out.println(jsonObject.toString());
			System.out.println("output: " + jsonObject.toString());
		}		
	}catch(Exception e) {
		e.printStackTrace();
		errorMsg= errorMsg+e.getMessage();
		isUserSaved=false;
		jsonObject = new JsonObject();
		jsonObject.addProperty("isUserSaved", isUserSaved);
		jsonObject.addProperty("errorMsg",errorMsg);
		rp.setContentType(CONTENT_TYPE);
		PrintWriter out = rp.getWriter();
		out.println(jsonObject.toString());
		System.out.println("output: " + jsonObject.toString());
		
	}
}
}

