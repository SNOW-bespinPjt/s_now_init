package com.btc.snow.admin.attendence;


import com.btc.snow.include.SubmitDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface IAdminAttendenceMapper {
    List<SubmitDto> selectSubmitInfo(Map<String, Object> map);

    int getTotalCntOfSubmit();

    int updateStatusForSubmit(int no);

    int cancelSubmit(int no);
}
