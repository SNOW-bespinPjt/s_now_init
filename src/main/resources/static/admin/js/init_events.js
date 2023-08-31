$(document).ready(function () {
    console.log("document ready!!");

    init_events();

});

function init_events() {
    console.log("init_events() CALLED!!");

    // 로그인
    $(document).on('click', '#loginLink', function () {
        console.log("loginLink button CLICK HANDLER");

            $('#contentDiv').css('display', 'none');
            $('#member_login_form').css('display','block');

    });

    // 네비바
    $(document).on('click', '#navAlert', function () {
        console.log("navAlert button CLICK HANDLER");

        alert('로그인 후 이용해주세요');

    });

}
