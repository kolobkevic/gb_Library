angular.module('employee-front').controller('activeOrdersController', function ($scope, $http) {
    const bookOnHandsPath = 'http://localhost:5555/official/api/v1/book_hands';
    const reservedBooksPath = 'http://localhost:5555/official/api/v1/reserved_books';
    const libraryBookContext = 'http://localhost:5555/official/api/v1/libraryBook';
    const worldBookPath = 'http://localhost:5555/official/api/v1/worldBook';

    let directoriesMenu = document.getElementById("directoriesMenu");
    let ordersMenu = document.getElementById("ordersMenu");

    let orderInfo = document.getElementById("orderInfo");
    let activeOrdersTable = document.getElementById("activeOrdersTable");

    if (directoriesMenu.style.display === 'block') directoriesMenu.style.display = 'none';

    let libraryBooksLink = document.getElementById("libraryBooksLink");
    let readersLink = document.getElementById("readersLink");
    let ordersLink = document.getElementById("ordersLink");
    let directoriesLink = document.getElementById("directoriesLink");

    let reservedOrdersLink = document.getElementById("reservedOrdersLink");
    let lendOutHistory = document.getElementById("lendOutHistory");
    let activeOrdersLink = document.getElementById("activeOrdersLink");

    let $orderSearchField = document.getElementById("orderSearchField");

    $orderSearchField.onchange = function () {
        $scope.loadActiveOrders();
    };

    function setActiveLink() {
        ordersMenu.style.display = 'inline';
        reservedOrdersLink.className = "inactive_item";
        activeOrdersLink.className = "active_item";
        lendOutHistory.className = "inactive_item";

        libraryBooksLink.className = "inactive_item";
        readersLink.className = "inactive_item";
        ordersLink.className = "active_item";
        directoriesLink.className = "inactive_item";
    }


    $scope.loadActiveOrders = function (pageIndex = 1) {
        orderInfo.style.display = 'none';

        if ($scope.currentPage < 1) {
            $scope.currentPage = 1;
        }
        if ($scope.currentPage > $scope.pageCount) {
            $scope.currentPage = $scope.pageCount;
        }

        $http({
            url: bookOnHandsPath,
            method: 'GET',
            params: {
                p: $scope.currentPage,
                st: document.getElementById('orderSearchField').value.trim(),
                a: 0
            }
        }).then(function (response) {
            $scope.pageCount = response.data.totalPages;
            $scope.currentPage = response.data.pageable.pageNumber + 1;

            document.getElementById("current_page-id").value = $scope.currentPage;
            $scope.ordersHistoryList = response.data.content;
        });
    };

    $scope.showMessageModalWindow = function () {
        document.getElementById("messageModalWindow").style.display = 'flex';
        document.getElementById("reserveBtn").addEventListener("click", function () {
            document.getElementById("messageModalWindow").style.display = 'none';
        });
    };

    $scope.goToPage = function (page) {
        $scope.currentPage = page;
        $scope.loadActiveOrders();
    };

    $scope.showOrderInfo = function (orderId) {
        orderInfo.style.display = 'inline';
        activeOrdersTable.style.display = 'none';

        $http({
            url: bookOnHandsPath + '/' + orderId,
            method: 'GET'
        }).then(function (response) {
            $scope.currentOrder = response.data;

            $http({
                url: worldBookPath + '/' + response.data.libraryBookDTO.worldBookDTO.id,
                method: 'GET'
            }).then(function (response) {
                $scope.worldBookInfo = response.data;
            })
        });
    };

    $scope.acceptBook = function () {
        $scope.currentOrder.returned = true;
        $http({
            url: bookOnHandsPath,
            method: 'PUT',
            data: $scope.currentOrder
        }).then(function successCallback(response) {
            $scope.currentOrder.libraryBookDTO.available = true;
            $http({
                url: libraryBookContext,
                method: 'PUT',
                data: $scope.currentOrder.libraryBookDTO
            }).then(function (response) {
                $scope.currentOrder = null;
                $scope.showMessageModalWindow();
                $scope.backToOrders();
            });
        }, function failureCallback(response) {
            console.log(response);
            alert(response.data.message);
        });
    };


    $scope.backToOrders = function () {
        $scope.loadActiveOrders();
        orderInfo.style.display = 'none';
        activeOrdersTable.style.display = 'inline';
    };

    setActiveLink();
    $scope.loadActiveOrders();
});