function createAccountForm() {
    console.log('createAccountForm() CALLED!!');

    let form = document.create_account_form;
    if (form.a_m_id.value == '') {
        alert('INPUT ID');
        form.a_m_id.focus();

    } else if (form.a_m_pw.value == '') {
        alert('INPUT PW');
        form.a_m_pw.focus();

    } else if (form.a_m_name.value == '') {
        alert('INPUT NAME');
        form.a_m_name.focus();

    } else if (form.a_m_mail.value == '') {
        alert('INPUT MAIL');
        form.a_m_mail.focus();

    } else if (form.a_m_phone.value == '') {
        alert('INPUT PHONE');
        form.a_m_phone.focus();

    } else if (form.a_m_position.value == '') {
        alert('SELECT POSITION');
        form.a_m_position.focus();

    } else {
        form.submit();

    }

}