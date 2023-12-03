package com.BankProject;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet
{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		
		String uname = req.getParameter("username");
		String pass = req.getParameter("password");
		
		Connection con ;
		PreparedStatement pst;
		
		try 
		{
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/practice","root","Nitesh@123");
			pst=con.prepareStatement("select * from BankUser where username=? and password=?");
			pst.setString(1, uname);
			pst.setString(2, pass);
			 
			ResultSet resultSet = pst.executeQuery();
			
			boolean b = resultSet.next();
			
			if(b)
			{
	            resp.sendRedirect("MainMenu.html");
			}
			else
			{
				out.print("<h4 style='color:red'>Username and Password didn't Matched<h4>");
				
				RequestDispatcher rd = req.getRequestDispatcher("BankLogin.html");
				rd.include(req, resp);
				
			}
		
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}
