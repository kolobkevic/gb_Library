angular.module('reader-front').controller('verificationController', function ($scope, $http) {
    const contextPath = 'http://localhost:8070/reader/';
    $scope.verified = "";

    $scope.verifyUser = function () {
        $http({
            url: contextPath + 'api/v1/users/verify',
            method: 'GET',
            params: {code: code},
        }).then(function callback(response) {
            console.log(response);
            if (response.data === "verify_success") {
                $scope.verified = "<h2>Поздравляем! Ваш аккаунт был подтвержден!</h2><p></p>" +
                    "<h3>Теперь вы можете войти в свой аккаунт</h3>";
            } else if(response.data === "verify_fail") {
                $scope.verified = "<h2>К сожалению нам не удалось подвердить ваш аккаунт</h2><p></p>" +
                    "<h3>Попробуйте заново</h3>";
            }
        })
    }

    $scope.verifyUser();
});