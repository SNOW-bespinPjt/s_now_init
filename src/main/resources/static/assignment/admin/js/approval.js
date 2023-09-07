// approval.js
// <script src="https://code.jquery.com/jquery-3.7.0.js" integrity="sha256-JlqSTELeR4TLqP0OG9dxM7yDPqX1ox/HfgiSLBj8+kM=" crossOrigin="anonymous"></script>
function approveAdmin(no) {
    console.log('approveAdmin() CALLED!!');
    console.log('approveAdmin() : ', no);

    $.ajax({
        url: '/admin/member/set_admin_approval?no=' + no,
        type: 'GET',
        success: function onData(data) {
            console.log(data);

            alert('승인이 완료되었습니다');
            location.href='/admin/member/list_admins';
        },
        error: function onError(error) {
            console.error(error);

            alert('승인에 실패하였습니다');
        }
    });
}

