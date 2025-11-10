package com.demo.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.*;
import com.demo.form.LoginForm;

public class LoginAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse response) throws Exception {
        LoginForm loginForm = (LoginForm) form;
        String username = loginForm.getUsername();
        String password = loginForm.getPassword();

        // Kiểm tra bỏ trống
        if (username == null || username.trim().isEmpty()) {
            request.setAttribute("error", "Vui lòng nhập tên đăng nhập!");
            return mapping.findForward("failure");
        }

        if (password == null || password.trim().isEmpty()) {
            request.setAttribute("error", "Vui lòng nhập mật khẩu!");
            return mapping.findForward("failure");
        }

        // Kiểm tra đăng nhập hợp lệ (hardcode)
        if ("admin".equals(username) && "123".equals(password)) {
            request.getSession().setAttribute("user", username);
            return mapping.findForward("success");
        } else {
            request.setAttribute("error", "Tên đăng nhập hoặc mật khẩu không đúng!");
            return mapping.findForward("failure");
        }
    }
}
