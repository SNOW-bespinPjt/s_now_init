package com.btc.snow.user.attendance;


import com.btc.snow.include.SubmitDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserAttendanceMapper {
    // 큐알체크 확인
    public void test(String t_content);

    void testInsert(String hi);

    int qrCheckConfrim(Map<Object, Object> map);

    UserAttendanceDto selectAttendenceStatus(Map<Object, Object> map);

    int isValidStatus(Map<Object, Object> map);

    List<UserAttendanceDto> selectAllUserforAttendence(Map<String, Object> map);

    UserAttendanceDto selectValidAttDto(Map<String, Object> map);

    void updateUstatus();

    void updateAstatusForLT();

    List<UserAttendanceDto> selectAbsentAttendence(String id);

    List<UserAttendanceDto> selectACKAttendence(String id);

    List<UserAttendanceDto> selectTardyAttendence(String id);


    int updateDocumentToSubmit(SubmitDto submitDto);

    int selectUpdateStatus(String id);


    int updateAttendenceUstatus(SubmitDto submitDto);

    int getTotalCnt(String uId);
}
