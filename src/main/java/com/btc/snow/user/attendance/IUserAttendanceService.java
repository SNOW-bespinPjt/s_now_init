package com.btc.snow.user.attendance;

import com.google.zxing.WriterException;

public interface IUserAttendanceService {
    public void qrCreate() throws WriterException;

    public  Object qrChackConfirm(int u_m_no);
}
