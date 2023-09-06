package com.btc.snow.admin.attendence;

import java.util.Map;

public interface IAdminAttendenceService {

    Map<String, Object> selectSubmitDto(int no, int pageNum, int amount);

    int updateStatusForSubmit(int no);

    int updateStatusForSubmitToCancle(int no);
}
