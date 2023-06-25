angular.module('employee-front').controller('directoriesController', function ($scope, $location, $localStorage, $routeParams) {
    function routing() {
        let param = $routeParams.page;
        if (param === 'authors') $location.path('/authors/authors.html');
    }

    routing();
});