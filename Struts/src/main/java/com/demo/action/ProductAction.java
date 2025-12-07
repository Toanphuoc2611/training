package com.demo.action;

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
            return listProducts(request, response);
        }
        
        return mapping.findForward("success");
    }
    
    private ActionForward listProducts(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int page = Integer.parseInt(request.getParameter("page") != null ? request.getParameter("page") : "1");
        int rows = Integer.parseInt(request.getParameter("rows") != null ? request.getParameter("rows") : "10");
        
        String searchProductName = request.getParameter("searchProductName");
        String searchQuantityStr = request.getParameter("searchQuantity");
        String searchFromDateStr = request.getParameter("searchFromDate");
        String searchToDateStr = request.getParameter("searchToDate");
        
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
}
