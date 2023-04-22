var libApp = angular.module('front', ['ngRoute', 'ngStorage']);


libApp.controller('entranceController', function ($scope, $http, $localstorage) {
    $scope.enterOfficial = function (){
        console.log("go to official");
    };

    $scope.enterReader = function (){
        console.log("go to reader");
    };
});
