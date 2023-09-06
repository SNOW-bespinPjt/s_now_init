package com.btc.snow.user.assignment;

import com.btc.snow.admin.assignment.AdminAssignmentDto;
import com.btc.snow.admin.assignment.StatisticsDto;
import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.Map;

public interface IUserAssignmentService {

    // 과제 리스트(과제 페이지)
    List<UserAssignmentDto> listAssignment(HttpSession session);

    // 과제 등록
    int RegistrationConfirm(UserAssignmentDto userAssignmentDto);

    // 과제 상세페이지
    AdminAssignmentDto getDetail(int no);
    UserAssignmentDto getUserAssignmentInfo(int user_id, int no);

    // 과제 수정
    int modifyAssignmentConfirm(UserAssignmentDto userAssignmentDto);

    // 과제 삭제
    int deleteAssignmentConfirm(int no);

    // 과제 점수
    Map<String, Object> myPoint(int user_no);

    // 나의 과제
    List<UserAssignmentDto> myAssignment(int user_no);
}
