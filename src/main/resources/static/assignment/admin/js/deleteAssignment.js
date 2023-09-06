// JavaScript 파일 (deleteAssignment.js)
function deleteAssignment(no) {
    if (confirm("과제를 삭제하시겠습니까?")) {
        console.log("deleteAssignment() CALLED!!")
        console.log("deleteAssignment(no) ", no)

        // 확인을 누르면 컨트롤러로 이동
        window.location.href = `/admin/assignment/delete_assignment_confirm?no=${no}`;
    }
}
