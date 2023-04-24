(function () {
    angular
        .module('reader-front', ['ngRoute', 'ngStorage'])
        .config(config)
        .run(run);

    function config($routeProvider) {
        $routeProvider
            .when('/', {
                templateUrl: 'index.html',
                controller: 'entranceController'
            })
            .when('/books', {
                templateUrl: 'books/books.html',
                controller: 'booksController'
            })
            .when('/current_books', {
                templateUrl: 'current_books/current_books.html',
                controller: 'currentBooksController'
            })
            .when('/reserved_books', {
                templateUrl: 'reserved_books/reserved_books.html',
                controller: 'reservedBooksController'
            })
            .when('/personal_account', {
                templateUrl: 'personal_account/personal_account.html',
                controller: 'personalAccountController'
            })
            .when('/orders', {
                templateUrl: 'orders/orders.html',
                controller: 'ordersController'
            })
            .otherwise({
                redirectTo: '/'
            });
    }

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
        $http.post('http://localhost:5555/auth/auth', $scope.user)
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