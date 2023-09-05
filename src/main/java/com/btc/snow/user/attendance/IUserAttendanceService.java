package com.btc.snow.user.attendance;

import com.btc.snow.include.SubmitDto;
import com.btc.snow.include.page.PageMakerDto;
import com.btc.snow.user.member.UserMemberDto;
import com.google.zxing.WriterException;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public interface IUserAttendanceService {
    public Object qrCreate(UserMemberDto userMemberDto) throws WriterException;

    public Object qrChackConfirm(String u_id);


    public Object selectUserforAttendence(String u_id);

    public Object selectAllUserforAttendence(String u_id);

    public UserAttendanceDto selectValidSubmitAttendence(HttpSession session);

    List<UserAttendanceDto> selectAbsentAttendence(String session);

    List<UserAttendanceDto> selectACKAttendence(String id);

    List<UserAttendanceDto> selectTardyAttendence(String id);

    Object submitDocument(SubmitDto submitDto);

    PageMakerDto listAttendence(String uId, int pageNum, int amount);
}
