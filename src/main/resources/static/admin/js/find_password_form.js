function findPassword() {
    console.log('findPassword() CALLED');

    let form = document.find_password_form;

    if (form.id.value == '') {
        alert('INPUT ADMIN ID.');
        form.id.focus();
    } else if (form.name.value == '') {
        alert('INPUT ADMIN NAME.');
        form.name.focus();
    } else if (form.mail.value == '') {
        alert('INPUT ADMIN MAIL.');
        form.mail.focus();
    } else {
        form.submit();
    }
}