angular.module('reader-front').controller('authenticationController', function ($scope, $http, $location) {
    const contextPath = 'http://localhost:8070/reader/api/v1/users';
    const header = document.getElementById("header");
    const footer = document.getElementById("footer");

    let hideHeaderAndFooter = function () {
        header.style.display = "none";
        footer.style.display = "none";
    };

    const loginField = document.getElementById("loginField");
    const passwordField = document.getElementById("passwordField");

    $scope.startAuthentication = function () {
        if (loginField.value.trim() == '' ||
            passwordField.value.trim() == '') {
            alert("Все поля должы быть заполены!");
            return;
        }
        alert('Все поля полнены');
        // Заглущка для аутентификации
        // После успешной аутентификации переход в каталог
        $location.path("/books_catalog");
    };

    hideHeaderAndFooter();
});