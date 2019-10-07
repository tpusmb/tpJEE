$(document).ready(function () {
    $(".creat-licence-btn").click(function (event) {
        $("#for-user").val($(event.target).data('destination_email'));
    });
    $(".edit-licence-key").click(function (event) {
        $("#licence-key-to-edit").val($(event.target).data('licence_key_to_edit'));
        $("#block-key").val($(event.target).data('block_key'));
        $("#block-key").prop('checked', $(event.target).data('block_key'));
        $("#expiration-date-edit").val($(event.target).data('expiry_date'));
    });
    // Swap check box value
    $("#block-key").click(function (event) {
        if ($("#block-key").val() === "true") {
            $("#block-key").val("false");
        } else {
            $("#block-key").val("true");
        }
    });
});