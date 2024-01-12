function submitForm(tg) {
    document.getElementById(tg).submit();
}
const errorDiv = document.querySelector('.error');

document.addEventListener("DOMContentLoaded", function () {
    const urlParams = new URLSearchParams(window.location.search);
    const errorParam = urlParams.get('error');
    const errorCodeParam = urlParams.get('errorCode');
    if (errorDiv && errorParam && errorParam && errorCodeParam === '1') {
        errorDiv.innerHTML = 'Неверный логин или пароль';
    }
    if (errorDiv && errorParam && errorParam && errorCodeParam === '2') {
        errorDiv.innerHTML = 'Пользователь с таким именем уже существует';
    }
});




