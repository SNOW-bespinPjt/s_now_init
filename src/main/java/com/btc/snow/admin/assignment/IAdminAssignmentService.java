package com.btc.snow.admin.assignment;

import com.btc.snow.user.assignment.UserAssignmentDto;
import com.btc.snow.user.member.UserMemberDto;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public interface IAdminAssignmentService {

    // 과제 리스트
    List<AdminAssignmentDto> listAssignment();

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

    // 과제 제출 학생 리스트
    List<UserMemberDto> getUserList();
    List<UserAssignmentDto> checkAssignmentList(int no);

}
