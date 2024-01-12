package com.example.files.controller.userServlets;

import com.example.files.model.User;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/deleteAcc")
public class DeleteAccServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userName = (String) request.getSession().getAttribute("userName");
        User.deleteAccount(userName, getServletContext());

        response.sendRedirect(request.getContextPath() + "/login.jsp");
    }
}