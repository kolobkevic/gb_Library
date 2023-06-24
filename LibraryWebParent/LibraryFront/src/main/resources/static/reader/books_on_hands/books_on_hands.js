angular.module('reader-front').controller('bookOnHandsController', function ($scope, $http) {
    const contextPath = 'http://localhost:5555/reader/';
    let currentPageIndex = 1;

    $scope.loadBookOnHands = function (pageIndex = 1) {
        currentPageIndex = pageIndex;
        let unReturned = true;
        $http({
            url: contextPath + 'api/v1/book_history',
            method: 'GET',
            params: {unReturned: true}
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