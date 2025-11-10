<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>
    <title>Welcome</title>
</head>
<body>
<h2>Xin chào, <%= session.getAttribute("user") %>!</h2>
<p>Bạn đã đăng nhập thành công.</p>
<a href="login.jsp">Đăng xuất</a>
</body>
</html>
