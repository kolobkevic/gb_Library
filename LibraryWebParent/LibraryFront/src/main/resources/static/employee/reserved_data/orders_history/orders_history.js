angular.module('employee-front').controller('ordersHistoryController', function () {
    let directoriesMenu = document.getElementById("directoriesMenu");
    let ordersMenu = document.getElementById("ordersMenu");
    if (directoriesMenu.style.display === 'block') directoriesMenu.style.display = 'none';

    let libraryBooksLink = document.getElementById("libraryBooksLink");
    let readersLink = document.getElementById("readersLink");
    let ordersLink = document.getElementById("ordersLink");
    let directoriesLink = document.getElementById("directoriesLink");

    let reservedOrdersLink = document.getElementById("reservedOrdersLink");
    let lendOutHistory = document.getElementById("lendOutHistory");

    function setActiveLink() {
        ordersMenu.style.display = 'inline';
        reservedOrdersLink.className = "inactive_item"
        lendOutHistory.className = "active_item"

        libraryBooksLink.className = "inactive_item";
        readersLink.className = "inactive_item";
        ordersLink.className = "active_item";
        directoriesLink.className = "inactive_item";
    }

    setActiveLink();
});