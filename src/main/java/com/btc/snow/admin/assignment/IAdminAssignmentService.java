package com.btc.snow.admin.assignment;

import jakarta.servlet.http.HttpSession;

import java.util.List;

public interface IAdminAssignmentService {

    // 과제 리스트
    List<AdminAssignmentDto> listupAssignment();

    int RegistrationConfirm(AdminAssignmentDto adminAssignmentDto);
}
