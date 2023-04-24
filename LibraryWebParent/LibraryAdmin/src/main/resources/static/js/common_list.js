function showDeleteConfirmModal(link){
    let entityId = link.attr("entityID");

    $("#yesButton").attr("href", link.attr("href"));
    $("#confirmText").text("Вы уверены что хотите удалить этот элемент " +
        "(ID=" + entityId + ")?");

    // $("#confirmModal").modal();
    let myModal = new bootstrap.Modal(document.getElementById("confirmModal"));
    myModal.show();
}