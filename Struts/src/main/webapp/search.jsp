<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Tìm kiếm khách hàng</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            background-color: #f5f5f5;
        }
        .container {
            max-width: 1200px;
            margin: 0 auto;
            background-color: white;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
        }
        .header-section {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 20px;
        }
        .header-actions {
            display: flex;
            gap: 10px;
        }
        h2 {
            color: #333;
            border-bottom: 2px solid #007bff;
            padding-bottom: 10px;
            margin: 0;
        }
        .btn-add {
            padding: 10px 20px;
            background-color: #28a745;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 14px;
            font-weight: bold;
            text-decoration: none;
            display: inline-block;
            transition: background-color 0.3s;
        }
        .btn-add:hover {
            background-color: #218838;
        }
        .btn-delete {
            padding: 10px 20px;
            background-color: #dc3545;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 14px;
            font-weight: bold;
            transition: background-color 0.3s;
        }
        .btn-delete:hover {
            background-color: #c82333;
        }
        .btn-delete:disabled {
            background-color: #ccc;
            cursor: not-allowed;
        }
        .search-form {
            background-color: #f8f9fa;
            padding: 20px;
            border-radius: 5px;
            margin-bottom: 20px;
        }
        .form-row {
            display: flex;
            gap: 15px;
            margin-bottom: 15px;
            flex-wrap: wrap;
        }
        .form-group {
            flex: 1;
            min-width: 200px;
        }
        .form-group label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
            color: #555;
        }
        .form-group input,
        .form-group select {
            width: 100%;
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
        }
        .btn {
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 14px;
            transition: background-color 0.3s;
        }
        .btn-primary {
            background-color: #007bff;
            color: white;
        }
        .btn-primary:hover {
            background-color: #0056b3;
        }
        .pagination {
            display: flex;
            gap: 10px;
            margin-bottom: 15px;
            align-items: center;
        }
        .pagination button {
            padding: 8px 15px;
            background-color: #6c757d;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        .pagination button:hover {
            background-color: #545b62;
        }
        .pagination button:disabled {
            background-color: #ccc;
            cursor: not-allowed;
        }
        .pagination-info {
            margin-left: auto;
            color: #666;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 10px;
        }
        table th {
            background-color: #007bff;
            color: white;
            padding: 12px;
            text-align: left;
            font-weight: bold;
        }
        table td {
            padding: 10px;
            border-bottom: 1px solid #ddd;
        }
        table tr:hover {
            background-color: #f5f5f5;
        }
        table tr:nth-child(even) {
            background-color: #f9f9f9;
        }
        .no-data {
            text-align: center;
            padding: 20px;
            color: #999;
        }
        .customer-id {
            color: #007bff;
            cursor: pointer;
            text-decoration: underline;
        }
        .customer-id:hover {
            color: #0056b3;
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
        .alert-warning {
            background-color: #fff3cd;
            border: 1px solid #ffeaa7;
            color: #856404;
        }
    </style>
</head>
<body>
<div class="container">
    <!-- Display success message -->
    <logic:messagesPresent message="true">
        <div class="alert alert-success">
            <html:messages id="msg" message="true">
                <bean:write name="msg"/>
            </html:messages>
        </div>
    </logic:messagesPresent>

    <!-- Display error messages -->
    <logic:messagesPresent>
        <div class="alert alert-danger">
            <html:messages id="error">
                <bean:write name="error"/>
            </html:messages>
        </div>
    </logic:messagesPresent>

    <div class="header-section">
        <h2>Tìm kiếm khách hàng</h2>
        <div class="header-actions">
            <button type="button" class="btn-delete" id="deleteBtn" onclick="deleteSelected()" disabled>
                Xóa
            </button>
            <a href="edit.do?action=add" class="btn-add">+ Thêm mới</a>
        </div>
    </div>

    <!-- Search Form -->
    <div class="search-form">
        <html:form action="/search" method="post">
            <div class="form-row">
                <div class="form-group">
                    <label for="customerName">Tên khách hàng:</label>
                    <html:text property="customerName" styleId="customerName"/>
                </div>

                <div class="form-group">
                    <label for="gender">Giới tính:</label>
                    <html:select property="gender" styleId="gender">
                        <html:option value="">-- Tất cả --</html:option>
                        <html:option value="male">Nam</html:option>
                        <html:option value="female">Nữ</html:option>
                    </html:select>
                </div>

                <div class="form-group">
                    <label for="birthDateFrom">Ngày sinh từ:</label>
                    <html:text property="birthDateFrom" styleId="birthDateFrom"
                               styleClass="date-input"/>
                </div>

                <div class="form-group">
                    <label for="birthDateTo">Ngày sinh đến:</label>
                    <html:text property="birthDateTo" styleId="birthDateTo"
                               styleClass="date-input"/>
                </div>
            </div>

            <div class="form-row">
                <html:submit styleClass="btn btn-primary" value="Tìm kiếm"/>
            </div>

            <html:hidden property="currentPage"/>
        </html:form>
    </div>

    <!-- Pagination Controls -->
    <div class="pagination">
        <html:form action="/search" method="post" styleId="paginationForm">
            <html:hidden property="customerName"/>
            <html:hidden property="gender"/>
            <html:hidden property="birthDateFrom"/>
            <html:hidden property="birthDateTo"/>
            <html:hidden property="currentPage"/>

            <button type="button" onclick="goToPage('first')"
                    <%= request.getAttribute("currentPage") != null &&
                            ((Integer)request.getAttribute("currentPage")) == 1 ?
                            "disabled" : "" %>>
                Đầu
            </button>
Gia
            <button type="button" onclick="goToPage('previous')"
                    <%= request.getAttribute("currentPage") != null &&
                            ((Integer)request.getAttribute("currentPage")) == 1 ?
                            "disabled" : "" %>>
                Trước
            </button>

            <button type="button" onclick="goToPage('next')"
                    <%= request.getAttribute("currentPage") != null &&
                            request.getAttribute("totalPages") != null &&
                            ((Integer)request.getAttribute("currentPage")).intValue() >=
                                    ((Integer)request.getAttribute("totalPages")).intValue() ?
                            "disabled" : "" %>>
                Sau
            </button>

            <button type="button" onclick="goToPage('last')"
                    <%= request.getAttribute("currentPage") != null &&
                            request.getAttribute("totalPages") != null &&
                            ((Integer)request.getAttribute("currentPage")).intValue() >=
                                    ((Integer)request.getAttribute("totalPages")).intValue() ?
                            "disabled" : "" %>>
                Cuối
            </button>
        </html:form>

        <div class="pagination-info">
            <logic:present name="totalRecords">
                Trang <bean:write name="currentPage"/> /
                <bean:write name="totalPages"/>
                (Tổng: <bean:write name="totalRecords"/> bản ghi)
            </logic:present>
        </div>
    </div>

    <!-- Customer List Table -->
    <form id="deleteForm" method="post" action="delete.do">
        <table>
            <thead>
            <tr>
                <th style="width: 50px;">
                    <input type="checkbox" id="selectAll" onclick="toggleSelectAll()"/>
                </th>
                <th style="width: 80px;">ID</th>
                <th>Tên khách hàng</th>
                <th style="width: 150px;">Ngày sinh</th>
                <th style="width: 100px;">Giới tính</th>
                <th>Địa chỉ</th>
            </tr>
            </thead>
            <tbody>
            <logic:present name="customerList">
                <logic:notEmpty name="customerList">
                    <logic:iterate id="customer" name="customerList">
                        <tr>
                            <td>
                                <input type="checkbox" name="selectedIds" class="customer-checkbox"
                                       value="<bean:write name='customer' property='id'/>"
                                       onchange="updateDeleteButton()"/>
                            </td>
                            <td>
                                <a href="edit.do?action=edit&id=<bean:write name='customer' property='id'/>"
                                   class="customer-id">
                                    <bean:write name="customer" property="id"/>
                                </a>
                            </td>
                            <td><bean:write name="customer" property="customerName"/></td>
                            <td>
                                <bean:write name="customer" property="birthDate"
                                            format="dd/MM/yyyy"/>
                            </td>
                            <td>
                                <logic:equal name="customer" property="gender" value="male">
                                    Nam
                                </logic:equal>
                                <logic:equal name="customer" property="gender" value="female">
                                    Nữ
                                </logic:equal>
                            </td>
                            <td><bean:write name="customer" property="address"/></td>
                        </tr>
                    </logic:iterate>
                </logic:notEmpty>
                <logic:empty name="customerList">
                    <tr>
                        <td colspan="6" class="no-data">
                            Không tìm thấy kết quả phù hợp
                        </td>
                    </tr>
                </logic:empty>
            </logic:present>
            <logic:notPresent name="customerList">
                <tr>
                    <td colspan="6" class="no-data">
                        Vui lòng nhập điều kiện tìm kiếm
                    </td>
                </tr>
            </logic:notPresent>
            </tbody>
        </table>
    </form>
</div>

<script>
    // Pagination functions
    function goToPage(action) {
        var form = document.getElementById('paginationForm');
        var currentPageInput = form.elements['currentPage'];
        var totalPages = <%= request.getAttribute("totalPages") != null ?
                                request.getAttribute("totalPages") : 1 %>;
        var currentPage = parseInt(currentPageInput.value) || 1;

        if (action === 'first') {
            currentPageInput.value = 1;
        } else if (action === 'previous') {
            currentPageInput.value = Math.max(1, currentPage - 1);
        } else if (action === 'next') {
            currentPageInput.value = Math.min(totalPages, currentPage + 1);
        } else if (action === 'last') {
            currentPageInput.value = totalPages;
        }

        form.submit();
    }

    // Select all checkboxes
    function toggleSelectAll() {
        var selectAll = document.getElementById('selectAll');
        var checkboxes = document.getElementsByClassName('customer-checkbox');

        for (var i = 0; i < checkboxes.length; i++) {
            checkboxes[i].checked = selectAll.checked;
        }

        updateDeleteButton();
    }

    // Update delete button state
    function updateDeleteButton() {
        var checkboxes = document.getElementsByClassName('customer-checkbox');
        var deleteBtn = document.getElementById('deleteBtn');
        var selectAll = document.getElementById('selectAll');
        var hasChecked = false;
        var allChecked = true;

        for (var i = 0; i < checkboxes.length; i++) {
            if (checkboxes[i].checked) {
                hasChecked = true;
            } else {
                allChecked = false;
            }
        }

        // Enable/disable delete button
        deleteBtn.disabled = !hasChecked;

        // Update select all checkbox
        if (checkboxes.length > 0) {
            selectAll.checked = allChecked;
        }
    }

    // Delete selected customers
    function deleteSelected() {
        var checkboxes = document.getElementsByClassName('customer-checkbox');
        var selectedCount = 0;

        for (var i = 0; i < checkboxes.length; i++) {
            if (checkboxes[i].checked) {
                selectedCount++;
            }
        }

        if (selectedCount === 0) {
            alert('Vui lòng chọn ít nhất một khách hàng để xóa!');
            return;
        }

        var confirmMsg = 'Bạn có chắc chắn muốn xóa ' + selectedCount + ' khách hàng đã chọn?';

        if (confirm(confirmMsg)) {
            document.getElementById('deleteForm').submit();
        }
    }

    // Set date input type
    window.onload = function() {
        var dateInputs = document.getElementsByClassName('date-input');
        for (var i = 0; i < dateInputs.length; i++) {
            dateInputs[i].type = 'date';
        }
    };
</script>
</body>
</html>