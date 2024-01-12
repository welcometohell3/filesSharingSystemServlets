<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>

<!DOCTYPE html>
<html lang="en" class="login">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Регистрация</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<form action="reg" method="post">
    <h2>Регистрация</h2>
    <label for="userName">Логин:</label>
    <input type="text" id="userName" name="userName" required placeholder="логин">
    <br>
    <label for="password">Пароль:</label>
    <input type="password" id="password" name="password" required placeholder="пароль">
    <br>
    <input type="submit" value="Зарегистрироваться">
</form>

<div class="error">

</div>

<div class="no-account">
    <p>Уже есть аккаунт? <a href="login.jsp ">Войдите</a></p>
</div>


<script src="js/index.js"></script>

</body>
</html>
