package com.btc.snow.user.meeting.meal;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IUserMealMapper {
    int insertMeal(UserMealDto userMealDto);
}
