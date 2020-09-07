package com.dong.web.servlet;

import com.dong.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "DelSelectedServlet", urlPatterns = "/DelSelectedServlet")
public class DelSelectedServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //1.获取选中的所有id
        String[] ids = request.getParameterValues("uid");
        //2.调用Service方法，删除id对应的用户
        UserServiceImpl service = new UserServiceImpl();
        service.delSelectedUser(ids);

        //3.跳转到查询所有的Servlet
        response.sendRedirect(request.getContextPath()+"/FindUserByPageServlet");
    }
}
