<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><bean:write name="pageTitle"/></title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            background-color: #f5f5f5;
        }
        .container {
            max-width: 800px;
            margin: 0 auto;
            background-color: white;
            padding: 30px;
            border-radius: 5px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
        }
        h2 {
            color: #333;
            border-bottom: 2px solid #007bff;
            padding-bottom: 10px;
            margin-bottom: 30px;
        }
        .form-group {
            margin-bottom: 20px;
        }
        .form-group label {
            display: block;
            margin-bottom: 8px;
            font-weight: bold;
            color: #555;
        }
        .form-group label span.required {
            color: red;
        }
        .form-group input[type="text"],
        .form-group input[type="date"],
        .form-group select,
        .form-group textarea {
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
            font-size: 14px;
        }
        .form-group textarea {
            min-height: 100px;
            resize: vertical;
        }
        .form-group input:focus,
        .form-group select:focus,
        .form-group textarea:focus {
            outline: none;
            border-color: #007bff;
            box-shadow: 0 0 5px rgba(0,123,255,0.3);
        }
        .error {
            color: red;
            font-size: 13px;
            margin-top: 5px;
            display: block;
        }
        .form-group input.error-input,
        .form-group select.error-input,
        .form-group textarea.error-input {
            border-color: red;
        }
        .button-group {
            display: flex;
            gap: 15px;
            margin-top: 30px;
        }
        .btn {
            padding: 12px 30px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 14px;
            font-weight: bold;
            transition: all 0.3s;
        }
        .btn-primary {
            background-color: #007bff;
            color: white;
        }
        .btn-primary:hover {
            background-color: #0056b3;
        }
        .btn-secondary {
            background-color: #6c757d;
            color: white;
            text-decoration: none;
            display: inline-block;
            text-align: center;
        }
        .btn-secondary:hover {
            background-color: #545b62;
        }
        .alert {
            padding: 15px;
            margin-bottom: 20px;
            border-radius: 4px;
        }
        .alert-success {
            background-color: #d4edda;
            border: 1px solid #c3e6cb;
            color: #155724;
        }
        .alert-danger {
            background-color: #f8d7da;
            border: 1px solid #f5c6cb;
            color: #721c24;
        }
        .back-link {
            display: inline-block;
            margin-bottom: 20px;
            color: #007bff;
            text-decoration: none;
        }
        .back-link:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
<div class="container">
    <a href="search.do" class="back-link">← Quay lại danh sách</a>

    <h2><bean:write name="pageTitle"/></h2>

    <!-- Display error messages -->
    <logic:messagesPresent>
        <div class="alert alert-danger">
            <html:messages id="error">
                <bean:write name="error"/>
            </html:messages>
        </div>
    </logic:messagesPresent>

    <html:form action="/save" method="post">
        <!-- Hidden field for ID -->
        <html:hidden property="id"/>

        <!-- Customer Name -->
        <div class="form-group">
            <label for="customerName">
                Tên khách hàng <span class="required">*</span>
            </label>
            <html:text property="customerName" styleId="customerName"/>
            <html:errors property="customerName"/>
        </div>

        <!-- Birth Date -->
        <div class="form-group">
            <label for="birthDate">
                Ngày sinh <span class="required">*</span>
            </label>
            <html:text property="birthDate" styleId="birthDate"/>
            <html:errors property="birthDate"/>
        </div>

        <!-- Gender -->
        <div class="form-group">
            <label for="gender">
                Giới tính <span class="required">*</span>
            </label>
            <html:select property="gender" styleId="gender">
                <html:option value="">-- Chọn giới tính --</html:option>
                <html:option value="male">Nam</html:option>
                <html:option value="female">Nữ</html:option>
            </html:select>
            <html:errors property="gender"/>
        </div>

        <!-- Address -->
        <div class="form-group">
            <label for="address">
                Địa chỉ <span class="required">*</span>
            </label>
            <html:textarea property="address" styleId="address" rows="4"/>
            <html:errors property="address" />
        </div>

        <!-- Button Group -->
        <div class="button-group">
            <html:submit styleClass="btn btn-primary" value="Lưu"/>
            <a href="search.do" class="btn btn-secondary">Hủy</a>
        </div>
    </html:form>
</div>
</body>
</html>