package com.BankProject;

import java.util.List;
import java.util.ArrayList;
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

//***********************************Controller***********************************

@WebServlet("/transaction")
public class TransactionList extends HttpServlet
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
			
			pst =  con.prepareStatement("select * from transaction where accountNumber = ?");
			
			pst.setString(1, accountNo);
			
			ResultSet rs = pst.executeQuery();
			
			boolean b = rs.next();
			
			List<Transaction> transactions = new ArrayList<>();
		
			if(b)
			{
				while(rs.next())
				{
					Transaction transaction = new Transaction(rs.getInt("transaction_id"),
															  rs.getInt("accountNumber"),
															  rs.getString("transaction_date"),
															  rs.getInt("amount"),
															  rs.getString("transaction_type"));
					
	//				int transaction_id = rs.getInt("transaction_id");
	//				int accountNumber = rs.getInt("accountNumber");
	//				String transaction_date = rs.getString("transaction_date");
	//				int amount = rs.getInt("amount");
	//				String transaction_type = rs.getString("transaction_type");
					
					transactions.add(transaction);
				}
				
				HttpSession session = req.getSession();
				
				session.setAttribute("tList", transactions);
				
				resp.sendRedirect("TransactionDetail.jsp");
				
			}
			else
			{
				resp.setContentType("text/html");
	
				out.print("<h4 style='color:white'>Account Number does not existed</h4>");
				
				RequestDispatcher rd = req.getRequestDispatcher("/TransactionHistory.html");
				rd.include(req, resp);
				
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			resp.setContentType("text/html");
			
			out.print("<h4 style='color:red'> Exception occured :"+e.getMessage()+"</h4>");
			
			RequestDispatcher rd = req.getRequestDispatcher("/TransactionHistory.html");
			rd.include(req, resp);
		}
	}
}
