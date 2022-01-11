package com.cal;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Calsi
 */
@WebServlet("/Calsi")
public class Calsi extends HttpServlet {
	private static final long serialVersionUID = 1L;

    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.setContentType("html");
		PrintWriter pr=null;
		try {
			
			pr=response.getWriter();
			pr.println("<center>");
			int a=Integer.parseInt(request.getParameter("num1"));
			int b=Integer.parseInt(request.getParameter("num2"));
			int c=0;
			String s=request.getParameter("btn");
			if(s.equals("+"))c=a+b;
			
			if(s.equals("-"))c=a-b;
			
			if(s.equals("*"))c=a*b;
			
			if(s.equals("/"))c=a/b;
			pr.println("<h3>"+a+s+b+"="+c+"<h3>");
			} catch (Exception e) {
			// TODO: handle exception
				pr.println("Error:"+e.getMessage());
		}
		finally {
			pr.println("<br>");
			pr.println("To Goto main Page<a href=cal.html>Click here</a>");
			pr.println("</center>");
			pr.close();
		}
	}

}
