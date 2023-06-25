angular.module('employee-front').controller('genresController', function ($scope, $http, $localStorage) {
    const corePath = 'http://' + window.location.hostname + ':5555/official/api/v1';

    let libraryBooksLink = document.getElementById("libraryBooksLink");
    let readersLink = document.getElementById("readersLink");
    let ordersLink = document.getElementById("ordersLink");
    let directoriesLink = document.getElementById("directoriesLink");

    let authorsLink = document.getElementById("authorsLink");
    let genresLink = document.getElementById("genresLink");
    let locationsLink = document.getElementById("locationsLink");
    let worldBooksLink = document.getElementById("worldBooksLink");

    function setActiveLink() {
        libraryBooksLink.className = "inactive_item";
        readersLink.className = "inactive_item";
        ordersLink.className = "inactive_item";
        directoriesLink.className = "active_item";

        authorsLink.className = "inactive_item";
        genresLink.className = "active_item";
        locationsLink.className = "inactive_item";
        worldBooksLink.className = "inactive_item";
    }

    let directoriesMenu = document.getElementById("directoriesMenu");
    if (directoriesMenu.style.display === 'none') directoriesMenu.style.display = 'block';

    $scope.editId = -10;

    let $input = document.getElementById('input_name');
    let $input2 = document.getElementById('input_description');


    $input.onchange = function () {
        // document.getElementById("error_text").style.visibility = 'hidden';
        $scope.loadGenres();
    };
    $input2.onchange = function () {
        // document.getElementById("error_text").style.visibility = 'hidden';
        $scope.loadGenres();
    };

    $scope.loadGenres = function () {

        $http({
                url: corePath + '/genres',
                method: 'GET',
                params: {
                    name: document.getElementById("input_name").value,
                    description: document.getElementById("input_description").value
                }

            }
        ).then(function (response) {
            $scope.genreList = response.data;
        });
    };


    $scope.goToEdit = function (id) {
        $scope.editId = id;

        if (id < 0) {
            document.getElementById("booksList").style.display = 'inline';
            document.getElementById("editForm").style.display = 'none';
            $scope.loadGenres();
        } else {
            document.getElementById("booksList").style.display = 'none';
            document.getElementById("editForm").style.display = 'inline';

            if (id === 0) {
                document.getElementById("genre_id").innerText = "Добавление нового жанра:";
            }

            if (id > 0) {
                document.getElementById("genre_id").innerText = "Редактирование id: " + id;
                $http({
                        url: corePath + '/genres/' + id,
                        method: 'GET'
                    }
                ).then(function (response) {
                    document.getElementById("form_genre").value = response.data.name;
                    document.getElementById("form_description").value = response.data.description;
                });
            }
        }

    }


    $scope.saveGenre = function () {
        if ($scope.genre == null) {
            if (($scope.editId === 0)) {
                alert('Форма не заполнена');
            }
            else {
                $scope.goToEdit(-10);
            }
            return;
        }
        if ($scope.editId === 0) {
            $scope.genre.name = document.getElementById("form_genre").value;
            $scope.genre.description = document.getElementById("form_description").value;
            $http.post(corePath + '/genres', $scope.genre)
                .then(function successCallback(response) {
                    $scope.genre = null;
                    alert('Жанр успешно сохранён');
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
            $scope.genre.id = $scope.editId;
            $scope.genre.name = document.getElementById("form_genre").value;
            $scope.genre.description = document.getElementById("form_description").value;
            $http.put(corePath + '/genres', $scope.genre)
                .then(function successCallback(response) {
                    $scope.genre = null;
                    alert('Жанр успешно сохранён');
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

    $scope.loadGenres();

    setActiveLink();
});