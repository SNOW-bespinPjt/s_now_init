function uploadFile() {
    console.log('uploadFile() CALLED!!');

    let form = document.detail_assignment;
    if (form.file_user.value == '') {
        alert('INPUT FILE');
        form.file_user.focus();

    } else {
        form.submit();

    }

}