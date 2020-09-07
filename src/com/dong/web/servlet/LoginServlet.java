package com.dong.web.servlet;

import com.dong.domain.User;
import com.dong.service.impl.UserServiceImpl;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet(name = "LoginServlet", urlPatterns = "/LoginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //1.设置编码
        request.setCharacterEncoding("utf-8");

        //2.获取数据
        //2.1 获取用户填写的验证码
        String verifycode = request.getParameter("verifycode");

        //3.验证码校验
        //3.1 获取随机生成的验证码信息
        HttpSession session = request.getSession();
        String checkcode_server =(String) session.getAttribute("CHECKCODE_SERVER");
        //确保验证码的一次性
        session.removeAttribute("CHECKCODE_SERVER");

        //判断
        if (!checkcode_server.equalsIgnoreCase(verifycode)){//验证码不正确
            //提示信息
            request.setAttribute("login_msg","验证码错误!");
            //跳转登录页面
            request.getRequestDispatcher("/login.jsp").forward(request,response);
            return;//验证码不正确，下面代码不需要执行了
        }
        //验证码正确

        Map<String, String[]> map = request.getParameterMap();
        //4.封装User对象
        User user = new User();
        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        //5.调用Service查询方法
        UserServiceImpl service = new UserServiceImpl();
        User loginUser = service.login(user);
        //6.判断是否登录成功
        if (loginUser != null){
            //登录成功
            //将用户存入session
            session.setAttribute("user",loginUser);
            //没有共享数据，重定向跳转页面
            response.sendRedirect(request.getContextPath()+"/index.jsp");

        }else {
            //登录失败
            //提示信息
            request.setAttribute("login_msg","用户名或密码错误!");
            //跳转登录页面
            request.getRequestDispatcher("/login.jsp").forward(request,response);
        }
    }
}
