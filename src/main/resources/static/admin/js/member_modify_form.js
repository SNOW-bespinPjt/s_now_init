function memberModifyForm() {
    console.log('memberModifyForm() CALLED!!');

    let form = document.member_modify_form;
    if (form.a_m_name.value == '') {
        alert('INPUT MAIL');
        form.a_m_name.focus();

    } else if (form.a_m_mail.value == '') {
        alert('INPUT MAIL');
        form.a_m_mail.focus();

    } else if (form.a_m_phone.value == '') {
        alert('INPUT PHONE');
        form.a_m_phone.focus();

    } else {
        form.submit();

    }

}