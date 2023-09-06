package com.btc.snow.user.meeting.meal;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public void updateHit(int no) {
        log.info("updateHit");

        iUserMealMapper.updateHit(no);
    }

    public Map<String, Object> mealList() {
        log.info("mealList()");

        Map<String, Object> map = new HashMap<>();

        List<UserMealDto> userMealDtos = iUserMealMapper.selectMeal();

        map.put("userMealDtos", userMealDtos);

        return map;
    }
}

