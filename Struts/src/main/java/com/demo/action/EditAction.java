package com.demo.action;

import com.demo.model.Customer;
import com.demo.form.CustomerForm;
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

public class EditAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        CustomerForm customerForm = (CustomerForm) form;
        String action = request.getParameter("action");
        String customerId = request.getParameter("id");

        // Xác định action: add hoặc edit
        if ("add".equals(action)) {
            // Thêm mới - reset form
            customerForm.reset(mapping, request);
            request.setAttribute("pageTitle", "Thêm khách hàng");
            request.setAttribute("isAddMode", true);
            return mapping.findForward("success");

        } else if ("edit".equals(action)) {
            // Chỉnh sửa - load dữ liệu
            if (customerId == null || customerId.trim().isEmpty()) {
                return mapping.findForward("failure");
            }

            Customer customer = getCustomerById(Integer.parseInt(customerId));

            if (customer == null) {
                return mapping.findForward("failure");
            }

            // Đổ dữ liệu vào form
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            customerForm.setId(String.valueOf(customer.getId()));
            customerForm.setCustomerName(customer.getCustomerName());
            customerForm.setBirthDate(sdf.format(customer.getBirthDate()));
            customerForm.setGender(customer.getGender());
            customerForm.setAddress(customer.getAddress());

            request.setAttribute("pageTitle", "Chỉnh sửa khách hàng");
            request.setAttribute("isAddMode", false);
            return mapping.findForward("success");

        } else {
            // Không có action hợp lệ
            return mapping.findForward("failure");
        }
    }

    // Dữ liệu mẫu - bạn thay bằng service thực tế
    private Customer getCustomerById(int id) {
        List<Customer> customers = getAllCustomers();

        for (Customer customer : customers) {
            if (customer.getId() == id) {
                return customer;
            }
        }

        return null;
    }

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