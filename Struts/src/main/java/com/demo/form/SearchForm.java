package com.demo.form;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import javax.servlet.http.HttpServletRequest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SearchForm extends ActionForm {
    private String customerName;
    private String gender;
    private String birthDateFrom;
    private String birthDateTo;
    private int currentPage;
    private int pageSize = 5;

    private int totalPages;
    private String[] selectedIds;

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

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    @Override
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        sdf.setLenient(false); // strict parsing

        Date fromDate = null;
        Date toDate = null;
        StringBuilder alertMsg = new StringBuilder();

        // Validate birthDateFrom format if provided
        if (birthDateFrom != null && !birthDateFrom.trim().isEmpty()) {
            try {
                fromDate = sdf.parse(birthDateFrom.trim());
            } catch (ParseException e) {
                errors.add("birthDateFrom", new ActionMessage("error.date.format.from"));
                alertMsg.append("Ngày sinh từ phải có định dạng yyyy/MM/dd.");
            }
        }

        // Validate birthDateTo format if provided
        if (birthDateTo != null && !birthDateTo.trim().isEmpty()) {
            try {
                toDate = sdf.parse(birthDateTo.trim());
            } catch (ParseException e) {
                errors.add("birthDateTo", new ActionMessage("error.date.format.to"));
                alertMsg.append("Ngày sinh đến phải có định dạng yyyy/MM/dd.");
            }
        }

        // If both dates are valid and present, check range (birthDateFrom must be strictly before birthDateTo)
        if (fromDate != null && toDate != null) {
            if (!fromDate.before(toDate)) { // not strictly earlier
                errors.add("dateRange", new ActionMessage("error.date.range"));
                alertMsg.append("Ngày sinh từ phải nhỏ hơn ngày sinh đến.");
            }
        }

        // If there are any errors, set attributes so JSP can show a JS alert
        if (!errors.isEmpty()) {
            request.setAttribute("showDateAlert", Boolean.TRUE);
            request.setAttribute("dateValidationMessage", alertMsg.toString());
        }

        return errors;
    }
}