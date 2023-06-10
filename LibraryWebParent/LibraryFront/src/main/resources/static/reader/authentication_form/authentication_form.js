angular.module('reader-front').controller('authenticationController', function ($scope, $http) {
    const contextPath = 'http://localhost:8070/reader/api/v1/users';

    // document.getElementById("header").style.display = "none"
    // document.getElementById("footer").style.display = "none"

    $scope.startAuthentication = function () {
        if (document.getElementById("loginField").value.trim() == '' ||
            document.getElementById("loginField").value.trim() == '') {
            alert("Все поля должы быть заполены!");
            return;
        }


        // После успешной аутентификации переход в каталог
    };
});