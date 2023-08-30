package com.btc.snow.user.attendance;

import com.btc.snow.user.member.UserMemberDto;
import com.google.zxing.WriterException;

public interface IUserAttendanceService {
    public Object qrCreate(UserMemberDto userMemberDto) throws WriterException;

    public Object qrChackConfirm(int u_m_no);

//    public Object selectUserforAttendence();
}
