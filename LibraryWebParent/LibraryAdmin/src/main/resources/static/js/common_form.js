$(document).ready(function (){
   $("#buttonCancel").on("click", function (){
        window.location = module_URI;
   });
});

function showErrorModal(message){
    showModalDialog("Error", message);
}

function showWarningModal(message){
    showModalDialog("Warning", message);
}

function showModalDialog(title, message) {
    $("#modalTitle").text(title);
    $("#modalBody").text(message);

    // $("#modalDialog").modal();
    let myModal = new bootstrap.Modal(document.getElementById("modalDialog"));
    myModal.show();

}