package com.btc.snow.user.assignment;

import java.util.List;

public interface IUserAssignmentService {

    // 과제 리스트(과제 페이지)
    List<UserAssignmentDto> listAssignment();

    // 과제 등록
    int RegistrationConfirm(UserAssignmentDto userAssignmentDto);

    // 과제 상세페이지
    Integer getIsSubmit(int no, int user_no);
    UserAssignmentDto getAssignment(int no);

    // 과제 수정
    int modifyAssignmentConfirm(UserAssignmentDto userAssignmentDto);

    // 과제 삭제
    int deleteAssignmentConfirm(int no);
    ;
}
