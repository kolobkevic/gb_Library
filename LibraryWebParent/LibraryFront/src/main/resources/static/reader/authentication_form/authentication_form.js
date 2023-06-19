angular.module('reader-front').controller('authenticationController', function ($scope, $http, $location, $localStorage) {
    const contextPath = 'http://localhost:5555/reader/api/v1/users';
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
        alert('Все поля заполнены');
        // Заглущка для аутентификации
        $http.post('http://localhost:5555/auth/authenticate', $scope.auth_user)
            .then(function successCallback(response) {
                if (response.data.token) {
                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                    $localStorage.webUser = {username: $scope.auth_user.email, token: response.data.token};
                    $scope.auth_user.email = null;
                    $scope.auth_user.password = null;
                    $location.path('/books_catalog');
                }
            }, function errorCallback(response) {
            });
        $location.path("/books_catalog");
    };

    hideHeaderAndFooter();
});