package com.btc.snow.user.meeting.meal;

import java.util.Map;

public interface IUserMealService {

    public int writeMeal(UserMealDto userMealDto);

    public UserMealDto mealDetail(int no);

    public void updateHit(int no);

    public Map<String, Object> mealList();
}
