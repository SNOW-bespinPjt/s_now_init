package com.btc.snow.user.meeting.meal;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IUserMealMapper {
    int insertMeal(UserMealDto userMealDto);

    UserMealDto mealDetail(int no);

    void updateHit(int no);

    List<UserMealDto> selectMeal();
}
