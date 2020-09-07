package com.dong.web.servlet;

import com.dong.domain.User;
import com.dong.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "FindUserServlet", urlPatterns = "/FindUserServlet")
public class FindUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //1.获取id
        String id =request.getParameter("id");
        //2.调用Service查询
        UserServiceImpl service = new UserServiceImpl();
        User user = service.findUserById(id);

        //3.将user存入request
        request.setAttribute("user",user);
        //优化：将请求头Referer，存入request，用户跳转返回
        request.setAttribute("Referer",request.getHeader("Referer"));

        //4.转发到update.jsp
        request.getRequestDispatcher("/update.jsp").forward(request,response);
    }
}
