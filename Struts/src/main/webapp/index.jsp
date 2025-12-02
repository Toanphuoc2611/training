<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>

<html>
<head>
    <title>Hệ thống quản lý sản phẩm</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
            background-color: #f0f0f0;
        }
        .container {
            max-width: 600px;
            margin: 50px auto;
            background-color: white;
            padding: 40px;
            border-radius: 8px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.1);
            text-align: center;
        }
        h1 {
            color: #333;
            margin-bottom: 30px;
        }
        .menu-section {
            margin: 20px 0;
        }
        .menu-section a {
            display: inline-block;
            padding: 15px 40px;
            margin: 10px;
            background-color: #4CAF50;
            color: white;
            text-decoration: none;
            border-radius: 4px;
            font-size: 16px;
            cursor: pointer;
            transition: background-color 0.3s;
        }
        .menu-section a:hover {
            background-color: #45a049;
        }
        .menu-section a.config {
            background-color: #2196F3;
        }
        .menu-section a.config:hover {
            background-color: #0b7dda;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Hệ thống quản lý sản phẩm</h1>
        
        <div class="menu-section">
            <a href="<%= request.getContextPath() %>/search.do">Tìm kiếm sản phẩm</a>
        </div>
        
        <div class="menu-section">
            <a href="<%= request.getContextPath() %>/column.do?action=init" class="config">Cấu hình cột hiển thị</a>
        </div>
        
        <div class="menu-section">
            <a href="<%= request.getContextPath() %>/displayItem.jsp">Xem danh sách sản phẩm</a>
        </div>
    </div>
</body>
</html>
