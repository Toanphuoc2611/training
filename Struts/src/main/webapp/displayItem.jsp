<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html>
<head>
    <title>Danh sách sản phẩm</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.13.2/themes/base/jquery-ui.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/free-jqgrid/4.15.5/css/jquery.jqgrid.min.css">
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
        }
        .container {
            max-width: 1400px;
            margin: 0 auto;
        }
        .header-section {
            margin-bottom: 20px;
        }
        .action-buttons {
            margin-bottom: 20px;
        }
        .action-buttons a, .action-buttons button {
            display: inline-block;
            padding: 10px 20px;
            margin-right: 10px;
            background-color: #4CAF50;
            color: white;
            text-decoration: none;
            border-radius: 4px;
            cursor: pointer;
            border: none;
            font-size: 14px;
            pointer-events: auto;
            z-index: 10;
            position: relative;
            font-weight: 500;
            transition: all 0.3s ease;
        }
        .action-buttons a:hover, .action-buttons button:hover {
            background-color: #45a049;
            transform: translateY(-2px);
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.2);
        }
        .action-buttons a:active, .action-buttons button:active {
            transform: translateY(0);
        }
        .action-buttons .config-btn {
            background-color: #2196F3;
        }
        .action-buttons .config-btn:hover {
            background-color: #0b7dda;
        }
        .search-section {
            background: #f9f9f9;
            padding: 20px;
            border-radius: 4px;
            margin-bottom: 20px;
            border: 1px solid #ddd;
        }
        .search-row {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
            gap: 15px;
            margin-bottom: 15px;
        }
        .form-group {
            display: flex;
            flex-direction: column;
        }
        .form-group label {
            font-weight: bold;
            margin-bottom: 5px;
            color: #333;
        }
        .form-group input {
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
            font-size: 14px;
            background-color: #fff;
            color: #333;
            cursor: text;
            position: relative;
            z-index: 10;
            pointer-events: auto;
        }
        .form-group input:focus {
            outline: 2px solid #4CAF50;
            border-color: #4CAF50;
        }
        .search-buttons {
            display: flex;
            gap: 10px;
            justify-content: flex-start;
        }
        .search-buttons button {
            padding: 8px 20px;
            font-size: 14px;
            cursor: pointer;
            pointer-events: auto;
            z-index: 10;
            position: relative;
            border: none;
            border-radius: 4px;
            color: white;
            font-weight: 500;
            transition: background-color 0.3s ease;
        }
        .search-buttons button:hover {
            opacity: 0.9;
            transform: translateY(-2px);
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.2);
        }
        .search-buttons button:active {
            transform: translateY(0);
        }
        .selected-columns-info {
            background: #e8f5e9;
            padding: 15px;
            border-radius: 4px;
            margin-bottom: 20px;
            border-left: 4px solid #4CAF50;
        }
        .ui-jqgrid {
            margin-bottom: 20px;
            border: 1px solid #ddd;
            border-radius: 4px;
            overflow: hidden;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        }
        .ui-jqgrid .ui-jqgrid-htable {
            background-color: #f5f5f5;
            border-bottom: 2px solid #ddd;
        }
        .ui-jqgrid .ui-jqgrid-htable th {
            background-color: #4CAF50;
            color: white;
            font-weight: bold;
            padding: 12px;
            text-align: left;
            border-right: 1px solid #ddd;
        }
        .ui-jqgrid .ui-jqgrid-htable th:last-child {
            border-right: none;
        }
        .ui-jqgrid .ui-jqgrid-btable td {
            padding: 12px;
            border-bottom: 1px solid #eee;
            border-right: 1px solid #eee;
        }
        .ui-jqgrid .ui-jqgrid-btable tr:hover {
            background-color: #f9f9f9;
        }
        .ui-jqgrid .ui-jqgrid-btable tr:nth-child(even) {
            background-color: #fafafa;
        }
        .ui-jqgrid .ui-jqgrid-btable td:last-child {
            border-right: none;
        }
        .ui-jqgrid input[type="checkbox"] {
            width: 18px;
            height: 18px;
            cursor: pointer;
            pointer-events: auto;
            z-index: 1000;
            position: relative;
        }
        .ui-jqgrid-pager {
            background-color: #f5f5f5;
            border-top: 1px solid #ddd;
            padding: 10px;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header-section">
            <h1>Danh sách sản phẩm</h1>
            <div class="action-buttons">
                <a href="<%= request.getContextPath() %>/column.do?action=init" class="config-btn">Cấu hình cột hiển thị</a>
                <button type="button" onclick="resetSearch()" style="background-color: #FF9800;">Xóa tìm kiếm</button>
            </div>
        </div>

        <div class="selected-columns-info">
            <strong>Các cột được chọn để hiển thị:</strong>
            <div style="margin-top: 10px;">
                <logic:present name="displayColumnConfig" scope="session">
                    <logic:iterate name="displayColumnConfig" property="selectedColumns" id="col" scope="session">
                        <span style="display: inline-block; background: #4CAF50; color: white; padding: 5px 10px; margin: 3px; border-radius: 3px;"><bean:write name="col"/></span>
                    </logic:iterate>
                </logic:present>

                <logic:notPresent name="displayColumnConfig" scope="session">
                    <logic:present name="columnConfig" scope="session">
                        <logic:iterate name="columnConfig" property="selectedColumns" id="col" scope="session">
                            <span style="display: inline-block; background: #4CAF50; color: white; padding: 5px 10px; margin: 3px; border-radius: 3px;"><bean:write name="col"/></span>
                        </logic:iterate>
                    </logic:present>

                    <logic:notPresent name="columnConfig" scope="session">
                        <span style="display: inline-block; background: #4CAF50; color: white; padding: 5px 10px; margin: 3px; border-radius: 3px;">Product Id</span>
                        <span style="display: inline-block; background: #4CAF50; color: white; padding: 5px 10px; margin: 3px; border-radius: 3px;">Product Name</span>
                        <span style="display: inline-block; background: #4CAF50; color: white; padding: 5px 10px; margin: 3px; border-radius: 3px;">Price</span>
                        <span style="display: inline-block; background: #4CAF50; color: white; padding: 5px 10px; margin: 3px; border-radius: 3px;">Quantity</span>
                        <span style="display: inline-block; background: #4CAF50; color: white; padding: 5px 10px; margin: 3px; border-radius: 3px;">Sold</span>
                    </logic:notPresent>
                </logic:notPresent>
            </div>
        </div>

        <div class="search-section">
            <h3>Tìm kiếm</h3>
            <div class="search-row">
                <div class="form-group">
                    <label for="searchProductName">Tên sản phẩm:</label>
                    <input type="text" id="searchProductName" placeholder="Nhập tên sản phẩm...">
                </div>
                <div class="form-group">
                    <label for="searchQuantity">Số lượng:</label>
                    <input type="number" id="searchQuantity" placeholder="Nhập số lượng...">
                </div>
            </div>
            <div class="search-row">
                <div class="form-group">
                    <label for="searchFromDate">Từ ngày nhập kho:</label>
                    <input type="date" id="searchFromDate">
                </div>
                <div class="form-group">
                    <label for="searchToDate">Đến ngày nhập kho:</label>
                    <input type="date" id="searchToDate">
                </div>
            </div>
            <div class="search-buttons">
                <button type="button" onclick="searchProducts()" style="background-color: #4CAF50;">Tìm kiếm</button>
                <button type="button" onclick="resetSearch()" style="background-color: #FF9800;">Xóa tìm kiếm</button>
                <button type="button" id="deleteBtn" onclick="deleteSelected()" style="background-color: #f44336; display: none;">Xóa các mục được chọn</button>
            </div>
        </div>

        <table id="jqGrid"></table>
        
        <div style="margin-top: 20px; text-align: center; display: flex; gap: 10px; justify-content: center;">
            <button type="button" onclick="goToFirstPage()" style="background-color: #2196F3;">« Đầu</button>
            <button type="button" onclick="goToPrevPage()" style="background-color: #2196F3;">< Trước</button>
            <span id="pageInfo" style="padding: 10px 15px; border: 1px solid #ddd; border-radius: 4px; min-width: 150px;">Trang 1</span>
            <button type="button" onclick="goToNextPage()" style="background-color: #2196F3;">Tiếp ></button>
            <button type="button" onclick="goToLastPage()" style="background-color: #2196F3;">Cuối »</button>
        </div>
    </div>

    <script src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
    <script src="https://code.jquery.com/ui/1.13.2/jquery-ui.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/free-jqgrid/4.15.5/jquery.jqgrid.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/free-jqgrid/4.15.5/i18n/grid.locale-en.min.js"></script>

    <script>
        var selectedColumns = [];
        var currentPage = 1;
        var totalPages = 1;
        
        function getSelectedColumns() {
            var spanElements = document.querySelectorAll('.selected-columns-info span');
            selectedColumns = [];
            spanElements.forEach(function(span) {
                selectedColumns.push(span.textContent.trim());
            });
            return selectedColumns;
        }

        function getColumnConfig() {
            var columns = getSelectedColumns();
            var colModel = [];
            var colNames = [];
            
            var columnMap = {
                'Product Id': { name: 'id', index: 'id', width: 80, formatter: function(cellvalue, options, rowObject) {
                    return '<a href="<%= request.getContextPath() %>/product.do?action=view&id=' + cellvalue + '" target="_blank">' + cellvalue + '</a>';
                }},
                'Product Name': { name: 'productName', index: 'productName', width: 200 },
                'Price': { name: 'price', index: 'price', width: 100, formatter: 'currency' },
                'Quantity': { name: 'quantity', index: 'quantity', width: 100 },
                'Sold': { name: 'sold', index: 'sold', width: 80 },
                'Image': { name: 'image', index: 'image', width: 150 },
                'Import Date': { name: 'importDate', index: 'importDate', width: 120, formatter: 'date', formatoptions: { srcformat: 'Y-m-d H:i:s', newformat: 'Y-m-d' } },
                'Select': { name: 'select', index: 'select', width: 50, sortable: false, editable: false, search: false, formatter: function(cellvalue, options, rowObject) {
                    return '<input type="checkbox" class="row-checkbox" value="' + rowObject.id + '">';
                }}
            };
            
            colNames.push('<input type="checkbox" id="jqGrid_selectAllCheckbox" style="width: 18px; height: 18px; cursor: pointer;">');
            colModel.push(columnMap['Select']);
            
            columns.forEach(function(col) {
                if (columnMap[col]) {
                    colNames.push(col);
                    colModel.push(columnMap[col]);
                }
            });
            
            return {
                colNames: colNames,
                colModel: colModel
            };
        }

        function initializeGrid() {
            var config = getColumnConfig();
            
            jQuery("#jqGrid").jqGrid({
                url: '<%= request.getContextPath() %>/product.do?action=list',
                datatype: "json",
                mtype: "GET",
                colNames: config.colNames,
                colModel: config.colModel,
                rowNum: 5,
                sortname: 'id',
                sortorder: 'asc',
                viewrecords: true,
                jsonReader: {
                    page: "page",
                    total: "total",
                    records: "records",
                    root: "rows",
                    repeatitems: false,
                    id: "id"
                },
                height: 'auto',
                autowidth: true,
                loadComplete: function() {
                    jQuery("#jqGrid").jqGrid('setGridWidth', jQuery(".container").width());
                    var data = jQuery("#jqGrid").jqGrid('getGridParam', 'data');
                    currentPage = jQuery("#jqGrid").jqGrid('getGridParam', 'page');
                    totalPages = jQuery("#jqGrid").jqGrid('getGridParam', 'lastpage');
                    updatePageInfo();
                    
                    // Bind select-all checkbox in header
                    setTimeout(function() {
                        var headerCheckbox = jQuery("#jqGrid_selectAllCheckbox");
                        if (headerCheckbox.length > 0) {
                            headerCheckbox.off('click').on('click', function(e) {
                                e.stopPropagation();
                                selectAllRows(this.checked);
                            });
                        }
                    }, 100);
                    
                    // Bind individual checkboxes
                    jQuery('.row-checkbox').on('change', function() {
                        handleCheckboxChange(this);
                    });
                }
            });
            
            jQuery(window).bind('resize', function() {
                var newWidth = jQuery(".container").width();
                jQuery("#jqGrid").jqGrid('setGridWidth', newWidth);
            });
        }

        function selectAllRows(checked) {
            if (checked) {
                jQuery('.row-checkbox').each(function() {
                    if (!this.checked) {
                        this.checked = true;
                        handleCheckboxChange(this);
                    }
                });
            } else {
                jQuery('.row-checkbox').each(function() {
                    if (this.checked) {
                        this.checked = false;
                        handleCheckboxChange(this);
                    }
                });
            }
            updateDeleteButton();
        }

        function handleCheckboxChange(checkbox) {
            var rowId = jQuery(checkbox).closest('tr').attr('id');
            if (checkbox.checked) {
                jQuery("#jqGrid").jqGrid('setSelection', rowId);
            } else {
                jQuery("#jqGrid").jqGrid('resetSelection', rowId);
            }
            updateDeleteButton();
        }

        function updateDeleteButton() {
            var selectedRows = jQuery(".row-checkbox:checked").length;
            var deleteBtn = jQuery("#deleteBtn");
            if (selectedRows > 0) {
                deleteBtn.show();
                deleteBtn.text("Xóa " + selectedRows + " mục được chọn");
            } else {
                deleteBtn.hide();
            }
        }

        function getSelectedRows() {
            var selectedIds = [];
            jQuery(".row-checkbox:checked").each(function() {
                selectedIds.push(jQuery(this).val());
            });
            return selectedIds;
        }

        function deleteSelected() {
            var selectedIds = getSelectedRows();
            if (selectedIds.length === 0) {
                alert("Vui lòng chọn ít nhất một mục để xóa");
                return;
            }
            
            if (!confirm("Bạn có chắc chắn muốn xóa " + selectedIds.length + " mục được chọn?")) {
                return;
            }
            
            // Send delete request to server
            jQuery.ajax({
                url: '<%= request.getContextPath() %>/product.do?action=delete',
                type: 'POST',
                dataType: 'json',
                data: {
                    ids: selectedIds.join(',')
                },
                success: function(response) {
                    if (response.success) {
                        alert("Xóa thành công");
                        // Reload grid
                        jQuery("#jqGrid").jqGrid('setGridParam', {
                            page: 1
                        }).trigger("reloadGrid");
                    } else {
                        alert("Lỗi: " + response.message);
                    }
                },
                error: function() {
                    alert("Lỗi xóa dữ liệu");
                }
            });
        }

        function updatePageInfo() {
            jQuery("#pageInfo").text("Trang " + currentPage + " / " + totalPages);
        }

        function goToFirstPage() {
            if (currentPage > 1) {
                jQuery("#jqGrid").jqGrid('setGridParam', {page: 1}).trigger("reloadGrid");
            }
        }

        function goToPrevPage() {
            if (currentPage > 1) {
                jQuery("#jqGrid").jqGrid('setGridParam', {page: currentPage - 1}).trigger("reloadGrid");
            }
        }

        function goToNextPage() {
            if (currentPage < totalPages) {
                jQuery("#jqGrid").jqGrid('setGridParam', {page: currentPage + 1}).trigger("reloadGrid");
            }
        }

        function goToLastPage() {
            if (currentPage < totalPages) {
                jQuery("#jqGrid").jqGrid('setGridParam', {page: totalPages}).trigger("reloadGrid");
            }
        }

        function searchProducts() {
            var productName = jQuery("#searchProductName").val();
            var quantity = jQuery("#searchQuantity").val();
            var fromDate = jQuery("#searchFromDate").val();
            var toDate = jQuery("#searchToDate").val();
            
            var params = {};
            if (productName) params.searchProductName = productName;
            if (quantity) params.searchQuantity = quantity;
            if (fromDate) params.searchFromDate = fromDate;
            if (toDate) params.searchToDate = toDate;
            
            jQuery("#jqGrid").jqGrid('setGridParam', {
                url: '<%= request.getContextPath() %>/product.do?action=list',
                postData: params,
                page: 1
            }).trigger("reloadGrid");
        }

        function resetSearch() {
            jQuery("#searchProductName").val('');
            jQuery("#searchQuantity").val('');
            jQuery("#searchFromDate").val('');
            jQuery("#searchToDate").val('');
            
            jQuery("#jqGrid").jqGrid('setGridParam', {
                url: '<%= request.getContextPath() %>/product.do?action=list',
                postData: {},
                page: 1
            }).trigger("reloadGrid");
        }

        jQuery(document).ready(function() {
            initializeGrid();
        });
    </script>
</body>
</html>
