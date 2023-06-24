angular.module('main-page', ['ngRoute', 'ngStorage']).controller('mainPageController', function ($rootScope, $scope, $http, $location, $localStorage, $window) {
    const contextPath = 'http://localhost:5555/auth/authenticate';

    let loginField = document.getElementById("emailField");
    let passwordField = document.getElementById("passwordField");

    $scope.tryToLogout = function () {
        $scope.clearUser();
        $scope.user = null;
        $location.path('/');
    };

    $scope.clearUser = function () {
        delete $localStorage.webUser;
        $http.defaults.headers.common.Authorization = '';
    };

    $scope.isUserLoggedIn = function () {
        if ($localStorage.webUser) {
            console.log($localStorage.webUser);
            $scope.userData = $localStorage.webUser.username;
            return true;
        } else {
            return false;
        }
    };

    function checkInputFields() {
        if (loginField.value.trim() === '' ||
            passwordField.value.trim() === '') {
            alert("Все поля должы быть заполены!");
            return false;
        }
    }

    function formAuthUserModel() {
        return {
            email: loginField.value.trim(),
            password: passwordField.value.trim(),
        };
    }


    $scope.startEmployeeAuth = function () {
        if (!checkInputFields()) return;
        let auth_user = formAuthUserModel();

        console.log(auth_user);

        $http({
            url: contextPath,
            method: 'POST',
            data: auth_user
        }).then(function successCallback(response) {
            if (response.data.token) {

                // Отредактировать с ролями
                $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                $localStorage.webUser = {username: auth_user.email, token: response.data.token};

                loginField.value = ''
                passwordField.value = ''

                $window.location.href = 'official/index.html';
            }
        }, function errorCallback(response) {
            console.log(response);
        });
    };



    function fillAdminURI() {
        const adminPort = 8050;
        const adminPref = "admin";

        const currentURI = window.location;
        document.getElementById("adminEnter").href = currentURI.protocol + "//" + currentURI.hostname + ":" + adminPort + "/" + adminPref;
    }

    fillAdminURI();
});