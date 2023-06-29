angular.module('employee-front').controller('authorsController', function ($scope, $http, $localStorage) {
    const corePath = 'http://' + window.location.hostname + ':5555/official/api/v1';
    $scope.currentPage = 1;
    $scope.editId = -10;

    let libraryBooksLink = document.getElementById("libraryBooksLink");
    let readersLink = document.getElementById("readersLink");
    let ordersLink = document.getElementById("ordersLink");
    let directoriesLink = document.getElementById("directoriesLink");

    let authorsLink = document.getElementById("authorsLink");
    let genresLink = document.getElementById("genresLink");
    let locationsLink = document.getElementById("locationsLink");
    let worldBooksLink = document.getElementById("worldBooksLink");

    let ordersMenu = document.getElementById("ordersMenu");

    function setActiveLink() {
        ordersMenu.style.display = 'none';

        authorsLink.className = "active_item";
        genresLink.className = "inactive_item";
        locationsLink.className = "inactive_item";
        worldBooksLink.className = "inactive_item";

        libraryBooksLink.className = "inactive_item";
        readersLink.className = "inactive_item";
        ordersLink.className = "inactive_item";
        directoriesLink.className = "active_item";
    }

    let directoriesMenu = document.getElementById("directoriesMenu");
    if (directoriesMenu.style.display === 'none') directoriesMenu.style.display = 'block';

    let $input = document.getElementById('input_firstName');
    let $input2 = document.getElementById('input_lastName');


    $input.onchange = function () {
        $scope.loadAuthors();
    };
    $input2.onchange = function () {
        $scope.loadAuthors();
    };

    $scope.loadAuthors = function () {
        if ($scope.currentPage < 1) {
            $scope.currentPage = 1;
        }
        if ($scope.currentPage > $scope.pagesCount) {
            $scope.currentPage = $scope.pagesCount;
        }

        $http({
                url: corePath + '/authors',
                method: 'GET',
                params: {
                    firstName: document.getElementById("input_firstName").value,
                    lastName: document.getElementById("input_lastName").value,
                    p: $scope.currentPage,
                }

            }
        ).then(function (response) {
            $scope.pagesCount = response.data.totalPages;
            $scope.currentPage = response.data.pageable.pageNumber + 1;
            document.getElementById("current_page-id").value = $scope.currentPage;
            $scope.authorList = response.data.content;
        });
    };
    $scope.goToPage = function (page) {
        $scope.currentPage = page;
        $scope.loadAuthors();
    };


    $scope.goToEdit = function (id) {
        $scope.editId = id;

        if (id < 0) {
            document.getElementById("authorsList").style.display = 'inline';
            document.getElementById("editForm").style.display = 'none';
            $scope.loadAuthors();
        } else {
            document.getElementById("authorsList").style.display = 'none';
            document.getElementById("editForm").style.display = 'inline';

            if (id === 0) {
                document.getElementById("author_id").innerText = "Добавление нового Автора:";
            }

            if (id > 0) {
                document.getElementById("author_id").innerText = "Редактирование id: " + id;
                $http({
                        url: corePath + '/authors/' + id,
                        method: 'GET'
                    }
                ).then(function (response) {
                    document.getElementById("form_firstName").value = response.data.firstName;
                    document.getElementById("form_lastName").value = response.data.lastName;
                });
            }
        }

    }


    $scope.saveAuthor = function () {
        if ($scope.author == null) {
            if (($scope.editId === 0)) {
                alert('Форма не заполнена');
            }
            else {
                $scope.goToEdit(-10);
            }
            return;
        }
        if ($scope.editId === 0) {
            $scope.author.firstName = document.getElementById("form_firstName").value;
            $scope.author.lastName = document.getElementById("form_lastName").value;
            $http.post(corePath + '/authors', $scope.author)
                .then(function successCallback(response) {
                    $scope.author = null;
                    alert('Автор успешно сохранён');
                    $scope.goToEdit(-10);
                }, function failureCallback(response) {
                    console.log(response);
                    alert(response.data.message);
                }).catch(function (err) {
                console.log(err);
                if (err.status === 401) {
                    alert("Пожалуйста, авторизуйтесь");
                    $scope.clearUser();
                } else alert(err.data.message)
            });
        } else {
            $scope.author.id = $scope.editId;
            $scope.author.firstName = document.getElementById("form_firstName").value;
            $scope.author.lastName = document.getElementById("form_lastName").value;
            $http.put(corePath + '/authors', $scope.author)
                .then(function successCallback(response) {
                    $scope.author = null;
                    alert('Автор успешно сохранён');
                    $scope.goToEdit(-10);
                }, function failureCallback(response) {
                    console.log(response);
                    alert(response.data.message);
                }).catch(function (err) {
                console.log(err);
                if (err.status === 401) {
                    alert("Пожалуйста, авторизуйтесь");
                    $scope.clearUser();
                } else alert(err.data.message)
            });

        }
    }

    setActiveLink();
    $scope.loadAuthors();
});