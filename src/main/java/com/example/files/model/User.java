package com.example.files.model;

import jakarta.servlet.ServletContext;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class User {
    private static final List<String> userList = new ArrayList<>();

    public static List<String> getUserList() {
        return userList;
    }

    public static String getUsersFilePath(ServletContext servletContext) {
        String USERS_FILE_PATH = "/WEB-INF/users.txt";
        return servletContext.getRealPath(USERS_FILE_PATH);
    }

    public static String getCatalogFilePath(String username, ServletContext servletContext) {
        String CATALOG_DIR = "/WEB-INF/uploads/";
        return servletContext.getRealPath(CATALOG_DIR) + username;
    }

    public static boolean isValidUser(String userName, String password, ServletContext servletContext) {
        try (BufferedReader reader = new BufferedReader(new FileReader(getUsersFilePath(servletContext)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 2 && parts[0].equals(userName) && parts[1].equals(password)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    public static void loadUsers(ServletContext servletContext) {
        try (BufferedReader br = new BufferedReader(new FileReader(getUsersFilePath(servletContext)))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] userInfo = line.split(";");
                String username = userInfo[0];
                userList.add(username);
                File userDir = new File(getCatalogFilePath(username, servletContext));
                userDir.mkdirs();
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public static boolean isUserExists(String userName, ServletContext servletContext) {
        try (BufferedReader reader = new BufferedReader(new FileReader(User.getUsersFilePath(servletContext)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length > 0 && parts[0].equals(userName)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    public static void addUser(String userName, String password, ServletContext servletContext) {

        try (FileWriter writer = new FileWriter(User.getUsersFilePath(servletContext), true)) {
            if (new File(User.getUsersFilePath(servletContext)).length() > 0) {
                writer.write("\n");
            }
            writer.write(userName + ";" + password);

            File userDir = new File(User.getCatalogFilePath(userName, servletContext));
            boolean mkdirsResult = userDir.mkdirs();

            if (!mkdirsResult) {
                System.out.println("Не удалось создать каталог для пользователя " + userName);
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void changePassword(String userName, String newPassword, ServletContext servletContext) {
        Path filePath = Path.of(getUsersFilePath(servletContext));

        try {
            List<String> lines = Files.readAllLines(filePath);

            for (int i = 0; i < lines.size(); i++) {
                String[] parts = lines.get(i).split(";");
                if (parts.length == 2 && parts[0].equals(userName)) {
                    lines.set(i, userName + ";" + newPassword);
                    break;
                }
            }

            Files.write(filePath, lines);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void deleteAccount(String userName, ServletContext servletContext) {
        Path filePath = Path.of(User.getUsersFilePath(servletContext));

        try {
            List<String> lines = Files.readAllLines(filePath);

            for (int i = 0; i < lines.size(); i++) {
                String[] parts = lines.get(i).split(";");
                if (parts.length == 2 && parts[0].equals(userName)) {
                    lines.remove(i);
                    break;
                }
            }
            userList.remove(userName);
            Files.write(filePath, lines);
            com.example.files.model.Files.deleteAllUserFiles(userName, servletContext);

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

}
