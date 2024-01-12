package com.example.files.controller.fileServlets;

import com.example.files.model.Files;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

@WebServlet("/download")
public class DownloadFileServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String userName = (String) request.getSession().getAttribute("userName");
        String fileName = request.getParameter("fileName");

        response.setContentType("APPLICATION/OCTET-STREAM");
        response.setHeader("Content-Disposition", "attachment; filename=\"" +
                new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1) + "\"");

        try (OutputStream outStream = response.getOutputStream()) {
            Files.downloadFile(userName, fileName, outStream,getServletContext());
        }
    }
}