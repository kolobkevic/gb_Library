(function () {
    angular
        .module('reader-front', ['ngRoute', 'ngStorage'])
        .config(config)
        .run(run);

    function config($routeProvider) {
        $routeProvider
            // .when('/', {
            //     templateUrl: 'static/index.html',
            // })
            .when('/books_catalog', {
                templateUrl: 'books_catalog/books_catalog.html',
                controller: 'booksCatalogController'
            })
            .when('/reader_wishlist', {
                templateUrl: 'reader_wishlist/reader_wishlist.html',
                controller: 'booksWishlistController'
            })
            .when('/books_reserved', {
                templateUrl: 'books_reserved/books_reserved.html',
                controller: 'booksReservedController'
            })
            .when('/books_on_hands', {
                templateUrl: 'books_on_hands/books_on_hands.html',
                controller: 'bookOnHandsController'
            })
            .when('/books_history', {
                templateUrl: 'books_history/books_history.html',
                controller: 'bookHistoryController'
            })
            .when('/personal_account', {
                templateUrl: 'personal_account/personal_account.html',
                controller: 'personalAccountController'
            })
            .when('/registration', {
                templateUrl: 'registration_form/registration_form.html',
                controller: 'registrationController'
            })
            .when('/authentication', {
                templateUrl: 'authentication_form/authentication_form.html',
                controller: 'authenticationController'
            })
            .otherwise({
                redirectTo: '/books_catalog'
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