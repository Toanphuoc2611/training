package com.demo.service;

import com.demo.model.Product;
import java.text.SimpleDateFormat;
import java.util.*;

public class ProductService {
    
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            products.add(new Product(1, "Laptop Dell XPS 13", 1299.99, 15, 5, "laptop1.jpg", sdf.parse("2024-01-15")));
            products.add(new Product(2, "iPhone 15 Pro", 999.99, 25, 10, "iphone15.jpg", sdf.parse("2024-01-20")));
            products.add(new Product(3, "Samsung 65 QLED", 1499.99, 8, 2, "tv65.jpg", sdf.parse("2024-02-05")));
            products.add(new Product(4, "Sony WH-1000XM5", 399.99, 50, 20, "headphones.jpg", sdf.parse("2024-02-10")));
            products.add(new Product(5, "iPad Pro 12.9", 1099.99, 12, 4, "ipad.jpg", sdf.parse("2024-02-15")));
            products.add(new Product(6, "MacBook Air M3", 1299.99, 10, 3, "macbook.jpg", sdf.parse("2024-03-01")));
            products.add(new Product(7, "Google Pixel 8", 799.99, 30, 12, "pixel8.jpg", sdf.parse("2024-03-05")));
            products.add(new Product(8, "Samsung Galaxy Watch", 299.99, 40, 18, "watch.jpg", sdf.parse("2024-03-10")));
            products.add(new Product(9, "AirPods Max", 549.99, 20, 8, "airpods.jpg", sdf.parse("2024-03-15")));
            products.add(new Product(10, "Lenovo ThinkPad X1", 1199.99, 14, 6, "thinkpad.jpg", sdf.parse("2024-03-20")));
            products.add(new Product(11, "Dell Monitor 4K", 599.99, 22, 9, "monitor.jpg", sdf.parse("2024-04-01")));
            products.add(new Product(12, "Logitech MX Master", 99.99, 60, 35, "mouse.jpg", sdf.parse("2024-04-05")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return products;
    }
    
    public Map<String, Object> getProducts(int page, int rows, String searchProductName, 
                                           Integer searchQuantity, Date searchFromDate, Date searchToDate) {
        List<Product> allProducts = getAllProducts();
        List<Product> filteredProducts = new ArrayList<>();
        
        for (Product product : allProducts) {
            boolean match = true;
            
            if (searchProductName != null && !searchProductName.trim().isEmpty()) {
                if (!product.getProductName().toLowerCase().contains(searchProductName.toLowerCase())) {
                    match = false;
                }
            }
            
            if (searchQuantity != null && searchQuantity > 0) {
                if (!product.getQuantity().equals(searchQuantity)) {
                    match = false;
                }
            }
            
            if (searchFromDate != null && product.getImportDate().before(searchFromDate)) {
                match = false;
            }
            
            if (searchToDate != null) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(searchToDate);
                cal.set(Calendar.HOUR_OF_DAY, 23);
                cal.set(Calendar.MINUTE, 59);
                cal.set(Calendar.SECOND, 59);
                if (product.getImportDate().after(cal.getTime())) {
                    match = false;
                }
            }
            
            if (match) {
                filteredProducts.add(product);
            }
        }
        
        Map<String, Object> result = new HashMap<>();
        int total = filteredProducts.size();
        int totalPages = (total + rows - 1) / rows;
        
        int startIndex = (page - 1) * rows;
        int endIndex = Math.min(startIndex + rows, total);
        
        List<Product> pageProducts = new ArrayList<>();
        if (startIndex < total) {
            pageProducts = filteredProducts.subList(startIndex, endIndex);
        }
        
        result.put("page", page);
        result.put("total", totalPages);
        result.put("records", total);
        result.put("rows", pageProducts);
        
        return result;
    }
}
