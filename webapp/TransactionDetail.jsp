<%@page import="java.util.Iterator"%>
<%@page import="java.util.List, com.BankProject.Transaction"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Transaction Details</title>
<style>
    body {
        font-family: 'Arial', sans-serif;
        /* background-color: LightSteelBlue; */
        padding: 20px;
        /* color: DimGray; */
    }

    table {
        width: 100%;
        margin-top: 20px;
        border-collapse: collapse;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    }

    th, td {
        border: 1px solid LightGray;
        padding: 15px;
        text-align: left;
    }

    th {
        background-color: DodgerBlue;
        color: White;
    }

    tr:nth-child(even) {
        background-color: LightSteelBlue;
    }

    caption {
        caption-side: top;
        font-size: 24px;
        font-weight: bold;
        margin-bottom: 20px;
    }

    h2 {
        color: DodgerBlue;
        margin-bottom: 20px;
    }
</style>
</head>
<body>

<div>
<h2>Transaction Details</h2>

<%
    List<Transaction> TransList = (List)session.getAttribute("tList");
%>

<table>
    <thead>
        <tr>
            <th>Transaction Id</th>
            <th>Account Number</th>
            <th>Transaction Date</th>
            <th>Amount</th>
            <th>Transaction Type</th>
        </tr>
    </thead>

    <%
        Iterator<Transaction> it = TransList.iterator();
        while(it.hasNext()) 
        {
            Transaction transaction = it.next();
    %>
   
    <tr>
        <td><%= transaction.getTransaction_id() %></td>
        <td><%= transaction.getAccountNumber() %></td>
        <td><%= transaction.getTransaction_date() %></td>
        <td><%= transaction.getAmount() %></td>
        <td><%= transaction.getTransaction_type() %></td>
    </tr>
    
    <%
        }
    %>

</table>
</div>

</body>
</html>
 