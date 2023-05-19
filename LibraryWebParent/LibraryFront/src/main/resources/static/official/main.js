(function () {
    angular
        .module('employee-front', ['ngRoute', 'ngStorage'])
        .run(run);



    function run($rootScope, $http, $localStorage) {

        if ($localStorage.biblioUser) {
            try {
                let jwt = $localStorage.biblioUser.token;
                let payload = JSON.parse(atob(jwt.split('.')[1]));
                let currentTime = parseInt(new Date().getTime() / 1000);
                if (currentTime > payload.exp) {
                    console.log("Token is expired!!!");
                    delete $localStorage.biblioUser;
                    $http.defaults.headers.common.Authorization = '';
                }
            } catch (e) {
            }

            if ($localStorage.biblioUser) {
                $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.biblioUser.token;
            }
        }
    }
})();

angular.module('employee-front').controller('indexController', function ($rootScope, $scope, $http, $localStorage) {
    const contextPath = 'http://' + window.location.hostname + ':5555' + '/auth'


    $scope.tryToAuth = function () {
        $http.post(contextPath + '/auth', $scope.user)
            .then(function successCallback(response) {
                if (response.data.token) {
                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                    $localStorage.biblioUser = {
                        username: $scope.user.username,
                        token: response.data.token,
                        roles: response.data.roles
                    };

                    $scope.username = $localStorage.biblioUser.username
                    $scope.user.username = null;
                    $scope.user.password = null;
                    location.reload();
                }
            }, function errorCallback(response) {
                alert(response.data.message);
            });
    };


    $scope.tryToLogout = function () {
        $scope.clearUser();
        if ($scope.user.username) {
            $scope.user.username = null;
        }
        if ($scope.user.password) {
            $scope.user.password = null;
        }
        window.location.href = 'http://' + window.location.hostname + ':5555/core';
    };

    $scope.clearUser = function () {
        delete $localStorage.biblioUser;
        $http.defaults.headers.common.Authorization = '';
        location.reload();
    };

    $rootScope.isUserLoggedIn = function () {
        if ($localStorage.biblioUser) {
            $scope.username = $localStorage.biblioUser.username
            return true;
        } else {
            return false;
        }
    };
    $rootScope.hasUserRole = function (role) {
        if (!$rootScope.isUserLoggedIn()) return false;
        for (let i = 0; i < $localStorage.biblioUser.roles.length; i++) {
            if ($localStorage.biblioUser.roles[i].includes(role)) return true;
        }
        return false;
    }
});