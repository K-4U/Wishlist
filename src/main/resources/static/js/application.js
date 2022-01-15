$(document).ready(function () {
    var table = $(".datatable").DataTable({
        searching: false,
        lengthChange: false,
        info: false,
        pageLength: 50
    });

});


$('#confirm').on('show.bs.modal', function (e) {
    $(this).find('.btn-ok').attr('href', $(e.relatedTarget).data('href'));
});
$('#confirm-buy').on('show.bs.modal', function (e) {
    $(this).find('.btn-ok').attr('href', $(e.relatedTarget).data('href'));
});

$('#details').on('show.bs.modal', function (e) {
    var remarks = $(e.relatedTarget).data('remarks');
    var title = $(e.relatedTarget).data('title');
    $(this).find(".modal-body").text(remarks);
    $(this).find(".modal-title").text(title);
});
