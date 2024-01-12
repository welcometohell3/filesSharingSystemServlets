package com.example.files.model;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.StandardCopyOption;

import static com.example.files.model.User.getCatalogFilePath;

public class Files {
    static void deleteDirectory(File directory) {
        if (directory.exists()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        deleteDirectory(file);
                    } else {
                        file.delete();
                    }
                }
            }
            directory.delete();
        }
    }

    public static void downloadFile(String userName, String fileName, OutputStream outStream, ServletContext servletContext) throws IOException {
        String filePath = getCatalogFilePath(userName, servletContext) + File.separator + fileName;
        File file = new File(filePath);

        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
            }
        }
    }

    public static void deleteFile(String userName, String fileName, ServletContext servletContext) {
        String userUploadPath = getCatalogFilePath(userName, servletContext);
        File fileToDelete = new File(userUploadPath + File.separator + fileName);

        if (fileToDelete.exists()) {
            fileToDelete.delete();
        }
    }

    public static String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");

        for (String item : items) {
            if (item.trim().startsWith("filename")) {
                return item.substring(item.indexOf("=") + 2, item.length() - 1);
            }
        }
        return "";
    }

    public static void deleteAllUserFiles(String userName, ServletContext servletContext) {
        String userUploadPath = getCatalogFilePath(userName, servletContext);
        File userUploadDir = new File(userUploadPath);
        deleteDirectory(userUploadDir);
    }

    public static void shareFile(String sourceUser, String targetUser, String fileName, ServletContext servletContext) throws IOException {
        String sourceFilePath = getCatalogFilePath(sourceUser, servletContext) + File.separator + fileName;
        String targetFilePath = getCatalogFilePath(targetUser, servletContext) + File.separator + fileName;

        File sourceFile = new File(sourceFilePath);
        File targetFile = new File(targetFilePath);
//        File newTargetFile = new File(targetFilePath + " (от " + sourceUser + ")");

        java.nio.file.Files.copy(sourceFile.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

//        newTargetFile = newTargetFile.getCanonicalFile(); // Убедитесь, что путь к файлу канонизирован
//        targetFile = targetFile.getCanonicalFile(); // Убедитесь, что путь к файлу канонизирован
    }
}
