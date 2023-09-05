function createAccountForm() {
    console.log('createAccountForm() CALLED!!');

    let form = document.create_account_form;
    if (form.id.value == '') {
        alert('INPUT ID');
        form.id.focus();

    } else if (form.pw.value == '') {
        alert('INPUT PW');
        form.pw.focus();
        form.pw.focus();

    } else if (form.name.value == '') {
        alert('INPUT NAME');
        form.name.focus();

    } else if (form.mail.value == '') {
        alert('INPUT MAIL');
        form.mail.focus();

    } else if (form.phone.value == '') {
        alert('INPUT PHONE');
        form.phone.focus();

    } else if (form.position.value == '') {
        alert('SELECT POSITION');
        form.position.focus();

    } else {
        form.submit();

    }

}