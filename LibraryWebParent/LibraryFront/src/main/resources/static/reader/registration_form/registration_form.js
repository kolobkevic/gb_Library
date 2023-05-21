angular.module('reader-front').controller('registrationController', function ($scope, $http) {
    const contextPath = 'http://localhost:8070/reader/';
    let isPasswordReady = false;
    $scope.isEmailReady = false;
    let p1;

    $scope.registerUser = function () {
        $http.post(contextPath + 'api/v1/users/create', $scope.new_user)
            .then(function successCallback(response) {
                console.log($scope.new_user);
                $scope.new_user = null;
                alert('Пользователь успешно создан');
                window.location.href = '../books/books.html'; // временное решение
            }, function failureCallback(response) {
                console.log(response);
                alert(response.data.messages);
            });
    }

    $scope.checkEmail = function (email) {
        p1 = new Promise((resolve, reject) => {
            resolve(
                $http({
                    url: contextPath + 'api/v1/users/check_email',
                    method: 'GET',
                    params: {email: email},
                    transformResponse: [function (data) {
                        return data;
                    }]
                }).then(function callback(response) {
                    if (response.data === "Duplicated") {
                        alert('Пользователь с таким email уже зарегестрирован');
                        return false;
                    } else return true;
                })
            );
        });
    }

    $scope.checkPassword = function (confirmPassword) {
        if (confirmPassword !== $scope.new_user.password) {
            return false;
        } else {
            console.log("passwords are equal");
            return true;
        }
    }

    $scope.preregisterCheck = function () {
        $scope.checkEmail($scope.new_user.email);

        p1.then((value) => {
            if (value === true && isPasswordReady === true) {
                $scope.registerUser();
            }
        });

        if (!$scope.checkPassword($scope.confirmPassword)) {
            isPasswordReady = false;
            alert('Пароли не совпадают');
        } else isPasswordReady = true;
    }
});