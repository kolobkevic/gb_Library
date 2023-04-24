angular.module('reader-front').controller('booksController', function ($scope, $http, $location, $localStorage) {
    const contextPath = 'http://localhost:8070/books/';
    let currentPageIndex = 1;

    $scope.loadBooks = function (pageIndex = 1) {
        currentPageIndex = pageIndex;
        $http({
            url: contextPath + 'api/v1/books',
            method: 'GET',
            params: {p: pageIndex},
        }).then(function (response) {
            console.log(response);
            $scope.booksListPage = response.data;
            $scope.paginationArray = $scope.generatePagesIndexes(1, $scope.booksListPage.totalPages);
        });
    };


    $scope.reserveBook = function (inventory_number) {
        $http({
            url: 'http://localhost:8070/readers/api/v1/reserved/' + '/add/' + inventory_number, // адрес пока такой
            method: 'GET',
        }).then(function successCallback(response) {
            alert('Книга добавлена в резерв');
            console.log(response);
        });
    };

    $scope.generatePagesIndexes = function (startPage, endPage) {
        let arr = [];
        for (let i = startPage; i < endPage + 1; i++) {
            arr.push(i);
        }
        return arr;
    }

    $scope.loadBooks();
});