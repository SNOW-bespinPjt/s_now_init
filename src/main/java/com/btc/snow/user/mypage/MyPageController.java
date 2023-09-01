package com.btc.snow.user.mypage;


import com.btc.snow.user.attendance.UserAttendanceDto;
import com.btc.snow.user.attendance.UserAttendanceService;
import com.btc.snow.user.member.UserMemberDto;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/mypage")
@Log4j2
public class MyPageController {

    @Autowired
    UserAttendanceService userAttendanceService;


    @GetMapping(value = {"", "/"})
    public Object home(HttpSession httpSession) {
        HttpHeaders headers = new HttpHeaders();
        log.info("home()!!!");
        UserMemberDto userMemberDto = (UserMemberDto) httpSession.getAttribute("loginedUserDto");

        String u_id = userMemberDto.getId();
        ModelAndView modelAndView = new ModelAndView();
        List<UserAttendanceDto> userAttendanceDtos = new ArrayList<>();

        userAttendanceDtos = userAttendanceService.selectAllUserforAttendence(u_id);


        modelAndView.addObject("userAttendanceDtos", userAttendanceDtos);
        modelAndView.setViewName("/user/mypage/home");
        
        return modelAndView;
    }


}
