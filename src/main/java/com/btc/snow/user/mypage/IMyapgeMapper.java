package com.btc.snow.user.mypage;


import com.btc.snow.include.StudyPromiseDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IMyapgeMapper {

    List<StudyPromiseDto> selectSceduleByUser(String id);

    int updateStatus(int no);
}
