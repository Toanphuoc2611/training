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
        }
        .action-buttons a:hover, .action-buttons button:hover {
            background-color: #45a049;
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
        }
        .search-buttons {
            display: flex;
            gap: 10px;
            justify-content: flex-start;
        }
        .search-buttons button {
            padding: 8px 20px;
            font-size: 14px;
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
            </div>
        </div>

        <table id="jqGrid"></table>
        <div id="jqGridPager"></div>
    </div>

    <script src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
    <script src="https://code.jquery.com/ui/1.13.2/jquery-ui.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/free-jqgrid/4.15.5/jquery.jqgrid.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/free-jqgrid/4.15.5/i18n/grid.locale-en.min.js"></script>

    <script>
        var selectedColumns = [];
        
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
                'Product Id': { name: 'id', index: 'id', width: 80 },
                'Product Name': { name: 'productName', index: 'productName', width: 200 },
                'Price': { name: 'price', index: 'price', width: 100, formatter: 'currency' },
                'Quantity': { name: 'quantity', index: 'quantity', width: 100 },
                'Sold': { name: 'sold', index: 'sold', width: 80 },
                'Image': { name: 'image', index: 'image', width: 150 },
                'Import Date': { name: 'importDate', index: 'importDate', width: 120, formatter: 'date', formatoptions: { srcformat: 'Y-m-d H:i:s', newformat: 'Y-m-d' } }
            };
            
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
                pager: '#jqGridPager',
                rowNum: 10,
                rowList: [10, 20, 30, 50],
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
                }
            });
            
            jQuery(window).bind('resize', function() {
                var newWidth = jQuery(".container").width();
                jQuery("#jqGrid").jqGrid('setGridWidth', newWidth);
            });
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
