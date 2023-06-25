angular.module('employee-front').controller('welcomeController', function ($scope, $localStorage) {
    $scope.userData = $localStorage.webUser.username;
    let directoriesMenu = document.getElementById("directoriesMenu");
    if (directoriesMenu.style.display === 'block') directoriesMenu.style.display = 'none';
});