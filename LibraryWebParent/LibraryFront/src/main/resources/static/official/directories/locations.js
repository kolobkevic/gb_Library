angular.module('employee-front').controller('locationsController', function ($rootScope, $scope, $http, $location, $localStorage) {

    const corePath = 'http://' + window.location.hostname + ':8060/official/api/v1';


    $scope.loadZones = function () {
        $http({
                url: corePath + '/storage/zones',
                method: 'GET',
            }
        ).then(function (response) {
            console.log(response.data);
            $scope.zoneList = response.data;
        });
    };


    $scope.showZoneInfo = function (zoneTitle) {
        $http({
                url: corePath + '/storage/zones/' + zoneTitle,
                method: 'GET',
            }
        ).then(function (response) {
            document.getElementById("booksList").style.display = 'none';
            document.getElementById("zone_info").style.display = 'inline';

            console.log(response.data);
            const zoneData = response.data;

            $scope.zoneTitle = zoneData.zone;
            $scope.sectorsList = zoneData.sectors;
            $scope.commonSectorsCount = zoneData.sectors.length;
            $scope.availableCount = 0;

            $scope.unavailableCount = 0;
            for (let i = 0; i < zoneData.sectors.length; i++) {
                if (zoneData.sectors[i].isAvailable === true) $scope.availableCount += 1;
                else $scope.unavailableCount += 1;
            }
        });
    };

    $scope.showCreateNewZoneForm = function () {
        document.getElementById("booksList").style.display = 'none';
        document.getElementById("createNewZoneForm").style.display = 'inline';
    };

    let checkFields = function () {
        return $scope.new_zone.zone == null ||
        $scope.new_zone.sector == null ||
        $scope.new_zone.available == null;
    };

    $scope.addNewZone = function () {
        if ($scope.new_zone == null || checkFields()) {
            alert('Форма не заполнена');
            return;
        }
        alert('Все заполнено!');
        console.log($scope.new_zone);
        $http.post(corePath + '/storage', $scope.new_zone) // post(url', requestBody(object))
            .then(function successCallback(response) {
                    $scope.new_zone = null;
                    alert('Новая зона успешно добавлена!');
                    $scope.loadZones();
                    $scope.backToLocations();
                },
                function failureCallback(response) {
                    alert('Не удалось создать новую зону!');
                });

    };

    $scope.backToLocations = function () {
        document.getElementById("booksList").style.display = 'inline';
        document.getElementById("zone_info").style.display = 'none';
        document.getElementById("createNewZoneForm").style.display = 'none';
    };


    $scope.loadZones();
});