angular.module('employee-front').controller('booksController', function ($rootScope, $scope, $http, $location, $localStorage) {

    const corePath = 'http://' + window.location.hostname + ':8060/official/api/v1';
    $scope.currentPage = 1;
    $scope.isEdit = false;

    $input1 = document.getElementById('input_title');
    $input2 = document.getElementById('input_author');
    $input3 = document.getElementById('input_genre');
    $input4 = document.getElementById('input_description');
    $input5 = document.getElementById('selectAge');

    $input1.onchange = function () {
        $scope.loadBooks();
    };
    $input2.onchange = function () {
        $scope.loadBooks();
    };

    $input3.onchange = function () {
        $scope.loadBooks();
    };

    $input4.onchange = function () {
        $scope.loadBooks();
    };
    $input5.onchange = function () {
        $scope.loadBooks();
    };


    $scope.loadBooks = function () {
        if ($scope.currentPage < 1) {
            $scope.currentPage = 1;
        }
        if ($scope.currentPage > $scope.pagesCount) {
            $scope.currentPage = $scope.pagesCount;
        }
        $http({
                url: corePath + '/libraryBook',
                method: 'GET',
                params: {
                    title: document.getElementById("input_title").value,
                    author: document.getElementById("input_author").value,
                    genre: document.getElementById("input_genre").value,
                    description: document.getElementById("input_description").value,
                    ageRating: document.getElementById("selectAge").value,
                    page: $scope.currentPage,
                    pageSize: 10
                }
            }
        ).then(function (response) {
            $scope.booksList = response.data.content;
            $scope.pagesCount = response.data.totalPages;
            $scope.currentPage = response.data.pageable.pageNumber + 1;
            document.getElementById("current_page-id").value = $scope.currentPage;
        });
    };
    //
    // $scope.generatePagesIndexes = function (startPage, endPage) {
    //     let arr = [];
    //     for (let i = startPage; i < endPage + 1; i++) {
    //         arr.push(i);
    //     }
    //     return arr;
    // }


    $scope.goToPage = function (page) {
        // document.getElementById("error_text").style.visibility = 'hidden';
        $scope.currentPage = page;
        $scope.loadBooks();
    };

    $scope.goToEdit = function (id) {

        $scope.isEdit = !$scope.isEdit;
        if ($scope.isEdit) {
            document.getElementById("booksList").style.display = 'none';
            document.getElementById("editForm").style.display = 'inline';
            console.log("редактор");
        } else {
            document.getElementById("booksList").style.display = 'inline';
            document.getElementById("editForm").style.display = 'none';
            console.log("список");
        }


    }


    $scope.loadBooks();
});