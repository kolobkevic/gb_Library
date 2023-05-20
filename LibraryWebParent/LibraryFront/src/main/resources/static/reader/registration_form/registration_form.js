angular.module('reader-front').controller('registrationController', function ($scope, $http, $location) {
    const contextPath = 'http://localhost:8070/reader/';

    $scope.registerUser = function () {
        if ($scope.new_user == null) {
            alert('Форма не заполнена');
            return;
        }
        if (!$scope.checkPassword($scope.confirmPassword)){
            alert('Пароли не совпадают');
            return;
        }
        $http.post(contextPath + 'api/v1/users/create', $scope.new_user)
            .then(function successCallback (response) {
                console.log($scope.new_user);
                $scope.new_user = null;
                alert('Пользователь успешно создан');
                window.location.href= '../books/books.html'; // временное решение
            }, function failureCallback (response) {
                console.log(response);
                alert(response.data.messages);
            });
    }

    $scope.checkPassword = function (confirmPassword){
        if(confirmPassword !== $scope.new_user.password){
            return false;
        } else {
            console.log("passwords are equal");
            return true;
        }
    }
});