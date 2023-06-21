angular.module('reader-front').controller('booksReservedController', function ($scope, $http, $localStorage) {
    const contextPath = 'http://localhost:5555/reader/api/v1/reserved';
    let currentPageIndex = 1;

    const emptyReservedBooksPanel = document.getElementById("emptyReservedBooksPanel");
    const reservedBooksTable = document.getElementById("reservedBooksTable");


    $scope.loadReservedBooks = function (pageIndex = 1) {
        currentPageIndex = pageIndex;
        $http({
            // url: contextPath + 'api/v1/reserved' + $localStorage.webUser.username,
            url: contextPath + '/1',
            method: 'GET'
        }).then(function (response) {
            console.log(response);
            if (response.data.content.length === 0) {
                emptyReservedBooksPanel.style.display = 'inline';
                reservedBooksTable.style.display = 'none';
            } else {
                emptyReservedBooksPanel.style.display = 'none';
                reservedBooksTable.style.display = 'inline';

                $scope.reservedBooks = response.data;
                $scope.paginationArray = $scope.generatePagesIndexes(1, $scope.reservedBooks.totalPages);
            }
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
            url: contextPath + '/' + id,
            method: 'DELETE'
        }).then(function successCallback(response) {
            console.log(response)
            alert('Бронирование успешно отменено');
            $scope.loadReservedBooks();
        });
    };
});