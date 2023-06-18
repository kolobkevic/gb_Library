angular.module('reader-front').controller('booksCatalogController', function ($scope, $http, $location) {

    let contextPath = 'http://localhost:5555/reader/api/v1/books_catalog';
    let usersPath = 'http://localhost:5555/reader/api/v1/users';
    let booksWishlistPath = 'http://localhost:5555/reader/api/v1/wishlist';
    let reservedPath = 'http://localhost:5555/reader/api/v1/reserved';
    let libraryBooksPath = 'http://localhost:5555/reader/api/v1/library_books';
    let genresPath = 'http://localhost:5555/reader/api/v1/genres';
    let currentPage = 1;

    let outputData = [];


    const header = document.getElementById("header");
    const footer = document.getElementById("footer");

    let showHeaderAndFooter = function () {
        if (header.style.display == "none" && footer.style.display == "none") {
            header.style.display = "inline";
            footer.style.display = "inline";
        }
    };


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
        let genres = document.getElementsByName("bookGenre");
        let ageRates = document.getElementsByName("ageRate");

        console.log(genres);

        $scope.chosenGenresOut = [];
        $scope.chosenAgeRatings = [];

        for (let i = 0; i < genres.length; i++) {
            if (genres[i].checked) {
                console.log(genres[i].value + ' is checked');
                if (genres[i].value == 'Все') continue;
                $scope.chosenGenresOut.push(genres[i].value);
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

    // Скорректировать после добавления аутентификации
    $scope.addToBooksWishlist = function (bookId) {
        $http.get(booksWishlistPath + '/1/add/' + bookId)
            .then(function (response) {
                console.log(bookId);
                console.log('Книга успешно добавлена в список');
                bookWishListData();
                $scope.loadBooksCatalogPage();
            });
    };

    let bookWishListData = function (pageIndex = 0) {
        $http({
            url: booksWishlistPath,
            method: 'GET',
            params: {
                p: pageIndex
            }
        }).then(function (response) {
            let responseData = response.data.content;

            for (let i = 0; i < responseData.length; i++) {
                outputData.push(responseData[i].book.title);
            }
        });
    };

    $scope.wishlistContainsBook = function (bookTitle) {
        let button = document.getElementById(bookTitle);
        if (outputData.includes(bookTitle)) {
            button.textContent = 'В желаемом';
            return true;
        } else {
            button.textContent = 'В желаемое';
            return false;
        }
    };

    let findLibraryBook = function (id) {
        $http({
            url: libraryBooksPath + '/' + id,
            method: 'GET'
        }).then(function (response) {
            let libraryBooks = response.data;
            $scope.libraryBook = libraryBooks.find(el => el.available === true);
        });
    };

    let findUserData = function (id) {
        $http({
            url: usersPath + '/' + id,
            method: 'GET'
        }).then(function (response) {
            $scope.userData = response.data;
        });
    }


    $scope.reserveThisBook = function (worldBook) {
        findLibraryBook(worldBook.id);
        // TODO Отредактировать по аутентификации
        findUserData(1);

        setTimeout(function () {
            if ($scope.userData != null && $scope.libraryBook != null) {
                let reservedBookJSON = {
                    'id' : $scope.libraryBook.id,
                    'user': $scope.userData,
                    'library_book': $scope.libraryBook,
                    'world_book': worldBook
                }
                console.log(reservedBookJSON);

                $http({
                    url: reservedPath,
                    method: 'POST',
                    data: reservedBookJSON
                }).then(function successCallback(response) {
                        reservedBookJSON = null;
                        alert('Книга ' + worldBook.title + ' автора ' + worldBook.authorDTO.firstName + ' ' + worldBook.authorDTO.lastName + ' забронирована');
                        // TODO Отредактировать по аутентификации
                        $location.path('/books_reserved');
                    },
                    function failureCallback(response) {
                        alert(response.data.messages);
                    });
            }
        }, 200);
};


showHeaderAndFooter();
// bookWishListData();
$scope.loadBooksCatalogPage();
$scope.loadGenres();
})
;