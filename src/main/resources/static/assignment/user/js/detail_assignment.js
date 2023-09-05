function uploadFile() {
    console.log('uploadFile() CALLED!!');

    let form = document.detail_assignment;
    if (form.file.value == '') {
        alert('INPUT FILE');
        form.file.focus();

    } else {
        form.submit();

    }

}