package com.btc.snow.user.meeting;

import com.btc.snow.user.meeting.meal.UserMealDto;
import com.btc.snow.user.meeting.study.UserStudyDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface IUserMeetingMapper {

    public Map<String, Object> studyList(String id);

    List<UserStudyDto> selectStudy();

    List<UserMealDto> selectMeal();

    List<UserStudyDto> mainStudy();
}
