function modifyAssignmentForm() {
    console.log('modifyAssignmentForm() CALLED!!');

    let form = document.assignment_modify_form;
    if (form.title.value == '') {
        alert('INPUT TITLE');
        form.title.focus();

    } else if (form.body.value == '') {
        alert('INPUT BODY');
        form.body.focus();

    } else {
        form.submit();

    }

}