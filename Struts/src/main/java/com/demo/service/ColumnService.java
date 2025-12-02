package com.demo.service;

import java.util.ArrayList;
import java.util.List;

public class ColumnService {
    
    // Các cột bắt buộc không thể ẩn
    private static final List<String> MANDATORY_COLUMNS = new ArrayList<>();
    static {
        MANDATORY_COLUMNS.add("Product Id");
        MANDATORY_COLUMNS.add("CheckBox");
    }
    
    /**
     * Kiểm tra cột có bắt buộc hay không
     */
    public boolean isMandatoryColumn(String column) {
        return MANDATORY_COLUMNS.contains(column);
    }
    
    /**
     * Di chuyển item từ bảng trái sang bảng phải
     */
    public String moveRight(List<String> availableColumns, List<String> selectedColumns, String selectedAvailable) {
        if (selectedAvailable == null || selectedAvailable.trim().isEmpty()) {
            return "Cần chọn Item để di chuyển sang phải";
        }
        
        if (availableColumns.remove(selectedAvailable)) {
            selectedColumns.add(selectedAvailable);
            return null;
        }
        return "Không thể di chuyển Item";
    }
    
    /**
     * Di chuyển item từ bảng phải sang bảng trái
     */
    public String moveLeft(List<String> availableColumns, List<String> selectedColumns, String selectedDisplay) {
        if (selectedDisplay == null || selectedDisplay.trim().isEmpty()) {
            return "Cần chọn Item để di chuyển sang trái";
        }
        
        // Kiểm tra cột bắt buộc
        if (isMandatoryColumn(selectedDisplay)) {
            return "Không thể ẩn item \"" + selectedDisplay + "\"";
        }
        
        // Kiểm tra nếu chỉ còn 2 cột (cả 2 đều bắt buộc)
        if (selectedColumns.size() <= 2) {
            return "Cần chọn Item để di chuyển sang trái";
        }
        
        if (selectedColumns.remove(selectedDisplay)) {
            availableColumns.add(selectedDisplay);
            return null;
        }
        return "Không thể di chuyển Item";
    }
    
    /**
     * Di chuyển item lên trên
     */
    public String moveUp(List<String> selectedColumns, String selectedDisplay) {
        if (selectedDisplay == null || selectedDisplay.trim().isEmpty()) {
            return "Cần chọn Item để di chuyển lên";
        }
        
        int currentIndex = selectedColumns.indexOf(selectedDisplay);
        if (currentIndex <= 0) {
            return "Không thể di chuyển Item này lên nữa";
        }
        
        selectedColumns.remove(currentIndex);
        selectedColumns.add(currentIndex - 1, selectedDisplay);
        return null;
    }
    
    /**
     * Di chuyển item xuống dưới
     */
    public String moveDown(List<String> selectedColumns, String selectedDisplay) {
        if (selectedDisplay == null || selectedDisplay.trim().isEmpty()) {
            return "Cần chọn Item để di chuyển xuống";
        }
        
        int currentIndex = selectedColumns.indexOf(selectedDisplay);
        if (currentIndex < 0 || currentIndex >= selectedColumns.size() - 1) {
            return "Không thể di chuyển Item này xuống nữa";
        }
        
        selectedColumns.remove(currentIndex);
        selectedColumns.add(currentIndex + 1, selectedDisplay);
        return null;
    }
    
    /**
     * Khởi tạo dữ liệu ban đầu
     */
    public void initializeColumns(List<String> availableColumns, List<String> selectedColumns) {
        // Tất cả cột có thể hiển thị
        List<String> allColumns = new ArrayList<>();
        allColumns.add("Product Id");
        allColumns.add("CheckBox");
        allColumns.add("Product Name");
        allColumns.add("Price");
        allColumns.add("Quantity");
        allColumns.add("Sold");
        allColumns.add("Image");
        
        // Khởi tạo: mặc định hiển thị tất cả cột
        selectedColumns.clear();
        selectedColumns.addAll(allColumns);
        availableColumns.clear();
    }
}
