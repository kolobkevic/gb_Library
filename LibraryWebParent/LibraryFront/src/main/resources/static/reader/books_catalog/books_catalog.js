angular.module('reader-front', []).controller('booksCatalogController', function ($scope, $http) {
    let contextPath = 'http://localhost:8070/reader/api/v1/books_catalog';
    let defaultPage = 1;
    let currentPage = 1;

    $scope.generatePagesIndexes = function (totalPages) {
        let arr = [];
        for (let i = 0; i < totalPages; i++) {
            arr.push(i + 1);
        }
        $scope.pagesNav = arr;
    }

    $scope.loadBooksCatalogPage = function (pageIndex = 1) {

        $http({
            url: contextPath,
            method: 'GET',
            params: {
                p: pageIndex,
                search_text: $scope.filter ? $scope.filter.search_text : null,
                genre: $scope.bookGenre
                // age_rate: $scope.ageRate
            }
        }).then(function (response) {
            currentPage = pageIndex;
            console.log(response);
            $scope.booksCatalog = response.data;
            console.log($scope.booksCatalog);
            $scope.generatePagesIndexes($scope.booksCatalog.totalPages);
        });
    }

    $scope.submitCheckBox = function () {
        let radioGenres = document.getElementsByName("bookGenre");
        let ageRates = document.getElementsByName("ageRate");

        for (let i = 0; i < radioGenres.length; i++) {
            if (radioGenres[i].checked) {
                $scope.bookGenre = radioGenres[i].value;
            }
        }

        // for (let i = 0; i < ageRates.length; i++) {
        //     if (ageRates[i].checked) {
        //         $scope.ageRate = ageRates[i].value;
        //     }
        // }

        $scope.loadBooksCatalogPage();
    }

    $scope.loadBooksCatalogPage();
});