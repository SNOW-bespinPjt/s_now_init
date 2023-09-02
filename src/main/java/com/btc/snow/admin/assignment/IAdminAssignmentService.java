package com.btc.snow.admin.assignment;

import jakarta.servlet.http.HttpSession;

import java.util.List;

public interface IAdminAssignmentService {

    // 과제 리스트
    List<AdminAssignmentDto> listupAssignment();

    // 과제 등록
    int RegistrationConfirm(AdminAssignmentDto adminAssignmentDto);

    // 과제 활성화 
    int SetAdminActive(int no);

    // 과제 상세페이지
    AdminAssignmentDto getAssignment(int no);

    // 과제 수정
    int modifyAssignmentConfirm(AdminAssignmentDto adminAssignmentDto);

    // 과제 삭제
    int deleteAssignmentConfirm(int no);
}
