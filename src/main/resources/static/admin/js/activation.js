function activeAssignment(no) {
    console.log('activeAssignment() CALLED!!');
    console.log('activeAssignment() : ', no);

    $.ajax({
        url: '/admin/assignment/set_admin_active?no=' + no,
        type: 'GET',
        success: function onData(data) {
            console.log(data);

            alert('과제가 상태가 변경 되었습니다');

            location.href='/admin/assignment/list';
        },
        error: function onError(error) {
            console.error(error);

            alert('과제 상태 변경에 실패하였습니다');
        }
    });
}
