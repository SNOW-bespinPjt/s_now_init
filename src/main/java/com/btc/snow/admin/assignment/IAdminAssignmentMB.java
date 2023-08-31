package com.btc.snow.admin.assignment;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IAdminAssignmentMB {

    // 과제 리스트
    List<AdminAssignmentDto> selectAssignments();

    // 과제 등록
    int insertAssignment(AdminAssignmentDto adminAssignmentDto);
}
