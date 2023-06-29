angular.module('employee-front').controller('readersController', function ($scope, $localStorage) {
    let directoriesMenu = document.getElementById("directoriesMenu");
    if (directoriesMenu.style.display === 'block') directoriesMenu.style.display = 'none';

    let libraryBooksLink = document.getElementById("libraryBooksLink");
    let readersLink = document.getElementById("readersLink");
    let ordersLink = document.getElementById("ordersLink");
    let directoriesLink = document.getElementById("directoriesLink");

    let ordersMenu = document.getElementById("ordersMenu");

    function setActiveLink() {
        ordersMenu.style.display = 'none';

        libraryBooksLink.className = "inactive_item";
        readersLink.className = "active_item";
        ordersLink.className = "inactive_item";
        directoriesLink.className = "inactive_item";
    }


    setActiveLink();
});