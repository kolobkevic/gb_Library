(function () {
    angular
        .module('employee-front', ['ngRoute', 'ngStorage'])
        .config(config)
        .run(run);

    function config($routeProvider) {
        $routeProvider
            .when('/', {
                templateUrl: 'welcome/welcome.html',
                controller: 'welcomeController'
            })
            .when('/library_books', {
                templateUrl: 'library_books/library_books.html',
                controller: 'libraryBooksController'
            })
            .when('/readers', {
                templateUrl: 'readers/readers.html',
                controller: 'readersController'
            })
            .when('/reserved_data/orders', {
                templateUrl: 'reserved_data/orders/orders.html',
                controller: 'ordersController'
            })
            .when('/reserved_data/active_orders', {
                templateUrl: 'reserved_data/active_orders/active_orders.html',
                controller: 'activeOrdersController'
            })
            .when('/reserved_data/orders_history', {
                templateUrl: 'reserved_data/orders_history/orders_history.html',
                controller: 'ordersHistoryController'
            })
            .when('/directories/authors', {
                templateUrl: 'directories/authors/authors.html',
                controller: 'authorsController'
            })
            .when('/directories/genres', {
                templateUrl: 'directories/genres/genres.html',
                controller: 'genresController'
            })
            .when('/directories/locations', {
                templateUrl: 'directories/locations/locations.html',
                controller: 'locationsController'
            })
            .when('/directories/world_books', {
                templateUrl: 'directories/world_books/world_books.html',
                controller: 'worldBooksController'
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

angular.module('employee-front').controller('indexController', function ($rootScope, $scope, $http, $location, $localStorage, $window) {
    const contextPath = 'http://localhost:5555/official';

    $scope.tryToLogout = function () {
        $scope.clearUser();
        $scope.user = null;
        $window.location.href = '../index.html';
    };

    $scope.clearUser = function () {
        delete $localStorage.webUser;
        $http.defaults.headers.common.Authorization = '';
    };

    $rootScope.isUserLoggedIn = function () {
        if ($localStorage.webUser) {
            console.log($localStorage.webUser);
            $scope.userData = $localStorage.webUser.username;
            return true;
        } else {
            return false;
        }
    };
});