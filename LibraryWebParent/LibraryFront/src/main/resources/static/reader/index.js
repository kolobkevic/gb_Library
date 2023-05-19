(function () {
    angular
        .module('reader-front', ['ngRoute', 'ngStorage'])
        .run(run);

    function run($rootScope, $http, $localStorage) {
        if ($localStorage.webUser) {
            try {
                let jwt = $localStorage.webUser.token;
                let payload = JSON.parse(atob(jwt.split('.')[1]));
                let currentTime = parseInt(new Date().getTime() / 1000);
                if (currentTime > payload.exp) {
                    console.log("Token is expired!!!");
                    delete $localStorage.webUser;
                    $http.defaults.headers.common.Authorization = '';
                }
            } catch (e) {
            }

            if ($localStorage.webUser) {
                $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.webUser.token;
            }
        }
    }
})();

angular.module('reader-front').controller('indexController', function ($rootScope, $scope, $http, $location, $localStorage) {
    const contextPath = 'http://localhost:8070/reader';

    $scope.tryToAuth = function () {
        $http.post('http://localhost:5555/auth/auth', $scope.user) // поменять адрес на актуальный
            .then(function successCallback(response) {
                if (response.data.token) {
                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                    $localStorage.webUser = {username: $scope.user.username, token: response.data.token};
                    $scope.user.username = null;
                    $scope.user.password = null;

                    $location.path('/books');
                }
            }, function errorCallback(response) {
            });
    };

    $scope.tryToLogout = function () {
        $scope.clearUser();
        $scope.user = null;
        $location.path('/');
    };

    $scope.clearUser = function () {
        delete $localStorage.webUser;
        $http.defaults.headers.common.Authorization = '';
    };

    $rootScope.isUserLoggedIn = function () {
        if ($localStorage.webUser) {
            return true;
        } else {
            return false;
        }
    };
});