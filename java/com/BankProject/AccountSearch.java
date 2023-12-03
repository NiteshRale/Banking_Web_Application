package com.BankProject;

import java.util.*;
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
import javax.servlet.http.HttpSession;

@WebServlet("/AccountSearch")
public class AccountSearch extends HttpServlet
{
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
	
		String accountNo = req.getParameter("ac");
	
		Connection con ;
		PreparedStatement pst;
		
		try 
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/practice","root","Nitesh@123");
			
			pst =  con.prepareStatement("select * from Account where accountNumber = ?");
			
			pst.setString(1, accountNo);
			
			ResultSet rs = pst.executeQuery();
			
			boolean b = rs.next();
			
			
			if(b == true)
			{
				String name = rs.getString("name");
				String add = rs.getString("address");
				int ac = rs.getInt("accountNumber");
				String mob = rs.getString("mobileNo");
				String pass = rs.getString("Password");	
				int bal = rs.getInt("balance");
				
				HttpSession session = req.getSession();
				
				List account = new ArrayList<>();
				
				account.add(name);
				account.add(add);
				account.add(ac);
				account.add(mob);
				account.add(pass);
				account.add(bal);
				
				session.setAttribute("acList", account);
				
				resp.sendRedirect("SearchDetail.jsp");
				
			}
			else
			{
				resp.setContentType("text/html");

				out.print("<h4 style='color:white'>Account Number does not existed</h4>");
				
				RequestDispatcher rd = req.getRequestDispatcher("/AccountSearch.html");
				rd.include(req, resp);
				
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			resp.setContentType("text/html");
			
			out.print("<h4 style='color:red'> Exception occured :"+e.getMessage()+"</h4>");
			
			RequestDispatcher rd = req.getRequestDispatcher("/AccountSearch.html");
			rd.include(req, resp);
		}
	
	}
}
