package _01_register.controller;

import java.io.BufferedReader;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * Servlet implementation class CheckIdforphone
 */
@WebServlet("/CheckIdforphone")
public class CheckIdforphone extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		doPost(request,response);
	}

	
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
		String usercellphone =jsonObject.get("usercellphone").getAsString();
	}

}
