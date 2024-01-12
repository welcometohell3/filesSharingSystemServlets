package com.example.files.controller.fileServlets;

import com.example.files.model.Files;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/delete")
public class DeleteFileServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String fileName = request.getParameter("fileName");

        String userName = (String) request.getSession().getAttribute("userName");
        Files.deleteFile(userName, fileName, getServletContext());
        response.sendRedirect(request.getContextPath() + "/catalog.jsp");
    }
}
