package com.demo.action;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        // Lấy danh sách các ID được chọn
        String[] selectedIds = request.getParameterValues("selectedIds");

        if (selectedIds == null || selectedIds.length == 0) {
            // Không có ID nào được chọn
            ActionMessages errors = new ActionMessages();
            errors.add(ActionMessages.GLOBAL_MESSAGE,
                    new ActionMessage("error.delete.noselection"));
            saveErrors(request, errors);
            return mapping.findForward("failure");
        }

        try {
            // TODO: Xóa các khách hàng trong database
            // customerService.deleteByIds(selectedIds);

            System.out.println("=== Deleting Customers ===");
            System.out.println("Number of customers to delete: " + selectedIds.length);
            for (String id : selectedIds) {
                System.out.println("Deleting customer ID: " + id);
            }

            // Thông báo thành công
            ActionMessages messages = new ActionMessages();
            messages.add(ActionMessages.GLOBAL_MESSAGE,
                    new ActionMessage("message.delete.success", selectedIds.length));
            saveMessages(request, messages);

            return mapping.findForward("success");

        } catch (Exception e) {
            e.printStackTrace();

            // Thông báo lỗi
            ActionMessages errors = new ActionMessages();
            errors.add(ActionMessages.GLOBAL_MESSAGE,
                    new ActionMessage("message.delete.error"));
            saveErrors(request, errors);

            return mapping.findForward("failure");
        }
    }
}