$('.nav.side-menu li').click(function () {
    // list
    if ($(this).hasClass('active')) $(this).toggleClass('active');
    else {
        $('.nav.side-menu li.active').toggleClass("active");
        $(this).toggleClass('active');
    }

    // child list
    if ($(this).find('ul').hasClass('d-block')) $(this).find('ul').toggleClass('d-block');
    else {
        $('ul.nav.child_menu.d-block').toggleClass('d-block');
        $(this).find('ul').toggleClass('d-block');
    }
});