angular.module('reader-front').controller('bookOnHandsController', function ($scope, $http, $localStorage) {
    const contextPath = 'http://localhost:8070/reader/'; // поменять адрес на актуальный
    let currentPageIndex = 1;

    $scope.loadBookOnHands = function (pageIndex = 1) {
        currentPageIndex = pageIndex;
        $http({
            // url: contextPath + 'api/v1/book_on_hands' + $localStorage.webUser.username,
            url: contextPath + 'api/v1/book_on_hands/1', // поменять адрес на актуальный
            method: 'GET',
        }).then(function (response) {
            console.log(response);
            $scope.booksOnHandsPage = response.data;
            $scope.paginationArray = $scope.generatePagesIndexes(1, $scope.booksOnHandsPage.totalPages);
        });
    };

    $scope.generatePagesIndexes = function (startPage, endPage) {
        let arr = [];
        for (let i = startPage; i < endPage + 1; i++) {
            arr.push(i);
        }
        return arr;
    }

    $scope.generateDateOfReturn = function (takeAt = $scope.booksOnHandsPage.content.takenAt){
        let date = Date.parse(takeAt) + Date.parse("30");
        return date + 30;
    }

    $scope.loadBookOnHands();
});