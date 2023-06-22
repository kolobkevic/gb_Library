angular.module('reader-front').controller('authenticationController', function ($scope, $http, $location, $localStorage) {

    const contextPath = 'http://localhost:5555/reader/api/v1/users';
    const header = document.getElementById("header");
    const footer = document.getElementById("footer");

    let hideHeaderAndFooter = function () {
        header.style.display = "none";
        footer.style.display = "none";
    };

    let loginField = document.getElementById("loginField");
    let passwordField = document.getElementById("passwordField");

    $scope.startAuthentication = function () {
        if (loginField.value.trim() === '' ||
            passwordField.value.trim() === '') {
            alert("Все поля должы быть заполены!");
            return;
        }
        $http.post('http://localhost:5555/auth/authenticate', $scope.auth_user)
            .then(function successCallback(response) {
                if (response.data.token) {
                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                    $localStorage.webUser = {username: $scope.auth_user.email, token: response.data.token};

                    $scope.auth_user.email = null;
                    $scope.auth_user.password = null;
                }
            }, function errorCallback(response) {
            });
        setTimeout(function () {
            $location.path('/books_catalog');
        }, 100);
    };

    hideHeaderAndFooter();
});