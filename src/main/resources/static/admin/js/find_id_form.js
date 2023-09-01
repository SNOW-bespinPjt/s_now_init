function findId() {
    console.log('findId() CALLED');

    let form = document.find_id_form;

if (form.name.value == '') {
        alert('INPUT ADMIN NAME.');
        form.name.focus();
    } else if (form.mail.value == '') {
        alert('INPUT ADMIN MAIL.');
        form.mail.focus();
    } else {
        form.submit();
    }
}