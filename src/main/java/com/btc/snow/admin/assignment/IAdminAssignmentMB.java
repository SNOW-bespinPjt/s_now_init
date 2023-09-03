package com.btc.snow.admin.assignment;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface IAdminAssignmentMB {

    // 과제 리스트
    List<AdminAssignmentDto> selectAssignments();

    // 과제 등록
    int insertAssignment(AdminAssignmentDto adminAssignmentDto);

    // 과제 활성화
    int updateAdminActive(int no);

    int updateAdminInactive(int no);

    // 활성화 번호 찾기
    AdminAssignmentDto selectActivationNum(int no);

    // 과제 상세페이지
    AdminAssignmentDto selectAssignment(int no);

    // 과제 삭제
    int deleteAssignment(int no);

    // 과제 수정
    int updateAssignment(AdminAssignmentDto adminAssignmentDto);

    // 과제 제출 학생 리스트
    List<AdminAssignmentDto> selectSubmitUser(int no, int groupId);
}
