    <%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
    <%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
    <%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
    <%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <!DOCTYPE html>
    <html>
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Tìm kiếm khách hàng</title>
        <link rel="stylesheet" href="<html:rewrite page="/css/search.css"/>">
    </head>
    <body>
    <div class="container">

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
                        <html:text property="customerName" styleId="customerName" name="searchForm"/>
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
                        <html:text property="birthDateFrom" styleId="birthDateFrom" name="searchForm"
                                   styleClass="date-input"/>
                    </div>

                    <div class="form-group">
                        <label for="birthDateTo">Ngày sinh đến:</label>
                        <html:text property="birthDateTo" styleId="birthDateTo" name="searchForm"
                                   styleClass="date-input"/>
                    </div>
                </div>

                <div class="form-row">
                    <html:submit styleClass="btn btn-primary" value="search" property="action"/>
                </div>

                <html:hidden property="currentPage" name="searchForm"/>
                <html:hidden property="totalPages" name="searchForm"/>
            </html:form>
        </div>

        <!-- Pagination Controls -->
        <div class="pagination">
            <html:form action="/search" method="post" styleId="paginationForm">
                <html:hidden property="currentPage" styleId="currentPage" name="searchForm"/>
                <html:hidden property="totalPages" name="searchForm" styleId="totalPages"/>
                <html:hidden property="action" value="search"/>
                <button type="button" value="Previous" onclick="goToPage('previous')"></button>
                <button type="button" value="Next" onclick="goToPage('next')"/></button>
            </html:form>

            <div class="pagination-info">
                <logic:present name="searchForm" property="currentPage">
                    Trang <bean:write name="searchForm" property="currentPage"/> /
                    <bean:write name="searchForm" property="totalPages"/>
                    (Tổng: <bean:write name="searchForm" property="totalPages"/> bản ghi)
                </logic:present>
            </div>
        </div>

        <!-- Customer List Table -->
        <html:form styleId="deleteForm" method="post" action="/search">
            <html:hidden property="action" value="delete"/>
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
        </html:form>
    </div>
    <script src="<html:rewrite page="/js/search.js"/>"></script>
    <logic:present name="showDateAlert">
        <script type="text/javascript">
            window.addEventListener('load', function () {
                alert('<bean:write name="dateValidationMessage"/>');
            });
        </script>
    </logic:present>
    </body>
    </html>