package com.example.files.controller.userServlets;

import com.example.files.model.User;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/login")
public class UserLoginServlet extends HttpServlet {
    @Override
    public void init() {
        User.loadUsers(getServletContext());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");

        if (User.isValidUser(userName, password, getServletContext())) {
            HttpSession session = request.getSession();
            session.setAttribute("userName", userName);

            response.sendRedirect(request.getContextPath() + "/catalog.jsp");
        } else {
            response.sendRedirect(request.getContextPath() + "/login.jsp?error=1&errorCode=1");

        }
    }
}
