$(document).ready(function () {
    $(".edit-robot-info").click(function (event) {
        $("#robotSerialNumber-edit").val($(event.target).data('robot_to_edit'));
        $("#canBeUsed-edit").val($(event.target).data('canbeused'));
        $("#canBeUsed-edit").prop('checked', $(event.target).data('canbeused'));
        $("#userEmailProprity-edit").val($(event.target).data('curent_user'));
        $("#robotName-edit").val($(event.target).data('robot_name'));
    });
    // Swap check box value
    $("#canBeUsed-edit").click(function (event) {
        if ($("#canBeUsed-edit").val() === "true") {
            $("#canBeUsed-edit").val("false");
        } else {
            $("#canBeUsed-edit").val("true");
        }
    });

    $("#userEmailProprity-edit").autocomplete({
        source : function(request, response) {
            $.ajax({
                url : "/getUsers",
                dataType : "json",
                data : {
                    q : request.term
                },
                success : function(data) {
                    console.log(data);
                    response(data);
                }
            });
        },
        minLength : 2
    })
});