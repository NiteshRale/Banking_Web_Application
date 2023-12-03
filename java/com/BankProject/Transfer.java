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

@WebServlet("/Transfer")
public class Transfer extends HttpServlet
{
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
	
		String accountNo1 = req.getParameter("ac1");
		String deposit = req.getParameter("Transf");
		int amount = Integer.parseInt(deposit);
		String accountNo2 = req.getParameter("ac2");
		
		Connection con ;
		PreparedStatement pst;
		
		int i=0;
		boolean b=true;
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/practice","root","Nitesh@123");
			
			pst = con.prepareStatement("select balance from Account where accountNumber = ?");
			pst.setString(1, accountNo1);
			ResultSet rs = pst.executeQuery();
			
			// ...

			while (rs.next()) 
			{
			    int currentBalance = rs.getInt("balance");

			    if (currentBalance > amount) 
			    {
			        int balance = currentBalance - amount;
			        pst = con.prepareStatement("update Account set balance = ? where accountNumber = ?");
			        pst.setInt(1, balance);
			        pst.setString(2, accountNo1);
			        int row = pst.executeUpdate();

			        if (row > 0) 
			        {
			            pst = con.prepareStatement("select balance from Account where accountNumber = ?");
			            pst.setString(1, accountNo2);
			            rs = pst.executeQuery();

			            while (rs.next()) 
			            {
			                int currentBalance2 = rs.getInt("balance");
			                int balance2 = amount + currentBalance2;

			                pst = con.prepareStatement("update Account set balance = ? where accountNumber = ?");
			                pst.setInt(1, balance2);
			                pst.setString(2, accountNo2);
			                int row2 = pst.executeUpdate();

			                i = row2;
			                
			                //Entering details inside Transaction table
			               if(row>0 && row2>0)
			               {
			            	    pst = con.prepareStatement("insert into Transaction (accountNumber, amount, transaction_date, transaction_type) values(?,?,now(),'Withdrawal')");
				    			pst.setString(1, accountNo1);
				    			pst.setInt(2, amount);
				    			pst.executeUpdate();
			            	   
			            	    pst = con.prepareStatement("insert into Transaction (accountNumber, amount, transaction_date, transaction_type) values(?,?,now(),'Deposit')");
				    			pst.setString(1, accountNo2);
				    			pst.setInt(2, amount);
				    			pst.executeUpdate();
				    			
			               }
			            }
			        } 
			        else 
			        {
			            // Handle the case where the update for the first account fails
			            resp.setContentType("text/html");
			            out.print("<h4 style='color:white'>Transfer failed. Please try again.</h4>");
			            RequestDispatcher rd = req.getRequestDispatcher("/Transfer.html");
			            rd.include(req, resp);
			        }
			    } 
			    else 
			    {
			    	b=false;
			        // Handle the case of insufficient balance
			        resp.setContentType("text/html");
			        out.print("<h4 style='color:white'>Insufficient balance in Sender Account</h4>");
			        RequestDispatcher rd = req.getRequestDispatcher("/Transfer.html");
			        rd.include(req, resp);
			    }
			}
			
			if(i < 1)
			{
				pst = con.prepareStatement("select balance from Account where accountNumber = ?");
				pst.setString(1, accountNo1);
				rs = pst.executeQuery();
				
				while(rs.next())
				{	
					int currentBalance3 = rs.getInt("balance");
					if(currentBalance3>amount)
					{
						int balance3 = currentBalance3 + amount;
						pst = con.prepareStatement("update Account set balance = ? where accountNumber = ?");
						pst.setInt(1, balance3);
						pst.setString(2, accountNo1);
						pst.executeUpdate();	
					}
				}//while end
				
				if(b)
				{	
					resp.setContentType("text/html");
	
					out.print("<h4 style='color:white'>Enter Correct Account no.</h4>");
					
					RequestDispatcher rd = req.getRequestDispatcher("/Transfer.html");
					rd.include(req, resp);
				}
				
			}//if end
			
			if(i>0)
			{
				resp.setContentType("text/html");

				out.print("<h4 style='color:white'>Transfer Successful</h4>");
				
				RequestDispatcher rd = req.getRequestDispatcher("/Transfer.html");
				rd.include(req, resp);
			}
			
			
		}//try
		
		
		catch (Exception e) 
		{
			e.printStackTrace();
			resp.setContentType("text/html");
			
			out.print("<h4 style='color:white'>Exception occured :"+e.getMessage()+"</h4>");
			
			RequestDispatcher rd = req.getRequestDispatcher("/Transfer.html");
			rd.include(req, resp);
		}
	
	}
}
