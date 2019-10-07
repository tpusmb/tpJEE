$(document).ready(function () {
    $(".create-rent-request-btn").click(function (event) {
        $("#for-user").val($(event.target).data('destination_email'));
    });
    $(".edit-rent-request").click(function (event) {
        $("#rent-request-to-edit").val($(event.target).data('rent_request_to_edit'));
        $("#robot_number-edit").val($(event.target).data('robot_number'));
        $("#computer_number-edit").val($(event.target).data('computer_number'));
        $("#duration-edit").val($(event.target).data('duration'));
    });
    $(".valid-rent-request").click(function (event) {
        $("#rent-request-id").val($(event.target).data('rent_request_id'));
    });
});