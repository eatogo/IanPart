package _01_register.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SendingverifynumberServlet")
public class SendingverifynumberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
     

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String a=new String();
		String b=new String();
		a="123";
		b="234";
		String c=a+b;
		System.out.println(c);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
