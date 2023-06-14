angular.module('reader-front').controller('booksReservedController', function ($scope, $http, $localStorage) {
    const contextPath = 'http://localhost:8070/reader/api/v1/reserved';
    let currentPageIndex = 1;

    $scope.loadReservedBooks = function (pageIndex = 1) {
        currentPageIndex = pageIndex;
        $http({
            // url: contextPath + 'api/v1/reserved' + $localStorage.webUser.username,
            url: contextPath + '/1',
            method: 'GET'
        }).then(function (response) {
            console.log(response);
            $scope.reservedBooks = response.data;
            $scope.paginationArray = $scope.generatePagesIndexes(1, $scope.reservedBooks.totalPages);
        });
    };

    $scope.generatePagesIndexes = function (startPage, endPage) {
        let arr = [];
        for (let i = startPage; i < endPage + 1; i++) {
            arr.push(i);
        }
        return arr;
    }

    $scope.loadReservedBooks();

    $scope.unReserveBook = function (id) {
        $http({
            url: contextPath + id,
            method: 'DELETE'
        }).then(function successCallback(response) {
            console.log(response)
            alert('Бронирование успешно отменено');
            $scope.loadReservedBooks();
        });
    };
});