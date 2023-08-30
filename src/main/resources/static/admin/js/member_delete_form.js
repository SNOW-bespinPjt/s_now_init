function memberDeleteForm() {
    console.log('memberDeleteForm() CALLED!!');

    let form = document.member_delete_form;
    if (form.a_m_pw.value == '') {
        alert('INPUT PW');
        form.a_m_pw.focus();

    } else {
        form.submit();

    }

}