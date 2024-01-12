<%@ page import="java.io.File" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.files.model.User" %>
<%@ page import="java.util.Arrays" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<%
    session = request.getSession();
    String userName = (String) session.getAttribute("userName");

    if (userName != null) {
        String userUploadPath = User.getCatalogFilePath(userName, session.getServletContext());
        File userUploadDir = new File(userUploadPath);
        String[] files = userUploadDir.list();
        List<String> userList = User.getUserList();
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Поделиться файлом</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<div class="navbar">
    <a href="profile.jsp">Мой профиль</a>
    <a href="catalog.jsp">Каталог файлов</a>
    <a href="logout.jsp">Выход из пользователя</a>
</div>

<div class="header">
    Поделиться файлами
</div>
<%
    if (files.length > 0) {
%>
<div class="container">
    <table class="tbl">
        <thead>
        <tr>
            <th>Имя пользователя</th>
            <th>Отправить файл</th>
        </tr>
        </thead>
        <tbody>
        <%
            for (String user : userList) {
                if (!user.isEmpty() && !user.equals(" ") && !user.equals(userName)) {
        %>
        <tr>
            <td>
                <div class="str"><%= user %>
                </div>
            </td>

            <td>
                <form action="share" method="post" style="display:inline; position: relative;">
                    <div style="display: flex; align-items: center; justify-content: center;">
                        <input type="hidden" name="sourceUser" value="<%= userName %>">
                        <input type="hidden" name="targetUser" value="<%= user %>">
                        <label>
                            <select name="fileName" class="slct">
                                <%
                                    for (String file : files) {
                                %>
                                <option class="fname" value="<%= file %>" style="margin: 5px; "><%= file %>
                                </option>
                                <%
                                    }
                                %>
                            </select>
                        </label>
                        <div style="display: flex; align-items: center; margin-left: 10px;">
                            <input type="image" src="img/send.png" alt="Отправить файл"
                                   style="width: 35px; height: 30px; border: none; background: none; cursor: pointer; vertical-align: middle; margin-bottom: 5px;">
                            <%
                                String successParam = request.getParameter("success");
                                String toParam = request.getParameter("to");
                                if (successParam != null && successParam.equals("true") && toParam.equals(user)) {
                            %>
                            <div style="color: lightgreen; position: absolute; left: 130px;">
                                Файл успешно отправлен
                            </div>
                            <%
                                }
                            %>
                        </div>
                    </div>
                </form>
            </td>
        </tr>
        <%
                }
            }
        %>
        </tbody>
    </table>
</div>
<%
} else {
%>
<p>Вы пока что не загрузили ни одного файла</p>
<%
    }
%>
<script src="js/index.js"></script>
</body>
</html>
<%
    } else {
        response.sendRedirect("login.jsp");
    }
%>
