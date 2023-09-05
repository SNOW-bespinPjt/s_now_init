package com.btc.snow.user.meeting.meal;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class UserMealService implements IUserMealService {

    @Autowired
    IUserMealMapper iUserMealMapper;

    public int writeMeal(UserMealDto userMealDto) {
        log.info("writeMeal()");

        int result = iUserMealMapper.insertMeal(userMealDto);

        if (result <= 0) {
            log.info("writeMeal() FAIL");

        } else {
            log.info("writeMeal() SUCCESS");

        }
        return result;
    }

    public UserMealDto mealDetail(int no) {
        log.info("mealDetail()");

        return iUserMealMapper.mealDetail(no);
    }
}

