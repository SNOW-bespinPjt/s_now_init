function openAssignmentModal(assignmentNo) {

    // 모달 열기
    document.getElementById('assignmentModal').style.display = 'block';
}

function closeAssignmentModal() {
    // 모달 닫기
    document.getElementById('assignmentModal').style.display = 'none';
}

window.onclick = function (event) {
    if (event.target == document.getElementById('assignmentModal')) {
        closeAssignmentModal();
    }
}
