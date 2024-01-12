package com.example.files.controller.userServlets;

import com.example.files.model.User;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/changePassword")
public class ChangePasswordServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String newPassword = request.getParameter("newPassword");

        String userName = (String) request.getSession().getAttribute("userName");
        User.changePassword(userName, newPassword, getServletContext());
        request.getSession().setAttribute("passwordChanged", true);

        response.sendRedirect(request.getContextPath() + "/profile.jsp");
    }
}
