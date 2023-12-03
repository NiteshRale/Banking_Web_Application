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

@WebServlet("/UpdateAccount")
public class UpdateAccount extends HttpServlet
{
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		
		String name = req.getParameter("name");
		String address = req.getParameter("addr");
		String accountNo = req.getParameter("ac");
		String mob = req.getParameter("mob");
		String pass = req.getParameter("pass");
		
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
				pst=con.prepareStatement("Update Account set name = ?, address = ?, mobileNo = ?, Password = ? where accountNumber = ?");
				pst.setString(1, name);
				pst.setString(2, address);
				pst.setString(3, mob);
				pst.setString(4, pass);
				pst.setString(5, accountNo);
				
				int count = pst.executeUpdate();
				
				if(count>0)
				{
					resp.setContentType("text/html");
					out.print("<h4 style='color:white'>Account updated Successfully</h4>");
					
					RequestDispatcher rd = req.getRequestDispatcher("/UpdateAccount.html");
					rd.include(req, resp);
				}
				else
				{
					resp.setContentType("text/html");
				
					out.print("<h4 style='color:white'>Failed to update account</h4>");
					
					RequestDispatcher rd = req.getRequestDispatcher("/UpdateAccount.html");
					rd.include(req, resp);
				}
			}
			else
			{
				resp.setContentType("text/html");

				out.print("<h4 style='color:white'>Account Number does not Existed</h4>");
				
				RequestDispatcher rd = req.getRequestDispatcher("/UpdateAccount.html");
				rd.include(req, resp);
				
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			resp.setContentType("text/html");
			
			out.print("<h4 style='color:white'>Exception occured :"+e.getMessage()+"</h4>");
			
			RequestDispatcher rd = req.getRequestDispatcher("/UpdateAccount.html");
			rd.include(req, resp);
		}
	
	}
}
