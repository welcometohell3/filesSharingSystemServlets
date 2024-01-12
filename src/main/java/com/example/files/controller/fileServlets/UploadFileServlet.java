package com.example.files.controller.fileServlets;

import com.example.files.model.Files;
import com.example.files.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;

@WebServlet("/upload")
@MultipartConfig(
        maxFileSize = 1024 * 1024 * 20,
        maxRequestSize = 1024 * 1024 * 20 // 10mb
)
public class UploadFileServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userName = (String) request.getSession().getAttribute("userName");
        String uploadPath = User.getCatalogFilePath(userName, getServletContext());
        boolean filename = false;
        boolean filesize = false;
        for (Part part : request.getParts()) {
            String fileName = Files.extractFileName(part);
            if (fileName.length() > 20) {
                filename = true;
                break;
            }
            if (part.getSize() > 0 && part.getSize() > 1024 * 1024 * 10) {
                filesize = true;
                break;
            }
            part.write(uploadPath + File.separator + fileName);
        }
        if (filename) {
            response.sendRedirect(request.getContextPath() + "/catalog.jsp?error=filename");
        } else if (filesize) {
            response.sendRedirect(request.getContextPath() + "/catalog.jsp?error=filesize");
        } else {
            response.sendRedirect(request.getContextPath() + "/catalog.jsp");
        }
    }

}

