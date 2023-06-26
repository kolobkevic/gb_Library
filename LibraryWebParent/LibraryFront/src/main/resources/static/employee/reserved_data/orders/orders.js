angular.module('employee-front').controller('ordersController', function ($scope, $http, $localStorage) {
    const reservedBooksPath = 'http://localhost:5555/official/api/v1/reserved_books';
    const bookOnHandsPath = 'http://localhost:5555/official/api/v1/book_hands';
    const userContext = 'http://localhost:5555/official/api/v1/users';
    const libraryBookContext = 'http://localhost:5555/official/api/v1/libraryBook';
    const worldBookContext = 'http://localhost:5555/official/api/v1/worldBook';

    // $scope.userData = $localStorage.webUser.username;

    let directoriesMenu = document.getElementById("directoriesMenu");
    if (directoriesMenu.style.display === 'block') directoriesMenu.style.display = 'none';
    let ordersMenu = document.getElementById("ordersMenu");

    let ordersTable = document.getElementById("ordersTable");
    let orderInfo = document.getElementById("orderInfo");


    let libraryBooksLink = document.getElementById("libraryBooksLink");
    let readersLink = document.getElementById("readersLink");
    let ordersLink = document.getElementById("ordersLink");
    let directoriesLink = document.getElementById("directoriesLink");

    let reservedOrdersLink = document.getElementById("reservedOrdersLink");
    let lendOutHistory = document.getElementById("lendOutHistory");

    let $orderSearchField = document.getElementById('orderSearchField');


    $orderSearchField.onchange = function () {
        $scope.loadOrders();
    };

    function setActiveLink() {
        ordersMenu.style.display = 'inline';
        reservedOrdersLink.className = "active_item"
        lendOutHistory.className = "inactive_item"

        libraryBooksLink.className = "inactive_item";
        readersLink.className = "inactive_item";
        ordersLink.className = "active_item";
        directoriesLink.className = "inactive_item";
    }

    $scope.loadOrders = function (pageIndex = 1) {
        $http({
            url: reservedBooksPath,
            method: 'GET',
            params: {
                p: pageIndex,
                search: document.getElementById('orderSearchField').value.trim(),
            }
        }).then(function (response) {
            console.log(response);

            $scope.pageCount = response.data.totalPages;
            $scope.currentPage = response.data.pageable.pageNumber + 1;

            document.getElementById("current_page-id").value = $scope.currentPage;
            $scope.ordersList = response.data.content;
        });
    };

    $scope.goToPage = function (page) {
        $scope.currentPage = page;
        $scope.loadAuthors();
    };

    function formDate() {
        let today = new Date();
        let dd = String(today.getDate()).padStart(2, '0');
        let mm = String(today.getMonth() + 1).padStart(2, '0');
        let yyyy = today.getFullYear();
        return yyyy + '-' + mm + '-' + dd;
    }

    function findUserInfo(id) {
        $http({
            url: userContext + '/' + id,
            method: 'GET'
        }).then(function (response) {
            $scope.userInfo = response.data;
        });
    }

    function findLibraryBookInfo(libraryBookId) {
        $http({
            url: libraryBookContext + '/' + libraryBookId,
            method: 'GET'
        }).then(function (response) {
            $scope.libraryBookInfo = response.data;
        });
    }

    function findWorldBookInfo(worldBookId) {
        $http({
            url: worldBookContext + '/' + worldBookId,
            method: 'GET'
        }).then(function (response) {
            $scope.worldBookInfo = response.data;
        });
    }

    $scope.lendOutBook = function (reservedId, userID, libraryBookId) {
        findUserInfo(userID);
        findLibraryBookInfo(libraryBookId);
        let currentDate = formDate();

        setTimeout(function () {
            console.log($scope.userInfo);
            console.log($scope.libraryBookInfo);
            console.log($scope.worldBookInfo);


            let lendOutBookJSON = {};
            lendOutBookJSON.libraryBookDTO = $scope.libraryBookInfo;
            lendOutBookJSON.userDTO = $scope.userInfo;
            lendOutBookJSON.takenAt = currentDate;
            lendOutBookJSON.returned = false;

            console.log(lendOutBookJSON);

            $http({
                url: bookOnHandsPath,
                method: 'POST',
                data: lendOutBookJSON
            }).then(function successCallback(response) {
                lendOutBookJSON = null;
                $http({
                    url: reservedBooksPath + '/' + reservedId,
                    method: 'DELETE'
                }).then(function () {
                    alert('Книга успешно выдана!');
                    $scope.backToOrders();
                });
            }, function failureCallback(response) {
                console.log(response);
                alert(response.data.message);
            });
        }, 260);
    };

    $scope.showOrderInfo = function (orderId) {
        orderInfo.style.display = 'inline';
        ordersTable.style.display = 'none';

        $http({
            url: reservedBooksPath + '/' + orderId,
            method: 'GET'
        }).then(function (response) {
            $scope.currentOrder = response.data;
        });
    };

    $scope.cancelOrder = function (id) {
        $http({
            url: reservedBooksPath + '/' + id,
            method: 'DELETE'
        }).then(function () {
            alert('Заказ успешно отменен!');
            $scope.backToOrders();
        });
    };

    $scope.backToOrders = function () {
        $scope.loadOrders();
        orderInfo.style.display = 'none';
        ordersTable.style.display = 'inline';
    };


    $scope.loadOrders();
    setActiveLink();
});