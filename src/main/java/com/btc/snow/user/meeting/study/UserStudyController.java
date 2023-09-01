package com.btc.snow.user.meeting.study;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Log4j2
@Controller
@RequestMapping("/user/study")
public class UserStudyController {

    @Autowired
    UserStudyService userStudyService;


    @GetMapping("/write_study")
    @ResponseBody
    public Object writeStudy() {
        log.info("writeStudy()");

        String nextPage = "";

//        userMeetingService.

        return nextPage;
    }


}
