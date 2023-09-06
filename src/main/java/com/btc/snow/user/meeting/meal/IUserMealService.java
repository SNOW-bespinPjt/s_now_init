package com.btc.snow.user.meeting.meal;

public interface IUserMealService {

    public int writeMeal(UserMealDto userMealDto);

    public UserMealDto mealDetail(int no);

    public void updateHit(int no);
}
