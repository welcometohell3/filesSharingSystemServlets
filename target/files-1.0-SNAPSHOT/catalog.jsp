<%@ page import="java.io.File" %>
<%@ page import="com.example.files.model.User" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%
    session = request.getSession();
    String userName = (String) session.getAttribute("userName");

    if (userName != null) {
        String userUploadPath = User.getCatalogFilePath(userName, session.getServletContext());
        File userUploadDir = new File(userUploadPath);
        String[] files = userUploadDir.list();
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Каталог файлов</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

<div class="navbar">
    <a href="profile.jsp">Мой профиль</a>
    <a href="#" id="fileUploadLink">Загрузить файл</a>
    <a href="share.jsp">Поделиться файлом</a>
    <a href="logout.jsp">Выход из пользователя</a>

</div>
<form id="fileUploadForm" action="upload" method="post" enctype="multipart/form-data">
    <input type="file" name="file" id="fileInput" style="display:none;" onchange="submitForm('fileUploadForm')">
    <input type="submit" value="Загрузить файл" style="display:none;">
</form>
<div style="color: red; text-align: center;">
    <% if (request.getParameter("error") != null) {
        String errorType = request.getParameter("error");
        if ("filename".equals(errorType)) {
    %>
    Название файла слишком длинное. Максимальная длина - 20 символов.
    <%
    } else if ("filesize".equals(errorType)) {
    %>
    Размер файла слишком большой. Максимальный размер - 10 МБ.
    <%
            }
        } %>
</div>
<div style="display: flex; align-items: center; justify-content: center;margin: 30px;font-size: 30px">
    <div style="margin-right: 10px;">Каталог файлов пользователя</div>
    <div style="color: deepskyblue"><%= userName %>
    </div>
</div>


<%
    if (files != null && files.length > 0) {
%>
<div class="container">
    <table class="tbl">
        <thead>
        <tr>
            <th>Имя файла</th>
            <th>Действия</th>
        </tr>
        </thead>
        <tbody>
        <%
            for (String file : files) {
        %>
        <tr>
            <td>
                <div class="str"><%= file %>
                </div>
            </td>
            <td>
                <form action="download" method="post" style="display:inline;">
                    <input type="hidden" name="fileName" value="<%= file %>">
                    <button type="submit" style="border: none; background: none; cursor: pointer;">
                        <img src="img/download.png" alt="Скачать файл" style="width: 25px; height: 25px;">
                    </button>
                </form>
                <form action="delete" method="post" style="display:inline;">
                    <input type="hidden" name="fileName" value="<%= file %>">
                    <button type="submit" style="border: none; background: none; cursor: pointer;">
                        <img src="img/trash.png" alt="Удалить файл" style="width: 25px; height: 25px;">
                    </button>
                </form>

            </td>
        </tr>
        <%
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
<%
    } else {
        response.sendRedirect("login.jsp");
    }
%>
<script>
    document.getElementById('fileUploadLink').addEventListener('click', function () {
        document.getElementById('fileInput').click();
    });
</script>
<script src="js/index.js"></script>
</body>
</html>
