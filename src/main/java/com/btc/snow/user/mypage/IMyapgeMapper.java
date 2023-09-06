package com.btc.snow.user.mypage;


import com.btc.snow.include.StudyPromiseDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IMyapgeMapper {

    StudyPromiseDto selectSceduleByUser(String id);
}
