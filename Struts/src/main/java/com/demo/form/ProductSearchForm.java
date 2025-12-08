package com.demo.form;

import org.apache.struts.action.ActionForm;

public class ProductSearchForm extends ActionForm {
    
    private String searchProductName;
    private String searchQuantity;
    private String searchFromDate;
    private String searchToDate;
    private int page;
    private int rows;
    
    public ProductSearchForm() {
        this.page = 1;
        this.rows = 5;
    }
    
    public String getSearchProductName() {
        return searchProductName;
    }
    
    public void setSearchProductName(String searchProductName) {
        this.searchProductName = searchProductName;
    }
    
    public String getSearchQuantity() {
        return searchQuantity;
    }
    
    public void setSearchQuantity(String searchQuantity) {
        this.searchQuantity = searchQuantity;
    }
    
    public String getSearchFromDate() {
        return searchFromDate;
    }
    
    public void setSearchFromDate(String searchFromDate) {
        this.searchFromDate = searchFromDate;
    }
    
    public String getSearchToDate() {
        return searchToDate;
    }
    
    public void setSearchToDate(String searchToDate) {
        this.searchToDate = searchToDate;
    }
    
    public int getPage() {
        return page;
    }
    
    public void setPage(int page) {
        this.page = page;
    }
    
    public int getRows() {
        return rows;
    }
    
    public void setRows(int rows) {
        this.rows = rows;
    }
}
