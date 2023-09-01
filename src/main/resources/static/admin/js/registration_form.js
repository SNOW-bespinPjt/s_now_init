function createAssignmentForm() {
    console.log('createAssignmentForm() CALLED!!');

    let form = document.registration_form;
    if (form.title.value == '') {
        alert('INPUT TITLE');
        form.title.focus();

    } else if (form.body.value == '') {
        alert('INPUT BODY');
        form.body.focus();

    } else if (form.file_admin.value == '') {
        alert('INPUT FILE');
        form.file_admin.focus();

    } else {
        form.submit();

    }

}