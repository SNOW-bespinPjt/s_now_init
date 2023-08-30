package com.btc.snow.user.attendance;


import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserAttendanceMapper {
    // 큐알체크 확인
    public  int qrCheckConfrim(int u_m_no);

    public void test(String t_content);

    void testInsert(String hi);

}
