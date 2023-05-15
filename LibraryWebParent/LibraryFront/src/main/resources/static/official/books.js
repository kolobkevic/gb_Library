angular.module('employee-front').controller('booksController', function ($rootScope, $scope, $http, $location, $localStorage) {

    const corePath = 'http://' + window.location.hostname + ':8060/official/api/v1';
    $scope.currentPage = 1;
    $scope.isEdit = false;

    $input = document.getElementById('input_min-id');
    $input2 = document.getElementById('input_max-id');
    $input3 = document.getElementById('input_title-id');
    $input4 = document.getElementById('current_page-id');


    $http({
            url: corePath + '/authors/all',
            method: 'GET'
        }
    ).then(function (response) {

        let selectForm = document.getElementById('selectAuthor');
        for (const responseElement of response.data) {
            let opt = document.createElement('option');
            opt.innerHTML = responseElement.firstName + " " + responseElement.lastName;
            opt.value = responseElement.id;
            selectForm.appendChild(opt);
        }
        // document.getElementById("selectAuthor").value = response.data.name;
        // document.getElementById("form_description").value = response.data.description;
    });
    $http({
            url: corePath + '/genres',
            method: 'GET'
        }
    ).then(function (response) {
        let selectForm = document.getElementById('selectGenre');

        for (const responseElement of response.data) {
            let opt = document.createElement('option');
            opt.innerHTML = responseElement.name;
            opt.value = responseElement.id;
            selectForm.appendChild(opt);
        }
        // document.getElementById("selectAuthor").value = response.data.name;
        // document.getElementById("form_description").value = response.data.description;
    });


    // $input.onchange = function () {
    //     // document.getElementById("error_text").style.visibility = 'hidden';
    //     $scope.currentPage = 1
    //     $scope.loadProducts();
    // };
    // $input2.onchange = function () {
    //     // document.getElementById("error_text").style.visibility = 'hidden';
    //     $scope.currentPage = 1
    //     $scope.loadProducts();
    // };
    // $input3.onchange = function () {
    //     // document.getElementById("error_text").style.visibility = 'hidden';
    //     $scope.currentPage = 1
    //     $scope.loadProducts();
    // };
    // $input4.onchange = function () {
    //     // document.getElementById("error_text").style.visibility = 'hidden';
    //     $scope.currentPage = document.getElementById("current_page-id").value;
    //     $scope.loadProducts();
    // };


    $scope.loadBooks = function () {
        if ($scope.currentPage < 1) {
            $scope.currentPage = 1;
        }
        if ($scope.currentPage > $scope.pagesCount) {
            $scope.currentPage = $scope.pagesCount;
        }
        $http({
                url: corePath + '/libraryBook',
                method: 'GET',
                params: {
                    // minPrice: document.getElementById("input_min-id").value,
                    // maxPrice: document.getElementById("input_max-id").value,
                    // partName: document.getElementById("input_title-id").value,
                    partName: document.getElementById("input_title-id").value,
                    // active: !($rootScope.hasUserRole('ADMIN') || $rootScope.hasUserRole('MANAGER')),
                    page: $scope.currentPage
                }
            }
        ).then(function (response) {
            $scope.booksList = response.data.content;
            $scope.pagesCount = response.data.totalPages;
            $scope.currentPage = response.data.pageable.pageNumber + 1;
            document.getElementById("current_page-id").value = $scope.currentPage;
        });
    };
    //
    // $scope.generatePagesIndexes = function (startPage, endPage) {
    //     let arr = [];
    //     for (let i = startPage; i < endPage + 1; i++) {
    //         arr.push(i);
    //     }
    //     return arr;
    // }


    $scope.goToPage = function (page) {
        // document.getElementById("error_text").style.visibility = 'hidden';
        $scope.currentPage = page;
        $scope.loadBooks();
    };

    $scope.goToEdit = function (id) {

        $scope.isEdit = !$scope.isEdit;
        if ($scope.isEdit) {
            document.getElementById("booksList").style.display = 'none';
            document.getElementById("editForm").style.display = 'inline';
            console.log("редактор");
        } else {
            document.getElementById("booksList").style.display = 'inline';
            document.getElementById("editForm").style.display = 'none';
            console.log("список");
        }


        // if (!$scope.new_user.username) {
        //     alert('Введите имя пользователя')

        // } else if ($scope.new_user.password !== $scope.new_user.password2) {
        //     alert('Пароли не совпадают')

        // } else if (!String($scope.new_user.email)
        //     .toLowerCase()
        //     .match(
        //         /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
        //     )) {
        //     alert('Неверный email')

        // } else {
        //     $http.post(contextPath + 'registration', $scope.new_user)
        //         .then(function successCallback(response) {
        //             $scope.new_user = null;
        //             alert('Пользователь создан');
        //             $location.path('/store');
        //         }, function failureCallback(response) {
        //             alert(response.data.message);
        //         });
        // }
    }


    $scope.loadBooks();
});