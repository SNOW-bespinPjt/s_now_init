function memberDeleteForm() {
    console.log('memberDeleteForm() CALLED!!');

    let form = document.member_delete_form;
    if (form.pw.value == '') {
        alert('INPUT PW');
        form.pw.focus();

    } else {
        form.submit();

    }

}