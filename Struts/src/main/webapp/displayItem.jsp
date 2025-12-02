<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html>
<head>
    <title>Danh sách sản phẩm</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
        }
        .container {
            max-width: 1200px;
            margin: 0 auto;
        }
        .header-section {
            margin-bottom: 20px;
        }
        .product-table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
        }
        .product-table th, .product-table td {
            padding: 12px;
            text-align: left;
            border: 1px solid #ddd;
        }
        .product-table th {
            background-color: #4CAF50;
            color: white;
            font-weight: bold;
        }
        .product-table tr:hover {
            background-color: #f5f5f5;
        }
        .product-table tr:nth-child(even) {
            background-color: #f9f9f9;
        }
        .action-buttons {
            margin-bottom: 20px;
        }
        .action-buttons a {
            display: inline-block;
            padding: 10px 20px;
            margin-right: 10px;
            background-color: #4CAF50;
            color: white;
            text-decoration: none;
            border-radius: 4px;
            cursor: pointer;
        }
        .action-buttons a:hover {
            background-color: #45a049;
        }
        .action-buttons .config-btn {
            background-color: #2196F3;
        }
        .action-buttons .config-btn:hover {
            background-color: #0b7dda;
        }
        .no-data {
            padding: 20px;
            text-align: center;
            color: #666;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header-section">
            <h1>Danh sách sản phẩm</h1>
            <div class="action-buttons">
                <a href="<%= request.getContextPath() %>/column.do?action=init" class="config-btn">Cấu hình cột hiển thị</a>
            </div>
        </div>
        
        <!-- Header columns: use displayColumnConfig (session) -> columnConfig (session) -> default -->
        
        <!-- Show only the list of selected column names (no products) -->
        <div class="selected-columns">
            <h3>Các cột được chọn để hiển thị:</h3>
            <ul>
                <logic:present name="displayColumnConfig" scope="session">
                    <logic:iterate name="displayColumnConfig" property="selectedColumns" id="col" scope="session">
                        <li><bean:write name="col"/></li>
                    </logic:iterate>
                </logic:present>

                <logic:notPresent name="displayColumnConfig" scope="session">
                    <logic:present name="columnConfig" scope="session">
                        <logic:iterate name="columnConfig" property="selectedColumns" id="col" scope="session">
                            <li><bean:write name="col"/></li>
                        </logic:iterate>
                    </logic:present>

                    <logic:notPresent name="columnConfig" scope="session">
                        <li>Product Id</li>
                        <li>CheckBox</li>
                        <li>Product Name</li>
                        <li>Price</li>
                        <li>Quantity</li>
                        <li>Sold</li>
                        <li>Image</li>
                    </logic:notPresent>
                </logic:notPresent>
            </ul>
        </div>
    </div>
</body>
</html>
