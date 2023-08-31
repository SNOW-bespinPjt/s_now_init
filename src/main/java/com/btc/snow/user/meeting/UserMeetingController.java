package com.btc.snow.user.meeting;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Log4j2
@RequestMapping("/user/meeting")
public class UserMeetingController {

    @Autowired
    UserMeetingService userMeetingService;

    @GetMapping("/list")
    public String list() {
        log.info("list()");

        String nextPage = "user/meeting/meeting_list";

        return nextPage;
    }

    @GetMapping("/meeting_form")
    public String meetingForm() {
        log.info("meetingForm()");

        String nextPage = "user/meeting/meeting_form";

        return nextPage;
    }

//    @GetMapping("/study_form")
//    @ResponseBody
//    public Object study_form() {
//        log.info("study_form()");
//
//        userMeetingService.
//
//        return nextPage;
//    }
}
