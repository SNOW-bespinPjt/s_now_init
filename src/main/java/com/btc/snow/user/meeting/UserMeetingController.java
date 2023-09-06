package com.btc.snow.user.meeting;

import com.btc.snow.user.meeting.meal.UserMealDto;
import com.btc.snow.user.meeting.study.UserStudyDto;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@Log4j2
@RequestMapping("/user/meeting")
public class UserMeetingController {

    @Autowired
    UserMeetingService userMeetingService;

    @GetMapping("/list")
    public String list(Model model, HttpSession session) {
        log.info("list()");

        String nextPage = "user/meeting/meeting_list";

        Map<String, Object> studyMap = userMeetingService.studyList();
        Map<String, Object> mealMap = userMeetingService.mealList();

        List<UserStudyDto> userStudyDtos = (List<UserStudyDto>) studyMap.get("userStudyDtos");
        List<UserMealDto> userMealDtos = (List<UserMealDto>) mealMap.get("userMealDtos");

        model.addAttribute("userStudyDtos", userStudyDtos);
        model.addAttribute("userMealDtos", userMealDtos);

        return nextPage;
    }

    @GetMapping("/meeting_form")
    public String meetingForm() {
        log.info("meetingForm()");

        String nextPage = "user/meeting/meeting_form";

        return nextPage;
    }
}
