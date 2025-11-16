package com.demo.action;

import com.demo.form.SearchForm;
import com.demo.model.Customer;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SearchAction extends DispatchAction {

    @Override
    protected ActionForward dispatchMethod(ActionMapping mapping, ActionForm form,
                                          HttpServletRequest request, HttpServletResponse response,
                                          String name) throws Exception {
        try {
            return super.dispatchMethod(mapping, form, request, response, name);
        } catch (NoSuchMethodException e) {
            // Xử lý lỗi khi method không tồn tại
            request.setAttribute("error", "Action '" + name + "' không tồn tại!");
            return mapping.findForward("init");
        }
    }

    public ActionForward search(ActionMapping mapping, ActionForm form,
                                HttpServletRequest request, HttpServletResponse response) throws Exception {
        SearchForm searchForm = (SearchForm) form;

        String name = searchForm.getCustomerName();
        String gender = searchForm.getGender();
        String dateFrom = searchForm.getBirthDateFrom();
        String dateTo = searchForm.getBirthDateTo();
        int currentPage = searchForm.getCurrentPage();
        int pageSize = searchForm.getPageSize();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        sdf.setLenient(false);

        Date fromDate = null;
        Date toDate = null;

        try {
            if (dateFrom != null && !dateFrom.trim().isEmpty()) {
                fromDate = sdf.parse(dateFrom);
            }
            if (dateTo != null && !dateTo.trim().isEmpty()) {
                toDate = sdf.parse(dateTo);
            }
        } catch (Exception e) {
            request.setAttribute("error", "Ngày tháng không hợp lệ (yyyy-MM-dd)");
            return mapping.findForward("init");
        }

        // Lấy danh sách tất cả customer
        List<Customer> all = getAllCustomers();
        List<Customer> filtered = new ArrayList<>();

        // Lọc dữ liệu
        for (Customer c : all) {

            // Lọc theo tên
            if (name != null && !name.trim().isEmpty()) {
                if (!c.getCustomerName().toLowerCase().contains(name.toLowerCase())) continue;
            }

            // Lọc theo gender
            if (gender != null && !gender.trim().isEmpty()) {
                if (!gender.equalsIgnoreCase(c.getGender())) continue;
            }

            // Lọc từ ngày
            if (fromDate != null) {
                if (c.getBirthDate().before(fromDate)) continue;
            }

            // Lọc đến ngày
            if (toDate != null) {
                if (c.getBirthDate().after(toDate)) continue;
            }

            filtered.add(c);
        }

        // ----------- PHÂN TRANG ---------------
        int total = filtered.size();
        int totalPages = (int) Math.ceil((double) total / pageSize);

        if (currentPage < 1) currentPage = 1;
        if (currentPage > totalPages) currentPage = totalPages;

        int start = (currentPage - 1) * pageSize;
        int end = Math.min(start + pageSize, total);

        List<Customer> pageList = filtered.subList(start, end);

        // Gửi ra JSP
        searchForm.setTotalPages(totalPages);
        searchForm.setCurrentPage(currentPage);
        request.getSession().setAttribute("customerList", pageList);


        return mapping.findForward("init");
    }
    public ActionForward delete(ActionMapping mapping, ActionForm form,
                                HttpServletRequest request, HttpServletResponse response) throws Exception {
        SearchForm searchForm = (SearchForm) form;
        String[] ids = searchForm.getSelectedIds();
        // Lấy tất cả customers
        List<Customer> allCustomers = getAllCustomers();

        // Chuyển IDs sang Integer
        List<Integer> deleteIds = new ArrayList<>();
        for (String idStr : ids) {
            try {
                deleteIds.add(Integer.parseInt(idStr));
            } catch (Exception e) {
                // Nếu lỗi parse thì bỏ qua id đó
            }
        }

        // Xóa khách hàng có ID nằm trong deleteIds
        List<Customer> remaining = new ArrayList<>();
        for (Customer c : allCustomers) {
            if (!deleteIds.contains(c.getId())) {
                remaining.add(c);
            }
        }
        return mapping.findForward("init");
    }

    // Dữ liệu mẫu
    private List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<Customer>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        try {
            customers.add(new Customer(1, "Nguyễn Văn An", sdf.parse("1990-05-15"), "male", "Hà Nội"));
            customers.add(new Customer(2, "Trần Thị Bình", sdf.parse("1992-08-20"), "female", "Hồ Chí Minh"));
            customers.add(new Customer(3, "Lê Văn Cường", sdf.parse("1988-12-10"), "male", "Đà Nẵng"));
            customers.add(new Customer(4, "Phạm Thị Dung", sdf.parse("1995-03-25"), "female", "Hải Phòng"));
            customers.add(new Customer(5, "Hoàng Văn Em", sdf.parse("1991-07-30"), "male", "Cần Thơ"));
            customers.add(new Customer(6, "Vũ Thị Phương", sdf.parse("1993-11-05"), "female", "Huế"));
            customers.add(new Customer(7, "Đặng Văn Giang", sdf.parse("1989-04-18"), "male", "Nha Trang"));
            customers.add(new Customer(8, "Bùi Thị Hà", sdf.parse("1994-09-22"), "female", "Vũng Tàu"));
            customers.add(new Customer(9, "Ngô Văn Hùng", sdf.parse("1987-06-12"), "male", "Biên Hòa"));
            customers.add(new Customer(10, "Trịnh Thị Lan", sdf.parse("1996-01-08"), "female", "Quy Nhơn"));
            customers.add(new Customer(11, "Đinh Văn Khoa", sdf.parse("1990-10-28"), "male", "Thái Nguyên"));
            customers.add(new Customer(12, "Lý Thị Mai", sdf.parse("1992-02-14"), "female", "Nam Định"));
            customers.add(new Customer(13, "Võ Văn Nam", sdf.parse("1991-05-19"), "male", "Bắc Ninh"));
            customers.add(new Customer(14, "Phan Thị Oanh", sdf.parse("1993-08-07"), "female", "Thanh Hóa"));
            customers.add(new Customer(15, "Dương Văn Phong", sdf.parse("1988-11-23"), "male", "Nghệ An"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return customers;
    }
}