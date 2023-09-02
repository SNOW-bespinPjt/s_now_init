function getAssignment(no) {
    console.log('getAssignment() CALLED!!');
    console.log('getAssignment() : ', no);

    $.ajax({
        url: '/admin/assignment/get_assignment?no=' + no,
        type: 'GET',
        cache: false, // 이 부분을 추가
        success: function onData (data) {
            console.log(data);

            // 데이터를 뷰에 넣는 예제 (단순 예시)
            $('#assignmentDetails [name="title"]').text(data.adminAssignmentDto.title);
            $('#assignmentDetails [name="body"]').text(data.adminAssignmentDto.body);

            location.href='/admin/assignment/get_assignment';
        },
        error: function onError(error) {
            console.error(error);

            location.href='/admin/assignment/list';
        }
    });
}


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
