function submitAssignment(no) {
    console.log('submitAssignment() CALLED!!');
    console.log('submitAssignment() : ', no);

    $.ajax({
        url: '/user/assignment/set_admin_active?no=' + no,
        type: 'GET',
        success: function onData(data) {
            console.log(data);

            alert('과제가 상태가 변경 되었습니다');

            location.href = '/admin/assignment/';
        },
        error: function onError(error) {
            console.error(error);

            alert('과제 상태 변경에 실패하였습니다');
        }
    });
}

function modifyAssignment(no) {
    console.log('modifyAssignment() CALLED!!');
    console.log('modifyAssignment() : ', no);

    $.ajax({
        url: '/user/assignment/set_admin_active?no=' + no,
        type: 'GET',
        success: function onData(data) {
            console.log(data);

            alert('과제가 상태가 변경 되었습니다');

            location.href = '/admin/assignment/';
        },
        error: function onError(error) {
            console.error(error);

            alert('과제 상태 변경에 실패하였습니다');
        }
    });
}
