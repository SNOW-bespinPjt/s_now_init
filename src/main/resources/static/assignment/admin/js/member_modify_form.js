function memberModifyForm() {
    console.log('memberModifyForm() CALLED!!');

    let form = document.member_modify_form;
    if (form.name.value == '') {
        alert('INPUT MAIL');
        form.name.focus();

    } else if (form.mail.value == '') {
        alert('INPUT MAIL');
        form.mail.focus();

    } else if (form.phone.value == '') {
        alert('INPUT PHONE');
        form.phone.focus();

    } else {
        form.submit();

    }

}