<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%
    session = request.getSession();
    String userName = (String) session.getAttribute("userName");
    if (userName != null) {
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Мой профиль</title>
    <link rel="stylesheet" href="css/style.css">
    <style>

        .container {
            max-width: 600px;
            margin: 50px auto;
            padding: 20px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            display: flex;
            flex-direction: column;
        }

        label {
            flex: 1;
            margin-bottom: 8px;
        }

        input[type="password"] {
            width: calc(100% - 16px);
            padding: 8px;
            margin-bottom: 15px;
            box-sizing: border-box;
            display: inline-block;
        }

        input[type="submit"] {
            background-color: #2196F3;
            border: none;
            padding: 10px 15px;
            font-size: 16px;
            cursor: pointer;
            color: white;
            margin-left: 8px;
            display: inline-block;
        }

        input[type="submit"]:hover {
            background-color: #0b7dda;
        }


        .success-message {
            color: lightgreen;
            margin-top: 10px;
        }

        .centered-form {
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .centered-form form {
            text-align: center;
        }

        .centered-form input[type="submit"]:hover {
            background-color: lightcoral;
        }
    </style>
</head>
<body>

<div class="navbar">
    <a href="profile.jsp">Мой профиль</a>
    <a href="catalog.jsp">Каталог файлов</a>
    <a href="share.jsp">Поделиться файлом</a>
    <a href="logout.jsp">Выход из пользователя</a>
</div>

<div class="container">
    <h1>Мой профиль</h1>
    <p>Имя пользователя: <%= userName %>
    </p>

    <form action="changePassword" method="post">
        <label for="newPassword">Новый пароль:</label>
        <input type="password" id="newPassword" name="newPassword" required>
        <input type="submit" value="Сменить пароль" style="display: inline">
        <div class="success-message" id="passwordChangeSuccess">
            Пароль успешно изменен.
        </div>
    </form>


</div>

<div class="centered-form">
    <form action="deleteAcc" method="post" onsubmit="return confirm('Вы уверены?');">
        <input type="submit" value="Удалить аккаунт" style="background-color: red">
    </form>
</div>

<script src="js/index.js"></script>
<script>
    document.addEventListener("DOMContentLoaded", function() {
        let passwordChanged =<%= session.getAttribute("passwordChanged") %>;
        const successMessage = document.getElementById("passwordChangeSuccess");

        if (passwordChanged) {
            successMessage.style.display = "block";
            setTimeout(function() {
                successMessage.style.display = "none";
            }, 5000);

            <% session.setAttribute("passwordChanged", null); %>
        } else {
            successMessage.style.display = "none";
        }
    });
</script>

</body>
</html>
<%
    } else {
        response.sendRedirect("login.jsp");
    }
%>
