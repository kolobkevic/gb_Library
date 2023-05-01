angular.module('employee-front').controller('genresController', function ($rootScope, $scope, $http, $location, $localStorage) {

    const corePath = 'http://' + window.location.hostname + ':8060/official/api/v1';

    $scope.editId = -10;

    $input1 = document.getElementById('input_title');
    $input2 = document.getElementById('input_author');
    $input3 = document.getElementById('input_genre');
    $input4 = document.getElementById('input_description');
    $input5 = document.getElementById('selectAge');


    $input1.onchange = function () {
        $scope.loadWorldBooks();
    };
    $input2.onchange = function () {
        $scope.loadWorldBooks();
    };

    $input3.onchange = function () {
        $scope.loadWorldBooks();
    };

    $input4.onchange = function () {
        $scope.loadWorldBooks();
    };
    $input5.onchange = function () {
        $scope.loadWorldBooks();
    };


    $scope.loadWorldBooks = function () {

        $http({
                url: corePath + '/worldBook',
                method: 'GET',
                params: {
                    title: document.getElementById("input_title").value,
                    genre: document.getElementById("input_genre").value,
                    author: document.getElementById("input_author").value,
                    description: document.getElementById("input_description").value,
                    ageRating: document.getElementById("selectAge").value
                }

            }
        ).then(function (response) {
            $scope.booksList = response.data;
            for (const responseElement of $scope.booksList) {
                responseElement.authorName=responseElement.author.firstName+" "+ responseElement.author.lastName;

            }

        });
    };



    $scope.loadWorldBooks();
});