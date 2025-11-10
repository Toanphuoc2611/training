package com.demo.action;

import com.demo.model.Customer;
import com.demo.form.SearchForm;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SearchAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        SearchForm searchForm = (SearchForm) form;

        // Lấy tham số action (search, first, previous, next, last)
        String action = request.getParameter("action");

        if (action != null) {
            handlePagination(searchForm, action);
        }

        // Gọi service để lấy dữ liệu (ở đây tôi dùng data mẫu)
        List<Customer> allCustomers = getAllCustomers();

        // Filter dữ liệu theo điều kiện tìm kiếm
        List<Customer> filteredCustomers = filterCustomers(allCustomers, searchForm);

        // Tính toán phân trang
        int totalRecords = filteredCustomers.size();
        int totalPages = (int) Math.ceil((double) totalRecords / searchForm.getPageSize());

        // Lấy dữ liệu cho trang hiện tại
        List<Customer> pageCustomers = getPageData(filteredCustomers,
                searchForm.getCurrentPage(),
                searchForm.getPageSize());

        // Set attributes để hiển thị trên JSP
        request.setAttribute("customerList", pageCustomers);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("totalRecords", totalRecords);
        request.setAttribute("currentPage", searchForm.getCurrentPage());

        return mapping.findForward("success");
    }

    private void handlePagination(SearchForm form, String action) {
        int currentPage = form.getCurrentPage();

        if ("first".equals(action)) {
            form.setCurrentPage(1);
        } else if ("previous".equals(action)) {
            if (currentPage > 1) {
                form.setCurrentPage(currentPage - 1);
            }
        } else if ("next".equals(action)) {
            form.setCurrentPage(currentPage + 1);
        } else if ("last".equals(action)) {
            // Sẽ được xử lý sau khi biết tổng số trang
        }
    }

    private List<Customer> filterCustomers(List<Customer> customers, SearchForm form) {
        List<Customer> result = new ArrayList<Customer>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date fromDate = null;
            Date toDate = null;

            if (form.getBirthDateFrom() != null && !form.getBirthDateFrom().isEmpty()) {
                fromDate = sdf.parse(form.getBirthDateFrom());
            }

            if (form.getBirthDateTo() != null && !form.getBirthDateTo().isEmpty()) {
                toDate = sdf.parse(form.getBirthDateTo());
            }

            for (Customer customer : customers) {
                boolean match = true;

                // Filter by name
                if (form.getCustomerName() != null && !form.getCustomerName().isEmpty()) {
                    if (!customer.getCustomerName().toLowerCase()
                            .contains(form.getCustomerName().toLowerCase())) {
                        match = false;
                    }
                }

                // Filter by gender
                if (form.getGender() != null && !form.getGender().isEmpty()
                        && !form.getGender().equals("all")) {
                    if (!customer.getGender().equals(form.getGender())) {
                        match = false;
                    }
                }

                // Filter by date range
                if (fromDate != null && customer.getBirthDate().before(fromDate)) {
                    match = false;
                }

                if (toDate != null && customer.getBirthDate().after(toDate)) {
                    match = false;
                }

                if (match) {
                    result.add(customer);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    private List<Customer> getPageData(List<Customer> customers, int currentPage, int pageSize) {
        int startIndex = (currentPage - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, customers.size());

        if (startIndex >= customers.size()) {
            return new ArrayList<Customer>();
        }

        return customers.subList(startIndex, endIndex);
    }

    // Dữ liệu mẫu - bạn thay thế bằng service thực tế
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