$(document).ready(function () {
    $(".datatable").dataTable({
        searching: false,
        lengthChange: false,
        info: false
    });
});


$('#confirm').on('show.bs.modal', function (e) {
    $(this).find('.btn-ok').attr('href', $(e.relatedTarget).data('href'));
});
