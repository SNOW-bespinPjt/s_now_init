$(document).ready(function () {
    console.log("document ready!!");

    init_events();

});


function init_events() {
    console.log('init_events() CALLED!!');

    // 관리자 리스트
    $(document).on('click', '#adminList', function () {
        console.log("adminList button CLICK HANDLER");



    });

}