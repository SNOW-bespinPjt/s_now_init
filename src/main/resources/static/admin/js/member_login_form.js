function memberLoginForm() {
    console.log('memberLoginForm() CALLED!!');

    let form = document.member_login_form;
    if (form.id.value == '') {
        alert('INPUT ID');
        form.id.focus();

    } else if (form.pw.value == '') {
        alert('INPUT PW');
        form.pw.focus();

    } else {
        form.submit();

    }

}