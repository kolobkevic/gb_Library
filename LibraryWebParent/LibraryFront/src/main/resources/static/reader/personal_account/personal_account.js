angular.module('reader-front').controller('personalAccountController', function ($scope, $http, $location) {
    const contextPath = 'http://localhost:8070/reader/'; // поменять адрес на актуальный

    $scope.loadUserInformation = function () {
        $http({
            // url: contextPath + 'api/v1/book_on_hands' + $localStorage.webUser.username,
            url: contextPath + 'api/v1/users/1', // поменять адрес на актуальный
            method: 'GET',
        }).then(function (response) {
            console.log(response);
            $scope.userInfo = response.data;
            $scope.updated_user = response.data;

        });
    };

    $scope.updateUser = function () {
        $http.put(contextPath + 'api/v1/users', $scope.updated_user)
            .then(function successCallback(response) {
                $scope.updated_user = null;
                alert('Данные пользователя успешно обновлены');
                $scope.loadUserInformation();
            }, function failureCallback(response) {
                alert(response.data.messages);
            });
    }

    $scope.loadUserInformation();
});