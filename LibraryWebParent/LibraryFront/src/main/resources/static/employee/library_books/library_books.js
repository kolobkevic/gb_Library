angular.module('employee-front').controller('libraryBooksController', function ($rootScope, $scope, $http, $location, $localStorage) {

    const corePath = 'http://' + window.location.hostname + ':5555/official/api/v1';
    $scope.currentPage = 1;
    $scope.isEdit = false;

    let directoriesMenu = document.getElementById("directoriesMenu");
    if (directoriesMenu.style.display === 'block') directoriesMenu.style.display = 'none';

    let libraryBooksLink = document.getElementById("libraryBooksLink");
    let readersLink = document.getElementById("readersLink");
    let ordersLink = document.getElementById("ordersLink");
    let directoriesLink = document.getElementById("directoriesLink");

    let ordersMenu = document.getElementById("ordersMenu");

    function setActiveLink() {
        ordersMenu.style.display = 'none';

        libraryBooksLink.className = "active_item";
        readersLink.className = "inactive_item";
        ordersLink.className = "inactive_item";
        directoriesLink.className = "inactive_item";
    }

    let $input1 = document.getElementById('input_title');
    let $input2 = document.getElementById('input_author');
    let $input3 = document.getElementById('input_genre');
    let $input4 = document.getElementById('input_description');
    let $input5 = document.getElementById('selectAge');
    let $selectZone = document.getElementById('selectZone');

    $input1.onchange = function () {
        $scope.loadBooks();
    };

    $input2.onchange = function () {
        $scope.loadBooks();
    };

    $input3.onchange = function () {
        $scope.loadBooks();
    };

    $input4.onchange = function () {
        $scope.loadBooks();
    };

    $input5.onchange = function () {
        $scope.loadBooks();
    };

    $selectZone.onchange = function () {
        $scope.loadSectorsList();
    };


    $scope.loadSectorsList = function () {
        let zoneIndex = document.getElementById('selectZone').value;
        let selectForm = document.getElementById('selectSector');
        selectForm.options.length = 0;

        if (zoneIndex >= 0) {
            for (const sector of $scope.zoneList[zoneIndex].sectors) {
                if (sector.isAvailable) {
                    let opt = document.createElement('option');
                    opt.innerHTML = sector.sector;
                    opt.value = sector.id;
                    selectForm.appendChild(opt);
                }
            }
        }
    }

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
                    title: document.getElementById("input_title").value,
                    author: document.getElementById("input_author").value,
                    genre: document.getElementById("input_genre").value,
                    description: document.getElementById("input_description").value,
                    ageRating: document.getElementById("selectAge").value,
                    page: $scope.currentPage,
                    pageSize: 10
                }
            }
        ).then(function (response) {
            $scope.booksList = response.data.content;
            $scope.pagesCount = response.data.totalPages;
            $scope.currentPage = response.data.pageable.pageNumber + 1;
            document.getElementById("current_page-id").value = $scope.currentPage;
        });
    };


    $scope.goToPage = function (page) {
        $scope.currentPage = page;
        $scope.loadBooks();
    };

    $scope.goToEdit = function (id) {

        $scope.isEdit = !$scope.isEdit;
        if ($scope.isEdit) {
            document.getElementById("booksList").style.display = 'none';
            document.getElementById("editForm").style.display = 'inline';
        } else {
            document.getElementById("booksList").style.display = 'inline';
            document.getElementById("editForm").style.display = 'none';
        }

        if (!$scope.isEdit)
            return;

        if ($selectZone.options.length === 0) {
            $http({
                    url: corePath + '/storage/zones',
                    method: 'GET',
                }
            ).then(function (response) {
                let opt = document.createElement('option');
                opt.innerHTML = "";
                opt.value = -1;
                $selectZone.appendChild(opt);

                let index = 0;
                for (const responseElement of response.data) {
                    let opt = document.createElement('option');
                    opt.innerHTML = responseElement.zone;
                    opt.value = index;
                    $selectZone.appendChild(opt);
                    index++;
                }

                $scope.zoneList = response.data;
            });
        }

        $http({
                url: corePath + '/libraryBook/' + id,
                method: 'GET'
            }
        ).then(function (response) {
            // TODO authorDTO is null, genresDTO is null

            console.log(response.data);
            $scope.editId = response.data.id;
            $scope.worldBookId = response.data.worldBookDTO.id;
            // document.getElementById("book_id").innerText = "(libraryBookId=" + response.data.id + ")";
            document.getElementById("title").innerText = response.data.worldBookDTO.title;
            // document.getElementById("author").innerText = response.data.worldBookDTO.authorDTO.firstName + " " + response.data.worldBookDTO.authorDTO.lastName;
            document.getElementById("description").innerText = "Описание: " + response.data.worldBookDTO.description;
            // document.getElementById("info").innerText = "Жанр: " + response.data.worldBookDTO.genreDTO.name + ", Возраст: " + response.data.worldBookDTO.ageRating;
            // document.getElementById("genre").innerText = response.data.worldBookDTO.genreDTO.name;
            // document.getElementById("ageRating").innerText = response.data.worldBookDTO.ageRating;
            document.getElementById("form_publisher").value = response.data.publisher;
            document.getElementById("form_isbn").value = response.data.isbn;
            document.getElementById("form_inventoryNumber").value = response.data.inventoryNumber;
            document.getElementById("form_available").checked = response.data.available;
            setSelectedValue($selectZone, response.data.placedAt.zone);
            $scope.loadSectorsList();
            setSelectedValue(document.getElementById('selectSector'), response.data.placedAt.sector);


            $scope.libraryBookId = response.data.id;
        });
    }

    $scope.saveBook = function () {

        if (document.getElementById('form_publisher').value === "") {
            alert('Заполните издателя');
            return;
        }
        if (document.getElementById('form_isbn').value === "") {
            alert('Заполните ISBN');
            return;
        }
        if (document.getElementById('form_inventoryNumber').value === "") {
            alert('Заполните Инвентарный номер');
            return;
        }
        if (document.getElementById('selectSector').value === "") {
            alert('Выберите место хранения книги');
            return;
        }

        $http({
                url: corePath + '/libraryBook',
                method: 'PUT',
                data: {
                    id: $scope.editId,
                    worldBookDTO:
                        {
                            id: $scope.worldBookId
                        },
                    publisher: document.getElementById("form_publisher").value,
                    isbn: document.getElementById("form_isbn").value,
                    inventoryNumber: document.getElementById("form_inventoryNumber").value,
                    available: document.getElementById("form_available").checked,
                    placedAt: {
                        id: document.getElementById('selectSector').value
                    }
                },
                json: false
            }
        )
            .then(function successCallback(response) {
                $scope.newBook = null;
                alert('Книга успешно сохранена');
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

    function setSelectedValue(selectObj, valueToSet) {
        for (let i = 0; i < selectObj.options.length; i++) {
            if (selectObj.options[i].text === valueToSet) {
                selectObj.options[i].selected = true;
                return;
            }
        }
    }

    setActiveLink();
    $scope.loadBooks();
});