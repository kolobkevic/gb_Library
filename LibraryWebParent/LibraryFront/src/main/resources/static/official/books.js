angular.module('employee-front').controller('booksController', function ($rootScope, $scope, $http, $location, $localStorage) {

    const corePath = 'http://' + window.location.hostname + ':8060/official/api/v1';
    $scope.currentPage = 1;
    $scope.isEdit = false;

    $input1 = document.getElementById('input_title');
    $input2 = document.getElementById('input_author');
    $input3 = document.getElementById('input_genre');
    $input4 = document.getElementById('input_description');
    $input5 = document.getElementById('selectAge');
    $selectZone = document.getElementById('selectZone');

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
        console.log($scope.zoneList[1])
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
    };



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
        } else {
            document.getElementById("booksList").style.display = 'inline';
            document.getElementById("editForm").style.display = 'none';
        }
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


            // document.getElementById("book_id").innerText = "(libraryBookId=" + response.data.id + ")";
            document.getElementById("title").innerText = response.data.worldBook.title;
            document.getElementById("author").innerText = response.data.worldBook.author.firstName + " " + response.data.worldBook.author.lastName;
            document.getElementById("genre").innerText = response.data.worldBook.genre.name;
            document.getElementById("ageRating").innerText = response.data.worldBook.ageRating;
            document.getElementById("description").innerText = response.data.worldBook.description;
            document.getElementById("form_publisher").value = response.data.publisher;
            document.getElementById("form_isbn").value = response.data.isbn;
            document.getElementById("form_inventoryNumber").value = response.data.inventoryNumber;
            document.getElementById("form_available").checked = response.data.available;



            setSelectedValue($selectZone, response.data.placedAt.zone);



            $scope.libraryBookId = response.data.id;
        });




    }

    function setSelectedValue(selectObj, valueToSet) {
        for (var i = 0; i < selectObj.options.length; i++) {
            if (selectObj.options[i].text== valueToSet) {
                selectObj.options[i].selected = true;
                return;
            }
        }
    }


    $scope.loadBooks();
});