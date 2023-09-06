package com.btc.snow.user.meeting.meal;

import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface IUserMealMapper {
    int insertMeal(UserMealDto userMealDto);

    UserMealDto mealDetail(int no);

    void updateHit(int no);

    List<UserMealDto> selectMeal(Map<String, Object> map);

    void updateStatus();

    int listCount();

    int mealAttend(HashMap<String, Object> map);

    int removeButton(HashMap<String, Object> map);
}
