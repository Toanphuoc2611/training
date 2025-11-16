<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<h2>Trang đăng nhập</h2>
<html:errors />

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
