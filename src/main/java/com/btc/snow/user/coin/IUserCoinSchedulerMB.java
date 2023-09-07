package com.btc.snow.user.coin;

import com.btc.snow.user.assignment.UserAssignmentDto;
import com.btc.snow.user.attendance.UserAttendanceDto;
import com.btc.snow.user.member.UserMemberDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IUserCoinSchedulerMB {

    List<UserMemberDto> selectAllUser();

    List<UserAttendanceDto> coinByAttendance(String id);

    int coinByStudy(String id);

    List<UserAssignmentDto> coinByAssignment(int user_no);

    int selectCoin(String id);

    int updateCoin(String id, int totalCoin);
}
