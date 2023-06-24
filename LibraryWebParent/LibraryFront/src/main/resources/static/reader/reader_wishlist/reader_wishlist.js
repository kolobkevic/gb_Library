angular.module('reader-front').controller('booksWishlistController', function ($scope, $http, $location, $localStorage) {
    let contextPath = 'http://localhost:5555/reader/api/v1/wishlist';
    let reservedPath = 'http://localhost:5555/reader/api/v1/reserved';
    let usersPath = 'http://localhost:5555/reader/api/v1/users';
    let libraryBooksPath = 'http://localhost:5555/reader/api/v1/library_books';

    let defaultPage = 1;
    let currentPage = 1;

    let outputReservedListData = [];

    const navigation = document.getElementById("navigation");
    const emptyWishlistPanel = document.getElementById("emptyWishlistPanel");
    const wishlistTable = document.getElementById("wishlistTable");

    const acceptReserveModalWindow = document.getElementById("acceptReserveModalWindow");
    const confirmReserveModalWindow = document.getElementById("confirmReserveModalWindow");
    const reserveBtn = document.getElementById("reserveBtn");
    const cancelReserveBtn = document.getElementById("cancelReserveBtn");
    const confirmedReserveBtn = document.getElementById("confirmedReserveBtn");
    const cancelConfirmReserveBtn = document.getElementById("cancelConfirmReserveBtn");


    $scope.generatePagesIndexes = function (totalPages) {
        let arr = [];
        for (let i = 0; i < totalPages; i++) {
            arr.push(i + 1);
        }
        $scope.pagesNav = arr;
    };

    $scope.loadBooksWishlist = function (pageIndex = defaultPage) {
        $http({
            url: contextPath + '/' + $localStorage.webUser.username,
            method: 'GET',
            params: {
                p: pageIndex
            }
        }).then(function (response) {
            currentPage = pageIndex;
            if (response.data.content.length === 0) {
                emptyWishlistPanel.style.display = 'inline';
                wishlistTable.style.display = 'none';
                navigation.style.display = 'none';
            } else {
                emptyWishlistPanel.style.display = 'none';
                wishlistTable.style.display = 'inline';
                navigation.style.display = 'inline';
            }
            $scope.booksWishlist = response.data;
            console.log($scope.booksWishlist);
            $scope.generatePagesIndexes($scope.booksWishlist.totalPages);
        });
    };

    $scope.removeCurrentBookFromWishlist = function (recordId) {
        $http({
            url: contextPath + '/' + recordId,
            method: 'DELETE'
        }).then(function () {
           $scope.loadBooksWishlist(currentPage);
        });
    };

    let bookReservedData = function () {
        $http({
            url: reservedPath + '/' + $localStorage.webUser.username,
            method: 'GET'
        }).then(function (response) {
            let responseData = response.data.content;

            for (let i = 0; i < responseData.length; i++) {
                outputReservedListData.push(responseData[i].world_book.title);
            }
        });
    };

    $scope.reservedContainsBook = function (book) {
        let button = document.getElementById(book.id);
        if (outputReservedListData.includes(book.title)) {
            button.textContent = 'Забронировано';
            return true;
        } else {
            button.textContent = 'Забронировать';
            return false;
        }
    }

    let findLibraryBook = function (worldBook) {
        $http({
            url: libraryBooksPath + '/' + worldBook.id,
            method: 'GET'
        }).then(function (response) {
            let libraryBooks = response.data;
            let availableBookFoundStatus = false;

            for (let i = 0; i < libraryBooks.length; i++) {
                if (libraryBooks[i].available === true) {
                    $scope.libraryBook = libraryBooks[i];
                    availableBookFoundStatus = true;
                    break;
                }
            }

            if (availableBookFoundStatus === false) {
                acceptReserveModalWindow.style.display = 'flex';
                reserveBtn.addEventListener("click", function () {
                    $scope.libraryBook = libraryBooks[0];
                    acceptReserveModalWindow.style.display = 'none';
                });
                cancelReserveBtn.addEventListener("click", function () {
                    acceptReserveModalWindow.style.display = 'none';
                    return -1;
                });
            } else {
                confirmReserveModalWindow.style.display = 'flex';
                confirmedReserveBtn.addEventListener("click", function () {
                    confirmReserveModalWindow.style.display = "none";
                });
                cancelConfirmReserveBtn.addEventListener("click", function () {
                    confirmReserveModalWindow.style.display = "none";
                    return -1;
                });
            }
        });
    };

    let findUserData = function () {
        $http({
            url: usersPath + '/' + $localStorage.webUser.username,
            method: 'GET'
        }).then(function (response) {
            $scope.userData = response.data;
        });
    }


    $scope.prepareForReserve = function (worldBook) {
        $scope.worldBook = worldBook;
        findUserData();
        findLibraryBook(worldBook);
    };

    $scope.reserveBook = function () {
        setTimeout(function () {
            if ($scope.userData != null && $scope.libraryBook != null) {
                let reservedBookJSON = {
                    'id': $scope.libraryBook.id,
                    'user': $scope.userData,
                    'library_book': $scope.libraryBook,
                    'world_book': $scope.worldBook
                }
                console.log(reservedBookJSON);

                $http({
                    url: reservedPath,
                    method: 'POST',
                    data: reservedBookJSON
                }).then(function successCallback(response) {
                        reservedBookJSON = null;
                        alert('Книга ' + $scope.worldBook.title + ' автора ' + $scope.worldBook.authorDTO.firstName + ' ' + $scope.worldBook.authorDTO.lastName + ' забронирована');
                        $location.path('/books_reserved');
                    },
                    function failureCallback(response) {
                        alert(response.data.messages);
                    });
            } else {
                console.log("Нет информации");
                alert('К сожалению, вариантов данной книги в библиотеке нет');
            }
        }, 200);
    };

    $scope.loadBooksWishlist();
    bookReservedData();
});