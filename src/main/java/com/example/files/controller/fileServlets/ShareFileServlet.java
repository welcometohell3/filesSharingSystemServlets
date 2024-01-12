package com.example.files.controller.fileServlets;

import com.example.files.model.Files;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/share")
public class ShareFileServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String sourceUser = request.getParameter("sourceUser");
        String targetUser = request.getParameter("targetUser");
        String fileName = request.getParameter("fileName");

        Files.shareFile(sourceUser, targetUser, fileName, getServletContext());

        var session = request.getSession();
        session.setAttribute("user", targetUser);
        response.sendRedirect(request.getContextPath() + "/share.jsp?to=" + targetUser + "&success=true");
    }
}