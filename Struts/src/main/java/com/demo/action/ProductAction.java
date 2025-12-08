package com.demo.action;

import com.demo.form.ProductSearchForm;
import com.demo.model.Product;
import com.demo.service.ProductService;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ProductAction extends Action {
    
    private ProductService productService = new ProductService();
    
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                HttpServletRequest request, HttpServletResponse response) throws Exception {
        String action = request.getParameter("action");
        
        if ("list".equals(action)) {
            ProductSearchForm searchForm = (ProductSearchForm) form;
            return listProducts(request, response, searchForm);
        } else if ("delete".equals(action)) {
            return deleteProducts(request, response);
        }
        
        return mapping.findForward("success");
    }
    
    private ActionForward listProducts(HttpServletRequest request, HttpServletResponse response, ProductSearchForm searchForm) throws Exception {
        int page = searchForm.getPage() > 0 ? searchForm.getPage() : 1;
        int rows = searchForm.getRows() > 0 ? searchForm.getRows() : 5;
        
        String pageParam = request.getParameter("page");
        String rowsParam = request.getParameter("rows");
        if (pageParam != null && !pageParam.isEmpty()) {
            page = Integer.parseInt(pageParam);
        }
        if (rowsParam != null && !rowsParam.isEmpty()) {
            rows = Integer.parseInt(rowsParam);
        }
        
        String searchProductName = searchForm.getSearchProductName();
        String searchQuantityStr = searchForm.getSearchQuantity();
        String searchFromDateStr = searchForm.getSearchFromDate();
        String searchToDateStr = searchForm.getSearchToDate();
        
        Integer searchQuantity = null;
        if (searchQuantityStr != null && !searchQuantityStr.trim().isEmpty()) {
            try {
                searchQuantity = Integer.parseInt(searchQuantityStr);
            } catch (NumberFormatException e) {
                searchQuantity = null;
            }
        }
        
        Date searchFromDate = null;
        Date searchToDate = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        if (searchFromDateStr != null && !searchFromDateStr.trim().isEmpty()) {
            try {
                searchFromDate = sdf.parse(searchFromDateStr);
            } catch (Exception e) {
                searchFromDate = null;
            }
        }
        
        if (searchToDateStr != null && !searchToDateStr.trim().isEmpty()) {
            try {
                searchToDate = sdf.parse(searchToDateStr);
            } catch (Exception e) {
                searchToDate = null;
            }
        }
        
        Map<String, Object> result = productService.getProducts(page, rows, searchProductName, searchQuantity, searchFromDate, searchToDate);
        @SuppressWarnings("unchecked")
        List<Product> products = (List<Product>) result.get("rows");
        
        String jsonResponse = buildJsonResponse(result, products);
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print(jsonResponse);
        out.flush();
        
        return null;
    }
    
    private String buildJsonResponse(Map<String, Object> result, List<Product> products) {
        StringBuilder json = new StringBuilder();
        json.append("{");
        json.append("\"page\":").append(result.get("page")).append(",");
        json.append("\"total\":").append(result.get("total")).append(",");
        json.append("\"records\":").append(result.get("records")).append(",");
        json.append("\"rows\":[");
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < products.size(); i++) {
            Product p = products.get(i);
            if (i > 0) json.append(",");
            json.append("{");
            json.append("\"id\":").append(p.getId()).append(",");
            json.append("\"productName\":\"").append(escapeJson(p.getProductName())).append("\",");
            json.append("\"price\":").append(p.getPrice()).append(",");
            json.append("\"quantity\":").append(p.getQuantity()).append(",");
            json.append("\"sold\":").append(p.getSold()).append(",");
            json.append("\"image\":\"").append(escapeJson(p.getImage())).append("\",");
            json.append("\"importDate\":\"").append(sdf.format(p.getImportDate())).append("\"");
            json.append("}");
        }
        
        json.append("]");
        json.append("}");
        
        return json.toString();
    }
    
    private String escapeJson(String str) {
        if (str == null) return "";
        return str.replace("\\", "\\\\")
                  .replace("\"", "\\\"")
                  .replace("\n", "\\n")
                  .replace("\r", "\\r")
                  .replace("\t", "\\t");
    }
    
    private ActionForward deleteProducts(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String idsStr = request.getParameter("ids");
        
        StringBuilder jsonResponse = new StringBuilder();
        try {
            if (idsStr == null || idsStr.trim().isEmpty()) {
                jsonResponse.append("{\"success\":false,\"message\":\"No IDs provided\"}");
            } else {
                String[] ids = idsStr.split(",");
                int deletedCount = 0;
                for (String id : ids) {
                    try {
                        Long productId = Long.parseLong(id.trim());
                        if (productService.deleteProduct(productId)) {
                            deletedCount++;
                        }
                    } catch (NumberFormatException e) {
                        // Skip invalid IDs
                    }
                }
                
                if (deletedCount > 0) {
                    jsonResponse.append("{\"success\":true,\"message\":\"").append(deletedCount).append(" product(s) deleted\"}");
                } else {
                    jsonResponse.append("{\"success\":false,\"message\":\"No products were deleted\"}");
                }
            }
        } catch (Exception e) {
            jsonResponse.append("{\"success\":false,\"message\":\"").append(escapeJson(e.getMessage())).append("\"}");
        }
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print(jsonResponse.toString());
        out.flush();
        
        return null;
    }
}
