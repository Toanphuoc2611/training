<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html>
<head>
    <title>Cấu hình cột hiển thị</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
        }
        .container {
            max-width: 1000px;
            margin: 0 auto;
        }
        .main-content {
            display: flex;
            gap: 20px;
            margin-top: 20px;
            align-items: flex-start;
        }
        .column-section {
            flex: 1;
        }
        .column-title {
            font-weight: bold;
            margin-bottom: 10px;
            font-size: 14px;
        }
        .column-table {
            width: 100%;
            border: 1px solid #ccc;
            border-collapse: collapse;
        }
        .column-table th, .column-table td {
            padding: 8px;
            text-align: left;
            border: 1px solid #ccc;
        }
        .column-table th {
            background-color: #f0f0f0;
        }
        .column-table tr.row-clickable { cursor: pointer; }
        .column-table tr.row-selected { background-color: #d0eaff; }
        .buttons-center {
            display: flex;
            flex-direction: column;
            gap: 10px;
            justify-content: center;
            padding: 0 10px;
        }
        .btn {
            padding: 8px 12px;
            cursor: pointer;
            font-size: 18px;
            border: 1px solid #ccc;
            background-color: #f9f9f9;
            min-width: 50px;
            text-align: center;
        }
        .btn:hover {
            background-color: #e0e0e0;
        }
        .btn:disabled {
            background-color: #e0e0e0;
            cursor: not-allowed;
            opacity: 0.6;
        }
        .bottom-buttons {
            margin-top: 30px;
            text-align: center;
            gap: 10px;
        }
        .bottom-buttons input[type="button"] {
            padding: 10px 30px;
            margin-right: 10px;
            font-size: 14px;
            cursor: pointer;
        }
        .error-message {
            color: red;
            margin-bottom: 10px;
            padding: 10px;
            background-color: #ffe0e0;
            border: 1px solid red;
            border-radius: 4px;
            display: none;
        }
        .error-message.show {
            display: block;
        }
    </style>
    <script>
        function getSelectValue(name) {
            var select = document.getElementsByName(name)[0];
            return select ? select.value : '';
        }
        
        function validateAndSubmit(action) {
            if (action === 'moveRight') {
                if (!getSelectValue('selectedAvailable')) {
                    alert('Cần chọn Item để di chuyển sang phải');
                    return false;
                }
            } else if (action === 'moveLeft' || action === 'moveUp' || action === 'moveDown') {
                if (!getSelectValue('selectedDisplay')) {
                    alert('Cần chọn Item để di chuyển');
                    return false;
                }
            }
            document.forms['columnForm'].action = '<%= request.getContextPath() %>/column.do?action=' + action;
            document.forms['columnForm'].submit();
            return false;
        }
    </script>
</head>
<body>
<div class="container">
    <h1>Cấu hình cột hiển thị</h1>

    <logic:present name="error">
        <div class="error-message show"><bean:write name="error"/></div>
    </logic:present>

    <html:form action="column.do" method="POST">
        <!-- No client-side selection required; actions are links that pass params to server -->

        <div class="main-content">
            <!-- Bảng trái: Cột không hiển thị -->
            <div class="column-section">
                <div class="column-title">Cột không được hiển thị</div>
                <table class="column-table" id="availableTable">
                    <thead>
                    <tr>
                        <th>Tên cột</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>
                            <select name="selectedAvailable" size="10" style="width:100%;">
                                <logic:iterate name="columnForm" property="availableColumns" id="col">
                                    <option value="<bean:write name='col'/>"><bean:write name="col"/></option>
                                </logic:iterate>
                            </select>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>

            <!-- Nút di chuyển ở giữa (server-side submits) -->
            <div class="buttons-center">
                <input type="button" class="btn" onclick="validateAndSubmit('moveRight')" title="Di chuyển sang phải" value="→" />
                <input type="button" class="btn" onclick="validateAndSubmit('moveLeft')" title="Di chuyển sang trái" value="←" />
            </div>

            <!-- Bảng phải: Cột được hiển thị -->
            <div class="column-section">
                <div class="column-title">Cột được hiển thị (Thứ tự ưu tiên)</div>
                <table class="column-table" id="displayTable">
                    <thead>
                    <tr>
                        <th>Tên cột</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>
                            <select name="selectedDisplay" size="10" style="width:100%;">
                                <logic:iterate name="columnForm" property="selectedColumns" id="col" indexId="idx">
                                    <option value="<bean:write name='col'/>"><bean:write name="idx"/>. <bean:write name="col"/></option>
                                </logic:iterate>
                            </select>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>

        <!-- Nút lên/xuống và Save/Cancel -->
        <div class="bottom-buttons">
            <input type="button" class="btn" onclick="validateAndSubmit('moveUp')" value="↑" title="Di chuyển lên" />
            <input type="button" class="btn" onclick="validateAndSubmit('moveDown')" value="↓" title="Di chuyển xuống" />
            <input type="button" value="Save" onclick="document.forms['columnForm'].action='<%= request.getContextPath() %>/column.do?action=save'; document.forms['columnForm'].submit();" />
            <input type="button" value="Cancel" onclick="document.forms['columnForm'].action='<%= request.getContextPath() %>/column.do?action=cancel'; document.forms['columnForm'].submit();" />
        </div>
    </html:form>
</div>
</body>
</html>
