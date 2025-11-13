package com.demo.action;

import org.apache.struts.actions.DispatchAction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CustomDispatchAction extends DispatchAction {

    /**
     * Method mặc định khi không có parameter hoặc parameter không hợp lệ
     */
    @Override
    protected ActionForward unspecified(ActionMapping mapping, ActionForm form,
                                        HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        System.out.println("=== Unspecified method called ===");

        // Có thể redirect về trang search hoặc hiển thị error
        ActionMessages errors = new ActionMessages();
        errors.add(ActionMessages.GLOBAL_MESSAGE,
                new ActionMessage("error.invalid.action"));
        saveErrors(request, errors);

        return mapping.findForward("error");
    }

    /**
     * Method tìm kiếm
     */
    public ActionForward search(ActionMapping mapping, ActionForm form,
                                HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        System.out.println("=== Search method ===");

        // Logic tìm kiếm
        // ...

        return mapping.findForward("success");
    }

    /**
     * Method xóa
     */
    public ActionForward delete(ActionMapping mapping, ActionForm form,
                                HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        System.out.println("=== Delete method ===");

        // Logic xóa
        // ...

        return mapping.findForward("success");
    }

    /**
     * Method thêm mới
     */
    public ActionForward add(ActionMapping mapping, ActionForm form,
                             HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        System.out.println("=== Add method ===");

        // Logic thêm mới
        // ...

        return mapping.findForward("success");
    }

    /**
     * Method chỉnh sửa
     */
    public ActionForward edit(ActionMapping mapping, ActionForm form,
                              HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        System.out.println("=== Edit method ===");

        // Logic edit
        // ...

        return mapping.findForward("success");
    }
}