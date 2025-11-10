package com.demo.form;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import javax.servlet.http.HttpServletRequest;

public class SearchForm extends ActionForm {
    private String customerName;
    private String gender;
    private String birthDateFrom;
    private String birthDateTo;
    private int currentPage = 1;
    private int pageSize = 5;
    private String[] selectedIds;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        this.customerName = "";
        this.gender = "";
        this.birthDateFrom = "";
        this.birthDateTo = "";
        this.selectedIds = null;
    }

    // Getters and Setters
    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthDateFrom() {
        return birthDateFrom;
    }

    public void setBirthDateFrom(String birthDateFrom) {
        this.birthDateFrom = birthDateFrom;
    }

    public String getBirthDateTo() {
        return birthDateTo;
    }

    public void setBirthDateTo(String birthDateTo) {
        this.birthDateTo = birthDateTo;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String[] getSelectedIds() {
        return selectedIds;
    }

    public void setSelectedIds(String[] selectedIds) {
        this.selectedIds = selectedIds;
    }
}