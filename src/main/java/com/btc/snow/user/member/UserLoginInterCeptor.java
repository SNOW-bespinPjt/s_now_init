package com.btc.snow.user.member;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class UserLoginInterCeptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("[LoginInterCeptor] preHandle()");

        HttpSession session = request.getSession(false);
        if (session != null) {
            Object object = session.getAttribute("loginedMemberDto");

            if (object != null)
                return true;

        }

        response.sendRedirect(request.getContextPath() + "/member/member_login_form");

        return false;

    }
}
