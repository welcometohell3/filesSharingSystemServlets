package com.example.files.controller.userServlets;

import com.example.files.model.User;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/reg")
public class RegUserServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");

        if (User.isUserExists(userName, getServletContext())) {
            response.sendRedirect(request.getContextPath() + "/reg.jsp?error=1&errorCode=2");
        } else {
            User.addUser(userName, password, getServletContext());
            response.sendRedirect(request.getContextPath() + "/login.jsp");
        }
    }
}