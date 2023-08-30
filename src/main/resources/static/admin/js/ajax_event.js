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

    // 아이디 찾기
    $(document).on('click', '#findIDLink', function () {
        console.log("findIDLink button CLICK HANDLER");

        $('#contentDiv').css('display', 'none');
        $('#find_id_form').css('display','block');

    });

    // 비밀번호 찾기
    $(document).on('click', '#findPWLink', function () {
        console.log("findPWLink button CLICK HANDLER");

        $('#contentDiv').css('display', 'none');
        $('#find_password_form').css('display','block');

    });

    // 회원가입
    $(document).on('click', '#AccountLink', function () {
        console.log("AccountLink button CLICK HANDLER");

        $('#contentDiv').css('display', 'none');
        $('#create_account_form').css('display','block');

    });

}
