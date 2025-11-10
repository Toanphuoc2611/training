<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>

<html>
<head>
    <title>Đăng nhập hệ thống</title>
    <style>
        body { font-family: Arial; margin: 50px; }
        .error { color: red; margin-bottom: 10px; }
        table { border-spacing: 5px; }
    </style>
</head>
<body>
<h2>Trang đăng nhập</h2>

<%-- Hiển thị thông báo lỗi nếu có --%>
<%
    String error = (String) request.getAttribute("error");
    if (error != null) {
%>
<div class="error"><%= error %></div>
<% } %>

<html:form action="/login" method="post">
    <table>
        <tr>
            <td>Tên đăng nhập:</td>
            <td><html:text property="username" /></td>
        </tr>
        <tr>
            <td>Mật khẩu:</td>
            <td><html:password property="password" /></td>
        </tr>
        <tr>
            <td colspan="2" align="center">
                <html:submit value="Đăng nhập" />
            </td>
        </tr>
    </table>
</html:form>
</body>
</html>
