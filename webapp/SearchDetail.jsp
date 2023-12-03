<%@ page import="java.util.List" contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Account Details</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            margin: 0;
            padding: 0;
            background-image: url('main3.jpg'); 
            background-size: cover;
            background-position: center;
            color: #333; 
        }

        h2 {
            text-align: center;
            color: #fff;
            padding: 20px 0;
        }

        table {
            width: 60%;
            margin: 20px auto;
            border-collapse: collapse;
            box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);
            background-color: #F7DC6F; /* Light blue background color for the table */
            border-radius: 8px; /* Rounded corners for the table */
            overflow: hidden; /* Hide overflowing content within the table */
        }

        th, td {
            padding: 15px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }

        th {
            background-color: #A9DFBF;
            color: white;
        }

        tr:hover {
            background-color: #d9edf7; 
        }
    </style>
</head>
<body>

    <h2>Account Details</h2>

    <table>
        <tr>
            <th>Name</th>
            <th>Address</th>
            <th>Account Number</th>
            <th>Mobile Number</th>
            <th>Password</th>
            <th>Balance</th>
        </tr>
        
        <%
            List account = (List) session.getAttribute("acList");
            if (account != null) {
        %>
                <tr>
                    <td><%= account.get(0) %></td>
                    <td><%= account.get(1) %></td>
                    <td><%= account.get(2) %></td>
                    <td><%= account.get(3) %></td>
                    <td><%= account.get(4) %></td>
                    <td><%= account.get(5) %></td>
                </tr>
        <%
            }
        %>
    </table>

</body>
</html>
