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
            document.getElementById("editCurrentSector").style.display = 'none';
            document.getElementById("booksList").style.display = 'none';
            document.getElementById("zone_info").style.display = 'inline';

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

            // const button = document.getElementById("delete");
            // if ($scope.commonSectorsCount == 1) {
            //     button.disabled = true;
            //     console.log('Кнопка заблокирована');
            // } else {
            //     button.disabled = false;
            //     console.log('Кнопка разблокирована');
            // }
        });
    };

    $scope.showCreateNewZoneForm = function () {
        document.getElementById("booksList").style.display = 'none';
        document.getElementById("createNewZoneForm").style.display = 'inline';
    };

    let checkFields = function () {
        return document.getElementById("newZoneTitle").value.trim() == '' ||
            document.getElementById("newZoneSector").value.trim() == '';
    };

    $scope.addNewZone = function () {
        if ($scope.new_zone == null || checkFields()) {
            alert('Форма не заполнена');
            return;
        }

        $scope.new_zone.available = document.getElementById("newZoneSectorAvailable").value;

        $http({
            url: corePath + '/storage',
            method: 'POST',
            data: $scope.new_zone
        }).then(function successCallback(response) {
                $scope.new_zone = null;
                alert('Новая зона успешно добавлена!');
                $scope.loadZones();
                $scope.backToLocations();
            },
            function failureCallback(response) {
                alert('Не удалось создать новую зону!');
            });

    };


    $scope.deleteCurrentZone = function (zoneTitle) {
        $http({
            url: corePath + '/storage/' + zoneTitle,
            method: 'DELETE',
        })
            .then(function successCallback(response) {
                    alert('Зона успешно удалена!');
                    $scope.loadZones();
                    $scope.backToLocations();
                },
                function failureCallback(response) {
                    alert('Не удалось удалить зону!');
                });
    };

    $scope.editSector = function (zoneTitle, sectorTitle, sectorAvailable) {
        document.getElementById("booksList").style.display = 'none';
        document.getElementById("zone_info").style.display = 'none';
        document.getElementById("createNewZoneForm").style.display = 'none';
        document.getElementById("editCurrentSector").style.display = 'inline';

        $http({
            url: corePath + '/storage',
            method: 'GET',
            params: {
                zone: zoneTitle,
                sector: sectorTitle
            }
        }).then(function successCallback(response) {
                $scope.edit_sector = response.data;
            },
            function failureCallback(response) {
                alert(response.data.messages);
            });
    };

    $scope.updateSector = function () {
        if ($scope.edit_sector == null || document.getElementById("editSectorTitle").value.trim() == '') {
            alert('Форма не заполнена');
            return;
        }
        console.log($scope.edit_sector);
        $http({
            url: corePath + '/storage',
            method: 'PUT',
            data: $scope.edit_sector
        })
            .then(function successCallback(response) {
                    alert('Сектор успешно обновлен!');
                    $scope.showZoneInfo($scope.edit_sector.zone)
                },
                function failureCallback(response) {
                    alert('Не удалось обновить сектор!');
                });
    }

    $scope.backToLocations = function () {
        $scope.loadZones();
        document.getElementById("booksList").style.display = 'inline';
        document.getElementById("zone_info").style.display = 'none';
        document.getElementById("createNewZoneForm").style.display = 'none';
    };


    $scope.loadZones();
});