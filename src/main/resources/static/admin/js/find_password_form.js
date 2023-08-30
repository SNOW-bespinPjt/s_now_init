function findPassword() {
    console.log('findPassword() CALLED');

    let form = document.find_password_form;

    if (form.a_m_id.value == '') {
        alert('INPUT ADMIN ID.');
        form.a_m_id.focus();
    } else if (form.a_m_name.value == '') {
        alert('INPUT ADMIN NAME.');
        form.a_m_name.focus();
    } else if (form.a_m_mail.value == '') {
        alert('INPUT ADMIN MAIL.');
        form.a_m_mail.focus();
    } else {
        form.submit();
    }
}