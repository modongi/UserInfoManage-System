package com.dong.web.servlet;

import com.dong.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "DelUserServlet", urlPatterns = "/DelUserServlet")
public class DelUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //1.设置编码
        request.setCharacterEncoding("utf-8");
        //2.获取当前用户的id
        String id = request.getParameter("id");
        //3.调用Service删除方法
        UserServiceImpl service = new UserServiceImpl();
        service.deleteUser(id);

        //4.跳转到查询所有的UserListServlet
        response.sendRedirect(request.getContextPath()+"/FindUserByPageServlet");
    }
}
