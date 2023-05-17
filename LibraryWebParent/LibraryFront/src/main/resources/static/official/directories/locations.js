angular.module('employee-front').controller('locationsController', function ($rootScope, $scope, $http, $location, $localStorage) {

    const corePath = 'http://' + window.location.hostname + ':8060/official/api/v1';


    $scope.loadZones = function () {

        $http({
                url: corePath + '/storage/zones',
                method: 'GET',

            }
        ).then(function (response) {
            $scope.zoneList = response.data;
        });
    };



    $scope.loadZones();
});