angular.module('reader-front').controller('userHistoryController', function ($scope, $http) {
    const contextPath = 'http://localhost:8070//reader/books/'; // поменять адрес на актуальный
    let currentPageIndex = 1;

    $scope.loadCurrentBooks = function (pageIndex = 1) {
        currentPageIndex = pageIndex;
        $http({
            url: contextPath + 'api/v1/books', // поменять адрес на актуальный
            method: 'GET',
            params: {p: pageIndex},
        }).then(function (response) {
            console.log(response);
            $scope.booksListPage = response.data;
            $scope.paginationArray = $scope.generatePagesIndexes(1, $scope.booksListPage.totalPages);
        });
    };

    $scope.generatePagesIndexes = function (startPage, endPage) {
        let arr = [];
        for (let i = startPage; i < endPage + 1; i++) {
            arr.push(i);
        }
        return arr;
    }

    $scope.loadCurrentBooks();
});