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

@WebServlet("/Deposit")
public class Deposit extends HttpServlet 
{
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
	
		String accountNo = req.getParameter("ac");
		String deposit = req.getParameter("deposit");
		int deposit1 = Integer.parseInt(deposit);
		int ac = Integer.parseInt(accountNo);
		
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
				pst = con.prepareStatement("select balance from Account where accountNumber = ?");
				pst.setString(1, accountNo);
				rs = pst.executeQuery();
				
				while(rs.next())
				{	
					int currentBalance = rs.getInt("balance");
					int balance = deposit1 + currentBalance;
					
					pst = con.prepareStatement("update Account set balance = ? where accountNumber = ?");
					pst.setInt(1, balance);
					pst.setString(2, accountNo);
					int row = pst.executeUpdate();
				
					if(row > 0)
					{
						pst = con.prepareStatement("insert into Transaction (accountNumber, amount, transaction_date, transaction_type) values(?,?,now(),'Deposit')");
						pst.setInt(1, ac);
						pst.setInt(2, deposit1);
						pst.executeUpdate();
						
						resp.setContentType("text/html");

						out.print("<h4 style='color:white'>Money deposited successfully</h4>");
						
						RequestDispatcher rd = req.getRequestDispatcher("/Deposit.html");
						rd.include(req, resp);
					}
					
				}
				
			}
			else
			{
				resp.setContentType("text/html");

				out.print("<h4 style='color:white'>Account Number does not existed</h4>");
				
				RequestDispatcher rd = req.getRequestDispatcher("/Deposit.html");
				rd.include(req, resp);
				
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			resp.setContentType("text/html");
			
			out.print("<h4 style='color:red'>Exception occured :"+e.getMessage()+"</h4>");
			
			RequestDispatcher rd = req.getRequestDispatcher("/Deposit.html");
			rd.include(req, resp);
		}
	
	}
}
