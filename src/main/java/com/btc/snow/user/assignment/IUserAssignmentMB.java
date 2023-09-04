package com.btc.snow.user.assignment;

import com.btc.snow.admin.assignment.AdminAssignmentDto;
import com.btc.snow.user.member.UserMemberDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IUserAssignmentMB {

    // 과제 리스트
    List<UserAssignmentDto> selectAssignments();

    // 모든 사용자의 정보 가져오기
    List<UserMemberDto> getAllUsers();

    // 과제 등록
    int insertAssignment(UserAssignmentDto userAssignmentDto);
    int submitAssignment(UserAssignmentDto userAssignmentDto);

    // 과제 상세페이지
    AdminAssignmentDto selectDetail(int no);
    UserAssignmentDto selectAssignmentInfo(int user_no, int no);

    // 과제 수정
    int updateAssignment(UserAssignmentDto userAssignmentDto);

    // 과제 삭제
    int deleteAssignment(int no);


}
