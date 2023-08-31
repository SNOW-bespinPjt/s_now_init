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
}
