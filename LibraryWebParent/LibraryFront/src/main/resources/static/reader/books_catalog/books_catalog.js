angular.module('reader-front', []).controller('booksCatalogController', function ($scope, $http) {
    let contextPath = 'http://localhost:8070/reader/api/v1/books_catalog';
    let defaultPage = 1;
    let currentPage = 1;

    $scope.generatePagesIndexes = function (startPage, endPage) {
        let arr = [];
        for (let i = startPage; i < endPage + 1; i++) {
            arr.push(i);
        }
        return arr;
    }

    $scope.loadBooksCatalogPage = function (pageIndex) {
        $http({
            url: contextPath,
            method: 'GET',
            params: {
                p: pageIndex
            }
        }).then(function (response) {
            currentPage = pageIndex;
            console.log(response);
            $scope.booksCatalog = response.data;
            console.log($scope.booksCatalog);
            $scope.paginationArray = $scope.generatePagesIndexes(1, $scope.booksCatalog.totalPages);
        });
    }

    $scope.searchBook = function () {
        if ($scope.search_book == null) {
            alert('Поле поиска пусто!');
        } else {
            alert($scope.search_book.searchText);
        }
    }

    $scope.loadBooksCatalogPage(defaultPage);
});