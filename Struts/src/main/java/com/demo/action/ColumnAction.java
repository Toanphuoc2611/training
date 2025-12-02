package com.demo.action;

import com.demo.form.ColumnForm;
import com.demo.service.ColumnService;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ColumnAction extends DispatchAction {
    
    private ColumnService columnService = new ColumnService();
    
    /**
     * Khởi tạo trang cấu hình cột
     */
    public ActionForward init(ActionMapping mapping, ActionForm form,
                              HttpServletRequest request, HttpServletResponse response) throws Exception {
        ColumnForm columnForm = (ColumnForm) form;
        
        // Khởi tạo dữ liệu từ session hoặc tạo mới
        HttpSession session = request.getSession();
        ColumnForm sessionForm = (ColumnForm) session.getAttribute("columnConfig");
        
        if (sessionForm == null) {
            columnService.initializeColumns(columnForm.getAvailableColumns(), 
                                           columnForm.getSelectedColumns());
            session.setAttribute("columnConfig", columnForm);
        } else {
            columnForm.setAvailableColumns(sessionForm.getAvailableColumns());
            columnForm.setSelectedColumns(sessionForm.getSelectedColumns());
        }
        
        return mapping.findForward("success");
    }
    
    /**
     * Di chuyển item từ bảng trái sang bảng phải
     */
    public ActionForward moveRight(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request, HttpServletResponse response) throws Exception {
        ColumnForm columnForm = (ColumnForm) form;
        HttpSession session = request.getSession();
        
        // Lấy dữ liệu từ session
        ColumnForm sessionForm = (ColumnForm) session.getAttribute("columnConfig");
        if (sessionForm == null) {
            sessionForm = new ColumnForm();
            columnService.initializeColumns(sessionForm.getAvailableColumns(), 
                                           sessionForm.getSelectedColumns());
            session.setAttribute("columnConfig", sessionForm);
        }
        
        String error = columnService.moveRight(sessionForm.getAvailableColumns(), 
                                               sessionForm.getSelectedColumns(), 
                                               columnForm.getSelectedAvailable());
        
        if (error != null) {
            request.setAttribute("error", error);
        }
        
        // Copy lại từ session
        columnForm.setAvailableColumns(sessionForm.getAvailableColumns());
        columnForm.setSelectedColumns(sessionForm.getSelectedColumns());
        columnForm.setSelectedAvailable(null);
        
        return mapping.findForward("success");
    }
    
    /**
     * Di chuyển item từ bảng phải sang bảng trái
     */
    public ActionForward moveLeft(ActionMapping mapping, ActionForm form,
                                  HttpServletRequest request, HttpServletResponse response) throws Exception {
        ColumnForm columnForm = (ColumnForm) form;
        HttpSession session = request.getSession();
        
        ColumnForm sessionForm = (ColumnForm) session.getAttribute("columnConfig");
        if (sessionForm == null) {
            sessionForm = new ColumnForm();
            columnService.initializeColumns(sessionForm.getAvailableColumns(), 
                                           sessionForm.getSelectedColumns());
            session.setAttribute("columnConfig", sessionForm);
        }
        
        String error = columnService.moveLeft(sessionForm.getAvailableColumns(), 
                                              sessionForm.getSelectedColumns(), 
                                              columnForm.getSelectedDisplay());
        
        if (error != null) {
            request.setAttribute("error", error);
        }
        
        columnForm.setAvailableColumns(sessionForm.getAvailableColumns());
        columnForm.setSelectedColumns(sessionForm.getSelectedColumns());
        columnForm.setSelectedDisplay(null);
        
        return mapping.findForward("success");
    }
    
    /**
     * Di chuyển item lên trên
     */
    public ActionForward moveUp(ActionMapping mapping, ActionForm form,
                                HttpServletRequest request, HttpServletResponse response) throws Exception {
        ColumnForm columnForm = (ColumnForm) form;
        HttpSession session = request.getSession();
        
        ColumnForm sessionForm = (ColumnForm) session.getAttribute("columnConfig");
        if (sessionForm == null) {
            return mapping.findForward("success");
        }
        
        String error = columnService.moveUp(sessionForm.getSelectedColumns(), 
                                           columnForm.getSelectedDisplay());
        
        if (error != null) {
            request.setAttribute("error", error);
        }
        
        columnForm.setAvailableColumns(sessionForm.getAvailableColumns());
        columnForm.setSelectedColumns(sessionForm.getSelectedColumns());
        columnForm.setSelectedDisplay(null);
        
        return mapping.findForward("success");
    }
    
    /**
     * Di chuyển item xuống dưới
     */
    public ActionForward moveDown(ActionMapping mapping, ActionForm form,
                                  HttpServletRequest request, HttpServletResponse response) throws Exception {
        ColumnForm columnForm = (ColumnForm) form;
        HttpSession session = request.getSession();
        
        ColumnForm sessionForm = (ColumnForm) session.getAttribute("columnConfig");
        if (sessionForm == null) {
            return mapping.findForward("success");
        }
        
        String error = columnService.moveDown(sessionForm.getSelectedColumns(), 
                                             columnForm.getSelectedDisplay());
        
        if (error != null) {
            request.setAttribute("error", error);
        }
        
        columnForm.setAvailableColumns(sessionForm.getAvailableColumns());
        columnForm.setSelectedColumns(sessionForm.getSelectedColumns());
        columnForm.setSelectedDisplay(null);
        
        return mapping.findForward("success");
    }
    
    /**
     * Lưu cấu hình và chuyển sang trang hiển thị
     */
    public ActionForward save(ActionMapping mapping, ActionForm form,
                              HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        ColumnForm columnForm = (ColumnForm) form;
        ColumnForm sessionForm = (ColumnForm) session.getAttribute("columnConfig");
        
        if (sessionForm != null) {
            // Lưu cấu hình hiện tại vào displayColumnConfig
            session.setAttribute("displayColumnConfig", sessionForm);
            // Cập nhật form để hiển thị
            columnForm.setAvailableColumns(sessionForm.getAvailableColumns());
            columnForm.setSelectedColumns(sessionForm.getSelectedColumns());
        }
        
        return mapping.findForward("displayItem");
    }
    
    /**
     * Hủy bỏ thay đổi và quay về trang hiển thị
     */
    public ActionForward cancel(ActionMapping mapping, ActionForm form,
                                HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        ColumnForm columnForm = (ColumnForm) form;
        
        // Khôi phục cấu hình trước đó từ displayColumnConfig
        ColumnForm displayConfig = (ColumnForm) session.getAttribute("displayColumnConfig");
        if (displayConfig != null) {
            session.setAttribute("columnConfig", displayConfig);
            columnForm.setAvailableColumns(displayConfig.getAvailableColumns());
            columnForm.setSelectedColumns(displayConfig.getSelectedColumns());
        }
        
        return mapping.findForward("displayItem");
    }
}
