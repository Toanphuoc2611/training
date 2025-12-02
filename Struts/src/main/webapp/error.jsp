<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
<html>
<head>
    <title>Lỗi ứng dụng</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            background-color: #f0f0f0;
        }
        .container {
            max-width: 600px;
            margin: 50px auto;
            background-color: white;
            padding: 40px;
            border-radius: 8px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.1);
        }
        h1 {
            color: #d32f2f;
        }
        .error-details {
            background-color: #ffebee;
            padding: 15px;
            border-left: 4px solid #d32f2f;
            margin: 20px 0;
            border-radius: 4px;
        }
        .error-details p {
            margin: 5px 0;
            font-size: 14px;
        }
        .back-link {
            margin-top: 20px;
        }
        .back-link a {
            color: #1976d2;
            text-decoration: none;
            font-weight: bold;
        }
        .back-link a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>⚠️ Có lỗi xảy ra</h1>
        
        <div class="error-details">
            <p><strong>Thông báo lỗi:</strong></p>
            <p><%= exception != null ? exception.getMessage() : "Lỗi không xác định" %></p>
            
            <% if (exception != null) { %>
                <p><strong>Loại lỗi:</strong></p>
                <p><%= exception.getClass().getName() %></p>
            <% } %>
        </div>
        
        <div class="back-link">
            <a href="<%= request.getContextPath() %>/index.jsp">← Quay lại trang chủ</a>
        </div>
    </div>
</body>
</html>
