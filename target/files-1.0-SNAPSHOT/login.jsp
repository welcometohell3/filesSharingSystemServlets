<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>

<!DOCTYPE html>
<html lang="en" class="login">
<head>
    <meta charset="UTF-8">
    <title>Аутентификация</title>
    <link href="css/style.css" rel="stylesheet" type="text/css">

</head>
<body>
<form action="login" method="post">
    <h2>Аутентификация</h2>
    <label for="userName">Логин:</label>
    <input type="text" id="userName" name="userName" required placeholder="логин">
    <br>
    <label for="password">Пароль:</label>
    <input type="password" id="password" name="password" required placeholder="пароль">
    <br>
    <input type="submit" value="Вход">
</form>
<div class="error">

</div>

<div class="no-account">
    <p>Нет аккаунта? <a href="reg.jsp">Зарегистрируйтесь</a></p>
</div>

<script src="js/index.js"></script>
</body>
</html>

