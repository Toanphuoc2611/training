package com.demo.daoImpl;

import com.demo.dao.CustomerDAO;
import com.demo.model.Customer;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import java.util.Date;
import java.util.List;

/**
 * DAO Implementation sử dụng SessionFactory + Named Queries
 * 
 * Lý do chọn SessionFactory thay vì HibernateTemplate:
 * 1. SessionFactory là cách tiếp cận hiện đại của Hibernate
 * 2. HibernateTemplate đã deprecated trong Spring
 * 3. SessionFactory cho phép kiểm soát chi tiết transaction lifecycle
 * 4. Performance tốt hơn vì không có overhead wrapper
 * 5. Hỗ trợ đầy đủ các features Hibernate mới
 */
public class CustomerDAOImpl implements CustomerDAO {

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    protected Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    /**
     * Tìm khách hàng theo ID
     * Dùng Named Query "Customer.findById" định nghĩa trong mapping file
     */
    public Customer findById(int id) {
        Query query = getCurrentSession().getNamedQuery("Customer.findById");
        query.setParameter("id", id);
        return (Customer) query.uniqueResult();
    }

    /**
     * Tìm khách hàng theo tên (LIKE)
     * Dùng Named Query "Customer.findByName"
     */
    @SuppressWarnings("unchecked")
    public List<Customer> search(String name) {
        Query query = getCurrentSession().getNamedQuery("Customer.findByName");
        query.setParameter("name", "%" + name.toLowerCase() + "%");
        return query.list();
    }

    /**
     * Tìm kiếm khách hàng theo múc điều kiện
     * - name: tên khách hàng (NULL để bỏ qua)
     * - gender: giới tính (NULL để bỏ qua)
     * - fromDate: ngày sinh từ (NULL để bỏ qua)
     * - toDate: ngày sinh đến (NULL để bỏ qua)
     * 
     * Dùng Named Query "Customer.searchByCondition" hỗ trợ NULL parameters
     */
    @SuppressWarnings("unchecked")
    public List<Customer> searchByCondition(String name, String gender, Date fromDate, Date toDate) {
        Query query = getCurrentSession().getNamedQuery("Customer.searchByCondition");
        
        // Set parameters - NULL sẽ được xử lý bởi CASE WHEN trong query
        if (name != null && !name.trim().isEmpty()) {
            query.setParameter("name", "%" + name.toLowerCase() + "%");
        } else {
            query.setParameter("name", null);
        }
        
        if (gender != null && !gender.trim().isEmpty()) {
            query.setParameter("gender", gender);
        } else {
            query.setParameter("gender", null);
        }
        
        query.setParameter("fromDate", fromDate);
        query.setParameter("toDate", toDate);
        
        return query.list();
    }

    /**
     * Lấy tất cả khách hàng
     * Dùng Named Query "Customer.findAll"
     */
    @SuppressWarnings("unchecked")
    public List<Customer> findAll() {
        Query query = getCurrentSession().getNamedQuery("Customer.findAll");
        return query.list();
    }

    /**
     * Lưu khách hàng mới
     */
    public void save(Customer customer) {
        getCurrentSession().save(customer);
    }

    /**
     * Cập nhật thông tin khách hàng
     */
    public void update(Customer customer) {
        getCurrentSession().update(customer);
    }

    /**
     * Xóa khách hàng
     */
    public void delete(Customer customer) {
        getCurrentSession().delete(customer);
    }

    /**
     * Xóa khách hàng theo ID
     */
    public void deleteById(int id) {
        Customer customer = findById(id);
        if (customer != null) {
            delete(customer);
        }
    }

    /**
     * Kiểm tra login (ví dụ - thực tế nên hash password)
     * Có thể thêm Named Query "Customer.login" nếu cần
     */
    public Customer login(String user, String pass) {
        // TODO: Implement login logic với hash password
        return null;
    }

    /**
     * Đếm tổng số khách hàng
     */
    public int count() {
        Query query = getCurrentSession().createQuery("select count(*) from Customer");
        return ((Number) query.uniqueResult()).intValue();
    }
}
