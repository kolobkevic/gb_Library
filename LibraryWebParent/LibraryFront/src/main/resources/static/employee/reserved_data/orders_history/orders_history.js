angular.module('employee-front').controller('ordersHistoryController', function ($scope, $http) {
    const worldBookPath = 'http://localhost:5555/official/api/v1/worldBook';
    const bookOnHandsPath = 'http://localhost:5555/official/api/v1/book_hands';

    let directoriesMenu = document.getElementById("directoriesMenu");
    let ordersMenu = document.getElementById("ordersMenu");
    if (directoriesMenu.style.display === 'block') directoriesMenu.style.display = 'none';

    let orderInfo = document.getElementById("orderInfo");
    let ordersHistoryTable = document.getElementById("ordersHistoryTable");

    let libraryBooksLink = document.getElementById("libraryBooksLink");
    let readersLink = document.getElementById("readersLink");
    let ordersLink = document.getElementById("ordersLink");
    let directoriesLink = document.getElementById("directoriesLink");

    let reservedOrdersLink = document.getElementById("reservedOrdersLink");
    let activeOrdersLink = document.getElementById("activeOrdersLink");
    let lendOutHistory = document.getElementById("lendOutHistory");

    let $orderSearchField = document.getElementById("orderSearchField");

    $orderSearchField.onchange = function () {
        $scope.loadOrdersHistory();
    };

    function setActiveLink() {
        ordersMenu.style.display = 'inline';
        reservedOrdersLink.className = "inactive_item";
        activeOrdersLink.className = "inactive_item";
        lendOutHistory.className = "active_item";

        libraryBooksLink.className = "inactive_item";
        readersLink.className = "inactive_item";
        ordersLink.className = "active_item";
        directoriesLink.className = "inactive_item";
    }

    $scope.loadOrdersHistory = function (pageIndex = 1) {
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
                a: 1
            }
        }).then(function (response) {
            console.log(response);

            $scope.pageCount = response.data.totalPages;
            $scope.currentPage = response.data.pageable.pageNumber + 1;

            document.getElementById("current_page-id").value = $scope.currentPage;
            $scope.ordersHistoryList = response.data.content;
        });
    };

    $scope.goToPage = function (page) {
        $scope.currentPage = page;
        $scope.loadOrdersHistory();
    };

    $scope.showOrderInfo = function (orderId) {
        orderInfo.style.display = 'inline';
        ordersHistoryTable.style.display = 'none';

        $http({
            url: bookOnHandsPath + '/' + orderId,
            method: 'GET'
        }).then(function (response) {
            console.log(response.data);
            $scope.currentOrder = response.data;

            $http({
                url: worldBookPath + '/' + response.data.libraryBookDTO.worldBookDTO.id,
                method: 'GET'
            }).then(function (response) {
                console.log(response.data);
                $scope.worldBookInfo = response.data;
            })
        });
    };

    $scope.backToOrders = function () {
        $scope.loadOrdersHistory();
        orderInfo.style.display = 'none';
        ordersHistoryTable.style.display = 'inline';
    };

    setActiveLink();
    $scope.loadOrdersHistory();
});