package com.btc.snow;


import com.btc.snow.user.attendance.UserAttendanceService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
@Log4j2
public class HomeController {

    @Autowired
    UserAttendanceService userAttendanceService;

    @GetMapping(value = {"", "/"})
    public String home() {
        log.info("HomeController home()");

        //분기 포인트 로그인!
//userAttendanceService.selectUserforAttendence();


        String nextPage = "home";

        return nextPage;
    }


}
