package com.btc.snow.user.meeting;

import com.btc.snow.user.meeting.meal.UserMealDto;
import com.btc.snow.user.meeting.study.UserStudyDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@Service
public class UserMeetingService {

    @Autowired
    IUserMeetingMapper iUserMeetingMapper;

    public Map<String, Object> studyList() {
        log.info("studyList()");

        Map<String, Object> map = new HashMap<>();

        List<UserStudyDto> userStudyDtos = iUserMeetingMapper.selectStudy();

        map.put("userStudyDtos", userStudyDtos);

        return map;
    }

    public Map<String, Object> mealList() {
        log.info("mealList()");

        Map<String, Object> map = new HashMap<>();

        List<UserMealDto> userMealDtos = iUserMeetingMapper.selectMeal();

        map.put("userMealDtos", userMealDtos);

        return map;
    }

    public Map<String, Object> mainStudy() {
        log.info("mainStudy()");

        Map<String, Object> map = new HashMap<>();

        List<UserStudyDto> userStudyDtos = iUserMeetingMapper.mainStudy();

        map.put("userStudyDtos", userStudyDtos);

        return map;
    }
}
