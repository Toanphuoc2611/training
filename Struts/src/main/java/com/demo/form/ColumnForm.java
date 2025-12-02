package com.demo.form;

import org.apache.struts.action.ActionForm;
import java.util.ArrayList;
import java.util.List;

public class ColumnForm extends ActionForm {
    
    private List<String> availableColumns;  // Các cột không được hiển thị (bảng trái)
    private List<String> selectedColumns;   // Các cột được hiển thị (bảng phải)
    private String selectedAvailable;       // Item được chọn từ bảng trái
    private String selectedDisplay;         // Item được chọn từ bảng phải
    
    public ColumnForm() {
        this.availableColumns = new ArrayList<>();
        this.selectedColumns = new ArrayList<>();
    }
    
    public List<String> getAvailableColumns() {
        return availableColumns;
    }
    
    public void setAvailableColumns(List<String> availableColumns) {
        this.availableColumns = availableColumns;
    }
    
    public List<String> getSelectedColumns() {
        return selectedColumns;
    }
    
    public void setSelectedColumns(List<String> selectedColumns) {
        this.selectedColumns = selectedColumns;
    }
    
    public String getSelectedAvailable() {
        return selectedAvailable;
    }
    
    public void setSelectedAvailable(String selectedAvailable) {
        this.selectedAvailable = selectedAvailable;
    }
    
    public String getSelectedDisplay() {
        return selectedDisplay;
    }
    
    public void setSelectedDisplay(String selectedDisplay) {
        this.selectedDisplay = selectedDisplay;
    }
}
