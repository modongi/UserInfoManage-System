package com.dong.web.servlet;

import com.dong.domain.PageBean;
import com.dong.domain.User;
import com.dong.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet(name = "FindUserByPageServlet", urlPatterns = "/FindUserByPageServlet")
public class FindUserByPageServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //0.设置编码
        request.setCharacterEncoding("utf-8");

        //1.获取参数
        String currentPage = request.getParameter("currentPage");//当前页码
        String rows = request.getParameter("rows");//每页显示的条数

        //分页查询优化：防止currentPage、rows为空，字符转换整数时出现转换异常
        if (currentPage == null || "".equals(currentPage)){
            currentPage = "1";
        }
        if (rows == null || "".equals(rows)){
            rows = "5";
        }

        //复杂条件查询:获取条件查询参数
        Map<String, String[]> condition = request.getParameterMap();

        //2.调用Service查询
        UserServiceImpl service = new UserServiceImpl();
        //PageBean<User> pb = service.findUserByPage(currentPage,rows);
        PageBean<User> pb = service.findUserByPage(currentPage,rows,condition);
        System.out.println(pb);//测试

        //3.将PageBean存入request域中
        request.setAttribute("pb",pb);
        //复杂条件查询优化:回显查询条件,将查询条件存入request
        request.setAttribute("condition",condition);

        //4.转发到list.jsp
        request.getRequestDispatcher("/list.jsp").forward(request,response);
    }
}
