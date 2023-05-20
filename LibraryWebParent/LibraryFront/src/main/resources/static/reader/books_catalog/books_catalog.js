angular.module('reader-front', []).controller('booksCatalogController', function ($scope, $http) {
    let contextPath = 'http://localhost:8070/reader/api/v1/books_catalog';
    let genresPath = 'http://localhost:8070/reader/api/v1/genres';
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
                chosen_genres: $scope.chosenGenresOut,
                chosen_age_rates: $scope.chosenAgeRatings
            }
        }).then(function (response) {
            currentPage = pageIndex;
            console.log(response);
            $scope.booksCatalog = response.data;
            console.log($scope.booksCatalog);
            $scope.generatePagesIndexes($scope.booksCatalog.totalPages);
        });
    }

    $scope.loadGenres = function () {
        $http.get(genresPath)
            .then(function (response) {
                $scope.genresList = response.data;
                console.log($scope.genresList);
            });
    }

    $scope.submitCheckBox = function () {
        console.log('Кнопка нажата');
        let radioGenres = document.getElementsByName("bookGenre");
        let ageRates = document.getElementsByName("ageRate");

        console.log(radioGenres);

        $scope.chosenGenresOut = [];
        $scope.chosenAgeRatings = [];

        for (let i = 0; i < radioGenres.length; i++) {
            if (radioGenres[i].checked) {
                console.log(radioGenres[i].value + ' is checked');
                if (radioGenres[i].value == 'Все') continue;
                $scope.chosenGenresOut.push(radioGenres[i].value);
            }
        }

        console.log($scope.chosenGenresOut);

        for (let i = 0; i < ageRates.length; i++) {
            if (ageRates[i].checked) {
                console.log(ageRates[i].value + ' is checked');
                $scope.chosenAgeRatings.push(ageRates[i].value);
            }
        }

        console.log($scope.chosenAgeRatings);
        $scope.loadBooksCatalogPage();
    }

    $scope.loadBooksCatalogPage();
    $scope.loadGenres();
});